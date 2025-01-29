package org.charcoalwhite.namepalette;

import com.mojang.brigadier.CommandDispatcher;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.HashSet;
import net.minecraft.command.CommandRegistryAccess;
import static net.minecraft.command.argument.ColorArgumentType.color;
import static net.minecraft.command.argument.ColorArgumentType.getColor;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TeamCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class NameCommand {
    public static final SimpleCommandExceptionType OPTION_COLOR_UNCHANGED_EXCEPTION = new SimpleCommandExceptionType(Text.literal("commands.name.option.color.unchanged"));

    public NameCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(
			literal("name")
			.then(
				literal("color")
				.then(
					argument("value", color())
					.executes(context -> {
						return executeColor((ServerCommandSource)context.getSource(), getColor(context, "value"));
					})
				)
			)
			.then(
				argument("prefix", string())
				.executes(context -> {
					return executePrefix((ServerCommandSource)context.getSource(), getString(context, "prefix"));
				})
			)
			.then(
				argument("suffix", string())
				.executes(context -> {
					return executeSuffix((ServerCommandSource)context.getSource(), getString(context, "suffix"));
				})
			)
		);
    }

	private static Team getPlayerTeam(ServerCommandSource source) throws CommandSyntaxException {
		ServerPlayerEntity player = source.getPlayerOrThrow();
		String teamName = "name." + player.getUuidAsString();
		Scoreboard scoreboard = source.getServer().getScoreboard();
		try {
			TeamCommand.executeAdd(source, teamName, player.getName());
		} catch (CommandSyntaxException e) {
		}

		Team team = scoreboard.getTeam(teamName);
		HashSet<ScoreHolder> hashSet = new HashSet<ScoreHolder>();
		hashSet.add(player.getScoreHolder());
		TeamCommand.executeJoin(source, team, hashSet);
		return team;
	}

    public static int executeColor(ServerCommandSource source, Formatting color) throws CommandSyntaxException {
		Team team = getPlayerTeam(source);
		TeamCommand.executeModifyColor(source, team, color);
		return 1;
	}

	public static int executePrefix(ServerCommandSource source, String prefix) throws CommandSyntaxException {
		Team team = getPlayerTeam(source);
		TeamCommand.executeModifyPrefix(source, team, Text.of(prefix));
		return 1;
	}

	public static int executeSuffix(ServerCommandSource source, String suffix) throws CommandSyntaxException {
		Team team = getPlayerTeam(source);
		TeamCommand.executeModifySuffix(source, team, Text.of(suffix));
		return 1;
	}
}
