package ca.braelor.l1ghtsword.assignment.interfaces;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public interface Item {

    ItemID getItemID();

    int getQuantity();

    void setQuantity(int quantity);

    void addQuantity(int quantity);

    void subQuantity(int quantity);

    public int getBurnChance();

    public ItemID getCookedItem();

    public ItemID getBurntItem();

    public int getLevelRequirement();

    public int getXpAmountGiven();

    public String getUseProperties();

    boolean isStackable();

    boolean isUsable();

    boolean isCookable();

    Item createNewInstanceOf (Item item);
}
