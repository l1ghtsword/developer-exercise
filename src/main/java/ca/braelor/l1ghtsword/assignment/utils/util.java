package ca.braelor.l1ghtsword.assignment.utils;

import ca.braelor.l1ghtsword.assignment.model.ItemData;

/**
 * Non instanced class containing general tools used by other classes to check for common information.
 * such as if an item is stackable, or convert and Item and Quantity into an ItemData obj
 */

@Deprecated public final class util {

    @Deprecated public static ItemData toItemData() { return new ItemData(); }

}
