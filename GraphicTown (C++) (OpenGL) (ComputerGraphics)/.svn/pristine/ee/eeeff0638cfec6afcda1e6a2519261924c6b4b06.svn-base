#include <windows.h>
#include <GL/glu.h>
#include "NODE.h"
#include "SPRING.h"
#include "cloth.h"
#include "glm.hpp"
#include "gtc/type_ptr.hpp"
#include "Utilities/Texture.h"
/////////////////////////////////////////////

// define physics ctrl pt
GLuint clothDetail = 10;
int numOfNodes;
NODE* nodeGrid0 = NULL;
NODE* nodeGrid1 = NULL;
NODE* currLoc = NULL;
NODE* nxtLoc = NULL;
SPRING* springs = NULL;
int numSprings;

//Gravity
glm::vec3 gravity = glm::vec3(0.7f, -0.98f, 0.0f);
GLuint updatect = 0;
//Values given to each spring
GLfloat SpringK = 15.0f;
GLfloat SpringL = 1.0f;
GLfloat m = 0.01f;
GLfloat dampFactor = 0.9f;
int tesselationLevel = 5;
Texture* flagTex;

//Set up variables
bool CLOTH_INIT()
{
	flagTex = fetchTexture("flag01.tga", false, false);
	numOfNodes = clothDetail*clothDetail;
	numSprings = (clothDetail - 1)*clothDetail * 2;
	numSprings += (clothDetail - 1)*(clothDetail - 1) * 2;
	numSprings += (clothDetail - 2)*clothDetail * 2;

	//Create space for balls & springs
	nodeGrid0 = new NODE[numOfNodes];
	nodeGrid1 = new NODE[numOfNodes];
	springs = new SPRING[numSprings];

	//Reset cloth
	for (int i = 0; i<clothDetail; ++i)
	{
		for (int j = 0; j<clothDetail; ++j)
		{
			nodeGrid0[i*clothDetail + j].pos = glm::vec3(8.5f, float(j) - float(clothDetail - 1) / 2+10.f, float(i) - float(clothDetail - 1) / 2);
			nodeGrid0[i*clothDetail + j].tex = glm::vec2(float(j) - float(clothDetail - 1) / 2 + 10.f, float(i) - float(clothDetail - 1) / 2);
			nodeGrid0[i*clothDetail + j].v = glm::vec3(0,0,0);
			nodeGrid0[i*clothDetail + j].m = m;
			nodeGrid0[i*clothDetail + j].holded = false;
		}
	}

	nodeGrid0[0].holded = true;
	nodeGrid0[clothDetail - 1].holded = true;

	//Fix the two nodes
	nodeGrid0[clothDetail*(clothDetail - 1)].holded = true;
	nodeGrid0[clothDetail*clothDetail - 1].holded = true;

	for (int i = 0; i<numOfNodes; ++i)
		nodeGrid1[i] = nodeGrid0[i];

	currLoc = nodeGrid0;
	nxtLoc = nodeGrid1;
	//Initialise the springs
	SPRING * currentSpring = &springs[0];

	for (int i = 0; i<clothDetail; ++i)
	{
		for (int j = 0; j<clothDetail - 1; ++j)
		{
			currentSpring->nodeEnd1 = i*clothDetail + j;
			currentSpring->nodeEnd2 = i*clothDetail + j + 1;

			currentSpring->springK = SpringK;
			currentSpring->springL = SpringL;

			++currentSpring;
		}
	}

	for (int i = 0; i<clothDetail - 1; ++i)
	{
		for (int j = 0; j<clothDetail; ++j)
		{
			currentSpring->nodeEnd1 = i*clothDetail + j;
			currentSpring->nodeEnd2 = (i + 1)*clothDetail + j;

			currentSpring->springK = SpringK;
			currentSpring->springL = SpringL;

			++currentSpring;
		}
	}

	for (int i = 0; i<clothDetail - 1; ++i)
	{
		for (int j = 0; j<clothDetail - 1; ++j)
		{
			currentSpring->nodeEnd1 = i*clothDetail + j;
			currentSpring->nodeEnd2 = (i + 1)*clothDetail + j + 1;

			currentSpring->springK = SpringK;
			currentSpring->springL = SpringL*sqrt(2.0f);

			++currentSpring;
		}
	}
	for (int i = 0; i<clothDetail - 1; ++i)
	{
		for (int j = 1; j<clothDetail; ++j)
		{
			currentSpring->nodeEnd1 = i*clothDetail + j;
			currentSpring->nodeEnd2 = (i + 1)*clothDetail + j - 1;

			currentSpring->springK = SpringK;
			currentSpring->springL = SpringL*sqrt(2.0f);

			++currentSpring;
		}
	}

	for (int i = 0; i<clothDetail; ++i)
	{
		for (int j = 0; j<clothDetail - 2; ++j)
		{
			currentSpring->nodeEnd1 = i*clothDetail + j;
			currentSpring->nodeEnd2 = i*clothDetail + j + 2;

			currentSpring->springK = SpringK;
			currentSpring->springL = SpringL * 2;

			++currentSpring;
		}
	}

	for (int i = 0; i<clothDetail - 2; ++i)
	{
		for (int j = 0; j<clothDetail; ++j)
		{
			currentSpring->nodeEnd1 = i*clothDetail + j;
			currentSpring->nodeEnd2 = (i + 2)*clothDetail + j;

			currentSpring->springK = SpringK;
			currentSpring->springL = SpringL * 2;

			++currentSpring;
		}
	}
	return true;
}

