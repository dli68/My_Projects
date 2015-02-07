/// GraphicsTown2006 - CS559 Sample Code
// written by Michael Gleicher - originally written Fall, 2000
// updated Fall, 2005
// updated Fall, 2006 - new texture manager, improvements for behaviors

// the simple example objects - not that exciting - yours should be
// better! 

// note - because this is a precompiled header, getting the name the same
// is more important than getting the path right
#include "../GrTown_PCH.H"
#include "../DrawUtils.H"
#include "Utilities/Texture.H"
#include "../MMatrix.H"
#include <glm.hpp>
#include "Suburbs.H"
#include "Utilities/ShaderTools.H"

void Helipcopter::drawTopBlades(boolean wingWaighted) {
	glScaled(2, 2, 2);
	glColor3f(0.4f, 0.2f, 0.0f);
	glTranslated(0, 4, -6);
	glRotated(bladeRotation, 0, 1, 0);
	drawBlad(wingWaighted);
	glRotated(90, 0, 1, 0);
	drawBlad(wingWaighted);
	glRotated(90, 0, 1, 0);
	drawBlad(wingWaighted);
	glRotated(90, 0, 1, 0);
	drawBlad(wingWaighted);
}

void Helipcopter::drawBlad(boolean wingWaighted) {
	double wingDrop = 0;
	double wingPeak = 0.2;
	double wingDropFormula;
	if (wingWaighted) {
		wingDropFormula = 0.002*(float)rotatespeed/8;
	}
	else {
		wingDropFormula = 0;
	}

	// Draw topside of wing
	glPushMatrix();
	glBegin(GL_QUAD_STRIP);
	{
		int i;
		for (i = 0; i<15; i++) {
			wingDrop -= i*wingDropFormula;
			glVertex3d(i, wingDrop + wingPeak, 0);
			glVertex3d(i, wingDrop, .8);
		}
	}
	glEnd();
	glPopMatrix();
	glPushMatrix();
	glTranslated(0, 0, -.8);
	glBegin(GL_QUAD_STRIP);
	{
		wingDrop = 0;
		int i;
		for (i = 0; i<15; i++) {
			wingDrop -= i*wingDropFormula;
			glVertex3d(i, wingDrop, 0);
			glVertex3d(i, wingDrop + wingPeak, .8);
		}
	}
	glEnd();
	glPopMatrix();

	// Draw bottomside of wing
	glPushMatrix();
	glBegin(GL_QUAD_STRIP);
	{
		wingDrop = 0;
		int i;
		for (i = 0; i<15; i++) {
			wingDrop -= i*wingDropFormula;
			glVertex3d(i, wingDrop, .8);
			glVertex3d(i, wingDrop - wingPeak, 0);
		}
	}
	glEnd();
	glPopMatrix();
	glPushMatrix();
	glTranslated(0, 0, -.8);
	glBegin(GL_QUAD_STRIP);
	{
		wingDrop = 0;
		int i;
		for (i = 0; i<15; i++) {
			wingDrop -= i*wingDropFormula;
			glVertex3d(i, wingDrop - wingPeak, .8);
			glVertex3d(i, wingDrop, 0);
		}
	}
	glEnd();
	glPopMatrix();
}
void Helipcopter::drawTale() {
	glColor3d(1, 0, 0);
	GLUquadric *body = gluNewQuadric();
	glShadeModel(GL_SMOOTH);

	// Tale 
	gluCylinder(body, 1.5, .8, 15, 50, 15);
	glTranslated(0, 1, 0);
	glColor3f(0.0f, 0.298f, 0.6f);
	gluCylinder(body, .8, .4, 15, 50, 15);

	// End tale
	glTranslated(0, -1, 15);
	glColor3f(0.0f, 0.298f, 0.6f);
	gluSphere(body, .8, 15, 15);

	glTranslated(0, 1, 0);
	glColor3f(0.0f, 0.298f, 0.6f);
	gluSphere(body, .4, 15, 15);

	// Build the windshaft back
	glRotated(-90, 0, 1, 0);
	glTranslated(-4, 0, -.1);
	glScaled(1.4, 1.4, 1.4);
	glBegin(GL_QUADS);
	{
		double width = .2;
		glColor3d(0, 0, 1);

		// Left side
		glVertex3d(3, 0, 0);
		glVertex3d(4, 2, 0);
		glVertex3d(3, 2, 0);
		glVertex3d(0, 0, 0);

		// Right side
		glVertex3d(0, 0, width);
		glVertex3d(3, 2, width);
		glVertex3d(4, 2, width);
		glVertex3d(3, 0, width);

		// Front
		glVertex3d(0, 0, 0);
		glVertex3d(0, 0, width);
		glVertex3d(3, 2, width);
		glVertex3d(3, 2, 0);

		// Back
		glVertex3d(3, 0, 0);
		glVertex3d(3, 0, width);
		glVertex3d(4, 2, width);
		glVertex3d(4, 2, 0);

		// Top
		glVertex3d(3, 2, 0);
		glVertex3d(3, 2, width);
		glVertex3d(4, 2, width);
		glVertex3d(4, 2, 0);
	}
	glEnd();

	// Build rotors on windshaft
	glTranslated(2.8, .4, 0);
	glScaled(.07, .07, .07);
	glRotated(90, 1, 0, 0);
	drawTopBlades(false);
}
void Helipcopter::drawWing() {
	glColor3f(0.0f,0.298f,0.6f);
	glScaled(2.5, 1, 2.5);
	glBegin(GL_QUAD_STRIP);
	{
		glVertex3d(0, 1, 0);
		glVertex3d(-3, 0, 0);
		glVertex3d(0, 1, -2);
		glVertex3d(-3, 0, -2);
		glVertex3d(0, 2, -2);
		glVertex3d(-3, 1, -2);
		glVertex3d(0, 2, 0);
		glVertex3d(-3, 1, 0);
		glVertex3d(0, 1, 0);
		glVertex3d(-3, 0, 0);
	}
	glEnd();
	// Close end
	glBegin(GL_QUADS);
	{
		glVertex3d(-3, 0, 0);
		glVertex3d(-3, 1, 0);
		glVertex3d(-3, 1, -2);
		glVertex3d(-3, 0, -2);
	}
	glEnd();
}
void Helipcopter::drawWings() {
	glPushMatrix();
	glTranslated(-2, 0, -10);
	drawWing();
	glPopMatrix();
	glPushMatrix();
	glTranslated(2, 0, -15);
	glRotated(180, 0, 1, 0);
	drawWing();
	glPopMatrix();
}
void Helipcopter::drawHeliBody() {
	glRotated(90, 0, 1, 0);
	glTranslated(0, -1.2, 0);
	glScaled(3, 2, 1);
	glColor3f(0.0f, 0.298f, 0.6f);
	// Front, bottom, back, top: Surounding quad strip
	double maxWidth = 3;
	double minWidth = 2;
	glBegin(GL_QUAD_STRIP);
	{
		glVertex3d(0, 0, -maxWidth);
		glVertex3d(0, 0, maxWidth);
		glVertex3d(0, 1.5, -maxWidth);
		glVertex3d(0, 1.5, maxWidth);
		glVertex3d(2, 3, -minWidth);
		glVertex3d(2, 3, minWidth);
		glVertex3d(3, 4, -minWidth);
		glVertex3d(3, 4, minWidth);
		glVertex3d(5, 4, -minWidth);
		glVertex3d(5, 4, minWidth);
		glVertex3d(6, 3, -minWidth);
		glVertex3d(6, 3, minWidth);
		glVertex3d(6, 0, -maxWidth);
		glVertex3d(6, 0, maxWidth);
		glVertex3d(5, -1, -maxWidth);
		glVertex3d(5, -1, maxWidth);
		glVertex3d(1, -1, -maxWidth);
		glVertex3d(1, -1, maxWidth);
		glVertex3d(0, 0, -maxWidth);
		glVertex3d(0, 0, maxWidth);
	}
	glEnd();

	// Draw left side
	glColor3f(0.0f, 0.298f, 0.6f);
	glBegin(GL_POLYGON);
	{
		glVertex3d(0, 0, -maxWidth);
		glVertex3d(0, 1.5, -maxWidth);
		glVertex3d(2, 3, -minWidth);
		glVertex3d(3, 4, -minWidth);
		glVertex3d(5, 4, -minWidth);
		glVertex3d(6, 3, -minWidth);
		glVertex3d(6, 0, -maxWidth);
		glVertex3d(5, -1, -maxWidth);
		glVertex3d(1, -1, -maxWidth);
		glVertex3d(0, 0, -maxWidth);

	}
	glEnd();
	// Draw right side
	glColor3f(0.0f, 0.298f, 0.6f);
	glBegin(GL_POLYGON);
	{
		glVertex3d(0, 0, maxWidth);
		glVertex3d(1, -1, maxWidth);
		glVertex3d(5, -1, maxWidth);
		glVertex3d(6, 0, maxWidth);
		glVertex3d(6, 3, minWidth);
		glVertex3d(5, 4, minWidth);
		glVertex3d(3, 4, minWidth);
		glVertex3d(2, 3, minWidth);
		glVertex3d(0, 1.5, maxWidth);
		glVertex3d(0, 0, maxWidth);
	}
	glEnd();

	glTranslated(6, 0, 0);
	glColor3d(0, 0, 1);
	// Draw cockpit GLASS: top
	glBegin(GL_QUAD_STRIP);
	{
		glVertex3d(0, 3, -minWidth);
		glVertex3d(0, 3, minWidth);
		glVertex3d(2, 1.5, -minWidth);
		glVertex3d(2, 1.5, minWidth);
		glVertex3d(3, 0, -minWidth);
		glVertex3d(3, 0, minWidth);
	}
	glEnd();

	// GLASS: left
	glBegin(GL_POLYGON);
	{
		glVertex3d(0, 3, -minWidth);
		glVertex3d(2, 1.5, -minWidth);
		glVertex3d(3, 0, -minWidth);
		glVertex3d(2, .5, -minWidth);
		glVertex3d(0, 1, -minWidth);
	}
	glEnd();

	// GLASS: right
	glBegin(GL_POLYGON);
	{
		glVertex3d(0, 1, minWidth);
		glVertex3d(2, .5, minWidth);
		glVertex3d(3, 0, minWidth);
		glVertex3d(2, 1.5, minWidth);
		glVertex3d(0, 3, minWidth);
	}
	glEnd();  

	// Draw rest of cockpit thats not glass
	// COCKPIT: left
	glColor3f(0.0f, 0.298f, 0.6f);
	glBegin(GL_POLYGON);
	{
		glVertex3d(0, 1, -minWidth);
		glVertex3d(2, .5, -minWidth);
		glVertex3d(3, 0, -minWidth);
		glVertex3d(2, 0, -minWidth);
		glVertex3d(0, 0, -maxWidth);
	}
	glEnd();
	// COCKPIT: left, bottom
	glColor3f(0.0f, 0.298f, 0.6f);
	glBegin(GL_POLYGON);
	{
		glVertex3d(0, 0, -maxWidth);
		glVertex3d(2, 0, -minWidth);
		glVertex3d(3, 0, -minWidth);
		glVertex3d(2, -1, -minWidth);
		glVertex3d(-1, -1, -minWidth);

	}
	glEnd();

	// COCKPIT: right
	glColor3f(0.0f, 0.298f, 0.6f);
	glBegin(GL_POLYGON);
	{
		glVertex3d(0, 0, maxWidth);
		glVertex3d(2, 0, minWidth);
		glVertex3d(3, 0, minWidth);
		glVertex3d(2, .5, minWidth);
		glVertex3d(0, 1, minWidth);

	}
	glEnd();
	// COCKPIT: right, bottom
	glColor3f(0.0f, 0.298f, 0.6f);
	glBegin(GL_POLYGON);
	{
		glVertex3d(-1, -1, minWidth);
		glVertex3d(2, -1, minWidth);
		glVertex3d(3, 0, minWidth);
		glVertex3d(2, 0, minWidth);
		glVertex3d(0, 0, maxWidth);
	}
	glEnd();

	// COCKPIT: Floor (bottom)
	glColor3f(0.0f, 0.298f, 0.6f);
	glBegin(GL_QUAD_STRIP);
	{
		glVertex3d(3, 0, -minWidth);
		glVertex3d(3, 0, minWidth);

		glVertex3d(2, -1, -minWidth);
		glVertex3d(2, -1, minWidth);

		glVertex3d(-1, -1, -minWidth);
		glVertex3d(-1, -1, minWidth);
	}
	glEnd(); 

	// Draw rotor stand
	GLUquadric *body = gluNewQuadric();
	glShadeModel(GL_SMOOTH);
	glRotated(-90, 1, 0, 0);
	glTranslated(-2, 0, 4);
	gluCylinder(body, 1, .4, .6, 15, 15);
}
Helipcopter::Helipcopter()
{
}
void Helipcopter::draw(DrawingState*)
{
	bladeRotation += rotatespeed;
	glPushMatrix();
    drawTale();
	glPopMatrix();
	glPushMatrix();
	drawHeliBody();
	glPopMatrix();
	glPushMatrix();
	drawTopBlades(true);
	glPopMatrix();
	glPushMatrix();
	drawWings();
	glPopMatrix();
}





