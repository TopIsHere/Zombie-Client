package com.zombie.client.module;

import com.zombie.client.ZombieClient;
import com.zombie.client.config.ConfigManager;
import com.zombie.client.module.modules.EntityGlowModule;
import com.zombie.client.module.modules.FullbrightModule;
import com.zombie.client.module.modules.SprintAssistModule;
import com.zombie.client.module.modules.WaypointModule;
import com.zombie.client.module.modules.ZoomModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModuleManager {
    private static final List<ClientModule> MODULES = new ArrayList<>();
    private ModuleManager() {}

    public static void registerDefaults() {
        MODULES.clear();
        MODULES.add(new FullbrightModule());
        MODULES.add(new ZoomModule());
        MODULES.add(new SprintAssistModule());
        MODULES.add(new EntityGlowModule());
        MODULES.add(new WaypointModule());
        ConfigManager.apply(MODULES);
    }

    public static List<ClientModule> all() { return Collections.unmodifiableList(MODULES); }
    public static void tick(Minecraft client) {
        for (ClientModule module : MODULES) if (module.enabled()) module.tick(client);
        ConfigManager.saveIfDirty();
    }
    public static void renderHud(GuiGraphics graphics, DeltaTracker delta) {
        int y = 8;
        Minecraft mc = Minecraft.getInstance();
        graphics.drawString(mc.font, "Zombie Client", 8, y, 0xFF7CFF6B, true);
        y += 12;
        graphics.drawString(mc.font, "FPS: " + mc.getFps(), 8, y, 0xFFFFFFFF, true);
        y += 12;
        if (mc.player != null) {
            var p = mc.player;
            graphics.drawString(mc.font,
                    String.format("XYZ: %.1f %.1f %.1f", p.getX(), p.getY(), p.getZ()),
                    8, y, 0xFFFFFFFF, true);
            y += 12;
            graphics.drawString(mc.font, "Facing: " + p.getDirection().getName(),
                    8, y, 0xFFFFFFFF, true);
            y += 14;
        }
        for (ClientModule module : MODULES) {
            if (module.enabled()) {
                graphics.drawString(mc.font, module.name(), 8, y, 0xFF7CFF6B, true);
                y += 11;
            }
        }
        for (ClientModule module : MODULES) if (module.enabled()) module.render(graphics, delta);
    }

    public static void changed() { ConfigManager.markDirty(); }
}