//Perform per-frame updates
void UPDATE()
{
	if (updatect == 180)
	{
		gravity = glm::vec3(10 * windspeed + ((double)rand() / (RAND_MAX)), -0.98, ((double)rand() / (RAND_MAX)));
		updatect = 0;
	}
	updatect++;
	
	//Release two pts
	currLoc[0].holded = false;
	currLoc[clothDetail - 1].holded = false;

	float timePassedInSeconds = 0.01f;
	for (int i = 0; i<numSprings; ++i)
	{
		float springLength = glm::length((currLoc[springs[i].nodeEnd1].pos -
			currLoc[springs[i].nodeEnd2].pos));

		float extension = springLength - springs[i].springL;

		springs[i].tension = springs[i].springK*extension / springs[i].springL;
	}
	//Calculate the nxtLoc from the currLoc
	for (int i = 0; i<numOfNodes; ++i)
	{
		//Transfer properties which do not change
		nxtLoc[i].holded = currLoc[i].holded;
		nxtLoc[i].m = currLoc[i].m;

		if (currLoc[i].holded)
		{
			nxtLoc[i].pos = currLoc[i].pos;
			nxtLoc[i].v=glm::vec3(0,0,0);
		}
		else
		{
			glm::vec3 force = gravity;
			for (int j = 0; j<numSprings; ++j)
			{
				if (springs[j].nodeEnd1 == i)
				{
					glm::vec3 tensionDirection = currLoc[springs[j].nodeEnd2].pos -
						currLoc[i].pos;
					tensionDirection = (tensionDirection / glm::length(tensionDirection));

					force += springs[j].tension*tensionDirection;
				}
				if (springs[j].nodeEnd2 == i)
				{
					glm::vec3 tensionDirection = currLoc[springs[j].nodeEnd1].pos -
						currLoc[i].pos;
					tensionDirection = (tensionDirection / glm::length(tensionDirection));
					force += springs[j].tension*tensionDirection;
				}
			}
			glm::vec3 acceleration = force / currLoc[i].m;

			nxtLoc[i].v = currLoc[i].v + acceleration*
				(float)timePassedInSeconds;

			nxtLoc[i].v *= dampFactor;

			nxtLoc[i].pos = currLoc[i].pos +
				(nxtLoc[i].v + currLoc[i].v)*(float)timePassedInSeconds / 2.0f;

			if (nxtLoc[i].pos.y<0.f)
				nxtLoc[i].pos.y = 0.f;
		}
	}

	//Swap
	NODE * temp = currLoc;
	currLoc = nxtLoc;
	nxtLoc = currLoc;

	//Calculate the normals
	for(int i = 0; i < numOfNodes; ++i){
		currLoc[i].normal=glm::vec3(0,0,0);
	}
	for(int i = 0; i<clothDetail - 1; ++i)
	{
		for (int j = 0; j<clothDetail - 1; ++j)
		{
			glm::vec3 & p0 = currLoc[i*clothDetail + j].pos;
			glm::vec3 & p1 = currLoc[i*clothDetail + j + 1].pos;
			glm::vec3 & p2 = currLoc[(i + 1)*clothDetail + j].pos;
			glm::vec3 & p3 = currLoc[(i + 1)*clothDetail + j + 1].pos;

			glm::vec3 & n0 = currLoc[i*clothDetail + j].normal;
			glm::vec3 & n1 = currLoc[i*clothDetail + j + 1].normal;
			glm::vec3 & n2 = currLoc[(i + 1)*clothDetail + j].normal;
			glm::vec3 & n3 = currLoc[(i + 1)*clothDetail + j + 1].normal;

			glm::vec3 normal = glm::cross((p1 - p0),(p2 - p0));

			n0 += normal;
			n1 += normal;
			n2 += normal;

			normal = glm::cross((p1 - p2),(p3 - p2));

			n1 += normal;
			n2 += normal;
			n3 += normal;
		}
	}

	//Normalize normals
	for (int i = 0; i<numOfNodes; ++i)
		currLoc[i].normal = currLoc[i].normal / glm::length(currLoc[i].normal);

	DRAW();
}

