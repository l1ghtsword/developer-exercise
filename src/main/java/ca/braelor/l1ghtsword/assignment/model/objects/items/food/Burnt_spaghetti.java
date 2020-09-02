package ca.braelor.l1ghtsword.assignment.model.objects.items.food;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.objects.FoodItem;

public class Burnt_spaghetti extends FoodItem {

    public Burnt_spaghetti() {
        this.itemID = ItemID.BURNT_SPAGHETTI;
        this.imUsable = false;
    }
}