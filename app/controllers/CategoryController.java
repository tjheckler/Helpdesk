package controllers;

import models.Categories;
import models.Category;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class CategoryController extends Controller
{
    private FormFactory formFactory;
    @Inject
    public CategoryController(FormFactory formFactory)
    {
        this.formFactory=formFactory;
    }

    public Result getCategories()
    {
        Categories categories = new Categories();
        return ok(views.html.categoryList.render(categories.getCategories().values()));

    }
    public Result getCategory(int id)
    {
        Categories categories = new Categories();
        Category category = categories.getCategories().get(id);
        return ok(views.html.category.render(category));
    }

}