/***********************************************************************/
// a Really simple tree - just a cone with a stem...
// use normals to make it look rounder...
SimpleTree1::SimpleTree1(float h, float r)
  : height(h), radius(r)
{
}
void SimpleTree1::draw(DrawingState*)
{
  // cone for the body
  glFrontFace(GL_CW);
  glColor3f(0.f,.6f,.3f);
  glBegin(GL_TRIANGLE_FAN);
  glNormal3f(0,1,0);
  glVertex3f(0,height,0);
  glNormal3f(1,0,0);
  glVertex3f(radius,height/4,0);
  glNormal3f(0,0,1);
  glVertex3f(0,height/4,radius);
  glNormal3f(-1,0,0);
  glVertex3f(-radius,height/4,0);
  glNormal3f(0,0,-1);
  glVertex3f(0,height/4,-radius);
  glNormal3f(1,0,0);
  glVertex3f(radius,height/4,0);
  glEnd();
  glFrontFace(GL_CCW);
  // stem
  glColor3f(.6f,.5f,.3f);
  glBegin(GL_QUAD_STRIP);
  glNormal3f(1,0,0);
  glVertex3f(radius/5,0,0);
  glVertex3f(radius/5,height/4,0);
  glNormal3f(0,0,1);
  glVertex3f(0,0,radius/5);
  glVertex3f(0,height/4,radius/5);
  glNormal3f(-1,0,0);
  glVertex3f(-radius/5,0,0);
  glVertex3f(-radius/5,height/4,0);
  glNormal3f(0,0,-1);
  glVertex3f(0,0,-radius/5);
  glVertex3f(0,height/4,-radius/5);
  glNormal3f(1,0,0);
  glVertex3f(radius/5,0,0);
  glVertex3f(radius/5,height/4,0);
  glEnd();
}
/***********************************************************************/
void BillboardCheatCylindricalBegin() {

	float modelview[16];
	int i, j;

	// save the current modelview matrix
	glPushMatrix();

	// get the current modelview matrix
	glGetFloatv(GL_MODELVIEW_MATRIX, modelview);

	for (i = 0; i<3; i += 2)
		for (j = 0; j<3; j++) {
		if (i == j)
			modelview[i * 4 + j] = 1.0;
		else
			modelview[i * 4 + j] = 0.0;
		}

	// set the modelview matrix
	glLoadMatrixf(modelview);
}

