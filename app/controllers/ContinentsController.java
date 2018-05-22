package controllers;

import models.Continent;
import models.Continents;
import play.mvc.Controller;
import play.mvc.Result;

public class ContinentsController extends Controller
{
    public Result getContinents()
    {
        Continents continents = new Continents();
        return ok(views.html.continents.render(continents.getContinents().values()));

    }
    public Result getContinent(String id)
    {
        Continents continents = new Continents();
        Continent continent = continents.getContinents().get(id);
        return ok(views.html.continent.render(continent));
    }
}
