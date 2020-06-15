package ca.braelor.l1ghtsword.assignment.components;

import net.gameslabs.api.Component;
import net.gameslabs.events.GiveXpEvent;
import net.gameslabs.model.objects.Assignment;
import net.gameslabs.model.enums.Skill;

/**
 * Simple Component that listens for GiveXpEvent's and will multiply xp
 * being given by 2x if that skill is in Construction.
 */

public class MyXPBoosterComponent extends Component {

    @Override
    public void onLoad() {
        registerEvent(GiveXpEvent.class, this::onGiveXP);
    }

    private void onGiveXP(GiveXpEvent e) {
        if(e.getSkill() == Skill.CONSTRUCTION){
            e.setXp((e.getXp()*2));
            Assignment.log("DOUBLE XP!!!");
        }
    }

    @Override
    public void onUnload() {
        // Do nothing...
    }
}
