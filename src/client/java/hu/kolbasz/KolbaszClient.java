package hu.kolbasz;

import hu.kolbasz.gui.KolbaszHud;
import hu.kolbasz.gui.ClickGui;
import hu.kolbasz.module.ModuleManager;
import hu.kolbasz.modules.Fullbright;
import hu.kolbasz.modules.FpsHud;
import hu.kolbasz.modules.Keystrokes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class KolbaszClient implements ClientModInitializer {
    public static final String MOD_ID = "kolbasz";
    private static ModuleManager moduleManager;
    private static KeyMapping openGuiKey;

    @Override
    public void onInitializeClient() {
        moduleManager = new ModuleManager();
        moduleManager.register(new Fullbright());
        moduleManager.register(new FpsHud());
        moduleManager.register(new Keystrokes());

        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "kolbasz.opengui",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "key.categories.misc"
        ));

        KolbaszHud.init();
        ClickGui.init();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.consumeClick()) {
                Minecraft mc = Minecraft.getInstance();
                mc.setScreen(new ClickGui());
            }
        });
    }

    public static ModuleManager getModuleManager() {
        return moduleManager;
    }
}
