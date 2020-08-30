package ca.braelor.l1ghtsword.assignment.model.objects.items;

import ca.braelor.l1ghtsword.assignment.implem.ItemData;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public class Empty extends ItemData {
    public Empty() {
        this.itemID=ItemID.EMPTY;
    }
}
