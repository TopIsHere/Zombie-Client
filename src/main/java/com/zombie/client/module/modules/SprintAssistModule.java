package com.zombie.client.module.modules;

import com.zombie.client.module.ClientModule;
import net.minecraft.client.Minecraft;

public final class SprintAssistModule extends ClientModule {
    public SprintAssistModule() { super("Sprint Assist", "Sprints when moving forward and allowed.", false); }
    @Override public void tick(Minecraft client) {
        if (client.player == null || client.options == null) return;
        if (client.options.keyUp.isDown() && !client.player.isCrouching() && client.player.hasEffect(net.minecraft.world.effect.MobEffects.BLINDNESS) == false) {
            client.player.setSprinting(true);
        }
    }
}
