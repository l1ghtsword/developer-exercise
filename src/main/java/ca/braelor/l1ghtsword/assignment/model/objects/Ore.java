package ca.braelor.l1ghtsword.assignment.model.objects;

import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.Rock;

/**
 * Instanced obj used for associated Rock resources used in the Mining Skill
 * Manages the Ore being mined variable such as What item will be received as a result of being mined successfully,
 * What level is required to mine this ore, how much xp does this ore give when mined, etc.
 */

public class Ore {

    private final Rock r;
    private final Item i;
    private final int level;
    private final int xp;

    public Ore(Rock rock) {
        this.r = rock;
        if(r.equals(Rock.TIN)){
            this.i = Item.TIN_ORE;
            this.level = 0;
            this.xp = 17;
        }
        else if(r.equals(Rock.COPPER)){
            this.i = Item.COPPER_ORE;
            this.level = 0;
            this.xp = 17;
        }
        else if(r.equals(Rock.IRON)){
            this.i = Item.IRON_ORE;
            this.level = 15;
            this.xp = 35;
        }
        else if(r.equals(Rock.SILVER)){
            this.i = Item.SILVER_ORE;
            this.level = 20;
            this.xp = 40;
        }
        else if(r.equals(Rock.COAL)){
            this.i = Item.COAL_ORE;
            this.level = 5;
            this.xp = 50;
        }
        else if(r.equals(Rock.GOLD)){
            this.i = Item.GOLD_ORE;
            this.level = 40;
            this.xp = 65;
        }
        else if(r.equals(Rock.MITHRIL)){
            this.i = Item.MITHRIL_ORE;
            this.level = 55;
            this.xp = 80;
        }
        else if(r.equals(Rock.ADAMANTITE)){
            this.i = Item.ADAMANTITE_ORE;
            this.level = 70;
            this.xp = 95;
        }
        else if(r.equals(Rock.RUNITE)){
            this.i = Item.RUNITE_ORE;
            this.level = 85;
            this.xp = 125;
        }
        else {
            i = Item.EMPTY;
            level = 0;
            xp = 0;
        }
    }

    public Rock getRock() { return this.r; }
    public Item getItem() { return this.i; }
    public int getLevel() { return this.level; }
    public int getXp() { return this.xp; }
}