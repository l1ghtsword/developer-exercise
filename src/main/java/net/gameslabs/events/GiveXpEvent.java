package net.gameslabs.events;

import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;
import net.gameslabs.model.enums.Skill;

public class GiveXpEvent extends PlayerEvent {
    private final Skill skill;
    private int xp;

    public GiveXpEvent(Player player, Skill skill, int xp) {
        //set instance player obj (inherited from the superclass) to provided player obj in event constructor
        super(player);
        //set instance skill obj to skill provided in event constructor
        this.skill = skill;
        //set instance xp value to value provided in event constructor
        this.xp = xp;
    }
    //Return event skill receiving xp
    public Skill getSkill() {
        return skill;
    }
    //Return event xp being given
    public int getXp() { return this.xp; }
    //Set event xp being given (redundant as this is set in event constructor)
    public void setXp(int xp) { this.xp = xp; }
}
