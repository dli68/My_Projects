const float pi = 3.14159;

varying vec3 fNormal;

void main() 
{
 
	 fNormal = normalize(gl_NormalMatrix * gl_Normal);   
	 gl_FrontColor = gl_Color;    
	 gl_Position= gl_ModelViewProjectionMatrix*gl_Vertex;
}
