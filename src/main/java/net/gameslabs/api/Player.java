package net.gameslabs.api;

import net.gameslabs.model.PlayerStats;
import net.gameslabs.model.Skill;

import java.util.UUID;

public interface Player {
    String getId();
    String getName();
    UUID getUuid();
}
