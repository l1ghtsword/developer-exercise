package net.gameslabs.implem;

import net.gameslabs.api.Player;
import net.gameslabs.model.PlayerStats;
import net.gameslabs.model.Skill;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

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
    public static Player newPlayer(String name) { return new PlayerImplem((Integer.toString(++players)), "player-" +name); }
    //Set player stats for given skill
    public void setXP(Skill s, int xp) {stats.setXp(s,xp); }
    //Return player stats for given skill
    public int getXP(Skill s) {return stats.getXp(s); }
    //Add given xp value for given player skill to skill xp total
    public void addXP(Skill s, int xp) {stats.addXp(s,xp); }
    //Subtract given xp value for given player skill to skill xp total
    public void subXP(Skill s, int xp) {stats.subXp(s,xp); }
}
