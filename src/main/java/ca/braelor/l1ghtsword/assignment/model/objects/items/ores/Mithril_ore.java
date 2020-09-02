package ca.braelor.l1ghtsword.assignment.model.objects.items.ores;

import ca.braelor.l1ghtsword.assignment.implem.ItemData;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public class Mithril_ore extends ItemData {

    public Mithril_ore() {
        this.itemID = ItemID.TIN_ORE;
        this.levelRequirement = 55;
        this.xpAmountGiven = 80;
    }
}