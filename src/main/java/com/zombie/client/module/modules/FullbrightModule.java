package com.zombie.client.module.modules;

import com.zombie.client.module.ClientModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;

public final class FullbrightModule extends ClientModule {
    private double previousGamma;
    public FullbrightModule() { super("Fullbright", "Raises gamma for visibility.", false); }

    @Override protected void onEnable() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options != null) {
            previousGamma = mc.options.gamma().get();
            mc.options.gamma().set(16.0);
        }
    }
    @Override protected void onDisable() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options != null) mc.options.gamma().set(previousGamma);
    }
}