inline void BillboardEnd() {
	// restore the previously stored modelview matrix
	glPopMatrix();
}





TextureTree::TextureTree(float h, float r, int t)
	: height(h), radius(r), type(t)
{
}
void TextureTree::draw(DrawingState*)
{	
  BillboardCheatCylindricalBegin();
  glRotatef(-90, 0, 1, 0);
  glColor3f(1.0f,1.0f,1.0f);
  if (type == 0){
	  fetchTexture("tree2.png");
  }
  else if (type == 1){
	  fetchTexture("tree1.png");
  }
  else if (type == 2){
	  fetchTexture("tree3.png");
  }
  else if (type == 3){
	  fetchTexture("tree4.png");
  }
  else{
	  fetchTexture("tree5.png");
  }
  glBegin(GL_POLYGON);
  glNormal3f(1, 0, 0);
  glTexCoord2f(0, 1); glVertex3i(10, 18, -10);
  glTexCoord2f(1, 1); glVertex3i(10, 18, 10);
  glTexCoord2f(1, 0); glVertex3i(10, 0, 10);
  glTexCoord2f(0, 0); glVertex3i(10, 0, -10);
  glEnd();
  glBindTexture(GL_TEXTURE_2D, 0);
  BillboardEnd();
}



/***********************************************************************/
// draw a patch of lawn - use a repeating texture
Lawn::Lawn(float xi1, float zi1, float xi2, float zi2)
  : x1(xi1), z1(zi1), x2(xi2), z2(zi2)
{
}

void Lawn::draw(DrawingState* d)
{
  // the catch here is that we need to use a polygon offset to draw
  // the lawn just above the ground...
  if (d->drGrTex)
	fetchTexture("grass.png",true,true);
  else
    glBindTexture(GL_TEXTURE_2D,0);
  glEnable(GL_POLYGON_OFFSET_FILL);
  glPolygonOffset(-2.,-2.);
  glNormal3f(0,1,0);
  glColor3f(0,1,1);
  glBegin(GL_POLYGON);
  glTexCoord2f(0,(z2-z1)/4.f);            glVertex3f(x1,0,z2);
  glTexCoord2f((x2-x1)/4.f, (z2-z1)/4.f); glVertex3f(x2,0,z2);
  glTexCoord2f((x2-x1)/4.f,0);            glVertex3f(x2,0,z1);
  glTexCoord2f(0,0);                      glVertex3f(x1,0,z1);
  glEnd();
  glDisable(GL_POLYGON_OFFSET_FILL);
  glBindTexture(GL_TEXTURE_2D,0);
}

