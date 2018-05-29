package models;

import java.util.Map;
import java.util.TreeMap;

public class Regions
{
    private Map<Integer, Region> regions;
    public Regions()
    {
        regions = new TreeMap<>();

        Region one =  new Region(1, "Little Rock");
       Region two =  new Region(2, "Conway");
        Region three =  new Region(3, "Cabot");
        regions.put(one.getRegionId(),one);
        regions.put(two.getRegionId(),two);
        regions.put(three.getRegionId(),three);
    }

    public Map<Integer, Region> getRegions()
    {
        return regions;
    }
}
