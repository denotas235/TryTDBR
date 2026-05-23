package com.tdbr.optimizer.shader;

import com.tdbr.optimizer.TDBROptimizerClientMod;
import com.tdbr.optimizer.renderer.TDBRDetector;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

import java.io.IOException;

public class TDBRShaderManager {

    public static ShaderProgram TDBR_TRANSLUCENT_SHADER;

    public static void register() {
        CoreShaderRegistrationCallback.EVENT.register((resourceFactory, register) -> {
            try {
                register.accept(
                    new Identifier("tdbr-optimizer", "core/tdbr_translucent"),
                    VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL
                );
                TDBROptimizerClientMod.LOGGER.info("TDBR Translucent Shader registrado.");
            } catch (IOException e) {
                TDBROptimizerClientMod.LOGGER.error("Falha ao registrar shader TDBR", e);
            }
        });
    }

    public static void onShaderLoad(ShaderProgram program) {
        if (program.getName().equals("tdbr-optimizer/core/tdbr_translucent")) {
            TDBR_TRANSLUCENT_SHADER = program;
            TDBROptimizerClientMod.LOGGER.info("TDBR Translucent Shader carregado! FB Fetch: {}", 
                TDBRDetector.HAS_FRAMEBUFFER_FETCH);
        }
    }
}
