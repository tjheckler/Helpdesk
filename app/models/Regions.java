package models;

import java.util.Map;
import java.util.TreeMap;

public class Regions
{
    private Map<Integer, Region> regions;
    public Regions()
    {
        regions = new TreeMap<>();

        Region one =  new Region(1, "North West");
       Region two =  new Region(2, "Central");
        Region three =  new Region(3, "South West");
        regions.put(one.getRegionId(),one);
        regions.put(two.getRegionId(),two);
        regions.put(three.getRegionId(),three);
    }

    public Map<Integer, Region> getRegions()
    {
        return regions;
    }
}
