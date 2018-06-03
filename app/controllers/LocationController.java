package controllers;

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

        return ok();

    }
    public Result getLocation(int id)
    {


        return ok();
    }
}
