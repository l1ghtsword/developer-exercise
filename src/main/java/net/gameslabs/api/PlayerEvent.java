package net.gameslabs.api;

/**
 * A player based event
 * Extends event making a player event's only add a player object ro the event called
 */

public class PlayerEvent extends Event {
    private final Player player;

    public PlayerEvent(Player player) { this.player = player; }

    public Player getPlayer() {
        return player;
    }
}
