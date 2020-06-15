package ca.braelor.l1ghtsword.assignment.model.objects;

import ca.braelor.l1ghtsword.assignment.exception.ItemNotCookableError;
import ca.braelor.l1ghtsword.assignment.model.enums.Item;

/**
 * Instanced obj to be used by the cooking skill. Will throw an exception if item provided to instance is NOT food
 * This is to allow catch to apply logic to inform the player that this is not a valid item that can be cooked.
 *
 * Obj will also keep track of /actual/ food variables like what you get if you burn it, the burn rate, level requirements, etc.
 * DOES NOT know item usability, that's managed in the Usable obj
 */

public class Food {

    private final Item i;
    private final int burnChance;
    private final Item cookSuccess;
    private final Item cookFail;
    private final int level;
    private final int xp;

    public Food(Item item) {
        this.i = item;
        if (i.equals(Item.RAW_SHRIMP)) {
            this.burnChance = 10;
            this.cookSuccess = Item.SHRIMP;
            this.cookFail = Item.BURNT_SHRIMP;
            this.level = 0;
            this.xp = 30;
        } else if (i.equals(Item.RAW_FISH)) {
            this.burnChance = 20;
            this.cookSuccess = Item.FISH;
            this.cookFail = Item.BURNT_FISH;
            this.level = 5;
            this.xp = 40;
        } else if (i.equals(Item.RAW_CHICKEN)) {
            this.burnChance = 30;
            this.cookSuccess = Item.CHICKEN;
            this.cookFail = Item.BURNT_CHICKEN;
            this.level = 20;
            this.xp = 80;
        } else if (i.equals(Item.RAW_BEEF)) {
            this.burnChance = 40;
            this.cookSuccess = Item.BEEF;
            this.cookFail = Item.BURNT_BEEF;
            this.level = 40;
            this.xp = 120;
        } else if (i.equals(Item.RAW_SPAGHETTI)) {
            this.burnChance = 55;
            this.cookSuccess = Item.SPAGHETTI;
            this.cookFail = Item.BURNT_SPAGHETTI;
            this.level = 85;
            this.xp = 240;
        } else {
            throw new ItemNotCookableError(i);
        }
    }

    public Item getItem() { return this.i; }
    public int getBurnChance() { return this.burnChance; }
    public Item getCooked() { return this.cookSuccess; }
    public Item getBurnt() { return this.cookFail; }
    public int getLevel() { return this.level; }
    public int getXp() { return this.xp; }
}