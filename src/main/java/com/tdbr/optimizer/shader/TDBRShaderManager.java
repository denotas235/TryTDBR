package com.tdbr.optimizer.shader;

import com.tdbr.optimizer.TDBROptimizerClientMod;
import com.tdbr.optimizer.renderer.TDBRDetector;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class TDBRShaderManager {

    public static ShaderProgram TDBR_TRANSLUCENT_SHADER;

    public static void register() {
        CoreShaderRegistrationCallback.EVENT.register(context -> {
            context.register(
                Identifier.of("tdbr-optimizer", "core/tdbr_translucent"),
                VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL,
                program -> {
                    TDBR_TRANSLUCENT_SHADER = program;
                    TDBROptimizerClientMod.LOGGER.info("TDBR Translucent Shader carregado! FB Fetch: {}", 
                        TDBRDetector.HAS_FRAMEBUFFER_FETCH);
                }
            );
            TDBROptimizerClientMod.LOGGER.info("TDBR Translucent Shader registrado.");
        });
    }
}
