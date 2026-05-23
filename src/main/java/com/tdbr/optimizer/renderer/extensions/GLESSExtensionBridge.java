package com.tdbr.optimizer.renderer.extensions;

import com.tdbr.optimizer.TDBROptimizerClientMod;

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
        TDBROptimizerClientMod.LOGGER.info("Contexto GLES detectado.");
        // EGL calls removidos - nao disponiveis em desktop OpenGL
    }
}
