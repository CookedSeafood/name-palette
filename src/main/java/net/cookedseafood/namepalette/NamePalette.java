package net.cookedseafood.namepalette;

import net.cookedseafood.namepalette.command.NameCommand;
import net.cookedseafood.namepalette.command.NamePaletteCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NamePalette implements ModInitializer {
    public static final String MOD_ID = "name-palette";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final byte versionMajor = 1;
    public static final byte versionMinor = 2;
    public static final byte versionPatch = 0;

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> NamePaletteCommand.register(dispatcher, registryAccess));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> NameCommand.register(dispatcher, registryAccess));
    }
}
