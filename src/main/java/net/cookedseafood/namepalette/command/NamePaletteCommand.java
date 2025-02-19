package net.cookedseafood.namepalette.command;

import com.mojang.brigadier.CommandDispatcher;
import net.cookedseafood.namepalette.NamePalette;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class NamePaletteCommand {
    public NamePaletteCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(
            CommandManager.literal("namepalette")
            .then(
				CommandManager.literal("version")
				.executes(context -> executeVersion((ServerCommandSource)context.getSource()))
			)
        );
    }

    public static int executeVersion(ServerCommandSource source) {
		source.sendFeedback(() -> Text.literal("NamePalette " + NamePalette.versionMajor + "." + NamePalette.versionMinor + "." + NamePalette.versionPatch), false);
		return 0;
	}
}
