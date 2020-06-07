package assignment;

import assignment.components.InventoryComponent;
import assignment.components.MyXPBoosterComponent;
import net.gameslabs.components.ChartComponent;
import net.gameslabs.model.Assignment;

public class Main {

    public static void main(String[] args) {
        new Assignment(
            new MyXPBoosterComponent(),
            new ChartComponent(),
            new InventoryComponent()
        ).run();
    }
}
