package models;

import java.util.Map;
import java.util.TreeMap;

public class Continents
{
    private Map<String, Continent> continents;
    public Continents()
    {
        continents = new TreeMap<>();

        Continent aisia =  new Continent("AS", "Asia",44391162,4406273622L);
        Continent africa = new Continent("AF","Africa",30244049,121577081L);
        continents.put(aisia.getId(), aisia);
        continents.put(africa.getId(),africa);
    }

    public Map<String, Continent> getContinents()
    {
        return continents;
    }
}
