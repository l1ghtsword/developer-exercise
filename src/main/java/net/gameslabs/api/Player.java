package net.gameslabs.api;

import java.util.UUID;

public interface Player {
    String getId();
    String getName();
    UUID getUuid();
}
