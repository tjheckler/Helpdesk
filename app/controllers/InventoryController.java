package controllers;

import models.Inventories;
import models.Inventory;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class InvetoryController extends Controller
{
    private FormFactory formFactory;

    @Inject
    public InventoryController(FormFactory formFactory)
    {
        this.formFactory=formFactory;
    }

    public Result getInventories()
    {
        Inventories inventories = new Inventories();
        return ok(views.html.inventoryList.render(inventories.getInventories().values()));

    }
    public Result getInventory(int id)
    {
        Inventories inventories = new Inventories();
        Inventory inventory = inventories.getInventories().get(id);
        return ok(views.html.inventory.render(inventory));
    }
}
