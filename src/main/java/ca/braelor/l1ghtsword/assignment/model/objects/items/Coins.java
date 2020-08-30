package ca.braelor.l1ghtsword.assignment.model.objects.items;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.implem.ItemData;

public class Coins extends ItemData {

    public Coins() {
        this.itemID = ItemID.COINS;
        this.imStackable = true;
    }

    public Coins(int amount) {
        this.itemID = ItemID.COINS;
        this.itemQuantity = amount;
        this.imStackable = true;
    }
}
