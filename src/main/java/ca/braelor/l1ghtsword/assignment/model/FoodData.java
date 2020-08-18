package ca.braelor.l1ghtsword.assignment.model;

import ca.braelor.l1ghtsword.assignment.interfaces.Item;

/**
 * Refactored to extend the base functionality if Item Data.
 *
 * Deprecated as this code would require multiple inheritance to be possible. merged with ItemData...
 */

@Deprecated public class FoodData extends ItemData {

    private int cookBurnChance;
    private Item itemOnCookSuccess;
    private Item itemOnCookFail;
    private int levelRequirement;
    private int xpAmountGiven;

    public int getBurnChance() {
        return this.cookBurnChance;
    }

    public Item getCookedItem() {
        return this.itemOnCookSuccess;
    }

    public Item getBurntItem() {
        return this.itemOnCookFail;
    }

    public int getLevelRequirement() {
        return this.levelRequirement;
    }

    public int getXpAmountGiven() {
        return this.xpAmountGiven;
    }
}