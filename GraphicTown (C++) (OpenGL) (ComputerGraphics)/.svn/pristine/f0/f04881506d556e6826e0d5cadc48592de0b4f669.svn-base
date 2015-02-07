/// GraphicsTown2006 - CS559 Sample Code
// written by Michael Gleicher - originally written Fall, 2000
// updated Fall, 2005
//
#include "GrTown_PCH.H"

#include "GrWorld.H"
#include "DrawingState.H"
#include "DrawUtils.H"
#include "Utilities/Texture.h"
#include "./stb/stb_image.h"
#include "./Utilities/ShaderTools.h"
#include <assert.h>
#include "cloth.h"
#include "model_obj.h"
#include <Map>

using std::vector;

// stuff in the world
vector<GrObject*> theObjects;

// store skybox maps
bool skyBoxLoaded = false;
Texture* skyBoxTex[6];

// things for drawing the world
///////////////////TERRAIN PARAMS
bool TerrainInit = false;
#define TERRAIN_RES 1024
#define STEP_SIZE		16
BYTE g_HeightMap[TERRAIN_RES*TERRAIN_RES];
float g_FogDepth = 30.0f;
GLuint dir1;
GLuint dir2;
int LOD = 16;
typedef std::map<std::string, GLuint> ModelTextures;
GLuint TerrainShader;
char* error;
ModelTextures       g_modelTextures;
GLuint phong;
GLuint normalShdr;
GLuint              g_nullTexture;
char path[80];
ModelOBJ bldg;
// things for drawing the 

//////////////////UTILS
GLuint CreateNullTexture(int width, int height)
{
	// Create an empty white texture. This texture is applied to OBJ models
	// that don't have any texture maps. This trick allows the same shader to
	// be used to draw the OBJ model with and without textures applied.

	int pitch = ((width * 32 + 31) & ~31) >> 3; // align to 4-byte boundaries
	std::vector<GLubyte> pixels(pitch * height, 255);
	GLuint texture = 0;

	glGenTextures(1, &texture);
	glBindTexture(GL_TEXTURE_2D, texture);

	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_BGRA,
		GL_UNSIGNED_BYTE, &pixels[0]);

	return texture;
}



//////////////////TERRAIN COMPONENTS
bool CreateTexture(GLuint &textureID, LPTSTR szFileName)
{
	HBITMAP hBMP;
	BITMAP  bitmap;

	glGenTextures(1, &textureID);
	hBMP = (HBITMAP)LoadImage(GetModuleHandle(NULL), szFileName, IMAGE_BITMAP, 0, 0, LR_CREATEDIBSECTION | LR_LOADFROMFILE);
	if (!hBMP)
		return FALSE;
	GetObject(hBMP, sizeof(bitmap), &bitmap);
	glPixelStorei(GL_UNPACK_ALIGNMENT, 4);
	// Typical Texture Generation Using Data From The Bitmap
	glBindTexture(GL_TEXTURE_2D, textureID);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexImage2D(GL_TEXTURE_2D, 0, 3, bitmap.bmWidth, bitmap.bmHeight, 0, GL_BGR_EXT, GL_UNSIGNED_BYTE, bitmap.bmBits);
	// MUST NOT BE INDEX BMP, BUT RGB
	DeleteObject(hBMP);
	return TRUE;
}

void FOG_Enabler(float depth, float height)
{
	float fogY = 0;
	if (height > depth)
		fogY = 0;
	else
		fogY = -(height - depth);
	glFogCoordfEXT(fogY);
}
void TEXTURE_FINDER(float x, float z)
{
	float u = (float)x / (float)TERRAIN_RES;
	float v = -(float)z / (float)TERRAIN_RES;
	glMultiTexCoord2fARB(GL_TEXTURE0_ARB, u, v);
	glMultiTexCoord2fARB(GL_TEXTURE1_ARB, u, v);
}
void LoadRawFile(LPSTR strName, int nSize, BYTE *g_HeightMap)
{
	FILE *pFile = NULL;
	pFile = fopen(strName, "rb");
	if (pFile == NULL)
	{
		MessageBox(NULL, "Can't find the height map!", "Error", MB_OK);
		return;
	}
	fread(g_HeightMap, 1, nSize, pFile);
	int result = ferror(pFile);
	if (result)
	{
		MessageBox(NULL, "Can't get data!", "Error", MB_OK);
	}
	fclose(pFile);
}
int SEARCH_HEIGHT(BYTE *g_HeightMap, int XSTEP, int YSTEP)
{
	// Make sure we don't go past our array size
	int x = XSTEP % TERRAIN_RES;					// Error check our x value
	int y = YSTEP % TERRAIN_RES;					// Error check our y value
	return g_HeightMap[x + (y * TERRAIN_RES)];	// Index into our height array and return the height
}


