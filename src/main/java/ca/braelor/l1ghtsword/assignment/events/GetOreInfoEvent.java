package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.model.enums.Rock;
import ca.braelor.l1ghtsword.assignment.model.objects.Ore;
import net.gameslabs.api.Event;

/**
 *  Event to get Ore information from a provided Rock resource
 *  Cannot fail as a Rock is required for this event and will always
 *  provide a valid Ore obj
 *
 *  associates Ore with Rock value and can be called to get Ore obj
 *  which will provide variables such as level requirement, item mined, etc
 */

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
