package ca.braelor.l1ghtsword.assignment.model.objects.items.food;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.objects.FoodItem;

public class Raw_beef extends FoodItem {

    public Raw_beef() {
        this.itemID = ItemID.RAW_BEEF;
        this.cookBurnChance = 40;
        this.itemOnCookSuccess = new Beef();
        this.itemOnCookFail = new Burnt_beef();
        this.levelRequirement = 40;
        this.xpAmountGiven = 120;
        this.onUseProperties = "70 HP restored!";
        this.imCookable = true;
    }
}