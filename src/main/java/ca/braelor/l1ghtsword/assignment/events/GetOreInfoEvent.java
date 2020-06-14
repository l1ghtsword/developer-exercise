package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.model.enums.Rock;
import ca.braelor.l1ghtsword.assignment.model.objects.Ore;
import net.gameslabs.api.Event;
import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;


public class GetOreInfoEvent extends Event {
    private final Rock r;
    private final Ore o;

    public GetOreInfoEvent(Rock rock) {
        this.r = rock;
        this.o = new Ore(rock);
    }

    public Rock getRock() { return this.r; }
    public Ore getOre(){ return this.o; }
}
