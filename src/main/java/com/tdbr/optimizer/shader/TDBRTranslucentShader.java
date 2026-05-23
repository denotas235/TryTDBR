package com.tdbr.optimizer.shader;

import com.tdbr.optimizer.renderer.TDBRDetector;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.RenderPhase;
import org.jetbrains.annotations.Nullable;

public class TDBRTranslucentShader {

    @Nullable
    public static ShaderProgram getTranslucentShader() {
        if (TDBRDetector.IS_TDBR_ARCHITECTURE && TDBRShaderManager.TDBR_TRANSLUCENT_SHADER != null) {
            return TDBRShaderManager.TDBR_TRANSLUCENT_SHADER;
        }
        return null; // Fallback: o Minecraft usa seu proprio shader
    }

    public static RenderPhase.ShaderProgram getTDBRTranslucentPhase() {
        return new RenderPhase.ShaderProgram(TDBRTranslucentShader::getTranslucentShader);
    }
}
