varying vec3 fNormal;

uniform int uTimeOfDay;

void main()
{

	// light source declaration
	vec3 light;
	float angle=0;
	if ((uTimeOfDay>= 5) && (uTimeOfDay<= 19)) // calculate sun position as instructor did...
	{
		angle = (((float)(uTimeOfDay - 5)) / 7.f) * (3.14159f / 2.f);
		light.x = (float)cos(angle);
		light.y = (float)sin(angle);
	}
	else
	{
		light.y = -1;
	}
		light = normalize(light);

	// based on position, the light intensity on the surface varies.
	float intensity;
	vec4 color=gl_Color;
	intensity = dot(light,fNormal);
	if (intensity > 0.95) color = vec4(color.x*0.8,color.y*0.8,color.z*0.8,1.0);
	else if (intensity > 0.5) color = vec4(color.x*0.6,color.y*0.6,color.z*0.6,1.0);
	else if (intensity > 0.25) color = vec4(color.x*0.4,color.y*0.4,color.z*0.4,1.0);
	else color = vec4(color.x*0.2,color.y*0.2,color.z*0.2,1.0);

	gl_FragColor=color;

}