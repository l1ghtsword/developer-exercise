package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.model.enums.Rock;
import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;

/**
 * Event used by MiningComponent listeners to get the player attempting to mine
 * and provide the listener the Rock being mined.
 * <p>
 * Does not provide success or failure, that is handled by MiningComponent
 */

public class PlayerMiningEvent extends PlayerEvent {
    private final Rock rock;

    public PlayerMiningEvent(Player playerMining, Rock rockBeingMined) {
        super(playerMining);
        this.rock = rockBeingMined;
    }

    public Rock getRock() {
        return this.rock;
    }
}