/***********************************************************************/
building1::building1()
{
	color(1.0f, 1.0f, 1.0f);
}
void building1::draw(DrawingState*)
{
	glColor3fv(&color.r);
	fetchTexture("building1front.jpg");
	polygoni(4, -35, 0, -35, 35, 0, -35, 35, 80, -35, -35, 80, -35);
	polygoni(4, -35, 80, -35, 35, 80, -35, 35, 160, -35, -35, 160, -35);
	polygoni(4, -35, 160, -35, 35, 160, -35, 35, 240, -35, -35, 240, -35);
	fetchTexture("building1side.jpg");
	polygoni(4, 35, 0, -35, 35, 0, 35, 35, 80, 35, 35, 80, -35);
	polygoni(4, 35, 80, -35, 35, 80, 35, 35, 160, 35, 35, 160, -35);
	polygoni(4, 35, 160, -35, 35, 160, 35, 35, 240, 35, 35, 240, -35);
	fetchTexture("building1front.jpg");
	polygoni(4, 35, 0, 35, -35, 0, 35, -35, 80, 35, 35, 80, 35);
	polygoni(4, 35, 80, 35, -35, 80, 35, -35, 160, 35, 35, 160, 35);
	polygoni(4, 35, 160, 35, -35, 160, 35, -35, 240, 35, 35, 240, 35);
	fetchTexture("building1side.jpg");
	polygoni(4, -35, 0, 35, -35, 0, -35, -35, 80, -35, -35, 80, 35);
	polygoni(4, -35, 80, 35, -35, 80, -35, -35, 160, -35, -35, 160, 35);
	polygoni(4, -35, 160, 35, -35, 160, -35, -35, 240, -35, -35, 240, 35);
	fetchTexture("building1roof.jpg");
	polygoni(4, -35, 240, 35, -35, 240, -35, 35, 240, -35, 35, 240, 35);
}
/***********************************************************************/
/* simplest possible house */
artmuseum::artmuseum()
{
	color(1.0f, 1.0f, 1.0f);
}
void artmuseum::draw(DrawingState*)
{
	float x0, x1, z0, z1, radius=20,height=50;
	int angleinterval = 10;
	glColor3fv(&color.r);

	// draw the body of first level
	fetchTexture("tower_body.jpg");
	for (int ang = 0; ang < 360; ang+=angleinterval){
		x0 = radius*cos(ang * 2 * pi / 360.0f);
		x1 = radius*cos((ang + angleinterval) * 2 * pi / 360.0f);
		z0 = radius*sin(ang * 2 * pi / 360.0f);
		z1 = radius*sin((ang + angleinterval) * 2 * pi / 360.0f);
		glBegin(GL_QUADS);

		glm::vec3 norm = glm::normalize(glm::cross(glm::vec3(x0-x1, 0, z0-z1), glm::vec3(0, height, 0)));
		glNormal3f(norm.x, norm.y, norm.z);

		glTexCoord2f((ang + angleinterval) / 360.f, 1);
		glVertex3f(x1, height, z1);

		glTexCoord2f((ang + angleinterval) / 360.f, 0);
		glVertex3f(x1, 0, z1);

		glTexCoord2f(ang / 360.f, 0);
		glVertex3f(x0, 0, z0);

		glTexCoord2f(ang / 360.f, 1);
		glVertex3f(x0, height, z0);

		glEnd();
	}
	

	// draw the body of second level
	int height2 = 70+height;
	int	radius2 = radius + 5;
	fetchTexture("tower_middle2.jpg");
	for (int ang = 0; ang < 360; ang += angleinterval){
		x0 = radius2*cos(ang * 2 * pi / 360.0f);
		x1 = radius2*cos((ang + angleinterval) * 2 * pi / 360.0f);
		z0 = radius2*sin(ang * 2 * pi / 360.0f);
		z1 = radius2*sin((ang + angleinterval) * 2 * pi / 360.0f);
		glBegin(GL_QUADS);

		glm::vec3 norm = glm::normalize(glm::cross(glm::vec3(x0 - x1, 0, z0 - z1), glm::vec3(0, height2, 0)));
		glNormal3f(norm.x, norm.y, norm.z);

		glTexCoord2f((ang + angleinterval) / 360.f, 0.98);
		glVertex3f(x1, height2, z1);

		glTexCoord2f((ang + angleinterval) / 360.f, 0.07);
		glVertex3f(x1, height, z1);

		glTexCoord2f(ang / 360.f, 0.07);
		glVertex3f(x0, height, z0);

		glTexCoord2f(ang / 360.f, 0.98);
		glVertex3f(x0, height2, z0);

		glEnd();
	}


	// draw the bottom of second level
	fetchTexture("tower_roof2.jpg");
	glBegin(GL_POLYGON);
	for (int ang = 0; ang < 360; ang += angleinterval){

		float radian = ang * (pi / 180.0f);
		float xcos = (float)cos(radian);
		float ysin = (float)sin(radian);
		float tx = xcos * 0.5 + 0.5;
		float ty = ysin * 0.5 + 0.5;

		x0 = radius2*cos(ang * 2 * pi / 360.0f);
		z0 = radius2*sin(ang * 2 * pi / 360.0f);

		glTexCoord2f(tx, ty);
		glVertex3f(x0, height, z0);

	}
	glEnd();


	// draw the top of second level
	glBegin(GL_POLYGON);
	glNormal3f(0,1,0);
	for (int ang = 360 - angleinterval; ang >= 0; ang -= angleinterval){

		float radian = ang * (pi / 180.0f);
		float xcos = (float)cos(radian);
		float ysin = (float)sin(radian);
		float tx = xcos * 0.5 + 0.5;
		float ty = ysin * 0.5 + 0.5;

		x0 = radius2*cos(ang * 2 * pi / 360.0f);
		z0 = radius2*sin(ang * 2 * pi / 360.0f);

		glTexCoord2f(tx, ty);
		glVertex3f(x0, height2, z0);

	}
	glEnd();
}

/***********************************************************************/
// OK, this is even simpler...
apt1::apt1()
{
  color(1.0f,1.0f,1.0f);
}
void apt1::draw(DrawingState*)
{
  glColor3fv(&color.r);
  fetchTexture("apt1.jpg");
  polygoni(4, -10, 0,-10,  10, 0,-10,  10,40,-10,  -10,40,-10);
  polygoni(4,  10, 0,-10,  10, 0, 10,  10,40, 10,   10,40,-10);
  polygoni(4,  10, 0, 10, -10, 0, 10, -10,40, 10,   10,40, 10);
  polygoni(4, -10, 0, 10, -10, 0,-10, -10,40,-10,  -10,40, 10);
  fetchTexture("roof3.png");
  polygoni(3, -10,40,-10,  10,40,-10,   0,50,0);
  polygoni(3,  10,40,-10,  10,40, 10,   0,50,0);
  polygoni(3,  10,40, 10, -10,40, 10,   0,50,0);
  polygoni(3, -10,40, 10, -10,40,-10,   0,50,0);
}

apt2::apt2()
{
	color(1.0f, 1.0f, 1.0f);
}
void apt2::draw(DrawingState*)
{
	glColor3fv(&color.r);
	fetchTexture("apt2.jpg");
	polygoni(4, -12, 0, -12, 12, 0, -12, 12, 55, -12, -12, 55, -12);
	polygoni(4, 12, 0, -12, 12, 0, 12, 12, 55, 12, 12, 55, -12);
	polygoni(4, 12, 0, 12, -12, 0, 12, -12, 55, 12, 12, 55, 12);
	polygoni(4, -12, 0, 12, -12, 0, -12, -12, 55, -12, -12, 55, 12);
	fetchTexture("apt2roof.jpg");
	polygoni(4, -12, 55, 12, -12, 55, -12, 12, 55, -12, 12, 55, 12);
}

apt3::apt3()
{
	color(1.0f, 1.0f, 1.0f);
	//	rotMatrix(transform, 'Y', 3.14159f / 2.f);
}
void apt3::draw(DrawingState*)
{
	glColor3fv(&color.r);
	fetchTexture("apt3front.jpg");
	polygoni(4, -35, 0, -27, 35, 0, -27, 35, 50, -27, -35, 50, -27);

	fetchTexture("apt3side.jpg");
	polygoni(4, 35, 0, -27, 35, 0, 27, 35, 50, 27, 35, 50, -27);

	fetchTexture("apt3front.jpg");
	polygoni(4, 35, 0, 27, -35, 0, 27, -35, 50, 27, 35, 50, 27);

	fetchTexture("apt3side.jpg");
	polygoni(4, -35, 0, 27, -35, 0, -27, -35, 50, -27, -35, 50, 27);

	fetchTexture("apt3roof.jpg");
	polygoni(4, -35, 50, 27, -35, 50, -27, 35, 50, -27, 35, 50, 27);
}

