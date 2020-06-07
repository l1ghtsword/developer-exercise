package net.gameslabs.components;

import net.gameslabs.api.Component;
import net.gameslabs.api.Player;
import net.gameslabs.events.GetPlayerLevel;
import net.gameslabs.events.GetXPForLevelEvent;
import net.gameslabs.events.GiveXpEvent;
import net.gameslabs.model.PlayerStats;

import java.util.HashMap;
import java.util.Map;

public class ChartComponent extends Component {
    //*Assumption* constant value for per level requirement
    private static final int XP_STEP = 50;
    //Declare Map to pair Player to PlayerStats obj as "persistence"
    private Map<Player, PlayerStats> persistence;
    //Initialize persistence as HashMap obj (Array of playerStat objects using "Player" as the key)
    public ChartComponent() {
        persistence = new HashMap<>();
    }

    @Override
    public void onLoad() {
        //Register event listener onGetXPForLevel listening for GetXPForLevelEvent event
        registerEvent(GetXPForLevelEvent.class, this::onGetXPForLevel);
        //Register event listener onGiveXPToPlayer listening for GiveXpEvent event
        registerEvent(GiveXpEvent.class, this::onGiveXPToPlayer);
        //Register event listener onGetPlayerLevel listening for GetPlayerLevel event
        registerEvent(GetPlayerLevel.class, this::onGetPlayerLevel);
    }

    //listener to set xp needed for next level ( player level * XP_STEP (50))
    private void onGetXPForLevel(GetXPForLevelEvent event) {
        event.setXp(event.getLevel() * XP_STEP);
    }

    //Listener
    private void onGiveXPToPlayer(GiveXpEvent event) { getStats(event.getPlayer()).addXp(event.getSkill(), event.getXp()); }

    private void onGetPlayerLevel(GetPlayerLevel event) { event.setLevel(getLevelFromXp(getStats(event.getPlayer()).getXp(event.getSkill()))); }

    private int getLevelFromXp(int xp) {
        return 1 + Math.floorDiv(xp, XP_STEP);
    }

    private PlayerStats getStats(Player player) {
        return persistence.computeIfAbsent(player, p -> new PlayerStats());
    }

    @Override
    public void onUnload() {
        // Nothing to do
    }
}
