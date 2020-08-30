package ca.braelor.l1ghtsword.assignment.model.objects.items.food;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.objects.FoodItem;

public class Burnt_chicken extends FoodItem {

    public Burnt_chicken() {
        this.itemID=ItemID.BURNT_CHICKEN;
        this.imUsable = false;
    }
}