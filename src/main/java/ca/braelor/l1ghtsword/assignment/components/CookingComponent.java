package ca.braelor.l1ghtsword.assignment.components;

import ca.braelor.l1ghtsword.assignment.events.*;
import ca.braelor.l1ghtsword.assignment.exception.PlayerLevelTooLow;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.FoodData;
import net.gameslabs.api.Component;
import net.gameslabs.events.GetPlayerLevelEvent;
import net.gameslabs.events.GiveXpEvent;
import net.gameslabs.model.enums.Skill;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import static ca.braelor.l1ghtsword.assignment.utils.util.isCookable;
import static net.gameslabs.model.objects.Assignment.log;

/**
 * PRIMARY REVISIONS
 *
 * -
 *
 * Component responsible for the cooking skill and its operations.
 * Does not check if player has space as order of operations will require a
 * cookable item exist before trying to cook it.
 * Also, checks level and cancels event if all requirements are not met
 * <p>
 * Variables such as burn chance and level requirement's are in the array of
 * Food objects. FoodItems maps Item objects to Food Objects by checking if they are cookable.
 * Iterates through Item list once on Component init, but can be referee to after load for
 * less taxing operations. Not ideal is Item list grows large but needs to be done in such a
 * way that only cookable items apply without too much Enum duplication.
 */

public class CookingComponent extends Component {

    private HashMap<ItemID, FoodData> foodItems;

    public CookingComponent() {
        foodItems = new HashMap<>();
        EnumSet.allOf(ItemID.class).forEach(i -> {
            if (isCookable(i)) {
                foodItems.put(i, new FoodData(i));
            }
        });
    }

    @Override
    public void onLoad() {
        registerEvent(PlayerCookingEvent.class, this::onPlayerCooking);
        registerEvent(GetFoodInfoEvent.class, this::onGetFoodInfo);
    }

    private void onPlayerCooking(PlayerCookingEvent e) {
        GetPlayerItemEvent getItem = new GetPlayerItemEvent(e.getPlayer(), e.getItem());
        send(getItem);

        if (getItem.hasItem()) {
            if (isCookable(e.getItem())) {
                FoodData f = getFood(e.getItem());
                GetPlayerLevelEvent pLevel = new GetPlayerLevelEvent(e.getPlayer(), Skill.COOKING);
                send(pLevel);

                try {
                    if (pLevel.getLevel() >= f.getLevel()) {
                        log(e.getPlayer().getName() + " has high enough level, Attempting to cook " + f.getItem());
                        if (cookFood(f.getBurnChance())) {
                            log(e.getPlayer().getName() + " has successfully cooked " + f.getItem());
                            send(new RemovePlayerItemEvent(e.getPlayer(), e.getItem()));
                            send(new GivePlayerItemEvent(e.getPlayer(), f.getCooked()));
                            log(e.getPlayer().getName() + " will receive " + f.getXp() + " XP");
                            send(new GiveXpEvent(e.getPlayer(), Skill.COOKING, f.getXp()));
                        } else {
                            log(e.getPlayer().getName() + " has failed to cook " + f.getItem());
                            send(new RemovePlayerItemEvent(e.getPlayer(), e.getItem()));
                            send(new GivePlayerItemEvent(e.getPlayer(), f.getBurnt()));
                        }
                    } else {
                        throw new PlayerLevelTooLow(e.getPlayer(), Skill.COOKING, f.getLevel());
                    }
                } catch (PlayerLevelTooLow err) {
                    log(err.getMessage());
                }
            } else {
                log("Cannot cook " + e.getItem() + ". Only able to cook food...");
            }
        } else {
            log(e.getPlayer().getName() + "Does not have any " + e.getItem() + "!");
        }
        e.setCancelled(true);
    }

    private FoodData onGetFoodInfo(GetFoodInfoEvent e) {
        return e.getFood();
    }

    private FoodData getFood(ItemID i) {
        return foodItems.get(i);
    }

    private boolean cookFood(int foodBurnChance) {
        int rng = ThreadLocalRandom.current().nextInt(1, 101);
        if (rng > foodBurnChance) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onUnload() {
        // Nothing to do
    }
}