clothshop::clothshop()
{
	color(1.0f, 1.0f, 1.0f);
}
void clothshop::draw(DrawingState*)
{
	glColor3fv(&color.r);
	fetchTexture("shop-front.jpg");
	polygoni(4, -25, 0, -30, 25, 0, -30, 25, 30, -30, -25, 30, -30);
	fetchTexture("building2front.jpg");
	polygoni(4, -25, 30, -30, 25, 30, -30, 25, 60, -30, -25, 60, -30);
	polygoni(4, -25, 60, -30, 25, 60, -30, 25, 90, -30, -25, 90, -30);
	polygoni(4, -25, 90, -30, 25, 90, -30, 25, 120, -30, -25, 120, -30);
	fetchTexture("shop-side.jpg");
	polygoni(4, 25, 0, -30, 25, 0, 30, 25, 30, 30, 25, 30, -30);
	fetchTexture("building2side.jpg");
	polygoni(4, 25, 30, -30, 25, 30, 30, 25, 60, 30, 25, 60, -30);
	polygoni(4, 25, 60, -30, 25, 60, 30, 25, 90, 30, 25, 90, -30);
	polygoni(4, 25, 90, -30, 25, 90, 30, 25, 120, 30, 25, 120, -30);
	fetchTexture("shop-back.jpg");
	polygoni(4, 25, 0, 30, -25, 0, 30, -25, 30, 30, 25, 30, 30);
	fetchTexture("building2front.jpg");
	polygoni(4, 25, 30, 30, -25, 30, 30, -25, 60, 30, 25, 60, 30);
	polygoni(4, 25, 60, 30, -25, 60, 30, -25, 90, 30, 25, 90, 30);
	polygoni(4, 25, 90, 30, -25, 90, 30, -25, 120, 30, 25, 120, 30);
	fetchTexture("shop-side2.jpg");
	polygoni(4, -25, 0, 30, -25, 0, -30, -25, 30, -30, -25, 30, 30);
	fetchTexture("building2side.jpg");
	polygoni(4, -25, 30, 30, -25, 30, -30, -25, 60, -30, -25, 60, 30);
	polygoni(4, -25, 60, 30, -25, 60, -30, -25, 90, -30, -25, 90, 30);
	polygoni(4, -25, 90, 30, -25, 90, -30, -25, 120, -30, -25, 120, 30);
	fetchTexture("building2roof.jpg");
	polygoni(4, -25, 120, 30, -25, 120, -30, 25, 120, -30, 25, 120, 30);
}




#define POOL_WIDTH 40
#define POOL_HEIGHT 3
#define POOL_LENGTH 40

