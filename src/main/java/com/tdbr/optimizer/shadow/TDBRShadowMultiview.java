package com.tdbr.optimizer.shadow;

import com.tdbr.optimizer.TDBROptimizerClientMod;
import com.tdbr.optimizer.renderer.TDBRDetector;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.util.math.Vec3d;

public class TDBRShadowMultiview {

    private static int renderPassCount = 0;
    private static Vec3d lastCameraPos = Vec3d.ZERO;
    private static long lastFrameTime = 0;

    public static void onBeginRenderPass(Camera camera) {
        long now = System.currentTimeMillis();
        Vec3d currentPos = camera.getPos();
        if (now == lastFrameTime && currentPos.squaredDistanceTo(lastCameraPos) < 1.0) {
            renderPassCount++;
        } else {
            renderPassCount = 1;
            lastFrameTime = now;
        }
        lastCameraPos = currentPos;

        if (TDBRDetector.IS_TDBR_ARCHITECTURE && renderPassCount > 1) {
            applyMultiviewOptimizations();
        }
    }

    private static void applyMultiviewOptimizations() {
        if (TDBRDetector.HAS_MULTIVIEW || TDBRDetector.HAS_MULTIVIEW2) {
            TDBROptimizerClientMod.LOGGER.info("Multiview ativo: compartilhando vertex shading entre views");
        }
        org.lwjgl.opengl.GL11.glDepthMask(true);
        org.lwjgl.opengl.GL11.glEnable(org.lwjgl.opengl.GL11.GL_DEPTH_TEST);
        org.lwjgl.opengl.GL11.glDepthFunc(org.lwjgl.opengl.GL11.GL_LEQUAL);
    }

    public static Frustum optimizeShadowFrustum(Frustum frustum, int cascadeLevel) {
        if (!TDBRDetector.IS_TDBR_ARCHITECTURE) return frustum;
        if (cascadeLevel >= 2) {
            TDBROptimizerClientMod.LOGGER.debug("Shadow cascade {}: considere reduzir resolucao em 50%", cascadeLevel);
        }
        return frustum;
    }

    public static int getRenderPassCount() { return renderPassCount; }
}
