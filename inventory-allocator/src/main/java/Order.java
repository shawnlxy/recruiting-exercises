import java.util.HashMap;
import java.util.Map;

public class Order {
    Map<String, Integer> map; //<apple, 1>

    public Order(String request) {
        this.map = new HashMap<>();
        //convert string like "apple:1,orange:2" to hashmap
        InventoryAllocator.mapping(this.map, request);
    }

}
