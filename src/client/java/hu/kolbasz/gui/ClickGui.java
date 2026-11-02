package hu.kolbasz.gui;

import hu.kolbasz.KolbaszClient;
import hu.kolbasz.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ClickGui extends Screen {
    private static final int WIDTH = 180;
    private int y;

    public ClickGui() {
        super(Component.literal("Kolbász Client"));
    }

    public static void init() {}

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float delta) {
        y = 20;
        g.fill(width / 2 - WIDTH / 2, 10, width / 2 + WIDTH / 2, height - 10, 0xC0101010);
        g.drawCenteredString(font, "Kolbász Client", width / 2, 14, 0xFFFF5555);

        List<Module> modules = KolbaszClient.getModuleManager().getModules();
        for (Module m : modules) {
            int bx = width / 2 - WIDTH / 2 + 5;
            int by = y;
            int bh = 20;
            boolean hovered = mouseX >= bx && mouseX <= bx + WIDTH - 10 && mouseY >= by && mouseY <= by + bh;
            int color = m.isEnabled() ? 0xFF337733 : 0xFF442222;
            if (hovered) color = m.isEnabled() ? 0xFF44AA44 : 0xFF663333;
            g.fill(bx, by, bx + WIDTH - 10, by + bh, color);
            g.drawCenteredString(font, m.getName(), width / 2, by + 6, 0xFFFFFF);
            y += bh + 4;
        }
        super.render(g, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        List<Module> modules = KolbaszClient.getModuleManager().getModules();
        int ty = 20;
        for (Module m : modules) {
            if (mouseY >= ty && mouseY <= ty + 20) {
                m.toggle();
                KolbaszClient.getModuleManager().save();
                return true;
            }
            ty += 24;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
