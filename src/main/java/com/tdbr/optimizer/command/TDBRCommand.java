package com.tdbr.optimizer.command;

import com.mojang.brigadier.CommandDispatcher;
import com.tdbr.optimizer.renderer.TDBRDetector;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class TDBRCommand {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("tdbr")
                .then(ClientCommandManager.literal("info").executes(ctx -> {
                    FabricClientCommandSource s = ctx.getSource();
                    s.sendFeedback(Text.literal("§6=== TDBR Optimizer ==="));
                    s.sendFeedback(Text.literal("§eGPU: §f" + TDBRDetector.GPU_RENDERER));
                    s.sendFeedback(Text.literal("§eVendor: §f" + TDBRDetector.GPU_VENDOR));
                    s.sendFeedback(Text.literal("§eVersao GL: §f" + TDBRDetector.GL_VERSION));
                    s.sendFeedback(Text.literal("§eTDBR Detectado: §f" + TDBRDetector.IS_TDBR_ARCHITECTURE));
                    s.sendFeedback(Text.literal("§eGLES Context: §f" + TDBRDetector.IS_GLES_CONTEXT));
                    s.sendFeedback(Text.literal("§6=== Extensoes ==="));
                    s.sendFeedback(Text.literal("§eFB Fetch: §f" + TDBRDetector.HAS_FRAMEBUFFER_FETCH));
                    s.sendFeedback(Text.literal("§ePLS: §f" + TDBRDetector.HAS_PIXEL_LOCAL_STORAGE));
                    s.sendFeedback(Text.literal("§eASTC HDR/LDR: §f" + TDBRDetector.HAS_ASTC_HDR + "/" + TDBRDetector.HAS_ASTC_LDR));
                    s.sendFeedback(Text.literal("§eMultiview: §f" + TDBRDetector.HAS_MULTIVIEW));
                    s.sendFeedback(Text.literal("§eBuffer Storage: §f" + TDBRDetector.HAS_BUFFER_STORAGE));
                    s.sendFeedback(Text.literal("§eDSA: §f" + TDBRDetector.HAS_DSA));
                    return 1;
                }))
                .then(ClientCommandManager.literal("status").executes(ctx -> {
                    ctx.getSource().sendFeedback(Text.literal(
                        "§aTDBR Optimizer ativo. Overdraw reduction: " + TDBRDetector.IS_TDBR_ARCHITECTURE
                    ));
                    return 1;
                }))
            );
        });
    }
}
