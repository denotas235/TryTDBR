package com.tdbr.optimizer.mixin;

import com.tdbr.optimizer.renderer.TDBRDetector;
import com.tdbr.optimizer.renderer.TDBRRendererHooks;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "renderWorld", at = @At("HEAD"))
    private void onRenderWorldHead(RenderTickCounter tickCounter, CallbackInfo ci) {
        if (TDBRDetector.IS_TDBR_ARCHITECTURE) {
            TDBRRendererHooks.onPreShadowRender();
        }
    }
}
