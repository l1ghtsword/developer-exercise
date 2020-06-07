package net.gameslabs.api;

import net.gameslabs.model.PlayerStats;
import net.gameslabs.model.Skill;

import java.util.UUID;

public interface Player {
    String getId();
    String getName();
    UUID getUuid();
    PlayerStats getStats();
    void setStats(Skill s, int xp);
    void addStats(Skill s, int xp);
    void subStats(Skill s, int xp);
}
