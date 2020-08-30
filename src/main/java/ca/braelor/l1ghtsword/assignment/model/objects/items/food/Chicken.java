package ca.braelor.l1ghtsword.assignment.model.objects.items.food;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.objects.FoodItem;

public class Chicken extends FoodItem {

    public Chicken() {
        this.itemID=ItemID.CHICKEN;
        this.onUseProperties = "60 HP restored!";
    }
}