package net.cookedseafood.namepalette.api;

import net.minecraft.scoreboard.Team;

public interface ServerPlayerEntityApi {
    default Team getNameTeam() {
        return null;
    }
}
