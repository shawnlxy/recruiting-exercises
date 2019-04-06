import java.util.HashMap;
import java.util.Map;

public class Order {
    //<apple, 1>
    Map<String, Integer> map;

    public Order(String request) {
        this.map = new HashMap<>();
        InventoryAllocator.mapping(this.map, request);
    }

}
