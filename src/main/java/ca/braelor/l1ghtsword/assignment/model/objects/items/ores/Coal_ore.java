package ca.braelor.l1ghtsword.assignment.model.objects.items.ores;

import ca.braelor.l1ghtsword.assignment.implem.ItemData;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public class Coal_ore extends ItemData {

    public Coal_ore() {
        this.itemID = ItemID.TIN_ORE;
        this.levelRequirement = 5;
        this.xpAmountGiven  = 50;
    }
}