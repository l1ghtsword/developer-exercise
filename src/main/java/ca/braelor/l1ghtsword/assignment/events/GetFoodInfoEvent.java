package ca.braelor.l1ghtsword.assignment.events;

import ca.braelor.l1ghtsword.assignment.exception.ItemNotCookableError;
import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import ca.braelor.l1ghtsword.assignment.model.objects.Food;
import net.gameslabs.api.Event;

import static net.gameslabs.model.objects.Assignment.log;

/**
 * Event called to get the Food obj from a valid Item name qualifier
 * If non-food item is associated with event, event will be canceled and
 * a message will be sent to the log. Ideally, this is prevented with a check
 * before sending the event to begin with, of course.
 *
 * Provides Item and Food objects when called
 */

public class GetFoodInfoEvent extends Event {
    private final Item i;
    private Food f;

    public GetFoodInfoEvent(Item item) {
        this.i = item;
        try { this.f = new Food(item);
        } catch (ItemNotCookableError err) {
            log(err.getMessage());
            this.setCancelled(true);
        }
    }

    public Item getItem(){ return this.i; }
    public Food getFood(){ return this.f; }
}
