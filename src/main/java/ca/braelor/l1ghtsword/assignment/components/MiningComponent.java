package ca.braelor.l1ghtsword.assignment.components;

import ca.braelor.l1ghtsword.assignment.events.*;
import ca.braelor.l1ghtsword.assignment.exception.*;
import ca.braelor.l1ghtsword.assignment.interfaces.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.Rock;
import ca.braelor.l1ghtsword.assignment.model.objects.items.Empty;
import ca.braelor.l1ghtsword.assignment.model.objects.items.ores.*;
import net.gameslabs.api.Component;
import net.gameslabs.events.GetPlayerLevelEvent;
import net.gameslabs.events.GiveXpEvent;
import net.gameslabs.model.enums.Skill;

import java.util.EnumSet;
import java.util.HashMap;

import static net.gameslabs.model.objects.Assignment.log;

/**
 * Component to handle all the specific attributes of the Mining Skill
 * The main responsibility of this component is mapping Rock types to Ore Obj.
 * Based on the Ore, a mining event listener will check player level and provide
 * player with Ore and Xp for successfully mining a rock.
 * <p>
 * Will check if player inventory is full (if it has Item.EMPTY in a slot) and
 * cancel the event if there is no room.
 */

public class MiningComponent extends Component {

    private HashMap<Rock, Item> ores;

    public MiningComponent() {
        ores = new HashMap<>();
        //Set aside static map of Item objects to perform checks and create new instances of later
        EnumSet.allOf(Rock.class).forEach(r -> ores.put(r, mapRockToOreItem(r)));
    }

    @Override
    public void onLoad() {
        registerEvent(PlayerMiningEvent.class, this::onPlayerMining);
    }

    private void onPlayerMining(PlayerMiningEvent event) {
        GetPlayerItemEvent getEmpty = new GetPlayerItemEvent(event.getPlayer(), new Empty());
        send(getEmpty);

        if (getEmpty.hasItem()) {
            GetPlayerLevelEvent pLevel = new GetPlayerLevelEvent(event.getPlayer(), Skill.MINING);
            send(pLevel);
            //Use list of Valid Ores as we only know what rock is being mined
            Item oreBeingHarvested = ores.get(event.getRock());

            try {
                if (pLevel.getLevel() >= oreBeingHarvested.getLevelRequirement()) {
                    log(event.getPlayer().getName() + " has high enough level, giving 1x " + oreBeingHarvested.getItemID());
                    send(new GivePlayerItemEvent(event.getPlayer(), oreBeingHarvested.createNewInstanceOf(oreBeingHarvested)));
                    log(event.getPlayer().getName() + " will receive " + oreBeingHarvested.getXpAmountGiven() + " XP");
                    send(new GiveXpEvent(event.getPlayer(), Skill.MINING, oreBeingHarvested.getXpAmountGiven()));
                } else {
                    throw new PlayerLevelTooLow(event.getPlayer(), Skill.MINING, oreBeingHarvested.getLevelRequirement());
                }
            } catch (PlayerLevelTooLow err) {
                log(err.getMessage());
            }
        } else {
            log("Player inventory is full!");
        }
        event.setCancelled(true);
    }

    private Item mapRockToOreItem(Rock rock) {
        switch (rock) {
            case TIN:
                return new Tin_ore();
            case COPPER:
                return new Copper_ore();
            case IRON:
                return new Iron_ore();
            case SILVER:
                return new Silver_ore();
            case COAL:
                return new Coal_ore();
            case GOLD:
                return new Gold_ore();
            case MITHRIL:
                return new Mithril_ore();
            case ADAMANTITE:
                return new Adamantite_ore();
            case RUNITE:
                return new Runite_ore();
            default:
                log("ERROR: Ore Item not found for " + rock);
                return new Empty();
        }
    }

    @Override
    public void onUnload() {
        ores.clear();
    }
}
