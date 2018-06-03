package controllers;

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

        return ok();

    }
    public Result getCategory(int id)
    {

        return ok();
    }

}