// remember, these function have some responsibilities defined in the
// header... 
// for now these do simple things. in the future, maybe they will draw
// more nicely

void drawSky(DrawingState* st)
{
  // skymap implementation

  //glClearColor(st->sky.r,st->sky.g,st->sky.b,st->sky.a);
  //glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT /*| GL_STENCIL_BUFFER_BIT*/);
	if (st->timeOfDay < 5) glColor3f(0, 0, .2f);
	else if (st->timeOfDay < 8) glColor3f(.2f, .2f, .45f);
	else if (st->timeOfDay < 13) glColor3f(1.f, 1.f, 1.f);
	else if (st->timeOfDay < 16) glColor3f(.7f, .7f, 0.5f);
	else if (st->timeOfDay < 19) glColor3f(.2f, .2f, .3f);
	else glColor3f(0, 0, .2f);
	//glColor3f(1, 1, 1);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT /*| GL_STENCIL_BUFFER_BIT*/);

	if (!skyBoxLoaded)
	{
		skyBoxTex[0] = fetchTexture("Textures/Skybox/xneg.tga", true, true);
		skyBoxTex[1] = fetchTexture("Textures/Skybox/xpos.tga", true, true);
		skyBoxTex[2] = fetchTexture("Textures/Skybox/yneg.tga", true, true);
		skyBoxTex[3] = fetchTexture("Textures/Skybox/ypos.tga", true, true);
		skyBoxTex[4] = fetchTexture("Textures/Skybox/zneg.tga", true, true);
		skyBoxTex[5] = fetchTexture("Textures/Skybox/zpos.tga", true, true);
		skyBoxLoaded = true;
	}
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);

	// Bind the BACK texture of the sky map to the BACK side of the cube
	GLfloat x = -10000;
	GLfloat y = -10000;
	GLfloat z = -10000;
	GLfloat width = 30000;
	GLfloat height = 30000;
	GLfloat length = 30000;
	
	glBindTexture(GL_TEXTURE_2D, skyBoxTex[0]->texName);


	glDisable(GL_LIGHTING);
	// Start drawing the side as a QUAD
	glBegin(GL_QUADS);

	// Assign the texture coordinates and vertices for the BACK Side
	glTexCoord2f(1.0f, 0.0f); glVertex3f(x + width, y, z);
	glTexCoord2f(1.0f, 1.0f); glVertex3f(x + width, y + height, z);
	glTexCoord2f(0.0f, 1.0f); glVertex3f(x, y + height, z);
	glTexCoord2f(0.0f, 0.0f); glVertex3f(x, y, z);

	glEnd();

	// Bind the FRONT texture
	glBindTexture(GL_TEXTURE_2D, skyBoxTex[1]->texName);

	glBegin(GL_QUADS);

	glTexCoord2f(1.0f, 0.0f); glVertex3f(x, y, z + length);
	glTexCoord2f(1.0f, 1.0f); glVertex3f(x, y + height, z + length);
	glTexCoord2f(0.0f, 1.0f); glVertex3f(x + width, y + height, z + length);
	glTexCoord2f(0.0f, 0.0f); glVertex3f(x + width, y, z + length);
	glEnd();

	glBindTexture(GL_TEXTURE_2D, skyBoxTex[2]->texName);

	glBegin(GL_QUADS);

	// Assign the texture coordinates and vertices for the BOTTOM Side
	glTexCoord2f(0.0f, 0.0f); glVertex3f(x + width, y, z);
	glTexCoord2f(1.0f, 1.0f);  glVertex3f(x, y, z);
	glTexCoord2f(1.0f, 0.0f); glVertex3f(x, y, z + length);
	glTexCoord2f(0.0f, 1.0f);   glVertex3f(x + width, y, z + length);


	glEnd();

	// Bind the TOP texture of the sky map to the TOP side of the box
	glBindTexture(GL_TEXTURE_2D, skyBoxTex[3]->texName);

	// Start drawing the side as a QUAD
	glBegin(GL_QUADS);

	// Assign the texture coordinates and vertices for the TOP Side
	glTexCoord2f(0.0f, 0.0f);  glVertex3f(x + width, y + height, z);
	glTexCoord2f(1.0f, 0.0f); glVertex3f(x + width, y + height, z + length);
	glTexCoord2f(1.0f, 1.0f); glVertex3f(x, y + height, z + length);
	glTexCoord2f(0.0f, 1.0f); glVertex3f(x, y + height, z);

	glEnd();

	glBindTexture(GL_TEXTURE_2D, skyBoxTex[4]->texName);

	// Start drawing the side as a QUAD
	glBegin(GL_QUADS);

	glTexCoord2f(1.0f, 1.0f); glVertex3f(x, y + height, z);
	glTexCoord2f(0.0f, 1.0f); glVertex3f(x, y + height, z + length);
	glTexCoord2f(0.0f, 0.0f); glVertex3f(x, y, z + length);
	glTexCoord2f(1.0f, 0.0f); glVertex3f(x, y, z);

	glEnd();

	glBindTexture(GL_TEXTURE_2D, skyBoxTex[5]->texName);

	glBegin(GL_QUADS);

	glTexCoord2f(0.0f, 0.0f); glVertex3f(x + width, y, z);
	glTexCoord2f(1.0f, 0.0f); glVertex3f(x + width, y, z + length);
	glTexCoord2f(1.0f, 1.0f); glVertex3f(x + width, y + height, z + length);
	glTexCoord2f(0.0f, 1.0f); glVertex3f(x + width, y + height, z);
	glEnd();
	glEnable(GL_LIGHTING);
	glColor3f(1, 1, 1);
}

