package ca.braelor.l1ghtsword.assignment.model.objects.items.food;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.objects.FoodItem;

public class Raw_spaghetti extends FoodItem {

    public Raw_spaghetti() {
        this.itemID=ItemID.RAW_SPAGHETTI;
        this.cookBurnChance = 55;
        this.itemOnCookSuccess = new Spaghetti();
        this.itemOnCookFail = new Burnt_spaghetti();
        this.levelRequirement = 85;
        this.xpAmountGiven = 240;
        this.onUseProperties = "120 HP restored!";
        this.imCookable = true;
    }
}