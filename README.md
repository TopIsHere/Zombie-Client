# Zombie Client (Fabric 26.1.2)

A modular, non-combat client utility mod. No KillAura, combat automation, or anti-cheat bypasses.

## Included working features
- **F6 Click GUI** with categories and toggles.
- **HUD** with FPS, coordinates, direction, and enabled module list.
- **Fullbright** toggle.
- **Zoom** (hold C by default; configurable key mapping).
- **Sprint Assist**: maintains sprint while moving forward when possible.
- **Waypoint utility**: stores a local waypoint and displays distance/coordinates.
- **Entity ESP**: highlights living entities using the vanilla glowing flag while enabled (client-side visual effect; see note below).
- **Config persistence**: saves module toggles and settings to `config/zombieclient.json`.

The project uses Java 25, Minecraft 26.1.2, Fabric Loader, Fabric API, and Fabric Loom.

## Build (Windows)
1. Install **JDK 25** (Temurin or Microsoft OpenJDK).
2. Install Git (optional) and IntelliJ IDEA 2025.3+ or use a terminal.
3. Open this folder in IntelliJ and allow Gradle to sync.
4. The project is pinned to Fabric API `0.155.3+26.1.2`, Fabric Loader `0.19.3`, and Loom `1.16.1`. If Fabric releases newer compatible versions, update `gradle.properties` using https://fabricmc.net/develop/ .
5. In PowerShell, from this folder run:
   ```powershell
   .\gradlew.bat build
   ```
   If the Gradle wrapper isn't present in the archive, generate it once with a local Gradle installation:
   ```powershell
   gradle wrapper --gradle-version 9.4.0
   .\gradlew.bat build
   ```
6. The JAR will be in `build\libs\zombie-client-0.1.0.jar`.
7. Install Fabric Loader for 26.1.2, put that JAR and a matching Fabric API JAR into your instance's `mods` folder, then launch the Fabric profile.

## Keybinds
- **F6**: open/close Click GUI.
- **C**: hold to zoom.
- Other toggles can be changed from the GUI.

## Notes
Minecraft 26.1 moved Fabric to Mojang's official names and Java 25. APIs changed substantially from 1.21.x. The source intentionally targets 26.1.2 and should be built against the matching Fabric API. This archive is source code, not a precompiled JAR. I could not compile-test it in this environment because only Java 21 is installed and Gradle is unavailable; Minecraft 26.1.2 requires Java 25. Run the build on a JDK 25 setup and resolve any version-specific API errors before treating it as release-ready.

The entity outline module uses the vanilla glowing flag and restores each entity's previous flag when disabled or when the entity leaves the world. Because the glowing flag is entity state, use it in single-player/private testing or where permitted.
