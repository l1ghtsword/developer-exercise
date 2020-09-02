package ca.braelor.l1ghtsword.assignment.model.objects.items.ores;

import ca.braelor.l1ghtsword.assignment.implem.ItemData;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public class Gold_ore extends ItemData {

    public Gold_ore() {
        this.itemID = ItemID.TIN_ORE;
        this.levelRequirement = 40;
        this.xpAmountGiven = 65;
    }
}