package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.Rock;
import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;


public class PlayerMiningEvent extends PlayerEvent {
    private final Rock r;

    public PlayerMiningEvent(Player player, Rock rock) {
        super(player);
        this.r = rock;
    }

    public Rock getRock() { return this.r; }
}
