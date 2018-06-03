package controllers;

import models.Inventory;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class InventoryController extends Controller
{
    private FormFactory formFactory;

    @Inject
    public InventoryController(FormFactory formFactory)
    {
        this.formFactory=formFactory;
    }

    public Result getInventories()
    {

        return ok();

    }
    public Result getInventory(int id)
    {

        return ok();
    }
}
