package ca.braelor.l1ghtsword.assignment.model;

import ca.braelor.l1ghtsword.assignment.exception.*;
import ca.braelor.l1ghtsword.assignment.interfaces.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.objects.items.empty;

/**
 * Refactored to define the setters and getters to be inherited by children class that will use them.
 * Their behaviour being defined by the interface "Item" which will group all items into this scope.
 */

public class ItemData implements Item {

    protected final ItemID itemID = ItemID.EMPTY;
    protected int itemQuantity = 1;
    protected int cookBurnChance = 100;
    protected Item itemOnCookSuccess = new empty();
    protected Item itemOnCookFail = new empty();
    protected int levelRequirement = 666;
    protected int xpAmountGiven = 0;
    protected String onUseProperties = "";
    protected final boolean imStackable = false;
    protected final boolean imUsable = false;
    protected boolean imCookable = false;

    public ItemID getItemID() {
        return this.itemID;
    }

    public int getQuantity() {
        return this.itemQuantity;
    }

    public void setQuantity(int newQuantity) {
        if (newQuantity > 0) {
            this.itemQuantity = newQuantity;
        } else if (newQuantity == 0) {
            throw new CannotBeZeroError();
        } else {
            throw new NegativeValueError();
        }
    }

    public void addQuantity(int newQuantity) {
        if (newQuantity > 0) {
            if (((long) this.itemQuantity + (long) newQuantity) <= Integer.MAX_VALUE) {
                this.itemQuantity += newQuantity;
            } else {
                throw new AdditionError();
            }
        } else {
            throw new NegativeValueError();
        }
    }

    public void subQuantity(int newQuantity) {
        if (newQuantity > 0) {
            if ((this.itemQuantity - newQuantity) >= 1) {
                this.itemQuantity -= newQuantity;
            } else {
                throw new SubtractionError();
            }
        } else {
            throw new NegativeValueError();
        }
    }

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

    public String getUseProperties() {
        return this.onUseProperties;
    }

    public boolean isStackable() {
        return this.imStackable;
    }

    public boolean isUsable() {
        return this.imUsable;
    }

    public boolean isCookable() {
        return this.imCookable;
    }
}