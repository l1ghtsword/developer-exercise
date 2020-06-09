package assignment.components;

import net.gameslabs.api.Component;
import net.gameslabs.api.Player;
import net.gameslabs.model.objects.PlayerInventory;

import java.util.HashMap;

public class InventoryComponent extends Component {

    private HashMap<Player, PlayerInventory> persistence;
    public InventoryComponent() { persistence = new HashMap<>(); }

    @Override
    public void onLoad() {
        //nothing to do yet
    }

    private PlayerInventory getInventory(Player player) {
        if (persistence.containsKey(player)) { return persistence.get(player); }
        return persistence.computeIfAbsent(player, p -> new PlayerInventory());
    }

    @Override
    public void onUnload() {
        // Nothing to do
    }
}
