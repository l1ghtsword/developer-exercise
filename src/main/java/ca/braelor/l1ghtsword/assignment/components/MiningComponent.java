package ca.braelor.l1ghtsword.assignment.components;

import ca.braelor.l1ghtsword.assignment.events.*;
import ca.braelor.l1ghtsword.assignment.exception.*;
import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemSlot;
import ca.braelor.l1ghtsword.assignment.model.enums.Rock;
import ca.braelor.l1ghtsword.assignment.model.enums.StackableItem;
import ca.braelor.l1ghtsword.assignment.model.objects.ItemData;
import ca.braelor.l1ghtsword.assignment.model.objects.Ore;
import ca.braelor.l1ghtsword.assignment.model.objects.PlayerInventory;
import net.gameslabs.api.Component;
import net.gameslabs.api.Player;
import net.gameslabs.events.GetPlayerLevelEvent;
import net.gameslabs.events.GiveXpEvent;
import net.gameslabs.model.enums.Skill;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import static net.gameslabs.model.objects.Assignment.log;

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
        GetPlayerLevelEvent pLevel = new GetPlayerLevelEvent(e.getPlayer(), Skill.MINING);
        Ore o = getOre(e.getRock());

        try {
            if (pLevel.getLevel() >= o.getLevel()) {
                send(new GivePlayerItemEvent(e.getPlayer(),o.getItem()));
                send(new GiveXpEvent(e.getPlayer(),Skill.MINING,o.getXp()));
            } else { throw new PlayerLevelTooLow(e.getPlayer(), Skill.MINING, o.getLevel()); }
        } catch (PlayerLevelTooLow err) { log(err.getMessage()); }
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
