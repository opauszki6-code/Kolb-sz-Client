package hu.kolbasz.gui;

import hu.kolbasz.KolbaszClient;
import hu.kolbasz.module.Module;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;
import net.minecraft.resources.Identifier;

public class KolbaszHud {
    public static void init() {
        HudElementRegistry.addLast(
                Identifier.fromNamespaceAndPath(KolbaszClient.MOD_ID, "hud"),
                KolbaszHud::render
        );
    }

    private static void render(GuiGraphics graphics, DeltaTracker tickCounter) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        int x = 4, y = 4;
        // Watermark
        graphics.drawString(mc.font, "Kolbász Client", x, y, 0xFFFF5555, true);
        y += 12;

        for (Module m : KolbaszClient.getModuleManager().getModules()) {
            if (m.isEnabled()) {
                graphics.drawString(mc.font, m.getName(), x, y, 0xFF55FF55, true);
                y += 12;
            }
        }
    }
}