// for now, just draw a huge green square. 10 miles on a side (5000
// ft/mile) 
void drawEarth(DrawingState* st)
{
	if (st->timeOfDay < 5) glColor3f(0, 0, .2f);
	else if (st->timeOfDay < 8) glColor3f(.2f, .2f, .45f);
	else if (st->timeOfDay < 13) glColor3f(1.f, 1.f, 1.f);
	else if (st->timeOfDay < 16) glColor3f(.7f, .7f, 0.5f);
	else if (st->timeOfDay < 19) glColor3f(.2f, .2f, .3f);
	else glColor3f(0, 0, .2f);
		if (!TerrainInit)
		{
			TerrainInit = true;
			strcpy(path, "object_b/");
			TerrainShader = loadShader("Shader/terrain.vert", "Shader/terrain.frag", error);
			glActiveTextureARB = (PFNGLACTIVETEXTUREARBPROC)wglGetProcAddress("glActiveTextureARB");
			glMultiTexCoord2fARB = (PFNGLMULTITEXCOORD2FARBPROC)wglGetProcAddress("glMultiTexCoord2fARB");
			glFogCoordfEXT = (PFNGLFOGCOORDFEXTPROC)wglGetProcAddress("glFogCoordfEXT");
			float fogColor[4] = { 0.8f, 0.8f, 0.8f, 1.0f };

			glEnable(GL_FOG);
			glFogi(GL_FOG_MODE, GL_LINEAR);
			glFogfv(GL_FOG_COLOR, fogColor);
			glFogf(GL_FOG_START, 0.0);
			glFogf(GL_FOG_END, 50.0);
			glFogi(GL_FOG_COORDINATE_SOURCE_EXT, GL_FOG_COORDINATE_EXT);

			// LOAD TERRAIN MAPPING
			CreateTexture(dir1, "Terrain.bmp"); // Load the terrain texture
			CreateTexture(dir2, "Detail.bmp");
			// LOAD TERRAIN
			LoadRawFile("Terrain.raw", TERRAIN_RES * TERRAIN_RES, g_HeightMap);
			glEnable(GL_DEPTH_TEST);
			glEnable(GL_TEXTURE_2D);
			glEnable(GL_CULL_FACE);

			////////INIT SHADER
			CLOTH_INIT();
			char bldgPath[30] = "object_b/gemini2.obj";
			bldg.import(bldgPath);
			bldg.normalize(250.0f, true);
			//bldg.normalize(0.001f, true);
			phong = loadShader("bpShader.vert", "bpShader.frag", error);
			normalShdr = loadShader("nmShader.vert", "nmShader.frag", error);
			g_nullTexture = CreateNullTexture(2, 2);
			const ModelOBJ::Material *pMaterial = 0;
			GLuint textureId = 0;
			std::string::size_type offset = 0;
			std::string filename;

			for (int i = 0; i < bldg.getNumberOfMaterials(); ++i)
			{
				pMaterial = &bldg.getMaterial(i);

				if (pMaterial->colorMapFilename.empty())
					continue;

				strcpy(&path[9], pMaterial->colorMapFilename.c_str());
				textureId = fetchTexture(path, false, false)->texName;

				if (!textureId)
				{
					offset = pMaterial->colorMapFilename.find_last_of('\\');

					if (offset != std::string::npos)
						filename = pMaterial->colorMapFilename.substr(++offset);
					else
						filename = pMaterial->colorMapFilename;
					strcpy(&path[9], (bldg.getPath() + filename).c_str());

					textureId = fetchTexture(path, false, false)->texName;
				}

				if (textureId)
					g_modelTextures[pMaterial->colorMapFilename] = textureId;

				if (pMaterial->bumpMapFilename.empty())
					continue;
				strcpy(&path[9], pMaterial->bumpMapFilename.c_str());

				textureId = fetchTexture(path, false, false)->texName;

				if (!textureId)
				{
					offset = pMaterial->bumpMapFilename.find_last_of('\\');

					if (offset != std::string::npos)
						filename = pMaterial->bumpMapFilename.substr(++offset);
					else
						filename = pMaterial->bumpMapFilename;


					strcpy(&path[9], (bldg.getPath() + filename).c_str());

					textureId = fetchTexture(path, false, false)->texName;
				}

				if (textureId)
					g_modelTextures[pMaterial->bumpMapFilename] = textureId;
			}
		}
		//glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		int XSTEP = 0, YSTEP = 0;
		int x, y, z;							// Create some variables for readability
		bool UP_DOWN = false;
		glPushMatrix();
		glActiveTextureARB(GL_TEXTURE0_ARB);
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, dir1);


		glActiveTextureARB(GL_TEXTURE1_ARB);
		glEnable(GL_TEXTURE_2D);
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_COMBINE_ARB);
		glTexEnvi(GL_TEXTURE_ENV, GL_RGB_SCALE_ARB, 2);
		glBindTexture(GL_TEXTURE_2D, dir2);
		glMatrixMode(GL_TEXTURE);
		glLoadIdentity();

		glScalef((float)LOD, (float)LOD, 1);
		glMatrixMode(GL_MODELVIEW);
		glTranslatef(-4000, -6, -4400);
		glScalef(10, 10, 10);
		// RENDER TRIANGLES
		glBegin(GL_TRIANGLE_STRIP);
		for (XSTEP = 0; XSTEP <= TERRAIN_RES; XSTEP += STEP_SIZE)
		{
			// Check fog border
			if (UP_DOWN)
			{
				for (YSTEP = TERRAIN_RES; YSTEP >= 0; YSTEP -= STEP_SIZE)
				{
					x = XSTEP;
					y = SEARCH_HEIGHT(g_HeightMap, XSTEP, YSTEP);
					z = YSTEP;
					FOG_Enabler(g_FogDepth, (float)y);
					TEXTURE_FINDER((float)x, (float)z);
					glVertex3i(x, y, z);
					x = XSTEP + STEP_SIZE;
					y = SEARCH_HEIGHT(g_HeightMap, XSTEP + STEP_SIZE, YSTEP);
					z = YSTEP;
					FOG_Enabler(g_FogDepth, (float)y);
					TEXTURE_FINDER((float)x, (float)z);
					glVertex3i(x, y, z);
				}
			}
			else
			{
				for (YSTEP = 0; YSTEP <= TERRAIN_RES; YSTEP += STEP_SIZE)
				{
					x = XSTEP + STEP_SIZE;
					y = SEARCH_HEIGHT(g_HeightMap, XSTEP + STEP_SIZE, YSTEP);
					z = YSTEP;
					FOG_Enabler(g_FogDepth, (float)y);
					TEXTURE_FINDER((float)x, (float)z);
					glVertex3i(x, y, z);
					x = XSTEP;
					y = SEARCH_HEIGHT(g_HeightMap, XSTEP, YSTEP);
					z = YSTEP;
					FOG_Enabler(g_FogDepth, (float)y);
					TEXTURE_FINDER((float)x, (float)z);
					glVertex3i(x, y, z);
				}
			}
			UP_DOWN = !UP_DOWN;
		}
		glEnd();
		glPopMatrix();
		// Turn the second multitexture pass off
		glActiveTextureARB(GL_TEXTURE1_ARB);
		glDisable(GL_TEXTURE_2D);

		// Turn the first multitexture pass off
		glActiveTextureARB(GL_TEXTURE0_ARB);
		glDisable(GL_TEXTURE_2D);

		glEnable(GL_TEXTURE_2D);


		const ModelOBJ::Mesh *pMesh = 0;
		const ModelOBJ::Material *pMaterial = 0;
		const ModelOBJ::Vertex *pVertices = 0;
		ModelTextures::const_iterator iter;
		GLuint texture = 0;
		glPushMatrix();
		glScalef(1.0f,2.0f,1.0f);
		glTranslatef(625, 125, 570);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		for (int i = 0; i < bldg.getNumberOfMeshes(); ++i)
		{
			pMesh = &bldg.getMesh(i);
			pMaterial = pMesh->pMaterial;
			pVertices = bldg.getVertexBuffer();

			glMaterialfv(GL_FRONT_AND_BACK, GL_AMBIENT, pMaterial->ambient);
			glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, pMaterial->diffuse);
			glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, pMaterial->specular);
			glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, pMaterial->shininess * 128.0f);
			// Per fragment Blinn-Phong code path.

			glUseProgram(phong);

			// Bind the color map texture.

			texture = g_nullTexture;

			iter = g_modelTextures.find(pMaterial->colorMapFilename);

			if (iter != g_modelTextures.end())
				texture = iter->second;

			glActiveTexture(GL_TEXTURE0);
			glEnable(GL_TEXTURE_2D);
			glBindTexture(GL_TEXTURE_2D, texture);

			// Update shader parameters.

			glUniform1i(glGetUniformLocation(
				phong, "colorMap"), 0);
			glUniform1f(glGetUniformLocation(
				phong, "materialAlpha"), pMaterial->alpha);


			// Render mesh.

			glEnableClientState(GL_VERTEX_ARRAY);
			glVertexPointer(3, GL_FLOAT, bldg.getVertexSize(),
				bldg.getVertexBuffer()->position);

			glClientActiveTexture(GL_TEXTURE0);
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);
			glTexCoordPointer(2, GL_FLOAT, bldg.getVertexSize(),
				bldg.getVertexBuffer()->texCoord);

			glEnableClientState(GL_NORMAL_ARRAY);
			glNormalPointer(GL_FLOAT, bldg.getVertexSize(),
				bldg.getVertexBuffer()->normal);


			glDrawElements(GL_TRIANGLES, pMesh->triangleCount * 3, GL_UNSIGNED_INT,
				bldg.getIndexBuffer() + pMesh->startIndex);


			glDisableClientState(GL_NORMAL_ARRAY);
			glDisableClientState(GL_VERTEX_ARRAY);
		}

		glBindTexture(GL_TEXTURE_2D, 0);
		glUseProgram(0);
		glDisable(GL_BLEND);
		glPushMatrix();
		glTranslatef(-13, 107, -23);
		UPDATE();
		glPopMatrix();
		glPopMatrix();
	}

