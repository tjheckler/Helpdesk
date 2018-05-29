package controllers;

import models.Region;
import models.Regions;
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
        Regions regions = new Regions();
        return ok(views.html.Region.regionList.render(regions.getRegions().values()));

    }
    public Result getRegion(int id)
    {
        Regions regions = new Regions();
       Region region = regions.getRegions().get(id);
        return ok(views.html.Region.region.render(region));
    }
}
