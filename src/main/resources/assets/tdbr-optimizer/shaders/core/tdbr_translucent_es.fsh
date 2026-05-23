#version 300 es
#extension GL_EXT_shader_framebuffer_fetch : require
#extension GL_EXT_shader_pixel_local_storage : require

precision highp float;
precision highp int;

uniform sampler2D Sampler0;
uniform sampler2D Sampler2;
uniform vec4 ColorModulator;

in vec4 vertexColor;
in vec2 texCoord0;
in vec2 texCoord2;

layout(location = 0) inout vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    vec4 lightColor = texture(Sampler2, texCoord2);
    color.rgb *= lightColor.rgb;
    if (color.a < 0.01) {
        discard;
    }
    fragColor = mix(fragColor, color, color.a);
}
