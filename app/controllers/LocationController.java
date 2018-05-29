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
    public LocationController(FormFactory formFactory)
    {
        this.formFactory=formFactory;
    }

    public Result getLocations()
    {
        Locations locations = new Locations();
        return ok(views.html.Location.locationList.render(locations.getLocations().values()));

    }
    public Result getLocation(int id)
    {
       Locations locations = new Locations();
        Location location = locations.getLocations().get(id);
        return ok(views.html.Location.location.render(location));
    }
}
