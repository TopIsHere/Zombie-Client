package com.zombie.client.module.modules;

import com.zombie.client.ZombieClient;
import com.zombie.client.module.ClientModule;
import net.minecraft.client.Minecraft;

public final class ZoomModule extends ClientModule {
    private double oldFov = -1;
    public ZoomModule() { super("Zoom", "Hold C to zoom the camera.", true); }

    @Override public void tick(Minecraft client) {
        if (client.options == null) return;
        boolean held = ZombieClient.ZOOM != null && ZombieClient.ZOOM.isDown();
        if (held) {
            if (oldFov < 0) oldFov = client.options.fov().get();
            client.options.fov().set(Math.max(15, oldFov * 0.25));
        } else if (oldFov >= 0) {
            client.options.fov().set(oldFov);
            oldFov = -1;
        }
    }
    @Override protected void onDisable() {
        if (oldFov >= 0) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.options != null) mc.options.fov().set(oldFov);
            oldFov = -1;
        }
    }
}
