package com.tdbr.optimizer.mixin;

import com.tdbr.optimizer.TDBROptimizerClientMod;
import com.tdbr.optimizer.renderer.TDBRDetector;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Queue;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

    // Removido @Shadow - vamos usar reflexao se necessario
    private Queue<Particle> tdbrParticles;

    @Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V", at = @At("HEAD"), cancellable = true)
    private void onAddParticle(Particle particle, CallbackInfo ci) {
        if (TDBRDetector.IS_TDBR_ARCHITECTURE && TDBROptimizerClientMod.CONFIG.enableParticleCulling) {
            // Contagem simplificada - o Minecraft gerencia particulas internamente
            // Esta e uma aproximacao; o efeito real vem do limitador do proprio Minecraft
            if (Math.random() > 0.7) { // Culling probabilistico em TDBR
                ci.cancel();
            }
        }
    }
}
