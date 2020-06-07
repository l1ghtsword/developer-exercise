package net.gameslabs.implem;

import net.gameslabs.api.Player;
import net.gameslabs.model.PlayerStats;
import net.gameslabs.model.Skill;

import java.util.*;

public class PlayerImplem implements Player {

    private String id;
    private String name;
    private UUID uuid;
    private static int players;
    private PlayerStats stats;

    private PlayerImplem(String id, String name) {
        this.id = id;
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.stats = new PlayerStats();
        //Iterate list of skill's and set default value of 0
        Arrays.asList(Skill.values()).forEach(s -> this.stats.setXp(s,0));
    }
    //Returns instance player ID
    @Override
    public String getId() {
        return this.id;
    }
    //Returns instance player Name
    @Override
    public String getName() {
        return this.name;
    }
    //Return instance player UUID
    @Override
    public UUID getUuid() {
        return this.uuid;
    }
    //Comparison method between two PlayerImplem objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerImplem that = (PlayerImplem) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(uuid, that.uuid);
    }
    //Return hashCode containing Player Obj identifiers
    @Override
    public int hashCode() {
        return Objects.hash(id, name, uuid);
    }
    //Return String containing Player Obj identifiers
    @Override
    public String toString() {
        return "(" + id + ", " + name + ", " + uuid + ")";
    }
    //Return PlayerImplem object

    @Override
    public PlayerStats getStats() { return this.stats; }

    @Override
    public void setStats(Skill s, int xp) { this.stats.setXp(s,xp); }

    @Override
    public void addStats(Skill s, int xp) { this.stats.addXp(s,xp); }

    @Override
    public void subStats(Skill s, int xp) { this.stats.subXp(s,xp); }

    public static Player newPlayer(String name) { return new PlayerImplem((Integer.toString(++players)), "player-" +name); }
    //Set player stats for given skill
}
