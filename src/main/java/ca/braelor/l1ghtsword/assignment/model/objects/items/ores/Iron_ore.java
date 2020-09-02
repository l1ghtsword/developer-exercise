package ca.braelor.l1ghtsword.assignment.model.objects.items.ores;

import ca.braelor.l1ghtsword.assignment.implem.ItemData;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public class Iron_ore extends ItemData {

    public Iron_ore() {
        this.itemID = ItemID.TIN_ORE;
        this.levelRequirement = 15;
        this.xpAmountGiven = 35;
    }
}