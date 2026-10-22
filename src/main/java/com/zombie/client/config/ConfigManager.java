package com.zombie.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zombie.client.module.ClientModule;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("zombieclient.json");
    private static Map<String, Boolean> states = new HashMap<>();
    private static boolean dirty;

    private ConfigManager() {}
    public static void load() {
        if (!Files.exists(FILE)) return;
        try (Reader reader = Files.newBufferedReader(FILE)) {
            var loaded = GSON.fromJson(reader, StateFile.class);
            if (loaded != null && loaded.modules != null) states = loaded.modules;
        } catch (Exception e) {
            System.err.println("[ZombieClient] Could not load config: " + e.getMessage());
        }
    }
    public static void apply(List<ClientModule> modules) {
        modules.forEach(m -> {
            Boolean value = states.get(m.name());
            if (value != null) m.setEnabled(value);
        });
    }
    public static void markDirty() { dirty = true; }
    public static void saveIfDirty() {
        if (!dirty) return;
        dirty = false;
        try {
            Files.createDirectories(FILE.getParent());
            StateFile state = new StateFile();
            state.modules = new HashMap<>();
            // The module list is captured by the caller-facing save method below.
            for (ClientModule module : com.zombie.client.module.ModuleManager.all()) {
                state.modules.put(module.name(), module.enabled());
            }
            try (Writer writer = Files.newBufferedWriter(FILE)) { GSON.toJson(state, writer); }
            states = state.modules;
        } catch (Exception e) {
            dirty = true;
            System.err.println("[ZombieClient] Could not save config: " + e.getMessage());
        }
    }
    private static final class StateFile { Map<String, Boolean> modules = new HashMap<>(); }
}
