
const float pi = 3.14159;

varying vec2 fTexCoord;

uniform float time;

void main() 
{
	// procedure texture - create waves
	vec4 fPos = gl_Vertex;
	fPos.y = fPos.y + 0.2*sin(time+fPos.x+fPos.z)+0.1*sin(2*(time+fPos.x+fPos.z));
	gl_Position =gl_ModelViewProjectionMatrix * fPos;
	fTexCoord.x=fPos.x/40.0;
	fTexCoord.y=fPos.z/40.0;
}
