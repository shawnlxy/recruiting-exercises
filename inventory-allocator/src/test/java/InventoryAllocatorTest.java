import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class InventoryAllocatorTest {
    InventoryAllocator o1; // inventory = [ { name: w1, inventory: { apple: 0, orange: 0 } } ]
    InventoryAllocator o2; // inventory = [ { name: w2, inventory: { apple: 1, orange: 1 } } ]
    InventoryAllocator o3; // inventory = [ { name: w3, inventory: { apple: 5, orange: 0 } }, { name: w3, inventory: { apple: 5, orange: 5 } } ]
    InventoryAllocator o4; // inventory = [ { name: w3, inventory: { apple: 5, orange: 0 } }, { name: w3, inventory: { apple: 5, orange: 5 } }
    Order order1; // order = {apple:1}
    Order order2; // order = {apple:10,orange:5}
    Order order3; // order = {apple:10,banana:5}

    @Before
    public void setUp() throws Exception {
        WareHouse w1 = new WareHouse("w1", "apple:0,orange:0");
        WareHouse w2 = new WareHouse("w2", "apple:1,orange:1");
        WareHouse w3 = new WareHouse("w3", "apple:5,orange:0");
        WareHouse w4 = new WareHouse("w4", "apple:5,orange:5");

        o1 = new InventoryAllocator();
        o1.addWareHouse(w1);
        o2 = new InventoryAllocator();
        o2.addWareHouse(w2);
        o3 = new InventoryAllocator();
        o3.addWareHouse(w3);
        o3.addWareHouse(w4);
        o4 = new InventoryAllocator();
        o4.addWareHouse(w3);
        o4.addWareHouse(w4);

        order1 = new Order("apple:1");
        order2 = new Order("apple:10,orange:5");
        order3 = new Order("apple:10,banana:5");
    }

    @Test
    public void allocate() {
        List<String> actual1 = o1.allocate(order1);
        List<String> expected1 = new ArrayList<>();
        Collections.sort(expected1);
        assertEquals("no allocations! apple not enough", expected1, actual1);

        List<String> actual2 = o2.allocate(order1);
        List<String> expected2 = Arrays.asList("w2:{apple:1}");
        Collections.sort(expected2);
        assertEquals("exact inventory match!", expected2, actual2);

        List<String> actual3 = o3.allocate(order2);
        List<String> expected3 = Arrays.asList("w3:{apple:5}", "w4:{apple:5,orange:5}");
        Collections.sort(expected3);
        assertEquals("split an item across warehouse!", expected3, actual3);

        List<String> actual4 = o4.allocate(order3);
        List<String> expected4 = new ArrayList<>();
        Collections.sort(expected4);
        assertEquals("no allocations! no banana", expected4, actual4);

    }

}
