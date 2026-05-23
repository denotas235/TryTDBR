package com.tdbr.optimizer.shader;

import com.tdbr.optimizer.renderer.TDBRDetector;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.ShaderProgramKeys;
import org.jetbrains.annotations.Nullable;

public class TDBRTranslucentShader {

    @Nullable
    public static ShaderProgram getTranslucentShader() {
        if (TDBRDetector.IS_TDBR_ARCHITECTURE && TDBRShaderManager.TDBR_TRANSLUCENT_SHADER != null) {
            return TDBRShaderManager.TDBR_TRANSLUCENT_SHADER;
        }
        return ShaderProgramKeys.RENDERTYPE_TRANSLUCENT.get();
    }

    public static RenderPhase.Shader getTDBRTranslucentPhase() {
        return new RenderPhase.Shader(TDBRTranslucentShader::getTranslucentShader);
    }
}
