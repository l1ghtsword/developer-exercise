package ca.braelor.l1ghtsword.assignment.components;

import ca.braelor.l1ghtsword.assignment.events.*;
import ca.braelor.l1ghtsword.assignment.exception.*;
import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.Rock;
import ca.braelor.l1ghtsword.assignment.model.objects.Ore;
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
 *
 *Will check if player inventory is full (if it has Item.EMPTY in a slot) and
 * cancel the event if there is no room.
 */

public class MiningComponent extends Component {

    private HashMap<Rock, Ore> ores;
    public MiningComponent() {
        ores = new HashMap<>();
        EnumSet.allOf(Rock.class).forEach(r -> ores.put(r,new Ore(r)));
    }

    @Override
    public void onLoad() {
        registerEvent(PlayerMiningEvent.class, this::onPlayerMining);
        registerEvent(GetOreInfoEvent.class, this::onGetOreInfo);
    }

    private void onPlayerMining(PlayerMiningEvent e) {
        GetPlayerItemEvent getEmpty = new GetPlayerItemEvent(e.getPlayer(),Item.EMPTY);
        send(getEmpty);
        if(getEmpty.hasItem()) {
            GetPlayerLevelEvent pLevel = new GetPlayerLevelEvent(e.getPlayer(), Skill.MINING);
            send(pLevel);
            Ore o = getOre(e.getRock());

            try {
                if (pLevel.getLevel() >= o.getLevel()) {
                    log(e.getPlayer().getName() + " has high enough level, giving 1x " + o.getItem());
                    send(new GivePlayerItemEvent(e.getPlayer(), o.getItem()));
                    log(e.getPlayer().getName() + " will receive " + o.getXp() + " XP");
                    send(new GiveXpEvent(e.getPlayer(), Skill.MINING, o.getXp()));
                } else {
                    throw new PlayerLevelTooLow(e.getPlayer(), Skill.MINING, o.getLevel());
                }
            } catch (PlayerLevelTooLow err) {
                log(err.getMessage());
            }
        } else { log("Player inventory is full!"); }
        e.setCancelled(true);
    }

    private Ore onGetOreInfo(GetOreInfoEvent e){
        return e.getOre();
    }

    private Ore getOre(Rock r) { return ores.get(r); }

    @Override
    public void onUnload() {
        // Nothing to do
    }
}
