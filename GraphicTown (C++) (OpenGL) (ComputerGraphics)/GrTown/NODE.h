#ifndef NODE_C
#define NODE_C

#pragma pack(push)
#pragma pack(4)
#include "glm.hpp"
class NODE
{
public:
	GLfloat m;
	glm::vec3 pos;
	glm::vec3 v;
	bool holded;
	glm::vec3 normal;
	glm::vec2 tex;
};

#pragma pack(pop)

#endif
