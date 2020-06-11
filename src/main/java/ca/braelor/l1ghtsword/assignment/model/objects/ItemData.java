package ca.braelor.l1ghtsword.assignment.model.objects;
import ca.braelor.l1ghtsword.assignment.exception.*;
import ca.braelor.l1ghtsword.assignment.model.enums.Item;

public class ItemData {

    private Item item;
    private int quantity = 1;

    //Method overloaded to Create ItemData object as a set quality (check if stackable first)
    //OR keep default value of 1
    public ItemData(Item i, int q) {
        this.item = i;
        this.quantity = q;
    }
    public ItemData(Item i) {
        this.item = i;
    }

    public Item getItem() { return this.item; }

    public int getQuantity() { return this.quantity; }

    public void setQuantity(int q) {
        if (q > 0) { this.quantity = q; }
        else if (q == 0) {throw new CannotBeZeroError(); }
        else { throw new NegativeValueError(); }
    }

    public void addQuantity(int q) {
        if(q > 0) {
            if (((long)this.quantity + (long)q) <= Integer.MAX_VALUE) {
                this.quantity += q;
            } else { throw new AdditionError(); }
        } else { throw new NegativeValueError(); }
    }

    public void subQuantity(int q) {
        if(q > 0) {
            if ((this.quantity - q) < 1) {
                this.quantity -= q;
            } else { throw new SubtractionError(); }
        } else { throw new NegativeValueError(); }
    }
}