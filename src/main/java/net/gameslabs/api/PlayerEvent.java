package net.gameslabs.api;

import net.gameslabs.model.PlayerStats;
import net.gameslabs.model.Skill;

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
    //Method to get stats of respective player
    public PlayerStats getPlayerStats() { return player.getStats(); }

    public void setPlayerStats(Skill s, int xp) { player.setStats(s,xp);}

    public void addPlayerStats(Skill s, int xp) { player.addStats(s,xp);}

    public void subPlayerStats(Skill s, int xp) { player.subStats(s,xp);}
}
