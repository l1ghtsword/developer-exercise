package ca.braelor.l1ghtsword.assignment;

import ca.braelor.l1ghtsword.assignment.components.CookingComponent;
import ca.braelor.l1ghtsword.assignment.components.InventoryComponent;
import ca.braelor.l1ghtsword.assignment.components.MiningComponent;
import ca.braelor.l1ghtsword.assignment.components.MyXPBoosterComponent;
import net.gameslabs.components.ChartComponent;
import net.gameslabs.model.objects.Assignment;

public class Main {

    public static void main(String[] args) {
        new Assignment(
                new MyXPBoosterComponent(),
                new ChartComponent(),
                new InventoryComponent(),
                new MiningComponent(),
                new CookingComponent()
        ).run();
    }
}
