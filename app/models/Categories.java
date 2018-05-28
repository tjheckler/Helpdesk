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

        Category IE =  new Category(1, "Internet Explorer");
        Category PCS = new Category(2,"PC Setup");
        Category PCH = new Category(3,"PC Hardware");
        categories.put(IE.getCategoryId(),IE);
        categories.put(PCS.getCategoryId(),PCS);
        categories.put(PCH.getCategoryId(),PCH);
    }

    public Map<Integer, Category> getCategories()
    {
        return categories;
    }
}
