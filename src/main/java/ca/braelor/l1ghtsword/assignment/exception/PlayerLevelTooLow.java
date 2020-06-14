package ca.braelor.l1ghtsword.assignment.exception;

import net.gameslabs.api.Player;
import net.gameslabs.model.enums.Skill;

public class PlayerLevelTooLow extends RuntimeException {
    public PlayerLevelTooLow(Player p, Skill skill, int level) { super(p.getName()+" "+skill+" level is not high enough. Must be at least level "+level+" to do that!"); }
}
