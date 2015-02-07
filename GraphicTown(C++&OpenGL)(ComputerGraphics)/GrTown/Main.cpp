/// GraphicsTown2006 - CS559 Sample Code
// written by Michael Gleicher - originally written Fall, 2000
//
// updated Fall 2006 - more testing code
// updated Fall 2008 - switched some internal stuff (no more linked lists)

#include "GrTown_PCH.H"
#include "GraphicsTownUI.H"
#include "FlyCamera.H"
#include "GrWorld.H"
#include "DrawUtils.H"
#include "Examples/Objects.H"
#include "Examples/SimpleBehaviors.H"
#include "Examples/Cars.H"

// for setting up shader paths and textures
#include "Utilities/ShaderTools.H"
#include "Utilities/Texture.H"


// Example code (objects and behaviors)
#include "Examples/Suburbs.H"

#include "Roads/Roads.H"
#include "Roads/Drive.H"


// define this to get 2 cars that always turn
// #define TESTCARS

const int numCars = 100;
const int nGrids = 5;

int main(int /*argc*/, char** /*argv*/)
{ 

  // put in some texture paths - look all over the place
	texturePaths.push_back(".");
	texturePaths.push_back("..");
	texturePaths.push_back("Textures/signs");
	texturePaths.push_back("../Textures/signs");
	texturePaths.push_back("Textures/Textures");
	texturePaths.push_back("../Textures/Textures");
	texturePaths.push_back("Textures/Objects");
	texturePaths.push_back("../Textures/Objects");

	shaderPaths.push_back("Shaders");
	shaderPaths.push_back("../Shaders");

  // add some more stuff
  GrObject* o1 = new Fancyhotel;
  o1->name = "Fancyhotel";
  o1->interesting = true;
  o1->laX = -40; o1->laY = 80; o1->laZ = 0;
  o1->lfX = -110; o1->lfY = 150; o1->lfZ = 100;
  add(o1,-100,0,100,pi/2.f);


  ////////////////////////////////////////////////////////////////////////
  // now to make a real town!
  int r,c;
  // make a 5x5 grid of town blocks - 5 houses per
  for( r=0; r<5; r++) {
	  for( c=0; c<5; c++) {
		  if (c == 3 && r==1){
			  GrObject* g = new SimpleSubdivision(5, false, true);
			  add(g, static_cast<float>(r * 530), 0, static_cast<float>(c * 230));
			  g->interesting = 1;
			  g->name = "Flag";
			  g->laX = -40; g->laY = 480; g->laZ = 0;
			  g->lfX = -110; g->lfY = 500; g->lfZ = 100;
		  }
		  else if (c == 2 && r==1){
			  GrObject* g = new SimpleSubdivision(5, true, false);
			  add(g, static_cast<float>(r * 530), 0, static_cast<float>(c * 230));
			  g->interesting = 1;
			  g->name = "Helicopter";
			  g->laX = 40; g->laY = 300; g->laZ = 0;
			  g->lfX = -190; g->lfY = 400; g->lfZ = 120;
		  }
		  else{
			  add(new SimpleSubdivision(5, false,false), static_cast<float>(r * 530), 0, static_cast<float>(c * 230));
		  }
		  add(new StraightRoad(static_cast<float>(r*530),static_cast<float>(c*230),static_cast<float>(r*530+500),static_cast<float>(c*230),true));
	  }
  }
  // make cross streets
  for(int r=0; r<=5; r++) {
	  for(c=0; c<4; c++) {
		  add(new StraightRoad(static_cast<float>(r*530 - 15), static_cast<float>(c*230 + 15), static_cast<float>(r*530 - 15), static_cast<float>(c*230+215),false));
	  }
  }

  // make intersections
  // make an intersection intersesting so we can look at it
  for(int r=0; r<=5; r++) {
	  for(c=0; c<5; c++) {
		  GrObject* g = new Intersection(static_cast<float>(r*530-15), static_cast<float>(c*230));
		  if ( (r==2) && (c==3) ) {
			  g->interesting = 1;
			  g->name = "Intersection(2,3)";
			  g->laX = static_cast<float>(r*530-15);    g->laY = 0;    g->laZ = static_cast<float>(c*230);
			  g->lfX = static_cast<float>(r*530+25);   g->lfY = 100;   g->lfZ = static_cast<float>(c*230+50);
		  }
		  add(g);
	  }
  }

#ifndef TESTCARS
  // add some cars
  for(int r=0; r<50; r++) {
	Car* c;
	switch(rand() % 3) {
	  case 0: c = new Van(rand()); break;
	  case 1: c = new SUV(rand()); break;
	  case 2: c = new HatchBack(rand()); break;
	}
	add(c);
    new RandomDrive(c,theRoads[rand() % theRoads.size()],.2f,rand() % 2);
  }
#endif

#ifdef TESTCARS
  // two cars - one always turns right, one always turns left
  Car* c1 = new HatchBack(1);
  Car* c2 = new HatchBack(2);
	add(c1);
	add(c2);
  new RandomDrive(c1,theRoads[7],.5,0,-1);
  new RandomDrive(c2,theRoads[8],.5,0,1);

#endif


	// a race track
    Road* t = new RoundRoad(-250,250,100);
	add(t);
	t->name = "DuckInThePool";
	t->interesting = true;
	t->laX = -50; t->laY = 35;   t->laZ = 45;
	t->lfX = -30; t->lfY = 40; t->lfZ = 20;
	// make cars go around the track
	Car* h = new HatchBack(1);
	h->name="Race1";		// warning! we can only do this since we don't delete
	add(h);
	new SimpleDrive(h,t,0,0);
	h = new HatchBack(3);
	h->name="Race2";		// warning! we can only do this since we don't delete
	add(h);
	Drive* d = new SimpleDrive(h,t,0,1);
	d->speed *= 2;


  // *****************************************************************
  // now make a UI
  FlyCamera* fc = new FlyCamera;
  Map* m = new Map;

  add(fc);
  add(m);

  GraphicsTownUI grTown;
  grTown.make_window();
  grTown.townView->defaultCamera = fc;
  grTown.window->show();

  Fl::run();
  return 1;
}

// $Header: /p/course/cs559-gleicher/private/CVS/GrTown/Main.cpp,v 1.8 2009/11/10 22:40:03 gleicher Exp $
