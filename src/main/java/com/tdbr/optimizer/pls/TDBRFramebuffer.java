package com.tdbr.optimizer.pls;

import com.tdbr.optimizer.TDBROptimizerClientMod;
import com.tdbr.optimizer.renderer.TDBRDetector;
import net.minecraft.client.gl.Framebuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class TDBRFramebuffer extends Framebuffer {

    private int normalTexture = -1;
    private int depthTexture = -1;
    private boolean usePLS;

    public TDBRFramebuffer(int width, int height) {
        super(true);
        this.usePLS = TDBRDetector.HAS_PIXEL_LOCAL_STORAGE;
        resize(width, height, true);
    }

    @Override
    public void resize(int width, int height, boolean clearError) {
        super.resize(width, height, clearError);
        if (this.normalTexture == -1) {
            this.normalTexture = GL11.glGenTextures();
            this.depthTexture = GL11.glGenTextures();
        }
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.normalTexture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL30.GL_RGBA8, width, height, 0,
            GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (java.nio.IntBuffer) null);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.depthTexture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL30.GL_DEPTH_COMPONENT24, width, height, 0,
            GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (java.nio.IntBuffer) null);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.fbo);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT1,
            GL11.GL_TEXTURE_2D, this.normalTexture, 0);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT,
            GL11.GL_TEXTURE_2D, this.depthTexture, 0);
        int status = GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER);
        if (status != GL30.GL_FRAMEBUFFER_COMPLETE) {
            TDBROptimizerClientMod.LOGGER.error("TDBRFramebuffer incompleto: {}", status);
        }
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        TDBROptimizerClientMod.LOGGER.info("TDBRFramebuffer {}x{} criado. PLS: {}", width, height, usePLS);
    }

    public int getNormalTexture() { return this.normalTexture; }
    public int getDepthTexture() { return this.depthTexture; }

    @Override
    public void delete() {
        super.delete();
        if (this.normalTexture > -1) { GL11.glDeleteTextures(this.normalTexture); this.normalTexture = -1; }
        if (this.depthTexture > -1) { GL11.glDeleteTextures(this.depthTexture); this.depthTexture = -1; }
    }
}
