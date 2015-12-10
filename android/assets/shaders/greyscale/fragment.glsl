#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
	vec4 textureColor = texture2D(u_texture, v_texCoords);
	float averageColor = (textureColor.x + textureColor.y + textureColor.z) / 3.0;
    gl_FragColor = vec4(vec3(averageColor), 1.0);
}