/***********************************************************************/
Fancyhotel::Fancyhotel() : GrObject("Church")
{
	color(1.0f, 1.0f, 1.0f);
}
void Fancyhotel::stairs()
{
	polygoni(4, -4, 0, -1, 4, 0, -1, 4, 1, -1, -4, 1, -1);
	polygoni(4, 4, 0, -1, 4, 0, 1, 4, 1, 1, 4, 1, -1);
	polygoni(4, 4, 0, 1, -4, 0, 1, -4, 1, 1, 4, 1, 1);
	polygoni(4, -4, 0, 1, -4, 0, -1, -4, 1, -1, -4, 1, 1);
	polygoni(4, -4, 1, 1, -4, 1, -1, 4, 1, -1, 4, 1, 1);
}
void Fancyhotel::pool(DrawingState* st)
{

	// draw pool frame
	fetchTexture("poolbottom2.jpg");
	polygoni(4, -20, 0, -20, 20, 0, -20, 20, 5, -20, -20, 5, -20);
	polygoni(4, 20, 0, -20, 20, 0, 20, 20, 5, 20, 20, 5, -20);
	polygoni(4, 20, 0, 20, -20, 0, 20, -20, 5, 20, 20, 5, 20);
	polygoni(4, -20, 0, 20, -20, 0, -20, -20, 5, -20, -20, 5, 20);
	polygoni(4, -20, 1, -20, 20, 1, -20, 20, 1, 20, -20, 1, 20);

	// draw stairs
	fetchTexture("poolstairs.jpg");
	glPushMatrix();
	glTranslatef(0, 4, 21);
	stairs();
	glPopMatrix();
	glPushMatrix();
	glTranslatef(0, 3, 23);
	stairs();
	glPopMatrix();
	glPushMatrix();
	glTranslatef(0, 2, 25);
	stairs();
	glPopMatrix();
	glPushMatrix();
	glTranslatef(0, 1, 27);
	stairs();
	glPopMatrix();
	glPushMatrix();
	glTranslatef(0, 0, 29);
	stairs();
	glPopMatrix();

	// draw water of the pool
	static GLfloat counter = 0;
	static bool init = false;
	static Texture* water;
	if (init == false)
	{
		init = true;
		water = fetchTexture("water.jpg");
	}
	glEnable(GL_TEXTURE_2D);
	glBindTexture(GL_TEXTURE_2D, water->texName);
	char* error; // not elegant
	GLuint program = loadShader("water.vert", "water.frag", error); // find and compiler the shader pair, but haven’t binded yet
	if (error)
		printf("Error compiling shaders");
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	glEnable(GL_BLEND);
	glEnable(GL_COLOR_MATERIAL);
	glUseProgram(program); // bind the shader that program points to 
	GLfloat loc = glGetUniformLocation(program, "time"); // get location of uniform
	glUniform1f(loc, counter); // send data to the uniform pointed by loc
	GLint loc2 = glGetUniformLocation(program, "uTimeOfDay");
	glUniform1i(loc2, st->timeOfDay);
	GLint loc3 = glGetUniformLocation(program, "transparency");
	glUniform1i(loc3, transparency*10);
	counter += waterwavespeed;
	glTranslatef(-20.0f,0,-20.0f);
	for (int i = 0; i < POOL_LENGTH; i++)
	{
		for (int j = 0; j < POOL_WIDTH; j++)
		{
			glBegin(GL_QUADS);
			glVertex3f(i + 1, POOL_HEIGHT, j + 1);
			glVertex3f(i + 1, POOL_HEIGHT, j);
			glVertex3f(i, POOL_HEIGHT, j);
			glVertex3f(i, POOL_HEIGHT, j + 1);
			glEnd();
		}
	}
	glUseProgram(0); // unbind the shader pair, return to fixed pipeline
	glDeleteProgram(program); // not delete will cause memory leak
	glDisable(GL_BLEND);

}
void Fancyhotel::hotelads()
{
	glPushMatrix();
	static int adsRotation=0;
	static int count = 0;
	glTranslatef(0, 100, -60);
	glRotated(adsRotation, 0, 1, 0);
	if (count == 2){
		adsRotation++;
		count = 0;
		if (adsRotation >= 360){
			adsRotation -= 360;
		}
	}
	count++;
	float x0, x1, z0, z1, radius = 10, height = 20;
	int angleinterval = 10;
	glColor3fv(&color.r);
	// draw the body 
	fetchTexture("gta.jpg");
	for (int ang = 0; ang < 360; ang += angleinterval){
		x0 = radius*cos(ang * 2 * pi / 360.0f);
		x1 = radius*cos((ang + angleinterval) * 2 * pi / 360.0f);
		z0 = radius*sin(ang * 2 * pi / 360.0f);
		z1 = radius*sin((ang + angleinterval) * 2 * pi / 360.0f);

		glPushMatrix();

		glBegin(GL_QUADS);

		glm::vec3 norm = glm::normalize(glm::cross(glm::vec3(x0 - x1, 0, z0 - z1), glm::vec3(0, height, 0)));
		glNormal3f(norm.x, norm.y, norm.z);

		glTexCoord2f(ang / 360.f, 0);
		glVertex3f(x0, 0, z0);

		glTexCoord2f(ang / 360.f, 1);
		glVertex3f(x0, height, z0);

		glTexCoord2f((ang + angleinterval) / 360.f, 1);
		glVertex3f(x1, height, z1);

		glTexCoord2f((ang + angleinterval) / 360.f, 0);
		glVertex3f(x1, 0, z1);

		glEnd();

		glPopMatrix();
	}
	// draw the top
	fetchTexture("adstop.jpg");
	glBegin(GL_POLYGON);
	for (int ang = 0; ang < 360; ang += angleinterval){

		float radian = ang * (pi / 180.0f);
		float xcos = (float)cos(radian);
		float ysin = (float)sin(radian);
		float tx = xcos * 0.5 + 0.5;
		float ty = ysin * 0.5 + 0.5;

		x0 = radius*cos(ang * 2 * pi / 360.0f);
		z0 = radius*sin(ang * 2 * pi / 360.0f);

		glTexCoord2f(tx, ty);
		glVertex3f(x0, height, z0);

	}
	glEnd();
	glPopMatrix();
}
static GLUquadric * q = gluNewQuadric();
void Fancyhotel::drawSnowMan() {

	glPushMatrix();

	glEnable(GL_AUTO_NORMAL);
	glEnable(GL_NORMALIZE);

	glScaled(8, 8, 8);
	glColor3f(0.8f, 1.0f, 1.0f);

	// Draw Body
	glTranslatef(0.0f, 0.75f, 0.0f);
	gluSphere(q, 0.75f, 20, 20);

	// Draw Head
	glTranslatef(0.0f, 1.0f, 0.0f);
	gluSphere(q, 0.25f, 20, 20);

	// Draw Eyes
	glPushMatrix();
	glColor3f(0.0f, 0.0f, 0.0f);
	glTranslatef(0.05f, 0.10f, 0.18f);
	gluSphere(q, 0.05f, 10, 10);
	glTranslatef(-0.1f, 0.0f, 0.0f);
	gluSphere(q, 0.05f, 10, 10);
	glPopMatrix();

	// Draw Nose
	glColor3f(1.0f, 0.5f, 0.5f);
	gluCylinder(q, 0.08f, 0, 0.5f, 10, 2);

	glDisable(GL_AUTO_NORMAL);
	glDisable(GL_NORMALIZE);

	glPopMatrix();
}
void Fancyhotel::hotelmain()
{
	glPushMatrix();
	glTranslatef(0, 0, -40);
	float x0, x1, z0, z1, radius = 50, height = 100;
	int angleinterval = 36;
	glColor3fv(&color.r);
	// draw the sizes of the hotel
	fetchTexture("hotelmainside.jpg");
	x0 = radius*cos(180 * 2 * pi / 360.0f);
	x1 = radius*cos(360 * 2 * pi / 360.0f);
	z0 = radius*sin(180 * 2 * pi / 360.0f);
	glBegin(GL_QUADS);
	glTexCoord2f(0, 1); glVertex3i(x0, height, z0);
	glTexCoord2f(1, 1); glVertex3i(x0, height, z0 - 50);
	glTexCoord2f(1, 0); glVertex3i(x0, 0, z0 - 50);
	glTexCoord2f(0, 0); glVertex3i(x0, 0, z0);
	glEnd();
	glBegin(GL_QUADS);
	glTexCoord2f(0, 1); glVertex3i(x1, height, z0);
	glTexCoord2f(1, 1); glVertex3i(x1, height, z0 - 50);
	glTexCoord2f(1, 0); glVertex3i(x1, 0, z0 - 50);
	glTexCoord2f(0, 0); glVertex3i(x1, 0, z0);
	glEnd();
	// draw the top of the hotel
	fetchTexture("hotelmaintop.jpg");
	glBegin(GL_POLYGON);
	for (int ang = 360; ang >= 180; ang -= angleinterval){
		float radian = ang * (pi / 180.0f);
		float xcos = (float)cos(radian);
		float ysin = (float)sin(radian);
		float tx = xcos * 0.5 + 0.5;
		float ty = ysin * 0.5 + 0.5;
		x0 = radius*cos(ang * 2 * pi / 360.0f);
		z0 = radius*sin(ang * 2 * pi / 360.0f);
		glTexCoord2f(tx, ty);
		glVertex3f(x0, height, z0 - 50);
	}
	glEnd();
	glBegin(GL_POLYGON);
	glTexCoord2f(0, 1); glVertex3i(x0, height, z0);
	glTexCoord2f(1, 1); glVertex3i(x1, height, z0);
	glTexCoord2f(1, 0); glVertex3i(x1, height, z0-50);
	glTexCoord2f(0, 0); glVertex3i(x0, height, z0-50);
	glEnd();
	// draw the front face of the hotel
	fetchTexture("apt3side.jpg");
	glBegin(GL_QUADS);
	glTexCoord2f(0, 0); glVertex3i(x0, 0, z0);
	glTexCoord2f(0, 1); glVertex3i(x0, height, z0);
	glTexCoord2f(1, 1); glVertex3i(x1, height, z0);
	glTexCoord2f(1, 0); glVertex3i(x1, 0, z0);
	glEnd();
	// draw the back face of the hotel
	glTranslatef(0, 0, -50);
	fetchTexture("apt3front.jpg");
	for (int ang = 180; ang < 360; ang += angleinterval){
		x0 = radius*cos(ang * 2 * pi / 360.0f);
		x1 = radius*cos((ang + angleinterval) * 2 * pi / 360.0f);
		z0 = radius*sin(ang * 2 * pi / 360.0f);
		z1 = radius*sin((ang + angleinterval) * 2 * pi / 360.0f);
		glBegin(GL_QUADS);

		glm::vec3 norm = glm::normalize(glm::cross(glm::vec3(x0 - x1, 0, z0 - z1), glm::vec3(0, height, 0)));
		glNormal3f(norm.x, norm.y, norm.z);

		glTexCoord2f(((ang - 180) + angleinterval) / 180.f, 1);
		glVertex3f(x1, height, z1);

		glTexCoord2f(((ang - 180) + angleinterval) / 180.f, 0);
		glVertex3f(x1, 0, z1);

		glTexCoord2f((ang - 180) / 180.f, 0);
		glVertex3f(x0, 0, z0);

		glTexCoord2f((ang - 180) / 180.f, 1);
		glVertex3f(x0, height, z0);

		glEnd();
	}
	glPopMatrix();
}
void Fancyhotel::drawDuck(DrawingState* st) {

	static GLfloat counter = 0;
	float oscillate = (0.2*sin(counter) + 0.1*sin(2 * counter))*0.5;

	char* error; // not elegant
	GLuint program = loadShader("duckshader.vert", "duckshader.frag", error); // find and compiler the shader pair, but haven’t binded yet
	if (error)
		printf("Error compiling shaders");

	glUseProgram(program); // bind the shader that program points to 
	GLint loc = glGetUniformLocation(program, "uTimeOfDay");
	glUniform1i(loc, st->timeOfDay);
	counter += waterwavespeed;
	//set the color of the body here, but not the eyes or beak
	glColor3d(1, 1, 0);

	glTranslatef(0, oscillate, 0);

	// draw the body
	glPushMatrix();
	glScaled(1, .7, 1.2);
	gluSphere(q, 1, 100, 100);
	glPopMatrix();

	// draw the head
	glPushMatrix();
	glScaled(.4, .4, .4);
	glTranslated(0, 1.8, 2.2);
	gluSphere(q, 1, 100, 100);
	glPopMatrix();

	// draw the wings
	glPushMatrix();
	glScaled(.3, .4, .75);
	glTranslated(2.9, 0, 0);
	gluSphere(q, 1, 100, 100);
	glPopMatrix();

	glPushMatrix();
	glScaled(.3, .4, .75);
	glTranslated(-2.9, 0, 0);
	gluSphere(q, 1, 100, 100);
	glPopMatrix();

	//draw a tail
	glPushMatrix();
	glScaled(.5, .3, .5);
	glTranslated(0, .8, -1.8);
	glRotated(-145, 1, 0, 0);
	gluCylinder(q, 1, 0, 1, 100, 100);
	glPopMatrix();

	// draw the eyes
	glPushMatrix();
	glScaled(.1, .1, .1);
	glColor3d(1, 1, 1);
	glTranslated(2, 10, 11.3);
	gluSphere(q, 1, 100, 100);
	glPopMatrix();

	glPushMatrix();
	glScaled(.1, .1, .1);
	glColor3d(1, 1, 1);
	glTranslated(-2, 10, 11.3);
	gluSphere(q, 1, 100, 100);
	glPopMatrix();

	// blue pupils
	glPushMatrix();
	glScaled(.05, .05, .05);
	glColor3d(0, 0, 1);
	glTranslated(4, 20, 24);
	gluSphere(q, 1, 100, 100);
	glPopMatrix();

	glPushMatrix();
	glScaled(.05, .05, .05);
	glColor3d(0, 0, 1);
	glTranslated(-4, 20, 24);
	gluSphere(q, 1, 100, 100);
	glPopMatrix();

	// draw the beak
	glPushMatrix();
	glScaled(.25, .1, .5);
	glColor3d(1, .5, 0);
	glTranslated(0, 7.5, 2.35);
	gluCylinder(q, 1, 0, 1, 100, 100);
	glPopMatrix();

	glUseProgram(0); // unbind the shader pair, return to fixed pipeline
	glDeleteProgram(program); // not delete will cause memory leak
} 

