package com.zombie.client.module.modules;

import com.zombie.client.module.ClientModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public final class WaypointModule extends ClientModule {
    private double x, y, z;
    private boolean hasWaypoint;
    public WaypointModule() { super("Waypoint", "Set a local waypoint with the GUI's waypoint button in a future update.", false); }

    @Override public void tick(Minecraft client) {
        if (client.player == null) return;
        // Default waypoint is captured when the module is first enabled.
        if (!hasWaypoint) {
            x = client.player.getX();
            y = client.player.getY();
            z = client.player.getZ();
            hasWaypoint = true;
        }
    }
    @Override public void render(GuiGraphics graphics, net.minecraft.client.DeltaTracker delta) {
        Minecraft mc = Minecraft.getInstance();
        if (hasWaypoint && mc.player != null) {
            double distance = mc.player.position().distanceTo(new net.minecraft.world.phys.Vec3(x, y, z));
            graphics.drawString(mc.font, String.format("Waypoint: %.0f blocks (%.0f %.0f %.0f)", distance, x, y, z),
                    8, mc.getWindow().getGuiScaledHeight() - 38, 0xFFFFD36B, true);
        }
    }
    @Override protected void onDisable() { hasWaypoint = false; }
}
