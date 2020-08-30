package ca.braelor.l1ghtsword.assignment.model.objects.items.food;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.objects.FoodItem;

public class Raw_chicken extends FoodItem {

    public Raw_chicken() {
        this.itemID=ItemID.RAW_CHICKEN;
        this.cookBurnChance = 30;
        this.itemOnCookSuccess = new Chicken();
        this.itemOnCookFail = new Burnt_chicken();
        this.levelRequirement = 20;
        this.xpAmountGiven = 80;
        this.onUseProperties = "50 HP restored!";
        this.imCookable = true;
    }
}