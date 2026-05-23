package com.tdbr.optimizer;

import com.tdbr.optimizer.command.TDBRCommand;
import com.tdbr.optimizer.config.TDBRConfig;
import com.tdbr.optimizer.renderer.TDBRDetector;
import com.tdbr.optimizer.renderer.extensions.GLESSExtensionBridge;
import com.tdbr.optimizer.shader.TDBRShaderManager;
import com.tdbr.optimizer.texture.TDBRASTCTextureLoader;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TDBROptimizerClientMod implements ClientModInitializer {
    public static final String MOD_ID = "tdbr-optimizer";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final TDBRConfig CONFIG = new TDBRConfig();

    @Override
    public void onInitializeClient() {
        LOGGER.info("TDBR Optimizer v1.0.0 - Inicializando...");
        TDBRShaderManager.register();
        TDBRASTCTextureLoader.init();
        TDBRCommand.register();
    }
}
