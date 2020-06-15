package ca.braelor.l1ghtsword.assignment.model.objects;

import ca.braelor.l1ghtsword.assignment.exception.ItemNotUsableError;
import ca.braelor.l1ghtsword.assignment.model.enums.Item;

/**
 * Instanced obj containing the Item properties of valid usable items.
 * Currently only returns a string, however this could be abstracted a bit to provide general
 * checks for functional properties that would effect players in game. Proof of concept only.
 *
 * If item is not usable, will throw an exception to be caught.
 */

public class Usable {

    private final Item i;
    private final String properties;

    public Usable(Item item) {
        this.i = item;
        if (i.equals(Item.RAW_SHRIMP)) {
            this.properties = "10 HP restored!";
        } else if (i.equals(Item.SHRIMP)) {
            this.properties = "20 HP restored!";
        } else if (i.equals(Item.RAW_FISH)) {
            this.properties = "20 HP restored!";
        } else if (i.equals(Item.RAW_CHICKEN)) {
            this.properties = "30 HP restored!";
        } else if (i.equals(Item.CHICKEN)) {
            this.properties = "50 HP restored!";
        } else if (i.equals(Item.RAW_BEEF)) {
            this.properties = "60 HP restored!";
        } else if (i.equals(Item.BEEF)) {
            this.properties = "90 HP restored!";
        } else if (i.equals(Item.RAW_SPAGHETTI)) {
            this.properties = "120 HP restored!";
        } else if (i.equals(Item.SPAGHETTI)) {
            this.properties = "200 HP restored!";
        } else {
            throw new ItemNotUsableError(i);
        }
    }

    public Item getItem() { return this.i; }
    public String getProperties() { return this.properties; }
}