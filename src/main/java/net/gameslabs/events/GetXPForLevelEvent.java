package net.gameslabs.events;

import net.gameslabs.api.Event;
/*
Event
 */


public class GetXPForLevelEvent extends Event {
    //Level and xp values for object
    private final int level;
    private int xp;

    //Constructor for object to set level provided on instance and set xp to 0
    public GetXPForLevelEvent(int level) {
        this.level = level;
        this.xp = 0;
    }
    //return event level to requesting listener
    public int getLevel() { return level; }
    //return event xp earned to requesting listener
    public int getXp() { return xp; }
    //Set XP earned for event (to be read by listener, does not require addXp method)
    public void setXp(int xp) { this.xp = xp; }
}
