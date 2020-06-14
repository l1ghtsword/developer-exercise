package net.gameslabs.model.objects;

import ca.braelor.l1ghtsword.assignment.events.GetPlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.events.GivePlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.events.PlayerMiningEvent;
import ca.braelor.l1ghtsword.assignment.events.RemovePlayerItemEvent;
import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.Rock;
import ca.braelor.l1ghtsword.assignment.model.objects.ItemData;
import net.gameslabs.api.Component;
import net.gameslabs.api.ComponentRegistry;
import net.gameslabs.api.Player;
import net.gameslabs.events.GetPlayerLevelEvent;
import net.gameslabs.events.GiveXpEvent;
import net.gameslabs.exception.AssignmentFailed;
import net.gameslabs.implem.PlayerImplem;
import net.gameslabs.model.enums.Skill;
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

        log("Sending xp event for Exploration. Level is "+getLevel(mainPlayer, Skill.EXPLORATION));
        registry.sendEvent(new GiveXpEvent(mainPlayer,Skill.EXPLORATION,25));
        log("Exploration level is now "+getLevel(mainPlayer, Skill.EXPLORATION));
        log(" ");
        log("Sending xp event for Construction. Level is "+getLevel(mainPlayer, Skill.CONSTRUCTION));
        registry.sendEvent(new GiveXpEvent(mainPlayer,Skill.CONSTRUCTION,25));
        log("Construction level is now "+getLevel(mainPlayer, Skill.CONSTRUCTION));

        log("Giving player 2000 coins!");
        registry.sendEvent(new GivePlayerItemEvent(someOtherPlayer,Item.COINS,2000));
        ItemData id = getItem(someOtherPlayer, Item.COINS);
        log(someOtherPlayer + " Has "+id.getQuantity()+" "+id.getItem()+" in their Inventory");
        log("Removing 1000 coins!");
        registry.sendEvent(new RemovePlayerItemEvent(someOtherPlayer,Item.COINS,1000));
        id = getItem(someOtherPlayer, Item.COINS);
        log(someOtherPlayer + " Has "+id.getQuantity()+" "+id.getItem()+" in their Inventory");
        log("Giving "+mainPlayer.getName()+" 5x Fish!");
        registry.sendEvent(new GivePlayerItemEvent(mainPlayer,Item.FISH,5));
        id = getItem(mainPlayer, Item.FISH);
        log(mainPlayer+" Has "+id.getQuantity()+" "+id.getItem()+" in their Inventory");
        id = getItem(someOtherPlayer, Item.FISH);
        log(someOtherPlayer+" Has "+id.getQuantity()+" "+id.getItem()+" in their Inventory");

        log(mainPlayer.getName()+getLevel(mainPlayer, Skill.MINING));
        log(mainPlayer+" is mining "+ Rock.COAL);
        registry.sendEvent(new PlayerMiningEvent(mainPlayer,Rock.COAL));
        log(mainPlayer+" is receiving 200 xp to reach level 5");
        registry.sendEvent(new GiveXpEvent(mainPlayer, Skill.MINING, 200));
        log("Mining level is now "+getLevel(mainPlayer, Skill.MINING));
        log(mainPlayer+" is mining "+ Rock.COAL);
        registry.sendEvent(new PlayerMiningEvent(mainPlayer,Rock.COAL));
        log("Mining level is now "+getLevel(mainPlayer, Skill.MINING));
        id = getItem(mainPlayer,Item.COAL_ORE);
        log(mainPlayer+" Has "+id.getQuantity()+" "+id.getItem()+" in their Inventory");



        //Call runChecks Method
        runChecks();
        //Call unload method from ComponentRegistry Obj
        registry.unload();
    }

    //Manual checks which will fail if mainplayer skill does not match defined value
    private void runChecks() {
        //calls getLevel method to skill check noobs
        if (getLevel(mainPlayer, Skill.EXPLORATION) != 1) throw new AssignmentFailed("Exploration XP should be set to level 1");
        if (getLevel(mainPlayer, Skill.CONSTRUCTION) != 2) throw new AssignmentFailed("Construction XP should be set to level 2");
        if (getItem(someOtherPlayer, Item.COINS).getQuantity() != 1000) throw new AssignmentFailed("Player does not have 1000 coins in their inventory");
        if (getLevel(mainPlayer, Skill.MINING) != 6) throw new AssignmentFailed("Mining XP should be set to level 6");
    }

    //Method to check player level
    // Will create new private instance of getPlayerLevel to call mainplayer's level for requested skill
    // will then send getPlayerLevel event and return
    private int getLevel(Player player, Skill skill) {
        GetPlayerLevelEvent getPlayerLevelEvent = new GetPlayerLevelEvent(player, skill);
        registry.sendEvent(getPlayerLevelEvent);
        return getPlayerLevelEvent.getLevel();
    }

    private ItemData getItem(Player player, Item item) {
        GetPlayerItemEvent getPlayerItemEvent = new GetPlayerItemEvent(player, item);
        registry.sendEvent(getPlayerItemEvent);
        return new ItemData(getPlayerItemEvent.getItem(), getPlayerItemEvent.getQuantity());
    }

    //Iterate through all received Objects as ArrayList and print it out as a string
    public static void log(Object... arguments) {
        System.out.println(Arrays.asList(arguments).toString());
    }
}
