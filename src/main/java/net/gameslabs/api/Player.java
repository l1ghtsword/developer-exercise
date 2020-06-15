package net.gameslabs.api;

import java.util.UUID;

public interface Player {
    String getId();
    String getName();

    //I added this, unused but good in serialization with mojang.net uuid's as opposed to names
    UUID getUuid();
}
