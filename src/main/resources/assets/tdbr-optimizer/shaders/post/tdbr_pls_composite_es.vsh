#version 300 es

in vec2 Position;

out vec2 texCoord;

const vec2[] corners = vec2[](
    vec2(0.0, 0.0),
    vec2(1.0, 0.0),
    vec2(1.0, 1.0),
    vec2(0.0, 1.0)
);

void main() {
    vec2 corner = corners[gl_VertexID];
    gl_Position = vec4(Position * 2.0 - 1.0, 0.0, 1.0);
    texCoord = corner;
}
