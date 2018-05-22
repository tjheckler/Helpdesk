package models;

public class Continent
{
    private String id;
    private String name;
    private int squareKilometers;
    private long population;

    public Continent(String id, String name, int squareKilometers, Long population)
    {
        this.id = id;
        this.name = name;
        this.squareKilometers = squareKilometers;
        this.population = population;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getSquareKilometers()
    {
        return squareKilometers;
    }

    public long getPopulation()
    {
        return population;
    }
}
