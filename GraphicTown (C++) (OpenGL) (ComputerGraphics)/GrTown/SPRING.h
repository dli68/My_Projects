#ifndef SPRING_C
#define SPRING_C
#include "NODE.h"
#include "glm.hpp"
class SPRING
{
public:
	GLfloat tension;
	GLfloat springK;
	GLfloat springL;
	int nodeEnd1;
	int nodeEnd2;
	SPRING():nodeEnd1(-1),nodeEnd2(-1)
	{}
	~SPRING()
	{}
};

#endif