void Fancyhotel::draw(DrawingState* st)
{
	// draw pool
	glPushMatrix();
	pool(st);
	glPopMatrix();
	// draw hotel building
	hotelmain();
	// draw ads on top of hotel
	hotelads();
	// draw snowman
	glPushMatrix();
	glTranslatef(-50, 0, 0);
	drawSnowMan();
	glPopMatrix();
	glPushMatrix();
	glTranslatef(50, 0, 0);
	drawSnowMan();
	glPopMatrix();
	// draw duck in the pool
	glPushMatrix();
	glTranslatef(0, 3.5f, 0);
	glScaled(3,3,3);
	drawDuck(st);
	glPopMatrix();
}

/*&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
StreetLight::StreetLight(double p) : poleZ(p)
{
	radius = 10;
}
void StreetLight::draw(DrawingState*)
{
  glColor3f(.6f,.5f,.3f);
  glBegin(GL_QUAD_STRIP);
  glNormal3d( 1, 0,0);
  glVertex3d( 1, 0,poleZ);
  glVertex3d( 1,20,poleZ);
  glNormal3d( 0, 0,1);
  glVertex3d( 0, 0,poleZ+1);
  glVertex3d( 0,20,poleZ+1);
  glNormal3d(-1, 0,0);
  glVertex3d(-1, 0,poleZ);
  glVertex3d(-1,20,poleZ);
  glNormal3d( 0, 0,-1);
  glVertex3d( 0, 0,poleZ-1);
  glVertex3d( 0,20,poleZ-1);
  glNormal3d( 1, 0,0);
  glVertex3d( 1, 0,poleZ);
  glVertex3d( 1,20,poleZ);
  glEnd();
  glBegin(GL_QUAD_STRIP);
  glNormal3d(0,1,0);
  glVertex3d( 0,21, 0);
  glVertex3d( 0,21, poleZ);
  glNormal3d(-.707,-.707, 0);
  glVertex3d(-1,20, 0);
  glVertex3d(-1,20, poleZ);
  glNormal3d(.707,-.707, 0);
  glVertex3d( 1,20, 0);
  glVertex3d( 1,20, poleZ);
  glNormal3d(0,1,0);
  glVertex3d( 0,21, 0);
  glVertex3d( 0,21, poleZ);
	glEnd();
}

void StreetLight::drawAfter(DrawingState* d)
{
  int tod = d->timeOfDay;
  bool daytime;
  if (tod >=6 && tod <= 19)
	daytime = true;
  else 
	daytime = false;

  if (!daytime) {

	glEnable(GL_BLEND);
	glBlendFunc(GL_SRC_ALPHA,GL_DST_ALPHA);
	float cone_length = 20;
	float radius = 10;
	float theta,dtheta = pi/16;
	int c;

	float norm[3];
	float sum;
	// Beam One
	glPushMatrix();
			
	glFrontFace(GL_CW);
			
	glBegin(GL_TRIANGLE_FAN);
	glColor4f(1,1,0,.8f);
	glVertex3f(0,cone_length,0);
	for ( c=0,theta = 0.0 ; c <= 32  ; theta += dtheta, c++ ) {
	  float z = sin(theta) * radius;
	  float x = cos(theta) * radius;
	  glColor4f(1,1,0,.2f);
	
	  norm[0] = 0; norm[1] = x; norm[2] = z; sum = norm[0] + norm[1] + norm[2];
	  norm[0] /= sum; norm[1] /= sum; norm[2] /= sum;				
	  glNormal3f( norm[0],norm[1],norm[2]);
	
	  glVertex3f( x, cone_length/6, z );
	}  
			
	glEnd();

			
	glPopMatrix();

	glDisable(GL_BLEND);
	glClearColor(1,1,1,1);

  }
}

/**********************************************************************/
Sign::Sign(float sxi, float syi, float hi, char* tex, 
		   SignShape ss)
  : sx(sxi), sy(syi), h(hi), texName(tex), shape(ss)
{
}
void Sign::draw(DrawingState*)
{
  glTexEnvf(GL_TEXTURE_ENV,GL_TEXTURE_ENV_MODE, GL_MODULATE);
  fetchTexture(texName);
  glBegin(GL_POLYGON);
  glNormal3f(0,0,1);
  glColor3f(1,1,1);
  drawShape(0);
  glEnd();
  // back of the sign
  glBindTexture(GL_TEXTURE_2D,0);
  glNormal3f(0,0,-1);
  drawShape(-.1f);
  // post
  glBegin(GL_POLYGON);
    glNormal3f(0,0,1);
	glVertex3f(-.1f,h,0);
	glVertex3f( .1f,h,0);
	glVertex3f( .1f,0,0);
	glVertex3f(-.1f,0,0);
  glEnd();
  glFrontFace(GL_CW);
  glBegin(GL_POLYGON);
    glNormal3f(0,0,1);
	glVertex3f(-.1f,h,-.05f);
	glVertex3f( .1f,h,-.05f);
	glVertex3f( .1f,0,-.05f);
	glVertex3f(-.1f,0,-.05f);
  glEnd();
  glFrontFace(GL_CCW);	
}
void Sign::drawShape(float z)
{
  glFrontFace(GL_CW);
  switch(shape) {
  case rectangle:
	glBegin(GL_POLYGON);
	glTexCoord2f(0.,0.); glVertex3f(-sx/2,h,z);
	glTexCoord2f(1.,0.); glVertex3f( sx/2,h,z);
	glTexCoord2f(1.,1.); glVertex3f( sx/2,h+sy,z);
	glTexCoord2f(0.,1.); glVertex3f(-sx/2,h+sy,z);
	glEnd();
	break;
  case octagon:
	glBegin(GL_POLYGON);
	glTexCoord2f(0.25f,0.f); glVertex3f(-sx/4,h,z);
	glTexCoord2f(0.75f,0.f); glVertex3f( sx/4,h,z);
	glTexCoord2f(1.f,.25f); glVertex3f( sx/2.f,h+    sy/4.f,z);
	glTexCoord2f(1.f,.75f); glVertex3f( sx/2.f,h+3.f*sy/4.f,z);
	glTexCoord2f(.75f,1); glVertex3f( sx/4,h+sy,z);
	glTexCoord2f(.25f,1); glVertex3f(-sx/4,h+sy,z);
	glTexCoord2f(0,.75f); glVertex3f(-sx/2.f,h+3.f*sy/4.f,z);
	glTexCoord2f(0,.25f); glVertex3f(-sx/2.f,h+sy/4.f,z);
	glEnd();
	break;
  case diamond:
	glBegin(GL_POLYGON);
	glTexCoord2f(0.45f, 0.f); glVertex3f(-sx/10,h,z);
	glTexCoord2f(0.55f, 0.f); glVertex3f( sx/10,h,z);
	glTexCoord2f(   1 ,.45f); glVertex3f( sx/2,h+9*sy/20.f,z);
	glTexCoord2f(   1 ,.55f); glVertex3f( sx/2,h+11*sy/20.f,z);
	glTexCoord2f(.55f, 1   ); glVertex3f( sx/10,h+sy,z);
	glTexCoord2f(.45f, 1   ); glVertex3f(-sx/10,h+sy,z);
	glTexCoord2f(   0 ,.55f); glVertex3f(-sx/2,h+11*sy/20.f,z);
	glTexCoord2f(   0 ,.45f); glVertex3f(-sx/2,h+ 9*sy/20.f,z);
	glEnd();
	break;
  };
  glFrontFace(GL_CCW);
}

