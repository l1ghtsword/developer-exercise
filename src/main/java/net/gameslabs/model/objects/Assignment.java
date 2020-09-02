package net.gameslabs.model.objects;

import ca.braelor.l1ghtsword.assignment.events.*;
import ca.braelor.l1ghtsword.assignment.interfaces.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.enums.Rock;
import ca.braelor.l1ghtsword.assignment.model.objects.items.*;
import ca.braelor.l1ghtsword.assignment.model.objects.items.food.*;
import ca.braelor.l1ghtsword.assignment.model.objects.items.ores.Coal_ore;
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

        Item raw_shrimp = new Raw_shrimp();
        Item raw_spaghetti = new Raw_spaghetti();
        Item coins = new Coins();
        Item fish = new Fish();

        //Player stats system checks
        log("Sending xp event for Exploration to "+mainPlayer.getName()+". Level is "+getLevel(mainPlayer, Skill.EXPLORATION));
        registry.sendEvent(new GiveXpEvent(mainPlayer,Skill.EXPLORATION,25));
        log(mainPlayer.getName()+" exploration level is now "+getLevel(mainPlayer, Skill.EXPLORATION));
        log(" ");
        log("Sending xp event for Construction to "+mainPlayer.getName()+". Level is "+getLevel(mainPlayer, Skill.CONSTRUCTION));
        registry.sendEvent(new GiveXpEvent(mainPlayer,Skill.CONSTRUCTION,25));
        log(mainPlayer.getName()+" construction level is now "+getLevel(mainPlayer, Skill.CONSTRUCTION));

        //Player inventory system checks (Flexible with stackable and unstackable items)
        log("Giving "+someOtherPlayer.getName()+" 2000 coins!");
        registry.sendEvent(new GivePlayerItemEvent(someOtherPlayer, new Coins(2000)));
        Item item = getItem(someOtherPlayer, new Coins());
        log(someOtherPlayer.getName() + " Has "+item.getQuantity()+" "+item.getItemID()+" in their Inventory");
        log("Removing 1000 coins!");
        registry.sendEvent(new RemovePlayerItemEvent(someOtherPlayer, ItemID.COINS,1000));
        item = getItem(someOtherPlayer, coins);
        log(someOtherPlayer.getName() + " Has "+item.getQuantity()+" "+item.getItemID()+" in their Inventory");
        log("Giving "+mainPlayer.getName()+" 5x Fish!");
        registry.sendEvent(new GivePlayerItemEvent(mainPlayer, fish, 5));
        item = getItem(mainPlayer, fish);
        log(mainPlayer.getName()+" Has "+item.getQuantity()+" "+item.getItemID()+" in their Inventory");
        item = getItem(someOtherPlayer, fish);
        log(someOtherPlayer.getName()+" Has "+item.getQuantity()+" "+item.getItemID()+" in their Inventory");

        //Mining event stuff
        log(mainPlayer.getName()+" has "+getLevel(mainPlayer, Skill.MINING)+" mining level");
        log(mainPlayer.getName()+" is mining "+ Rock.COAL);
        registry.sendEvent(new PlayerMiningEvent(mainPlayer,Rock.COAL));
        log(mainPlayer.getName()+" is receiving 200 xp to reach level 5");
        registry.sendEvent(new GiveXpEvent(mainPlayer, Skill.MINING, 200));
        log("Mining level is now "+getLevel(mainPlayer, Skill.MINING));
        log(mainPlayer.getName()+" is mining "+ Rock.COAL);
        registry.sendEvent(new PlayerMiningEvent(mainPlayer,Rock.COAL));
        log("Mining level is now "+getLevel(mainPlayer, Skill.MINING));
        item = getItem(mainPlayer, new Coal_ore());
        log(mainPlayer.getName()+" Has "+item.getQuantity()+" "+item.getItemID()+" in their Inventory");

        //Cooking Event stuff
        registry.sendEvent(new GivePlayerItemEvent(mainPlayer, raw_shrimp));
        registry.sendEvent(new GivePlayerItemEvent(mainPlayer, raw_spaghetti));
        log(mainPlayer.getName()+" has level "+getLevel(mainPlayer, Skill.COOKING)+" in cooking");

        log(mainPlayer.getName()+" is cooking "+ raw_shrimp.getItemID());
        registry.sendEvent(new PlayerCookingEvent(mainPlayer, raw_shrimp));

        log(mainPlayer.getName()+" is cooking "+ raw_spaghetti.getItemID());
        registry.sendEvent(new PlayerCookingEvent(mainPlayer, raw_spaghetti));

        log(mainPlayer.getName()+" is cooking "+ raw_shrimp.getItemID());
        registry.sendEvent(new PlayerCookingEvent(mainPlayer, raw_shrimp));

        log(mainPlayer.getName()+" is using (eating) "+ raw_spaghetti.getItemID());
        UsePlayerItemEvent useItemEvent = new UsePlayerItemEvent(mainPlayer, raw_spaghetti);
        registry.sendEvent(useItemEvent);
        log(mainPlayer.getName()+" has "+getItem(mainPlayer, new Shrimp()).getQuantity()+" "+ ItemID.SHRIMP);
        log(mainPlayer.getName()+" has "+getItem(mainPlayer, new Burnt_shrimp()).getQuantity()+" "+ ItemID.BURNT_SHRIMP);

        //Run those condition checks
        runChecks();

        //Unimportant, unload method call is empty
        registry.unload();
    }

    //Checks conditions that will throw uncaught exception and fail the assignment
    private void runChecks() {
        if (getLevel(mainPlayer, Skill.EXPLORATION) != 1) throw new AssignmentFailed("Exploration XP should be set to level 1");
        if (getLevel(mainPlayer, Skill.CONSTRUCTION) != 2) throw new AssignmentFailed("Construction XP should be set to level 2");
        if (getItem(someOtherPlayer, new Coins()).getQuantity() != 1000) throw new AssignmentFailed("Player does not have 1000 coins in their inventory");
        if (getLevel(mainPlayer, Skill.MINING) != 6) throw new AssignmentFailed("Mining XP should be set to level 6");
        if (hasItem(mainPlayer, new Burnt_shrimp()) ) { throw new AssignmentFailed(mainPlayer.getName()+" burnt the Shrimp!!! run this again!"); }
    }

    //Create event to get player level for specified skill
    private int getLevel(Player player, Skill skill) {
        GetPlayerLevelEvent getPlayerLevelEvent = new GetPlayerLevelEvent(player, skill);
        registry.sendEvent(getPlayerLevelEvent);
        return getPlayerLevelEvent.getLevel();
    }

    //Create event to check player inventory for Item (Item name not ItemData Obj) so at least 1 will pass
    //returns the actual ItemData in slot
    private Item getItem(Player player, Item item) {
        GetPlayerItemEvent getPlayerItemEvent = new GetPlayerItemEvent(player, item);
        registry.sendEvent(getPlayerItemEvent);
        if(getPlayerItemEvent.hasItem()) {
            return getPlayerItemEvent.getItem();
        }
        item.setQuantity(0);
        return item;
    }

    //Create event to check player inventory for item (Item name not ItemData Obj) so at least 1 will pass
    //returns bool with yes or no check
    private boolean hasItem(Player player, Item item) {
        GetPlayerItemEvent getPlayerItemEvent = new GetPlayerItemEvent(player, item);
        registry.sendEvent(getPlayerItemEvent);
        return getPlayerItemEvent.hasItem();
    }

    //Iterate through all received Objects as ArrayList and print it out as a string
    public static void log(Object... arguments) {
        System.out.println(Arrays.asList(arguments).toString());
    }
}
