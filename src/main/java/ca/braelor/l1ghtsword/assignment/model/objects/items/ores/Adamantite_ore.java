package ca.braelor.l1ghtsword.assignment.model.objects.items.ores;

import ca.braelor.l1ghtsword.assignment.implem.ItemData;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public class Adamantite_ore extends ItemData {

    public Adamantite_ore() {
        this.itemID = ItemID.TIN_ORE;
        this.levelRequirement = 70;
        this.xpAmountGiven = 95;
    }
}