// 
// setup lighting
void setupLights(DrawingState* dr)
{
  // depending on time of day, the lighting changes
  // ambient is either night or day
  if ((dr->timeOfDay >= 5) && (dr->timeOfDay <=19)) {
	float a0[] = {.4f,.4f,.4f,.4f};
	glLightfv(GL_LIGHT0, GL_AMBIENT, a0);
  } else {
	float a1[] = {.2f,.2f,.2f,.2f};
	glLightfv(GL_LIGHT0, GL_AMBIENT, a1);
  }
  // directional, if its on, depends on what hour it is
  float pos[4] = {0,0,0,0};
  if ((dr->timeOfDay >= 5) && (dr->timeOfDay <=19)) {
	float angle = (((float)(dr->timeOfDay-5)) / 7.f) * (3.14159f/2.f);
	pos[0] = (float) cos(angle);
	pos[1] = (float) sin(angle);
  }
  else {
	  pos[1] = -1;
  }
  glLightfv(GL_LIGHT0, GL_POSITION, pos);

  glEnable(GL_LIGHTING);
  glEnable(GL_LIGHT0);
  glEnable(GL_COLOR_MATERIAL);
}

// $Header: /p/course/cs559-gleicher/private/CVS/GrTown/GrWorld.cpp,v 1.3 2008/11/11 03:48:23 gleicher Exp $
