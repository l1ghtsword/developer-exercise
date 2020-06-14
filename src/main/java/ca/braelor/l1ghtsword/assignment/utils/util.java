package ca.braelor.l1ghtsword.assignment.utils;

import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.StackableItem;
import ca.braelor.l1ghtsword.assignment.model.objects.ItemData;

public final class util {

    public static boolean isStackable(Item i) {
        for(StackableItem s : StackableItem.values()) {
            if(s.toString().equals(i.toString())) { return true; }
        }
        return false;
    }

    public static ItemData toItemData(Item i, int q) { return new ItemData(i,q); }

}
