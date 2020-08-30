package ca.braelor.l1ghtsword.assignment.model.objects.items.ores;

import ca.braelor.l1ghtsword.assignment.implem.ItemData;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public class Runite_ore extends ItemData {

    public Runite_ore() {
        this.itemID = ItemID.TIN_ORE;
        this.levelRequirement = 85;
        this.xpAmountGiven  = 125;
    }
}