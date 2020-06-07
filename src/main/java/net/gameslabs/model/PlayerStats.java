package net.gameslabs.model;
import java.util.EnumMap;
import java.util.Map;

//Intended to structure and map implemented skills to a value and manage XP for player objects
public class PlayerStats {
    //Organize skills with a xp value represented as a number
    private Map<Skill, Integer> xpStats;
    //initialize xpStats object as an EnumMap object (Array of skill to value mappings)
    public PlayerStats() {
        xpStats = new EnumMap<>(Skill.class);
    }
    //Method to set skill XP (overriding any value previously set!)
    public void setXp(Skill skill, int xp) {
        xpStats.put(skill, xp);
    }
    //Method to get skill XP for a specified skill
    public int getXp(Skill skill) {
        return xpStats.getOrDefault(skill, 0);
    }
    //Method which combines get and set to determine current player XP level and add additional experience.
    //Modified to prevent negative or 0 values from being added
    public void addXp(Skill skill, int xp) {
        if(xp >= 0) { setXp(skill, getXp(skill) + xp); }
        else { throw new AssignmentFailed("XP_ADD_ERROR - Xp value added must be positive integer"); }
    }
    public void subXp(Skill skill, int xp) {
        if(xp <= 0) {
            if(getXp(skill) <= 0){ setXp(skill, getXp(skill) - xp); }
            else { throw new AssignmentFailed("XP_NEG_ERROR - Skill xp is already 0 and can not be a negative value!"); }
        }
        else { throw new AssignmentFailed("XP_SUB_ERROR - Value added must be a negative Integer"); }
    }
}
