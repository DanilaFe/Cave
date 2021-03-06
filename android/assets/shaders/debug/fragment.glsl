#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform sampler2D u_normalTexture;
uniform int u_textureWidth;
uniform int u_textureHeight;
uniform float u_texOffsetX;
uniform float u_texOffsetY;
uniform int u_numLights;
uniform vec3 u_lightColors[64];
uniform vec3 u_lightProps[64];
uniform vec3 u_ambient;

void main() {
	vec3 totalLights = vec3(0.0, 0.0, 0.0);
	vec2 worldPos = floor(vec2(v_texCoords.x * float(u_textureWidth) + u_texOffsetX, v_texCoords.y * float(u_textureHeight) + u_texOffsetY));
	vec3 normalVec = texture2D(u_normalTexture, v_texCoords).xyz;
	normalVec.xy -= .5;
    normalVec = normalize(normalVec);
	for(int i = 0; i < u_numLights; i++){
        vec3 light = u_lightProps[i];
        light.xy = floor(light.xy);
		float light_percent = 1.0 - pow(distance(light.xy, worldPos) / light.z, .3);
        light_percent = max(light_percent, 0.0);
		vec3 lightVec = normalize(vec3(light.x - worldPos.x, light.y - worldPos.y, 30));
		float diffuse = max(dot(normalVec, lightVec), 0.0);
		totalLights = totalLights + (u_lightColors[i] * light_percent * diffuse);
	}
    totalLights = totalLights + u_ambient;
	normalize(totalLights);
	gl_FragColor = (v_color * texture2D(u_texture, v_texCoords)) * vec4(totalLights, 1);
}
