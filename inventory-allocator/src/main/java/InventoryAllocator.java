import java.util.*;

public class InventoryAllocator {
    List<WareHouse> wareHouses;
    Map<String, Integer> totalInventory;

    public InventoryAllocator(){
        this.wareHouses = new ArrayList<>();
        this.totalInventory = new HashMap<>();
    }

    // allocate solution, return string list like [warehouse1:{apple:5}, warehouse2:{apple:5}]
    public List<String> allocate(Order order) {
        List<String> res = new ArrayList<>();
        if (!canAllocate(order)) {
            return res;
        }
        for (WareHouse w : wareHouses) {
            if (order.map.size() == 0) {
                break;
            }
            List<String> shipment = wareHouseShip(w, order);
            if (shipment == null || shipment.size() == 0) continue;
            String str = w.name + ":{";
            for (String s : shipment) {
                str = str + s + ",";
            }
            str = str.substring(0, str.length() - 1) + "}";
            res.add(str);
        }
        Collections.sort(res);
        return res;
    }

    // calculate the shipment for one warehouse, return list like {"apple:1" , "orange:2"}
    public List<String> wareHouseShip(WareHouse w, Order order) {
        List<String> res = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : w.map.entrySet()) {
            String fruit = entry.getKey();
            int num = entry.getValue();
            if (num == 0) continue;
            if (order.map.containsKey(fruit)) {
                int order_num = order.map.get(fruit);
                if (num >= order_num) {
                    order.map.remove(fruit);
                    w.map.put(fruit, num - order_num);
                    totalInventory.put(fruit, totalInventory.get(fruit) - order_num);
                    res.add(fruit + ":" + order_num);
                } else {
                    order.map.put(fruit, order_num - num);
                    w.map.put(fruit, 0);
                    totalInventory.put(fruit, totalInventory.get(fruit) - num);
                    res.add(fruit + ":" + num);
                }
            }
        }
        Collections.sort(res);
        return res;
    }

    //check if inventory is enough for order
    public boolean canAllocate(Order order) {
        if (order == null) {
            return false;
        }
        for (Map.Entry<String, Integer> entry : order.map.entrySet()) {
            String key = entry.getKey();
            int val = entry.getValue();
            if (this.totalInventory.getOrDefault(key, 0) < val) {
                return false;
            }
        }
        return true;
    }

    // add a warehouse's inventory into the total inventory
    public void addWareHouse(WareHouse w) {
        this.wareHouses.add(w);
        for (Map.Entry<String, Integer> entry : w.map.entrySet()) {
            String key = entry.getKey();
            int val = entry.getValue();
            this.totalInventory.putIfAbsent(key, 0);
            this.totalInventory.put(key, this.totalInventory.get(key) + val);
        }
    }

    //convert string to hashmap
    public static void mapping(Map<String, Integer> map, String s) {
        // s is like "apple:1,orange:2,banana:3"
        String[] sa = s.trim().split(",");
        for (String str : sa) {
            String[] pair = str.trim().split(":");
            map.put(pair[0], Integer.parseInt(pair[1]));
        }
    }
}
