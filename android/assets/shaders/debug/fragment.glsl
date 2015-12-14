#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform int u_textureWidth;
uniform int u_textureHeight;
uniform int u_texOffsetX;
uniform int u_texOffsety;
uniform int u_numLights;
uniform vec3 u_lightColors[128];
uniform vec3 u_lightProps[128];

void main() {
	vec3 totalLights = vec3(0.0, 0.0, 0.0);
	vec2 worldPos = vec2(floor(v_texCoords.x * float(u_textureWidth)) + float(u_texOffsetX), floor(v_texCoords.y * float(u_textureHeight)) + float(u_texOffsety));
	for(int i = 0; i < u_numLights; i++){
		float dist = distance(u_lightProps[i].xy, worldPos);
		float light_percent = 1.0 - pow(dist / u_lightProps[i].z, .4);
		totalLights = totalLights + (u_lightColors[i] * light_percent);
	}
	normalize(totalLights);
	gl_FragColor = (v_color * texture2D(u_texture, v_texCoords)) * vec4(totalLights, 1);
}
