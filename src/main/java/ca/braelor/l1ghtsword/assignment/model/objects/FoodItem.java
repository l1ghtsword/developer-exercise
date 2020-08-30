package ca.braelor.l1ghtsword.assignment.model.objects;

import ca.braelor.l1ghtsword.assignment.implem.ItemData;

public class FoodItem extends ItemData {

    public FoodItem() {
        this.imUsable=true;
    }

    //OOL Proof of concept, food items override super method and change the method while still having similar behaviour
    @Override
    public String getUseProperties() {
        return "Im a Food Item! - " + onUseProperties;
    }
}