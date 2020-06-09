package net.gameslabs.model.enums;

//Enum for skill titles, constants that will not change used to associate any skill check with an item on the list
public enum StackableItem {
    COINS;

















    public static boolean isStackable (Item i) {
        for(StackableItem s : StackableItem.values()) {
            if(s.toString().equals(i.toString())) { return true; }
        }
        return false;
    }
}