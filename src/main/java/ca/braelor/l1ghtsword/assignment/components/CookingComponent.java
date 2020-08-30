package ca.braelor.l1ghtsword.assignment.components;

import ca.braelor.l1ghtsword.assignment.events.*;
import ca.braelor.l1ghtsword.assignment.exception.PlayerLevelTooLow;
import ca.braelor.l1ghtsword.assignment.interfaces.Item;
import net.gameslabs.api.Component;
import net.gameslabs.api.Player;
import net.gameslabs.events.GetPlayerLevelEvent;
import net.gameslabs.events.GiveXpEvent;
import net.gameslabs.model.enums.Skill;

import java.util.concurrent.ThreadLocalRandom;
import static net.gameslabs.model.objects.Assignment.log;

/**
 * REFACTORED
 *
 * Removed any deprecated, hard coded logic in place of the Item interface.
 *
 * Will attempt to cook, cookable food.
 * Will check if player has item, then if item is cookable, then if player has correct level.
 * if all checks are good, will cook the food, and return a cooked or burnt item as a result.
 */

public class CookingComponent extends Component {

    public CookingComponent() {

    }

    @Override
    public void onLoad() {
        registerEvent(PlayerCookingEvent.class, this::onPlayerCooking);
    }

    private void onPlayerCooking(PlayerCookingEvent event) {

        GetPlayerItemEvent getItem = new GetPlayerItemEvent(event.getPlayer(), event.getItem());
        send(getItem);

        if (getItem.hasItem()) {
            if (event.getItem().isCookable()) {
                Item foodBeingCooked = event.getItem();
                GetPlayerLevelEvent pLevel = new GetPlayerLevelEvent(event.getPlayer(), Skill.COOKING);
                send(pLevel);

                try {
                    if (pLevel.getLevel() >= foodBeingCooked.getLevelRequirement()) {
                        log(event.getPlayer().getName() + " has high enough level, Attempting to cook " + foodBeingCooked.getItemID());
                        attemptToCookFood(event.getPlayer(), event.getItem());
                    } else {
                        throw new PlayerLevelTooLow(event.getPlayer(), Skill.COOKING, foodBeingCooked.getLevelRequirement());
                    }
                } catch (PlayerLevelTooLow err) {
                    log(err.getMessage());
                }
            } else {
                log("Cannot cook " + event.getItem() + ". Only able to cook food...");
            }
        } else {
            log(event.getPlayer().getName() + "Does not have any " + event.getItem() + "!");
        }
        event.setCancelled(true);
    }

    private void attemptToCookFood(Player player, Item item) {
        int rng = ThreadLocalRandom.current().nextInt(1, 101);
        if (rng > item.getBurnChance()) {
            log(player.getName() + " has successfully cooked " + item.getItemID());
            send(new RemovePlayerItemEvent(player, item.getItemID()));
            send(new GivePlayerItemEvent(player, item.createNewInstanceOf(item.getCookedItem())));
            log(player.getName() + " will receive " + item.getXpAmountGiven() + " XP");
            send(new GiveXpEvent(player, Skill.COOKING, item.getXpAmountGiven()));
        } else {
            log(player.getName() + " has failed to cook " + item.getItemID());
            send(new RemovePlayerItemEvent(player, item.getItemID()));
            send(new GivePlayerItemEvent(player, item.createNewInstanceOf(item.getBurntItem())));
        }
    }

    @Override
    public void onUnload() {
        // Nothing to do
    }
}
