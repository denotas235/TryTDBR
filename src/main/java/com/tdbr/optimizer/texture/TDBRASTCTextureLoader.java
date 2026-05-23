package com.tdbr.optimizer.texture;

import com.tdbr.optimizer.TDBROptimizerClientMod;
import com.tdbr.optimizer.renderer.TDBRDetector;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

public class TDBRASTCTextureLoader {

    public static void init() {
        if (!TDBRDetector.HAS_ASTC_LDR && !TDBRDetector.HAS_ASTC_HDR) {
            TDBROptimizerClientMod.LOGGER.info("ASTC nao suportado. Pulando loader.");
            return;
        }
        TDBROptimizerClientMod.LOGGER.info("ASTC Loader ativo.");
    }

    public static boolean tryLoadASTC(TextureManager manager, Identifier id) {
        if (!TDBRDetector.HAS_ASTC_LDR && !TDBRDetector.HAS_ASTC_HDR) return false;
        Identifier astcId = Identifier.of(id.getNamespace(), 
            id.getPath().replace(".png", ".astc"));
        var resourceManager = MinecraftClient.getInstance().getResourceManager();
        if (resourceManager.getResource(astcId).isPresent()) {
            ASTCTexture astcTex = new ASTCTexture(id);
            manager.registerTexture(id, astcTex);
            return true;
        }
        return false;
    }
}
