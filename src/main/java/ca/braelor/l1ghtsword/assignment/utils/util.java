package ca.braelor.l1ghtsword.assignment.utils;

import ca.braelor.l1ghtsword.assignment.exception.ItemNotCookableError;
import ca.braelor.l1ghtsword.assignment.model.enums.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.StackableItem;
import ca.braelor.l1ghtsword.assignment.model.enums.UsableItem;
import ca.braelor.l1ghtsword.assignment.model.objects.Food;
import ca.braelor.l1ghtsword.assignment.model.objects.ItemData;

/**
 * Non instanced class containing general tools used by other classes to check for common information.
 * such as if an item is stackable, or convert and Item and Quantity into an ItemData obj
 */

public final class util {

    public static boolean isStackable(Item i) {
        for(StackableItem s : StackableItem.values()) {
            if(s.toString().equals(i.toString())) { return true; }
        }
        return false;
    }

    public static boolean isUsable(Item i) {
        for(UsableItem u : UsableItem.values()) {
            if(u.toString().equals(i.toString())) { return true; }
        }
        return false;
    }

    public static boolean isCookable(Item i) {
        try {
            new Food(i);
            return true;
        } catch (ItemNotCookableError err) { return false; }
    }

    public static ItemData toItemData(Item i, int q) { return new ItemData(i,q); }

}
