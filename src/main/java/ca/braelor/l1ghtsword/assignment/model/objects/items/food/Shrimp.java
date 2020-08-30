package ca.braelor.l1ghtsword.assignment.model.objects.items.food;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.objects.FoodItem;

public class Shrimp extends FoodItem {

    public Shrimp() {
        this.itemID=ItemID.SHRIMP;
        this.onUseProperties = "20 HP restored!";
    }
}