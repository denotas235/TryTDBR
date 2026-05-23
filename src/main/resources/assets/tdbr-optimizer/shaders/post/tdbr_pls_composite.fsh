#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D NormalSampler;
uniform sampler2D DepthSampler;

uniform vec2 InSize;
uniform vec2 OutSize;

in vec2 texCoord;

out vec4 fragColor;

const vec3 LIGHT_DIR = normalize(vec3(0.5, 1.0, 0.3));

void main() {
    vec2 uv = texCoord;
    vec4 diffuse = texture(DiffuseSampler, uv);
    vec4 normalData = texture(NormalSampler, uv);
    float depth = texture(DepthSampler, uv).r;
    vec3 normal = normalize(normalData.rgb * 2.0 - 1.0);
    float NdotL = max(dot(normal, LIGHT_DIR), 0.0);
    vec3 ambient = diffuse.rgb * 0.3;
    vec3 lit = diffuse.rgb * NdotL * 0.7;
    vec3 finalColor = ambient + lit;
    float fogFactor = smoothstep(0.8, 1.0, depth);
    finalColor = mix(finalColor, vec3(0.6, 0.8, 1.0), fogFactor * 0.5);
    fragColor = vec4(finalColor, diffuse.a);
}
