package com.zombie.client.module;

public abstract class ClientModule {
    private final String name;
    private final String description;
    private boolean enabled;

    protected ClientModule(String name, String description, boolean defaultEnabled) {
        this.name = name;
        this.description = description;
        this.enabled = defaultEnabled;
    }

    public final String name() { return name; }
    public final String description() { return description; }
    public final boolean enabled() { return enabled; }

    public final void setEnabled(boolean value) {
        if (enabled == value) return;
        enabled = value;
        if (value) onEnable();
        else onDisable();
    }

    public final void toggle() { setEnabled(!enabled); }
    public void tick(net.minecraft.client.Minecraft client) {}
    public void render(net.minecraft.client.gui.GuiGraphics graphics, net.minecraft.client.DeltaTracker delta) {}
    protected void onEnable() {}
    protected void onDisable() {}
}
