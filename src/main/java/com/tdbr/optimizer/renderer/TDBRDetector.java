package com.tdbr.optimizer.renderer;

import com.tdbr.optimizer.TDBROptimizerClientMod;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class TDBRDetector {
    public static boolean IS_MALI_GPU = false;
    public static boolean IS_TDBR_ARCHITECTURE = false;
    public static boolean IS_GLES_CONTEXT = false;
    public static String GPU_VENDOR = "Unknown";
    public static String GPU_RENDERER = "Unknown";
    public static String GL_VERSION = "Unknown";

    public static boolean HAS_FRAMEBUFFER_FETCH = false;
    public static boolean HAS_PIXEL_LOCAL_STORAGE = false;
    public static boolean HAS_TILE_COMPRESSION_16 = false;
    public static boolean HAS_TILE_COMPRESSION_24 = false;
    public static boolean HAS_TILE_COMPRESSION_32 = false;
    public static boolean HAS_ASTC_HDR = false;
    public static boolean HAS_ASTC_LDR = false;
    public static boolean HAS_BUFFER_STORAGE = false;
    public static boolean HAS_DSA = false;
    public static boolean HAS_MULTIVIEW = false;
    public static boolean HAS_MULTIVIEW2 = false;
    public static boolean HAS_MULTISAMPLED_RTT = false;
    public static boolean HAS_MULTISAMPLED_RTT2 = false;
    public static boolean HAS_EXPLICIT_MEMORY_ACCESS = false;
    public static boolean HAS_EXPLICIT_ATTRIBUTE_ACCESS = false;
    public static boolean HAS_SHADER_IMAGE_LOAD_STORE = false;

    public static void detect() {
        GPU_VENDOR = safeGetString(GL11.GL_VENDOR);
        GPU_RENDERER = safeGetString(GL11.GL_RENDERER);
        GL_VERSION = safeGetString(GL11.GL_VERSION);
        IS_GLES_CONTEXT = GL_VERSION != null && GL_VERSION.contains("OpenGL ES");
        IS_MALI_GPU = GPU_VENDOR != null && GPU_VENDOR.toLowerCase().contains("arm");
        IS_TDBR_ARCHITECTURE = IS_MALI_GPU || (GPU_RENDERER != null &&
            (GPU_RENDERER.contains("Mali") || GPU_RENDERER.contains("Immortalis")));

        TDBROptimizerClientMod.LOGGER.info("=== TDBR DETECTOR ===");
        TDBROptimizerClientMod.LOGGER.info("Vendor: {}", GPU_VENDOR);
        TDBROptimizerClientMod.LOGGER.info("Renderer: {}", GPU_RENDERER);
        TDBROptimizerClientMod.LOGGER.info("Version: {}", GL_VERSION);
        TDBROptimizerClientMod.LOGGER.info("GLES Context: {}", IS_GLES_CONTEXT);
        TDBROptimizerClientMod.LOGGER.info("TDBR Architecture: {}", IS_TDBR_ARCHITECTURE);

        scanExtensions();

        if (IS_TDBR_ARCHITECTURE) {
            TDBROptimizerClientMod.LOGGER.info(">>> GPU TDBR detectada! Aplicando otimizacoes...");
        }
    }

    private static void scanExtensions() {
        int count = GL30.glGetInteger(GL30.GL_NUM_EXTENSIONS);
        for (int i = 0; i < count; i++) {
            String ext = GL30.glGetStringi(GL30.GL_EXTENSIONS, i);
            if (ext == null) continue;
            switch (ext) {
                case "GL_EXT_shader_framebuffer_fetch":
                case "GL_ARM_shader_framebuffer_fetch":
                case "GL_EXT_shader_framebuffer_fetch_non_coherent":
                    HAS_FRAMEBUFFER_FETCH = true; break;
                case "GL_EXT_shader_pixel_local_storage":
                    HAS_PIXEL_LOCAL_STORAGE = true; break;
                case "GL_ARM_texture_storage_compression_fixed_rate_16":
                    HAS_TILE_COMPRESSION_16 = true; break;
                case "GL_ARM_texture_storage_compression_fixed_rate_24":
                    HAS_TILE_COMPRESSION_24 = true; break;
                case "GL_ARM_texture_storage_compression_fixed_rate_32":
                    HAS_TILE_COMPRESSION_32 = true; break;
                case "GL_KHR_texture_compression_astc_hdr":
                    HAS_ASTC_HDR = true; break;
                case "GL_KHR_texture_compression_astc_ldr":
                case "GL_OES_texture_compression_astc":
                    HAS_ASTC_LDR = true; break;
                case "GL_ARB_buffer_storage":
                case "GL_EXT_buffer_storage":
                    HAS_BUFFER_STORAGE = true; break;
                case "GL_ARB_direct_state_access":
                case "GL_EXT_direct_state_access":
                    HAS_DSA = true; break;
                case "GL_OVR_multiview":
                    HAS_MULTIVIEW = true; break;
                case "GL_OVR_multiview2":
                    HAS_MULTIVIEW2 = true; break;
                case "GL_EXT_multisampled_render_to_texture":
                    HAS_MULTISAMPLED_RTT = true; break;
                case "GL_EXT_multisampled_render_to_texture2":
                    HAS_MULTISAMPLED_RTT2 = true; break;
                case "GL_ARM_explicit_memory_access":
                    HAS_EXPLICIT_MEMORY_ACCESS = true; break;
                case "GL_ARM_explicit_attribute_access":
                    HAS_EXPLICIT_ATTRIBUTE_ACCESS = true; break;
                case "GL_EXT_shader_image_load_store":
                case "GL_ARB_shader_image_load_store":
                    HAS_SHADER_IMAGE_LOAD_STORE = true; break;
            }
        }
        if (TDBROptimizerClientMod.CONFIG.logExtensions) {
            TDBROptimizerClientMod.LOGGER.info("FB Fetch: {}", HAS_FRAMEBUFFER_FETCH);
            TDBROptimizerClientMod.LOGGER.info("PLS: {}", HAS_PIXEL_LOCAL_STORAGE);
            TDBROptimizerClientMod.LOGGER.info("Tile Compression: {}/{}/{}", HAS_TILE_COMPRESSION_16, HAS_TILE_COMPRESSION_24, HAS_TILE_COMPRESSION_32);
            TDBROptimizerClientMod.LOGGER.info("ASTC HDR/LDR: {}/{}", HAS_ASTC_HDR, HAS_ASTC_LDR);
            TDBROptimizerClientMod.LOGGER.info("Buffer Storage: {}", HAS_BUFFER_STORAGE);
            TDBROptimizerClientMod.LOGGER.info("DSA: {}", HAS_DSA);
            TDBROptimizerClientMod.LOGGER.info("Multiview/2: {}/{}", HAS_MULTIVIEW, HAS_MULTIVIEW2);
            TDBROptimizerClientMod.LOGGER.info("MS RTT/2: {}/{}", HAS_MULTISAMPLED_RTT, HAS_MULTISAMPLED_RTT2);
            TDBROptimizerClientMod.LOGGER.info("Explicit Memory/Attribute: {}/{}", HAS_EXPLICIT_MEMORY_ACCESS, HAS_EXPLICIT_ATTRIBUTE_ACCESS);
            TDBROptimizerClientMod.LOGGER.info("Shader Image Load/Store: {}", HAS_SHADER_IMAGE_LOAD_STORE);
        }
    }

    private static String safeGetString(int name) {
        try { return GL11.glGetString(name); } catch (Exception e) { return "Unknown"; }
    }
}