// make the houses we create be from a set of colors
/////////////////////////////////////////
// stuff for all houses
static int houseColors[][3] = {
	{240,240,240}, {180,175,100}, 
	{200,100,100}, {147,144,244},
	{250,249,157}, {199,144,186},
};
int nHouseColors = 6;


////////////////////////////////////////////////////////////////////////
// draw a little suburban dream...
// a 100x200 lot...
SimpleLot::SimpleLot(int ht, int hc, boolean towerishere)
{
  // make the things we want
  add(new Lawn(0,0,100,200),0,0,0,0);
  if (!towerishere){
	  switch (ht % 5) {
	  case 0: {
		  building1* h1 = new building1();
		  add(h1, 50., 0, 60., 0);
		  Helipcopter* heli = new Helipcopter();
		  add(heli, 50., 280, 60., 0);
		  break; }
	  case 1: {
		  artmuseum* h2 = new artmuseum();
		  add(h2, 50., 0, 60., 0);
		  break; }
	  case 2: {
		  apt1* h3;
		  apt2* h3_2;
		  h3 = new apt1();
		  add(h3, 78., 0, 88, 0);
		  h3 = new apt1();
		  add(h3, 78, 0, 32, 0);
		  h3 = new apt1();
		  add(h3, 22, 0, 88., 0);
		  h3 = new apt1();
		  add(h3, 22, 0, 32., 0);
		  h3_2 = new apt2();
		  add(h3_2, 78., 0, 60., 0);
		  h3_2 = new apt2();
		  add(h3_2, 22, 0, 60., 0);
		  h3_2 = new apt2();
		  add(h3_2, 50., 0, 88., 0);
		  h3_2 = new apt2();
		  add(h3_2, 50., 0, 32., 0);
		  h3_2 = new apt2();
		  add(h3_2, 50., 0, 60, 0);
		  break; }
	  case 3: {
		  clothshop* h4 = new clothshop();
		  add(h4, 50., 0, 60., 0);
		  break; }
	  case 4:{
		  apt3* h1 = new apt3();
		  add(h1, 50., 0, 60., 0);
		  break; }
	  }
  }
  add(new TextureTree(15, 5, 0), 10, 0, 10, 0);
  add(new TextureTree(15, 5, 1), 10, 0, 190, 0);
  add(new TextureTree(15, 5, 2), 30, 0, 190, 0);
  add(new TextureTree(15, 5, 3), 50, 0, 190, 0);
  add(new TextureTree(15, 5, 4), 70, 0, 190, 0);
}

/***********************************************************************/
// a set of houses going down the street
// the street is along the X axis from (0,0) to ( (nh+1)*100, 0)
SimpleSubdivision::SimpleSubdivision(int nh, boolean towerishereleft, boolean towerishereright)
{
	// street lights in front of every other house
	// alternate sides of the street
	for(int sl=0; sl<nh; sl ++) {
		add(new StreetLight(sl%2 ? 9 : -9), static_cast<float>(sl*100 + 25), 0, (float)((sl%2) ? 8 : -9));
	}
	
    // add houses - one on each side of the street
	for (int hc=0; hc<nh; hc++) {
		GrObject* g2, *g1;
		if (towerishereleft && hc<2){
			g1 = new SimpleLot(rand(), rand(),true);
			g2 = new SimpleLot(rand(), rand(), false);
		}
		else if (towerishereright && hc<2){
			g1 = new SimpleLot(rand(), rand(), false);
			g2 = new SimpleLot(rand(), rand(),true);
		}
		else{
			g1 = new SimpleLot(rand(), rand(),false);
			g2 = new SimpleLot(rand(), rand(),false);

		}
		add(g1, (float)(hc * 100), 0, 15, 0);
		add(g2, (float)(hc * 100 + 100), 0, -15, pi);

	}

	add(new Sign(4,4,4,"stop.png",octagon),(float)(100*nh-2),0,17,-(3.141592f/2.f));
	add(new Sign(4,4,4,"stop.png",octagon),  2,0,-17 ,(3.141592f/2.f));
}























// $Header: /p/course/cs559-gleicher/private/CVS/GrTown/Examples/Suburbs.cpp,v 1.4 2010/11/17 22:50:22 gleicher Exp $
