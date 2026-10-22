package com.zombie.client;

import com.zombie.client.config.ConfigManager;
import com.zombie.client.module.ModuleManager;
import com.zombie.client.ui.ClickGuiScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.VanillaHudElements;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;

public final class ZombieClient implements ClientModInitializer {
    public static final String MOD_ID = "zombieclient";
    public static KeyMapping OPEN_GUI;
    public static KeyMapping ZOOM;

    @Override
    public void onInitializeClient() {
        ConfigManager.load();
        ModuleManager.registerDefaults();

        OPEN_GUI = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.zombieclient.gui", GLFW.GLFW_KEY_F6,
                KeyMapping.Category.MISC));
        ZOOM = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.zombieclient.zoom", GLFW.GLFW_KEY_C,
                KeyMapping.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_GUI.consumeClick()) {
                client.setScreen(new ClickGuiScreen());
            }
            ModuleManager.tick(client);
        });

        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "hud"),
                (graphics, tickCounter) -> ModuleManager.renderHud(graphics, tickCounter));
    }
}
