package controllers;

import models.Locations;
import models.Location;
import play.data.FormFactory;
import play.mvc.Result;

import javax.inject.Inject;

import static play.mvc.Results.ok;

public class LocationController
{
    private FormFactory formFactory;

    @Inject
    public InventoryController(FormFactory formFactory)
    {
        this.formFactory=formFactory;
    }

    public Result getLocations()
    {
        Locations locations = new Locations();
        return ok(views.html.inventoryList.render(locations.getLocations().values()));

    }
    public Result getInventory(int id)
    {
       Locations locations = new Locations();
        Location location = locations.getLocations().get(id);
        return ok(views.html.inventory.render(location));
    }
}
