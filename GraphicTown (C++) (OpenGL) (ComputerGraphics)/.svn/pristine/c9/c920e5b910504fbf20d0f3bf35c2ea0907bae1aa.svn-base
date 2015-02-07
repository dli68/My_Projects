/// GraphicsTown2006 - CS559 Sample Code
// written by Michael Gleicher - originally written Fall, 2000
// updated Fall, 2005

#include "GrTown_PCH.H"

#include "TownViewWidget.H"
#include "DrawingState.H"
#include "GrWorld.H"
#include "GraphicsTownUI.H"
#include "GrObject.H"
#include "DrawUtils.H"
#include "Utilities/Texture.H"
#include <time.h>
#include "FlyCamera.H"

#include <iostream>

#include "./stb/stb_image.h"
#include "./Utilities/ShaderTools.h"
#include <assert.h>
#include <math.h>
#include "./Examples/Suburbs.H"


using std::vector;

// at idle time, this gets called
// it must advance time to the current time step by simulating
// all of the objects
// it also gives the camera a chance to do its user interface
void tvIdler(void* v)
{
  TownViewWidget* tv = (TownViewWidget*) v;
  if (tv->getCamera()->uiStep())
	tv->damage(1);

  unsigned long t = clock();
  unsigned long dt = t- tv->lastClock;

  tv->lastClock = t;

  float speedup = static_cast<float>(tv->ui->speedup->value());

  unsigned long ti = static_cast<unsigned long>(static_cast<float>(dt) * speedup);

  tv->time +=  ti;

  if (ti>0)
	  for(vector<GrObject*>::iterator g = theObjects.begin(); g != theObjects.end(); ++g)
		(*g)->simulateUntil(tv->time);
  tv->damage(1);
}


////////////////////////////////////////////////////////////////////////////

TownViewWidget::TownViewWidget(int x, int y, int w, int h, 
							   const char* l)
  : Fl_Gl_Window(x,y,w,h,l),
	time(0),	// start time at the beginning
	lastClock(clock())
{
  // we will probably want them all...
  mode(FL_RGB | FL_DOUBLE | FL_DEPTH | FL_ALPHA /*| FL_STENCIL*/);
  Fl::add_idle(tvIdler,this);
  followCamera = new FollowCam();
  interestingCamera = new InterestingCam();

  // initialize the rain system.
  initRain();
  initSnow();


}
  
unsigned long lastDrawDone = 0;
int rotatespeed;
float windspeed;
float waterwavespeed;
float transparency;

void TownViewWidget::draw()
{
  // figure out how to draw

  DrawingState drst;
  getStateFromUI(&drst);
  glEnable(GL_TEXTURE_2D);

  if (drst.backCull) {
	  glEnable(GL_CULL_FACE);
	  glCullFace(GL_BACK);
  } else
	  glDisable(GL_CULL_FACE);

  glFrontFace(GL_CCW);

  // set up the camera for drawing!
  glEnable( GL_DEPTH_TEST );

  // we use blending for everything nowadays - there's little cost to having it on
  // NOTE: we avoid Z-writes if alpha is small, so transparent really is transparent
  glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
  glEnable(GL_BLEND);
  glAlphaFunc(GL_GREATER,0.05f);
  glEnable(GL_ALPHA_TEST);

  glMatrixMode(GL_PROJECTION);
  glViewport(0,0,w(),h());
  glLoadIdentity();

  // compute the aspect ratio so we don't distort things
  double aspect = ((double) w()) / ((double) h());
  gluPerspective(drst.fieldOfView, aspect, 1, 60000);

  // put the camera where we want it to be
  glMatrixMode(GL_MODELVIEW);
  glLoadIdentity();
  setupLights(&drst);

  Matrix camera;
  drst.camera->getCamera(camera);
  glMultMatrixf(&camera[0][0]);

  glClearStencil(0);

  // the actual clearing goes on in the sky routine since its the only
  // thing that knows what color to make the sky
  glPushMatrix();
  drawSky(&drst);
  drawEarth(&drst);
  glPopMatrix();

  glColor3f(1.0f,1.0f,1.0f);
  // based on weathermode and particel% change the weather.
  glEnable(GL_LIGHTING);
  prob = ui->particleamount->value();
  if (weathermode == 1){
	  drawRain();
  }
  else if (weathermode == 2){
	  drawSnow();
  }

  //  GrObject* g;
  drawObList(theObjects,&drst);
  drawAfterObList(theObjects, &drst);

  if (lastDrawDone) {
	  double ifr = ((double)CLOCKS_PER_SEC) / (double) (clock()-lastDrawDone+1);
	  ui->rate->value(ifr);
  }
  lastDrawDone = clock();

  // based on HeliRotSpeed change the rotate speed of the helicopters.
  rotatespeed = ui->HeliRotSpeed->value();

  // based on WindSpeed change the rotate speed of the wind.
  windspeed = ui->WindStrength->value();

  // based on WaveSpd change the water wave speed
  waterwavespeed = ui->WaveSpd->value();

  // based on Transparency chagne the transparency of the pool water
  transparency = ui->Transparency->value();
}

void TownViewWidget::getStateFromUI(DrawingState* st)
{
	st->timeOfDay = (int) ui->time->value();
	st->fieldOfView = (float) ui->fov->value();
	st->camera = getCamera();
	st->backCull = ui->cull->value();
	st->drGrTex = ui->lgTex->value();
}

