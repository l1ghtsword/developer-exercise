package ca.braelor.l1ghtsword.assignment.model.objects.items.ores;

import ca.braelor.l1ghtsword.assignment.implem.ItemData;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public class Copper_ore extends ItemData {

    public Copper_ore() {
        this.itemID = ItemID.COPPER_ORE;
        this.levelRequirement = 0;
        this.xpAmountGiven  = 17;
    }
}