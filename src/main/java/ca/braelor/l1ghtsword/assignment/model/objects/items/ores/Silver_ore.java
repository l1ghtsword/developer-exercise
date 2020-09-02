package ca.braelor.l1ghtsword.assignment.model.objects.items.ores;

import ca.braelor.l1ghtsword.assignment.implem.ItemData;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public class Silver_ore extends ItemData {

    public Silver_ore() {
        this.itemID = ItemID.TIN_ORE;
        this.levelRequirement = 20;
        this.xpAmountGiven = 40;
    }
}