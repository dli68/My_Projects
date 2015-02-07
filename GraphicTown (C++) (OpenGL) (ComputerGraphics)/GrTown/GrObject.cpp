/// GraphicsTown2006 - CS559 Sample Code
// written by Michael Gleicher - originally written Fall, 2000
// updated Fall, 2005
// updated Fall, 2006
//
#include "GrTown_PCH.H"

#include "GrObject.H"
#include "FlyCamera.H"
#include "Behavior.H"
#include "GrWorld.H"
#include "DrawingState.H"

using std::vector;

int noNameCtr=1;
GrObject::GrObject(char* nb, int& nn, char* xn) :
  ridable(0), interesting(0),
  parent(0)
{
	// since its easiest to just use C sprintf to build the name
	char buf[255];
	if (!nb)
		_snprintf(buf,255,"NoName-%d%c",nn++,0);
	else if (xn)
		_snprintf(buf,255,"%s-%s-%d%c",nb,xn,nn++,0);
	else
		_snprintf(buf,255,"%s-%d%c",nb,nn++,0);
	name = buf;

	idMatrix(transform);
	rideAbove = 0;
}
GrObject::~GrObject()
{
}

// null functions - not abstract since not all objects will override
// them
void GrObject::draw(DrawingState* drst)
{
}
void GrObject::drawAfter(DrawingState* drst)
{
}


// for most objects, the way to use it as a camera is to take its
// transformation and invert it - potentially using "rideabove"
void GrObject::getCamera(Matrix camera)
{
	Matrix tmpTransform;
	Matrix t1, t2, t3;
	copyMatrix(transform, tmpTransform);


	rotMatrix(t1, 'Y', -direction);
	multMatrix(t1, tmpTransform, t3);//t3 is result
	//copyMatrix(t2, tmpTransform);
	rotMatrix(t2, 'X', pitch);
	multMatrix(t2, t3, tmpTransform);


	copyMatrix(tmpTransform, camera); // copy transform to camera
	invertMatrix(camera); // what's this for?
	camera[3][1] = camera[3][1] - rideAbove;
}


// the user interface for the object may or may not do anything
bool GrObject::uiStep()
{
	return false;
}
int GrObject::handle(int e)
{
	static int stX = 0, stY = 0;
	static int lX = 0, lY = 0;
	static int buttonDown = 0;
	switch (e)
	{
	case FL_KEYBOARD: // why this doesn't work?
#ifdef DEBUG
		std::cout << "GrObject::handle keyboard action!\n";
#endif
		switch (Fl::event_key())
		{
		case 'a':
			direction += 1.0f;
			break;
		};

	case FL_PUSH:
		lX = stX = Fl::event_x();
		lY = stY = Fl::event_y();
		buttonDown = Fl::event_button();
#ifdef DEBUG
		//std::cout << "GrObject::handle mouse clicked!\n";
#endif
		return 1;
	case FL_RELEASE:
		buttonDown = 0;
		return 1;


	case FL_DRAG:
		int mX = Fl::event_x();
		int mY = Fl::event_y();
		if (buttonDown) {
			switch (Fl::event_button()) {
			case 1:
			case 3:
#ifdef DEBUG
				//std::cout << "GrObject::handle dragging detected!\n";
#endif
				float dx = static_cast<float>(mX - lX);
				float dy = static_cast<float>(mY - lY);
				if (fabs(dx) > fabs(dy)) {
					direction -= 0.01f * dx;
				}
				else
				{
					pitch += 0.01f * dy;
				}
				break;
			}
		}


		buttonDown = Fl::event_button();
		lX = mX; lY = mY;
		return 1;
	};
	return 0;
}



// note - we might not really get to the time in one shot
// so let it take up to 10 tries
void GrObject::simulateUntil(unsigned long t)
{
	for(vector<Behavior*>::iterator i = behaviors.begin(); i != behaviors.end(); ++i) {
	  Behavior *b = *i;
	  int ct=0;
	  while ((b->lastV < t) && (ct<10)) {
		b->simulateUntil(t);
		ct++;
	  }
	  /* if (ct>1) 
		  printf("needed %d simulation substeps\n",ct); */
	  // this is a common bug - you'll get caught here if you don't update lastV
	  if (ct>8)
		  printf("Warning! stuck behavior!\n");
  }
}

GrObject* GrObject::add(GrObject* g, float x, float y, float z, float ry)
{
	children.push_back(g);
	g->parent = this;
	Matrix t1,t2;
	rotMatrix(t1,'Y',ry);
	multMatrix(t1,g->transform,t2);
	copyMatrix(t2,g->transform);
	g->transform[3][0] += x;
	g->transform[3][1] += y;
	g->transform[3][2] += z;
	return g;
}


GrObject* add(GrObject* g, float x, float y, float z, float ry)
{
	theObjects.push_back(g);
	Matrix t1,t2;
	rotMatrix(t1,'Y',ry);
	multMatrix(t1,g->transform,t2);
	copyMatrix(t2,g->transform);
	g->transform[3][0] += x;
	g->transform[3][1] += y;
	g->transform[3][2] += z;
	return g;
}

// draw a list of objects
void drawObList(vector<GrObject*>& objs, DrawingState* drst)
{
  glPushMatrix();
  for(vector<GrObject*>::iterator i = objs.begin(); i != objs.end(); ++i) {
	  GrObject* g = (*i);
	  if (g != drst->camera) {
		  glPushMatrix();
		  glMultMatrixf(&g->transform[0][0]);
		  g->draw(drst);
		  glBindTexture(GL_TEXTURE_2D,0);
		  if (g->children.size()) {
			  drawObList(g->children,drst);
		  }
		  glPopMatrix();
	  }
  }
  glPopMatrix();
}
void drawAfterObList(vector<GrObject*>& objs, DrawingState* drst)
{
  glPushMatrix();
  for(std::vector<GrObject*>::iterator i = objs.begin(); i != objs.end(); ++i) {
	  GrObject* g = (*i);
	  if (g != drst->camera) {
		  glPushMatrix();
		  glMultMatrixf(&g->transform[0][0]);
		  g->drawAfter(drst);
		  glBindTexture(GL_TEXTURE_2D,0);
		  if (g->children.size()) {
			  drawAfterObList(g->children,drst);
		  }
		  glPopMatrix();
	  }
  }
  glPopMatrix();
}


// $Header: /p/course/cs559-gleicher/private/CVS/GrTown/GrObject.cpp,v 1.4 2008/11/14 19:53:30 gleicher Exp $
