package com.zombie.client.module.modules;

import com.zombie.client.module.ClientModule;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

import java.util.IdentityHashMap;
import java.util.Map;

public final class EntityGlowModule extends ClientModule {
    private final Map<Entity, Boolean> oldGlow = new IdentityHashMap<>();
    public EntityGlowModule() { super("Entity ESP", "Outlines visible entities using the vanilla glow effect.", false); }

    @Override public void tick(Minecraft client) {
        if (client.level == null || client.player == null) return;
        for (Entity entity : client.level.entitiesForRendering()) {
            if (entity == client.player) continue;
            oldGlow.putIfAbsent(entity, entity.hasGlowingTag());
            entity.setGlowingTag(true);
        }
    }
    @Override protected void onDisable() { restore(); }
    private void restore() {
        oldGlow.forEach(Entity::setGlowingTag);
        oldGlow.clear();
    }
    @Override public void tick(Minecraft client, boolean unused) { }
}
