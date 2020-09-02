package ca.braelor.l1ghtsword.assignment.model.objects.items.ores;

import ca.braelor.l1ghtsword.assignment.implem.ItemData;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public class Tin_ore extends ItemData {

    public Tin_ore() {
        this.itemID = ItemID.TIN_ORE;
        this.levelRequirement = 0;
        this.xpAmountGiven = 17;
    }
}