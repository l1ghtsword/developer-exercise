package net.gameslabs.components;

import net.gameslabs.api.Component;
import net.gameslabs.api.Player;
import net.gameslabs.events.GetPlayerLevelEvent;
import net.gameslabs.events.GetXPForLevelEvent;
import net.gameslabs.events.GiveXpEvent;
import net.gameslabs.model.objects.PlayerStats;

import java.util.HashMap;

public class ChartComponent extends Component {
    //Constant value for per level requirement 50 xp = level 2, 200 xp = level 5, etc
    private static final int XP_STEP = 50;
    //Declare Map to pair Player to PlayerStats obj as "persistence"
    private HashMap<Player, PlayerStats> persistence;
    //Initialize persistence as HashMap obj (Array of playerStat objects using "Player" as the key)
    public ChartComponent() { persistence = new HashMap<>(); }

    //Register Events to listen to
    @Override
    public void onLoad() {
        registerEvent(GetXPForLevelEvent.class, this::onGetXPForLevel);
        registerEvent(GiveXpEvent.class, this::onGiveXPToPlayer);
        registerEvent(GetPlayerLevelEvent.class, this::onGetPlayerLevel);
    }

    //Listener to calculate the level of a player based on their current xp
    private void onGetPlayerLevel(GetPlayerLevelEvent e) { e.setLevel(getLevelFromXp(getStats(e.getPlayer()).getXp(e.getSkill()))); }

    //listener to set event value xp to required xp for given level ( player level * XP_STEP (50))
    private void onGetXPForLevel(GetXPForLevelEvent e) { e.setXp(e.getLevel() * XP_STEP); }

    //Listener to give xp for any given skill by the provided event amount
    private void onGiveXPToPlayer(GiveXpEvent e) { getStats(e.getPlayer()).addXp(e.getSkill(), e.getXp()); }

    //Method to calculate level at given xp amount
    private int getLevelFromXp(int xp) { return 1 + Math.floorDiv(xp, XP_STEP); }

    //Method to check if player exists in HashMap and will always return a PlayerStats Obj
    //If player has no stats, an Obj will be created and paired with that player, then returned
    //This is acceptable as the PlayerStats Methods will always return a positive integer or 0
    private PlayerStats getStats(Player player) {
        if (persistence.containsKey(player)) { return persistence.get(player); }
        return persistence.computeIfAbsent(player, p -> new PlayerStats());
    }

    @Override
    public void onUnload() {
        // Nothing to do
    }
}
