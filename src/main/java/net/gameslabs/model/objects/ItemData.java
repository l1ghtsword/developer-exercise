package net.gameslabs.model.objects;
import net.gameslabs.exception.AssignmentFailed;
import net.gameslabs.model.enums.Item;
import net.gameslabs.model.enums.StackableItem;

public class ItemData {

    private Item item;
    private int quantity = 1;
    private boolean stackable;

    //Method overloaded to Create ItemData object as a set quality (check if stackable first)
    //OR keep default value of 1
    public ItemData(Item i, int q) {
        this.item = i;
        this.stackable = StackableItem.isStackable(i);
        this.setQuantity(q);
    }

    public ItemData(Item i) {
        this.item = i;
        this.stackable = StackableItem.isStackable(i);
    }

    public Item getItem() { return this.item; }

    public boolean isStackable() { return this.stackable; }

    public int getQuantity() { return this.quantity; }

    public void setQuantity(int q) {
        //isStackable should be called before setting quantity, unstackable items will throw exception
        if(!this.stackable) { throw new AssignmentFailed("ITEM_NOT_STACKABLE_ERROR - This item is not stackable!"); }
        if(q < 1) { throw new AssignmentFailed("ITEM_SET_QUAN_ERROR - Result set cannot be a negative integer or 0"); }
        this.quantity = q;
    }

    public void addQuantity(int q) {
        //isStackable should be called before adding quantity, unstackable items will throw exception
        if(!this.stackable) { throw new AssignmentFailed("ITEM_NOT_STACKABLE_ERROR - This item is not stackable!"); }
        if(q < 0) { throw new AssignmentFailed("ITEM_ADD_QUAN_ERROR - Value added cannot be a negative integer"); }
        if(((long)q + (long)this.quantity) > Integer.MAX_VALUE) { throw new AssignmentFailed("OVERFLOW_ERROR - Result cannot exceed "+Integer.MAX_VALUE); }
        this.quantity += q;
    }

    public void subQuantity(int q) {
        //isStackable should be called before subtracting quantity, unstackable items will throw exception
        if(!this.stackable) { throw new AssignmentFailed("ITEM_NOT_STACKABLE_ERROR - This item is not stackable!"); }
        if(q < 0) { throw new AssignmentFailed("ITEM_SUB_QUAN_ERROR - Value added cannot be a negative integer"); }
        if((q - this.quantity) < 1) { throw new AssignmentFailed("UNDERFLOW_ERROR - Result cannot be less then 1"); }
        this.quantity -= q;

    }
}