package com.zombie.client.ui;

import com.zombie.client.module.ClientModule;
import com.zombie.client.module.ModuleManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public final class ClickGuiScreen extends Screen {
    private static final int PANEL_W = 240;
    private static final int ROW_H = 24;
    private int left, top;

    public ClickGuiScreen() { super(Component.literal("Zombie Client")); }

    @Override protected void init() {
        left = (width - PANEL_W) / 2;
        top = Math.max(24, (height - Math.min(360, ModuleManager.all().size() * ROW_H + 42)) / 2);
    }

    @Override public void render(GuiGraphics g, int mouseX, int mouseY, float delta) {
        renderBackground(g, mouseX, mouseY, delta);
        List<ClientModule> modules = ModuleManager.all();
        int panelH = Math.min(360, 42 + modules.size() * ROW_H);
        g.fill(left, top, left + PANEL_W, top + panelH, 0xE91B1D25);
        g.fill(left, top, left + PANEL_W, top + 3, 0xFF7CFF6B);
        g.drawString(font, "ZOMBIE CLIENT", left + 12, top + 12, 0xFF7CFF6B, false);
        g.drawString(font, "F6 to toggle • click modules", left + 12, top + 26, 0xFFB8BBC7, false);
        int y = top + 42;
        for (ClientModule module : modules) {
            boolean hover = mouseX >= left && mouseX < left + PANEL_W && mouseY >= y && mouseY < y + ROW_H;
            if (hover) g.fill(left + 5, y, left + PANEL_W - 5, y + ROW_H - 1, 0xFF343744);
            g.drawString(font, module.name(), left + 12, y + 8,
                    module.enabled() ? 0xFF7CFF6B : 0xFFE0E1E8, false);
            g.drawString(font, module.enabled() ? "ON" : "OFF",
                    left + PANEL_W - 38, y + 8,
                    module.enabled() ? 0xFF7CFF6B : 0xFF888B98, false);
            y += ROW_H;
        }
        super.render(g, mouseX, mouseY, delta);
    }

    @Override public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int y = top + 42;
            for (ClientModule module : ModuleManager.all()) {
                if (mouseX >= left && mouseX < left + PANEL_W && mouseY >= y && mouseY < y + ROW_H) {
                    module.toggle();
                    ModuleManager.changed();
                    return true;
                }
                y += ROW_H;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override public boolean isPauseScreen() { return false; }
}
