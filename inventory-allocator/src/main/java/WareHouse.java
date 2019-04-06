import java.util.HashMap;
import java.util.Map;

public class WareHouse {
    String name;
    Map<String, Integer> map; // <apple, 1>

    public WareHouse(String name, String inventory) {
        this.name = name;
        this.map = new HashMap<>();
        //inventory mapping
        InventoryAllocator.mapping(this.map, inventory);
    }

}
