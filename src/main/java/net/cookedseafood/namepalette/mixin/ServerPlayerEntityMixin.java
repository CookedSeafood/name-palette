package net.cookedseafood.namepalette.mixin;

import net.cookedseafood.namepalette.api.ServerPlayerEntityApi;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements ServerPlayerEntityApi {
    @Override
    public Team getNameTeam() {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        Scoreboard scoreboard = player.getServer().getScoreboard();

        String teamName = "name." + player.getUuidAsString();
        Team team = scoreboard.getOrAddTeam(teamName);
        team.setDisplayName(player.getName());
        scoreboard.addScoreHolderToTeam(player.getNameForScoreboard(), team);
        return team;
    }
}
