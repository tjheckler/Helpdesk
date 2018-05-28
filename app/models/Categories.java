package models;

import scala.Int;

import java.util.Map;
import java.util.TreeMap;

public class Categories
{
    private Map<Integer, Category> categories;
    public Categories()
    {
        categories = new TreeMap<>();

        Category high =  new Category(1, "High");
        Category medium = new Category(2,"Medium");
        Category low = new Category(3,"Low");
        categories.put(high.getCategoryId(),high);
        categories.put(medium.getCategoryId(),medium);
        categories.put(low.getCategoryId(),low);
    }

    public Map<Integer, Category> getCategories()
    {
        return categories;
    }
}
