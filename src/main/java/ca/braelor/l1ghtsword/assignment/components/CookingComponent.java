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
 * <p>
 * Removed any deprecated, hard coded logic in place of the Item interface.
 * <p>
 * Will attempt to cook, cookable food.
 * Will check if player has item, then if item is cookable, then if player has correct level.
 * if all checks are good, will cook the food, and return a cooked or burnt item as a result.
 */

public class CookingComponent extends Component {

    @Override
    public void onLoad() {
        registerEvent(PlayerCookingEvent.class, this::onPlayerCooking);
    }

    private void onPlayerCooking(PlayerCookingEvent event) {
        Item foodBeingCooked = event.getItem();
        GetPlayerItemEvent getItemFromPlayer = new GetPlayerItemEvent(event.getPlayer(), event.getItem());
        GetPlayerLevelEvent pLevel;

        send(getItemFromPlayer);
        if (!getItemFromPlayer.hasItem()) {
            log(event.getPlayer().getName() + "Does not have any " + event.getItemID() + "!");
            getItemFromPlayer.setCancelled(true);
            event.setCancelled(true);
            return;
        }

        if (!foodBeingCooked.isCookable()) {
            log("Cannot cook " + event.getItemID() + ". Only able to cook food...");
            event.setCancelled(true);
            return;
        }

        pLevel = new GetPlayerLevelEvent(event.getPlayer(), Skill.COOKING);
        send(pLevel);

        try {
            if (pLevel.getLevel() < foodBeingCooked.getLevelRequirement()) {
                throw new PlayerLevelTooLow(event.getPlayer(), Skill.COOKING, foodBeingCooked.getLevelRequirement());
            }
        } catch (PlayerLevelTooLow err) {
            log(err.getMessage());
        }

        log(event.getPlayer().getName() + " has high enough level, Attempting to cook " + event.getItemID());
        attemptToCookFood(event.getPlayer(), foodBeingCooked);
        getItemFromPlayer.setCancelled(true);
        pLevel.setCancelled(true);
        event.setCancelled(true);
    }

    private void attemptToCookFood(Player player, Item item) {
        int rng = ThreadLocalRandom.current().nextInt(1, 101);
        if (rng >= (item.getBurnChance())) {
            log(player.getName() + " has successfully cooked " + item.getItemID());
            send(new RemovePlayerItemEvent(player, item.getItemID()));
            send(new GivePlayerItemEvent(player, item.createNewInstanceOf(item.getCookedItem())));
            log(player.getName() + " will receive " + item.getXpAmountGiven() + " XP");
            send(new GiveXpEvent(player, Skill.COOKING, item.getXpAmountGiven()));
            return;
        }

        log(player.getName() + " has failed to cook " + item.getItemID());
        send(new RemovePlayerItemEvent(player, item.getItemID()));
        send(new GivePlayerItemEvent(player, item.createNewInstanceOf(item.getBurntItem())));
    }

    @Override
    public void onUnload() {

    }
}
