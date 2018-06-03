package controllers;

import models.Region;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class RegionController extends Controller
{
    private FormFactory formFactory;

    @Inject
    public RegionController(FormFactory formFactory)
    {
        this.formFactory=formFactory;
    }

    public Result getRegions()
    {

        return ok();

    }
    public Result getRegion(int id)
    {

        return ok();
    }
}
