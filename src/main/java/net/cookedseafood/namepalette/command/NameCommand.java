package net.cookedseafood.namepalette.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ColorArgumentType;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TeamCommand;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class NameCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(
            CommandManager.literal("name")
            .then(
                CommandManager.literal("set")
                .then(
                    CommandManager.literal("color")
                    .then(
                        CommandManager.argument("value", ColorArgumentType.color())
                        .executes(context -> executeColor((ServerCommandSource)context.getSource(), ColorArgumentType.getColor(context, "value")))
                    )
                )
                .then(
                    CommandManager.literal("prefix")
                    .then(
                        CommandManager.argument("prefix", TextArgumentType.text(registryAccess))
                        .executes(context -> executePrefix((ServerCommandSource)context.getSource(), TextArgumentType.getTextArgument(context, "prefix")))
                    )
                )
                .then(
                    CommandManager.literal("suffix")
                    .then(
                        CommandManager.argument("suffix", TextArgumentType.text(registryAccess))
                        .executes(context -> executeSuffix((ServerCommandSource)context.getSource(), TextArgumentType.getTextArgument(context, "suffix")))
                    )
                )
            )
            .then(
                CommandManager.literal("reset")
                .executes(context -> executeReset((ServerCommandSource)context.getSource()))
            )
        );
    }

    public static int executeColor(ServerCommandSource source, Formatting color) throws CommandSyntaxException {
        return TeamCommand.executeModifyColor(source, source.getPlayerOrThrow().getNameTeam(), color);
    }

    public static int executePrefix(ServerCommandSource source, Text prefix) throws CommandSyntaxException {
        return TeamCommand.executeModifyPrefix(source, source.getPlayerOrThrow().getNameTeam(), prefix);
    }

    public static int executeSuffix(ServerCommandSource source, Text suffix) throws CommandSyntaxException {
        return TeamCommand.executeModifySuffix(source, source.getPlayerOrThrow().getNameTeam(), suffix);
    }

    public static int executeReset(ServerCommandSource source) throws CommandSyntaxException {
        return TeamCommand.executeRemove(source, source.getPlayerOrThrow().getNameTeam());
    }
}
