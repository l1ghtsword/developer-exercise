package ca.braelor.l1ghtsword.assignment.model.objects.items.food;

import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.objects.FoodItem;

public class Raw_fish extends FoodItem {

    public Raw_fish() {
        this.itemID=ItemID.RAW_FISH;
        this.cookBurnChance = 20;
        this.itemOnCookSuccess = new Fish();
        this.itemOnCookFail = new Burnt_fish();
        this.levelRequirement = 5;
        this.xpAmountGiven = 40;
        this.onUseProperties = "30 HP restored!";
        this.imCookable = true;
    }
}