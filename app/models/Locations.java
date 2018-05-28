package models;

import java.util.Map;
import java.util.TreeMap;

public class Locations
{
    private Map<Integer, Location> locations;
    public Locations()
    {
        locations = new TreeMap<>();

        Location one =  new Location(1, "Little Rock");
        Location two =  new Location(2, "Conway");
       Location three =  new Location(3, "Cabot");
        locations.put(one.getLocationsId(),one);
        locations.put(two.getLocationsId(),two);
       locations.put(three.getLocationsId(),three);
    }

    public Map<Integer, Location> getLocations()
    {
        return locations;
    }
}
