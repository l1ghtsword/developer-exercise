package ca.braelor.l1ghtsword.assignment.implem;

import ca.braelor.l1ghtsword.assignment.exception.*;
import ca.braelor.l1ghtsword.assignment.interfaces.Item;
import ca.braelor.l1ghtsword.assignment.model.enums.ItemID;
import ca.braelor.l1ghtsword.assignment.model.objects.items.Empty;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static net.gameslabs.model.objects.Assignment.log;

/**
 * Refactored to define the setters and getters to be inherited by children class that will use them.
 * Their behaviour being defined by the interface "Item" which will group all items into this scope.
 */

public class ItemData implements Item {

    protected ItemID itemID;
    protected int itemQuantity;
    protected int cookBurnChance;
    protected Item itemOnCookSuccess;
    protected Item itemOnCookFail;
    protected int levelRequirement;
    protected int xpAmountGiven;
    protected String onUseProperties;
    protected boolean imStackable;
    protected boolean imUsable;
    protected boolean imCookable;

    public ItemData() {
        this.itemQuantity = 1;
        this.onUseProperties="";
    }

    public ItemID getItemID() {
        return this.itemID;
    }

    public int getQuantity() {
        return this.itemQuantity;
    }

    public void setQuantity(int newQuantity) {
        if (newQuantity > 0) {
            this.itemQuantity = newQuantity;
        } else if (newQuantity == 0) {
            throw new CannotBeZeroError();
        } else {
            throw new NegativeValueError();
        }
    }

    public void addQuantity(int newQuantity) {
        if (newQuantity > 0) {
            if (((long) this.itemQuantity + (long) newQuantity) <= Integer.MAX_VALUE) {
                this.itemQuantity += newQuantity;
            } else {
                throw new AdditionError();
            }
        } else {
            throw new NegativeValueError();
        }
    }

    public void subQuantity(int newQuantity) {
        if (newQuantity > 0) {
            if ((this.itemQuantity - newQuantity) >= 1) {
                this.itemQuantity -= newQuantity;
            } else {
                throw new SubtractionError();
            }
        } else {
            throw new NegativeValueError();
        }
    }

    public int getBurnChance() {
        return this.cookBurnChance;
    }

    public Item getCookedItem() {
        return this.itemOnCookSuccess;
    }

    public Item getBurntItem() {
        return this.itemOnCookFail;
    }

    public int getLevelRequirement() {
        return this.levelRequirement;
    }

    public int getXpAmountGiven() {
        return this.xpAmountGiven;
    }

    public String getUseProperties() { return this.onUseProperties; }

    public boolean isStackable() {
        return this.imStackable;
    }

    public boolean isUsable() {
        return this.imUsable;
    }

    public boolean isCookable() {
        return this.imCookable;
    }

    /*
    Clearly i did not come up with on my own... stacktrace reference here https://stackoverflow.com/questions/7495785/java-how-to-instantiate-a-class-from-string
    I needed a way to on the fly create a object instance of ItemData subclasses on the fly, but was stuck with initialization errors as i cant know the correct subclass.

    item.getClass().getName() should get the correct class name in the form of a String argument.
    I use this to uniquely identify the correct Class, where i can then build a constructor object
    and create a new instance of the class.

    I could spend more time on this and perhaps create a map of ItemID Enums and Class Strings constructed at runtime and reference that,
    but for now i settled for reflection as its easier. Though im aware, slower.
    Additionally, there are limitations to requiring the same Item subclass as opposed to just an ID that im also aware of,
    but i do not reach those limitations in this example.
     */
    public Item createNewInstanceOf(Item item) {
        try {
            //Use reflection to get the class from the existing class name as a string
            Class<?> thisClass = Class.forName(item.getClass().getName());
            //Get types in the form of an array of Classes to feed to the constructor creation
            Class<?>[] types = {Double.TYPE, thisClass};
            //Create the constructor using the Class String and the assembled types
            Constructor<?> constructor = thisClass.getConstructor(types);
            //get Object parameters to feed to the constructor when initializing the new object
            Object[] parameters = {(double) 0, thisClass};
            //gib's Item cast object we created
            return (Item) constructor.newInstance(parameters);
        }
        catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException error) {
            log("Bad thing habbon :( \n" + error.getMessage() + "\n");
            error.printStackTrace();
            log("\n\n");
            //returns empty to prevent hard crash/ fix compiler ambiguity... Should never be reached unless bad thing habbon :(
            return new Empty();
        }
    }
}