package ca.braelor.l1ghtsword.assignment.interfaces;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;

public interface Item {

    ItemID getItemID();

    int getQuantity();

    void setQuantity(int quantity);

    void addQuantity(int quantity);

    void subQuantity(int quantity);

    boolean isStackable();

    boolean isUsable();

    boolean isCookable();
}
