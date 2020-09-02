package ca.braelor.l1ghtsword.assignment.model.objects.items.food;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.objects.FoodItem;

public class Beef extends FoodItem {

    public Beef() {
        this.itemID = ItemID.BEEF;
        this.onUseProperties = "90 HP restored!";
    }
}