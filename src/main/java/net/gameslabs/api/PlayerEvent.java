package net.gameslabs.api;

/**
 * A player based event
 * Extends event making a player event's only add a player object ro the event called
 */
public class PlayerEvent extends Event {
    //Create uninitialized "player" object
    private final Player player;
    //Class constructor to set "player" object when PlayerEvent object is created as provided "player" object
    public PlayerEvent(Player player) {
        this.player = player;
    }
    //Method to get player object for this instance of the PlayerEvent object
    public Player getPlayer() {
        return player;
    }
}
