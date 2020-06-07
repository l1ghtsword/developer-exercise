package assignment.components;

import net.gameslabs.api.Component;
import net.gameslabs.events.GiveXpEvent;
import net.gameslabs.model.Skill;

public class MyXPBoosterComponent extends Component {

    @Override
    public void onLoad() {
        registerEvent(GiveXpEvent.class, this::onGiveXP);
    }

    private void onGiveXP(GiveXpEvent e) {
        if(e.getSkill() == Skill.CONSTRUCTION){
            e.setXp((e.getXp()*2));
        }
    }

    @Override
    public void onUnload() {
        // Do nothing...
    }
}
