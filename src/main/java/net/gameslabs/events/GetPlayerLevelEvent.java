package net.gameslabs.events;

import net.gameslabs.api.Player;
import net.gameslabs.api.PlayerEvent;
import net.gameslabs.model.enums.Skill;

public class GetPlayerLevelEvent extends PlayerEvent {
    private final Skill skill;
    private int level;

    public GetPlayerLevelEvent(Player player, Skill skill) {
        //set instance player obj (inherited from the superclass) to provided player obj in event constructor
        super(player);
        //set instance skill obj to skill provided in event constructor
        this.skill = skill;
    }

    //Return event skill being checked
    public Skill getSkill() {
        return skill;
    }
    //Return event level being checked
    public int getLevel() {
        return level;
    }
    //set event obj player level to value provided
    public void setLevel(int level) {
        this.level = level;
    }
}
