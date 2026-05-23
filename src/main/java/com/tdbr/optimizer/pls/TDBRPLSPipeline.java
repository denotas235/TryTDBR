package com.tdbr.optimizer.pls;

import com.tdbr.optimizer.TDBROptimizerClientMod;
import com.tdbr.optimizer.renderer.TDBRDetector;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.DiffuseLighting;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class TDBRPLSPipeline {

    private static TDBRFramebuffer gBuffer;
    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        MinecraftClient client = MinecraftClient.getInstance();
        gBuffer = new TDBRFramebuffer(client.getWindow().getFramebufferWidth(), 
                                      client.getWindow().getFramebufferHeight());
        initialized = true;
        TDBROptimizerClientMod.LOGGER.info("PLS Pipeline inicializado. PLS disponivel: {}", 
            TDBRDetector.HAS_PIXEL_LOCAL_STORAGE);
    }

    public static void compose(DiffuseLighting lighting) {
        if (!initialized || gBuffer == null) return;
        Framebuffer main = MinecraftClient.getInstance().getFramebuffer();
        GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, gBuffer.fbo);
        GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, main.fbo);
        int width = main.textureWidth;
        int height = main.textureHeight;
        GL30.glBlitFramebuffer(0, 0, width, height, 0, 0, width, height,
            GL11.GL_COLOR_BUFFER_BIT, GL11.GL_LINEAR);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, main.fbo);
    }

    public static void onWindowResize(int width, int height) {
        if (gBuffer != null) gBuffer.resize(width, height, true);
    }

    public static TDBRFramebuffer getGBuffer() { return gBuffer; }
}
