package com.tdbr.optimizer.renderer.extensions;

import com.tdbr.optimizer.TDBROptimizerClientMod;
import org.lwjgl.egl.EGL;

public class GLESSExtensionBridge {

    public static boolean isGLESContext() {
        String version = org.lwjgl.opengl.GL11.glGetString(org.lwjgl.opengl.GL11.GL_VERSION);
        return version != null && version.contains("OpenGL ES");
    }

    public static void tryLoadCriticalPointers() {
        if (!isGLESContext()) {
            TDBROptimizerClientMod.LOGGER.info("Contexto nao e GLES. Pulando EGL bridge.");
            return;
        }
        TDBROptimizerClientMod.LOGGER.info("Contexto GLES detectado. Verificando EGL proc addresses...");
        long ptr = EGL.getProcedureAddress("glFramebufferFetchBarrierEXT");
        if (ptr != 0) {
            TDBROptimizerClientMod.LOGGER.info("glFramebufferFetchBarrierEXT disponivel em EGL!");
        }
        long ptr2 = EGL.getProcedureAddress("glPixelLocalStorageBarrierEXT");
        if (ptr2 != 0) {
            TDBROptimizerClientMod.LOGGER.info("glPixelLocalStorageBarrierEXT disponivel em EGL!");
        }
    }
}
