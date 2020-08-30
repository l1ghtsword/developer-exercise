package ca.braelor.l1ghtsword.assignment.model.objects.items.food;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.objects.FoodItem;

public class Fish extends FoodItem {

    public Fish() {
        this.itemID=ItemID.FISH;
        this.onUseProperties = "40 HP restored!";
    }
}