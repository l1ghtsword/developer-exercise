package ca.braelor.l1ghtsword.assignment.model.objects.items.food;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.objects.FoodItem;

public class Raw_shrimp extends FoodItem {

    public Raw_shrimp() {
        this.itemID=ItemID.RAW_SHRIMP;
        this.cookBurnChance = 10;
        this.itemOnCookSuccess = new Shrimp();
        this.itemOnCookFail = new Burnt_shrimp();
        this.levelRequirement = 0;
        this.xpAmountGiven = 30;
        this.onUseProperties = "10 HP restored!";
        this.imCookable = true;
    }
}