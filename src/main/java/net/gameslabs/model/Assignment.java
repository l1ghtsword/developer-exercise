package net.gameslabs.model;

import net.gameslabs.api.Component;
import net.gameslabs.api.ComponentRegistry;
import net.gameslabs.api.Player;
import net.gameslabs.events.GetPlayerLevel;
import net.gameslabs.events.GetXPForLevelEvent;
import net.gameslabs.events.GiveXpEvent;
import net.gameslabs.implem.PlayerImplem;

import java.util.Arrays;

public class Assignment {

    protected final ComponentRegistry registry;
    private final Player mainPlayer;
    private final Player someOtherPlayer;

    //Constructor method to receive one to many Component Objects
    public Assignment(Component ... myComponentsToAdd) {
        //Initialize ComponentRegistry object
        registry = new ComponentRegistry();
        //iterate through each Component Object passed in constructor, formatted as an Array list.
        //Then call the registerComponent method in the ComponentRegistry Obj to add the component to register (instanced Obj)
        Arrays.asList(myComponentsToAdd).forEach(registry::registerComponent);

        //Manually add the ChartComponent Obj to register?
        //RELOCATED// registry.registerComponent(new ChartComponent());

        registry.load();
        mainPlayer = PlayerImplem.newPlayer("L1ghtsword_");
        someOtherPlayer = PlayerImplem.newPlayer("Some_anon");
    }

    public final void run() {
        //Send GiveXpEvent Event Obj with params (PlayerImplem, Skill Obj, XP int)
        //registry.sendEvent(new GiveXpEvent(mainPlayer, Skill.CONSTRUCTION, 25));
        //Send GiveXpEvent Event Obj with params (PlayerImplem, Skill Obj, XP int)
        //registry.sendEvent(new GiveXpEvent(mainPlayer, Skill.EXPLORATION, 25));
        //Initialize getPlayerLevel Obj as mainplayer with their skill level in the construction skill
        GiveXpEvent give = new GiveXpEvent(mainPlayer,Skill.CONSTRUCTION, 25);
        GetXPForLevelEvent whatdoineed = new GetXPForLevelEvent(1);
        GetPlayerLevel getPlayerLevel = new GetPlayerLevel(mainPlayer, Skill.CONSTRUCTION);
        //System.out.println String, everything passed is cast as a string
        //log(mainPlayer," xp = "," level", mainPlayer, getPlayerLevel.getLevel());
        //log(mainPlayer," Construction xp = ",mainPlayer., mainPlayer, getPlayerLevel.getLevel());
        //Call runChecks Method

        //REEMPLOYMENT LATER
        // runChecks();

        //Call unload method from ComponentRegistry Obj
        registry.unload();
    }

    //Manual checks which will fail if mainplayer skill does not match defined value
    private void runChecks() {
        //calls getLevel method to skill check noobs
        if (getLevel(Skill.EXPLORATION) != 1) throw new AssignmentFailed("Exploration XP should be set to level 1");
        if (getLevel(Skill.CONSTRUCTION) != 2) throw new AssignmentFailed("Construction XP should be set to level 2");
    }

    //Method to check player level
    // Will create new private instance of getPlayerLevel to call mainplayer's level for requested skill
    // will then send getPlayerLevel event and return
    private int getLevel(Skill skill) {
        GetPlayerLevel getPlayerLevel = new GetPlayerLevel(mainPlayer, skill);
        registry.sendEvent(getPlayerLevel);
        return getPlayerLevel.getLevel();
    }
    //Iterate through all received Objects as ArrayList and print it out as a string
    public void log(Object ... arguments) {
        System.out.println(Arrays.asList(arguments).toString());
    }
}
