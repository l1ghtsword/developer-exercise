package ca.braelor.l1ghtsword.assignment.model;

import ca.braelor.l1ghtsword.assignment.exception.ItemNotUsableError;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

/**
 * Refactored to extend the base functionality if Item Data.
 *
 * Deprecated as this code would require multiple inheritance to be possible. merged with ItemData...
 */

@Deprecated public class UsableData {

    private String onUseProperties;

    public String getUseProperties() { return this.onUseProperties; }
}