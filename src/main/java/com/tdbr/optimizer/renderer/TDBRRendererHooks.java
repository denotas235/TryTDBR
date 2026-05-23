package com.tdbr.optimizer.renderer;

import org.lwjgl.opengl.GL11;

public class TDBRRendererHooks {

    public static void applyEarlyZOptimizations() {
        if (!TDBRDetector.IS_TDBR_ARCHITECTURE) return;
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glDepthMask(true);
        if (TDBRDetector.HAS_FRAMEBUFFER_FETCH) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }
    }

    public static void applyTransparentPassOptimizations() {
        if (!TDBRDetector.IS_TDBR_ARCHITECTURE) return;
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
    }

    public static void onPreShadowRender() {
        if (!TDBRDetector.IS_TDBR_ARCHITECTURE) return;
        if (TDBRDetector.HAS_MULTIVIEW || TDBRDetector.HAS_MULTIVIEW2) {
            // Hook para futura implementacao de multiview shadow mapping
        }
    }
}
