#pragma once

namespace displayUtils
{

	//! Initialize fullscreen quad
	//! @param vao this parameter will return new, initialized vao
	//! @param vbo this parameter will return new, initialized vbo
	void initQuadGeometry(GLuint* vao, GLuint* vbo);

	//! Draw given quad. Takes care of disabling VAO 
	void drawQuad(GLuint vao);

}