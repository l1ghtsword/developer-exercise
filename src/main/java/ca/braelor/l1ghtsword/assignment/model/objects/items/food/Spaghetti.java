package ca.braelor.l1ghtsword.assignment.model.objects.items.food;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.objects.FoodItem;

public class Spaghetti extends FoodItem {

    public Spaghetti() {
        this.itemID=ItemID.SPAGHETTI;
        this.onUseProperties = "200 HP restored!";
    }
}