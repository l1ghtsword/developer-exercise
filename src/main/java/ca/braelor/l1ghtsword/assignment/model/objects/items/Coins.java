package ca.braelor.l1ghtsword.assignment.model.objects.items;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.ItemData;

public class Coins extends ItemData {

    public Coins() {
        super(ItemID.COINS);
        this.imStackable = true;
    }

    public Coins(int amount) {
        super(ItemID.COINS);
        this.itemQuantity = amount;
        this.imStackable = true;
    }
}