//Render a frame
void DRAW()
{

	glPushMatrix();
	glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

	glTranslatef(0.0f, 0.0f, -28.0f);

	glEnable(GL_LIGHTING);
	GLfloat materialColor[] = { 1.0f, 0.0f, 0.0f, 1.0f };
	GLfloat materialColor2[] = { 1.0f, 1.0f, 0.0f, 1.0f };
	GLfloat materialColor3[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	glMaterialfv(GL_FRONT, GL_AMBIENT, materialColor);
	glMaterialfv(GL_FRONT, GL_DIFFUSE, materialColor);
	glMaterialfv(GL_FRONT, GL_SPECULAR, materialColor2);
	glMaterialf(GL_FRONT, GL_SHININESS, 32.0f);
	glEnable(GL_CULL_FACE);

	glDisable(GL_LIGHTING);
	glDisable(GL_CULL_FACE);
	glMaterialfv(GL_FRONT, GL_SPECULAR, materialColor3);

	//Draw cloth using OpenGL evaluators
	GLfloat materialColora[] = { 0.8f, 0.0f, 1.0f, 1.0f };
	GLfloat materialColorb[] = { 1.0f, 0.0f, 0.0f, 1.0f };
	glEnable(GL_LIGHTING);
	glMaterialfv(GL_FRONT, GL_AMBIENT, materialColora);	//set material
	glMaterialfv(GL_FRONT, GL_DIFFUSE, materialColora);
	glMaterialfv(GL_BACK, GL_AMBIENT, materialColorb);
	glMaterialfv(GL_BACK, GL_DIFFUSE, materialColorb);
	glFrontFace(GL_CW);
	GLfloat texpoints[2][2][2] = { { { 0.0, 0.0 }, { 0.0, 1.0 } },
	{ { 1.0, 0.0 }, { 1.0, 1.0 } } };
	glEnable(GL_MAP2_VERTEX_3);
	glEnable(GL_MAP2_NORMAL);
	glEnable(GL_MAP2_TEXTURE_COORD_2);
	glBindTexture(GL_TEXTURE_2D, flagTex->texName);
	glEnable(GL_TEXTURE_2D);

	// bind curved surface with texture
	glMap2f(GL_MAP2_TEXTURE_COORD_2, 0.0f, 1.0f, 2, 2,
				0.0f, 1.0f, 4, 2,
				&texpoints[0][0][0]);
		


	for (int i = 0; i<clothDetail - 1; i += 3)
	{
		for (int j = 0; j<clothDetail - 1; j += 3)
		{
			glMap2f(GL_MAP2_VERTEX_3, 0.0f, 1.0f, sizeof(NODE) / sizeof(float), 4,
				0.0f, 1.0f, clothDetail*sizeof(NODE) / sizeof(float), 4,
				glm::value_ptr(currLoc[i*clothDetail + j].pos));
			glMap2f(GL_MAP2_NORMAL, 0.0f, 1.0f, sizeof(NODE) / sizeof(float), 4,
				0.0f, 1.0f, clothDetail*sizeof(NODE) / sizeof(float), 4,
				glm::value_ptr(currLoc[i*clothDetail + j].normal));
			
			
			glMapGrid2f(tesselationLevel, 0.0f, 1.0f, tesselationLevel, 0.0f, 1.0f);
			glEvalMesh2(GL_FILL, 0, tesselationLevel, 0, tesselationLevel);
		}
	}
	glPopMatrix();
	glEnable(GL_TEXTURE_2D);
	glDisable(GL_LIGHTING);
	glFrontFace(GL_CCW);

	glDisable(GL_MAP2_VERTEX_3);
	glDisable(GL_MAP2_NORMAL);
}