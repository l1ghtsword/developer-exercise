package ca.braelor.l1ghtsword.assignment.model.objects;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.enums.Rock;

/**
 * I wont edit this for the scope of the reassignment, however this could be reimplemented better
 * I would take the tame approach as Item and create an interface then extend a data class to sort the different rocks.
 *
 *
 * Instanced obj used for associated Rock resources used in the Mining Skill
 * Manages the Ore being mined variable such as What item will be received as a result of being mined successfully,
 * What level is required to mine this ore, how much xp does this ore give when mined, etc.
 */

public class Ore {

    private final Rock r;
    private final ItemID i;
    private final int level;
    private final int xp;

    public Ore(Rock rock) {
        this.r = rock;
        if(r.equals(Rock.TIN)){
            this.i = ItemID.TIN_ORE;
            this.level = 0;
            this.xp = 17;
        }
        else if(r.equals(Rock.COPPER)){
            this.i = ItemID.COPPER_ORE;
            this.level = 0;
            this.xp = 17;
        }
        else if(r.equals(Rock.IRON)){
            this.i = ItemID.IRON_ORE;
            this.level = 15;
            this.xp = 35;
        }
        else if(r.equals(Rock.SILVER)){
            this.i = ItemID.SILVER_ORE;
            this.level = 20;
            this.xp = 40;
        }
        else if(r.equals(Rock.COAL)){
            this.i = ItemID.COAL_ORE;
            this.level = 5;
            this.xp = 50;
        }
        else if(r.equals(Rock.GOLD)){
            this.i = ItemID.GOLD_ORE;
            this.level = 40;
            this.xp = 65;
        }
        else if(r.equals(Rock.MITHRIL)){
            this.i = ItemID.MITHRIL_ORE;
            this.level = 55;
            this.xp = 80;
        }
        else if(r.equals(Rock.ADAMANTITE)){
            this.i = ItemID.ADAMANTITE_ORE;
            this.level = 70;
            this.xp = 95;
        }
        else if(r.equals(Rock.RUNITE)){
            this.i = ItemID.RUNITE_ORE;
            this.level = 85;
            this.xp = 125;
        }
        else {
            i = ItemID.EMPTY;
            level = 0;
            xp = 0;
        }
    }

    public Rock getRock() { return this.r; }
    public ItemID getItem() { return this.i; }
    public int getLevel() { return this.level; }
    public int getXp() { return this.xp; }
}