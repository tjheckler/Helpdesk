package models;

import java.util.Map;
import java.util.TreeMap;

public class Inventories
{
    private Map<Integer, Inventory> inventories;
    public Inventories()
    {
        inventories = new TreeMap<>();

        Inventory one =  new Inventory(1, "wxda",
                "Central","Dave","South");
        Inventory two =  new Inventory(2, "wxdab",
                "Central","Bob","South");
        Inventory three =  new Inventory(3, "wxdabc",
                "Central","Sally","South");
        inventories.put(one.getInventoryId(),one);
        inventories.put(two.getInventoryId(),two);
        inventories.put(three.getInventoryId(),three);
    }

    public Map<Integer, Inventory> getInventories()
    {
        return inventories;
    }
}
