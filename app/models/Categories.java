package models;

import java.util.Map;
import java.util.TreeMap;

public class Categories
{
    private Map<Integer, Category> categories;
    public Categories()
    {
        categories = new TreeMap<>();

        Category high =  new Category(1, "High");
        Category low = new Category(2,"Low");
        categories.put(high.getCategoryId(),high);
        categories.put(low.getCategoryId(),low);
    }

    public Map<Integer, Category> getCategories()
    {
        return categories;
    }
}
