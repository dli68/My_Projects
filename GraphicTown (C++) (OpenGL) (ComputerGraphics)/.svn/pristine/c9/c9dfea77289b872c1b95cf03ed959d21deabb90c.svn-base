varying vec2 fTexCoord;

uniform int uTimeOfDay;
uniform sampler2D diffuse;
uniform int transparency;

void main()
{

	// based on the time of the day set up variable coef.
	float trans=(float)transparency/10;
	float coef=1.0; // scale those component according to time
	if (uTimeOfDay < 5 || uTimeOfDay>19)
		coef = 0.1;
	else if (uTimeOfDay < 8 || uTimeOfDay >16)
		coef = 0.5;
	else
		coef = 0.9;

	// set up final color
	vec4 color=texture2D(diffuse,fTexCoord);
	color = color*coef;
	color.a = trans*0.8f+0.2f;
	gl_FragColor=color;

}