GrObject* TownViewWidget::getCamera()
{
  int p = ui->pickCamera->value();
  if (p) {
	if (ui->follower->value()) {
	  followCamera->following =  (GrObject*) ui->pickCamera->data(p);
	  return followCamera;
	}
	return (GrObject*) ui->pickCamera->data(p);
  } else {
	p = ui->pickInteresting->value();
	if (p) {
	  interestingCamera->focus = (GrObject*) ui->pickInteresting->data(p);
	  return interestingCamera;
	} else
	  return defaultCamera;
  } 
}

int TownViewWidget::handle(int e)
{
	switch(e) {
	case FL_SHOW:
		show();
		return 1;
	case FL_FOCUS:
		return 1;
	case FL_KEYBOARD:
		return 1;
	default:
		int r = getCamera()->handle(e);
		if (r) damage(1);
		return r;
	};
}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//												Particle system for weather					   						//
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

#define MAX_PARTICLES_RAIN 200000
#define MAX_PARTICLES_SNOW 100000
particles par_sys_rain[MAX_PARTICLES_RAIN];
particles par_sys_snow[MAX_PARTICLES_SNOW];
particles *par_sys;

// change weather mode; callback function for UI
void TownViewWidget::weathermodechage(int mode){
	weathermode = mode;
	if (mode == 1){
		par_sys = par_sys_rain;
	}
	else if (mode == 2){
		par_sys = par_sys_snow;
	}
}

// Initialize/Reset Particles - give them their attributes
void TownViewWidget::initParticles(int i, int mode) {

	par_sys[i].alive = true;
	par_sys[i].life = 1.0;
	par_sys[i].fade = float(rand() % 200) / 3000.0f;

	par_sys[i].xpos = (float)(rand() % 4001) - 500;
	par_sys[i].ypos = 700.0;
	par_sys[i].zpos = (float)(rand() % 2001) - 600;

	if (mode == 1){
		par_sys[i].vel = rainvelocity;
	}
	else if (mode == 2){
		par_sys[i].vel = snowvelocity;
		par_sys[i].firstclass = ((rand() % 500) < 250) ? true : false;
	}

	par_sys[i].available = ((rand() % 500) < (500*prob)) ? true : false;

	par_sys[i].gravity = -0.1;
}

void TownViewWidget::initRain(){
	par_sys = par_sys_rain;

	int x, z;
	glShadeModel(GL_SMOOTH);
	glClearColor(0.0, 0.0, 0.0, 0.0);
	glClearDepth(1.0);
	glEnable(GL_DEPTH_TEST);

	// Initialize particles
	for (loop = 0; loop < MAX_PARTICLES_RAIN; loop++) {
		initParticles(loop,1);
	}
}

void TownViewWidget::drawRain() {
	float x, y, z;
	bool avail;
	for (loop = 0; loop < MAX_PARTICLES_RAIN; loop = loop + 1) {
		if (par_sys[loop].alive == true) {
			x = par_sys[loop].xpos;
			y = par_sys[loop].ypos;
			z = par_sys[loop].zpos;
			avail = par_sys[loop].available;
			float windeffect = windspeed;
			// Draw particles
			if (avail){
				glColor3f(0.5, 0.5, 1.0);
				glBegin(GL_LINES);
				glVertex3f(x, y, z);
				glVertex3f(x, y + 0.5, z);
				glEnd();
			}
			// Update values
			// Adjust speed!
			par_sys[loop].ypos += par_sys[loop].vel;
			par_sys[loop].vel += par_sys[loop].gravity;
			par_sys[loop].xpos += windeffect;
			// Decay
			if (y < 500){
				par_sys[loop].life -= par_sys[loop].fade;
			}
			if (par_sys[loop].ypos <= 0) {
				par_sys[loop].life = -1.0;
			}
			//Revive 
			if (par_sys[loop].life < 0.0) {
				initParticles(loop,1);
			}
		}
	}
}

void TownViewWidget::initSnow(){
	par_sys = par_sys_snow;

	int x, z;
	glShadeModel(GL_SMOOTH);
	glClearColor(0.0, 0.0, 0.0, 0.0);
	glClearDepth(1.0);
	glEnable(GL_DEPTH_TEST);

	// Initialize particles
	for (loop = 0; loop < MAX_PARTICLES_SNOW; loop++) {
		initParticles(loop, 2);
	}
}


void TownViewWidget::drawSnow() {
	float x, y, z;
	bool firstclass;
	bool avail;
	glPointSize(2.0);
	glColor3f(1.0, 1.0, 1.0);
	glBegin(GL_POINTS);
	for (loop = 0; loop < MAX_PARTICLES_SNOW; loop = loop + 1) {
		if (par_sys[loop].alive == true) {
			x = par_sys[loop].xpos;
			y = par_sys[loop].ypos;
			z = par_sys[loop].zpos;
			firstclass = par_sys[loop].firstclass;
			avail = par_sys[loop].available;
			float windeffect = windspeed*1.5;
			// Draw particles
			if (avail && (y <= 400 || (y>400 && firstclass))){
				glVertex3f(x, y, z);
			}
			// Update values
			par_sys[loop].ypos += par_sys[loop].vel;
			par_sys[loop].xpos += windeffect;
			// Decay
			par_sys[loop].life -= par_sys[loop].fade;
			if (par_sys[loop].ypos <= 0) {
				par_sys[loop].life = -1.0;
			}
			//Revive 
			if (par_sys[loop].life < 0.0) {
				initParticles(loop,2);
			}
		}
	}
	glEnd();
}







// $Header: /p/course/cs559-gleicher/private/CVS/GrTown/TownViewWidget.cpp,v 1.6 2010/11/17 22:50:23 gleicher Exp $
