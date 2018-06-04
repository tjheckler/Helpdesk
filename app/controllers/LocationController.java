package controllers;


import models.Location;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

import java.util.List;


public class LocationController extends Controller
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public LocationController(JPAApi jpaApi,FormFactory formFactory)
    {
        this.formFactory=formFactory;
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getLocations()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String sql = "SELECT l FROM Location l " +
                " WHERE locationName LIKE :searchCriteria " +
                "ORDER BY locationName";
        String searchCriteria = form.get("searchCriteria");
        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        String queryParameter = searchCriteria + "%";

        List<Location> locations = jpaApi.em()
                .createQuery(sql, Location.class).setParameter("searchCriteria", queryParameter).getResultList();


        return ok(views.html.Location.locationList.render(locations,searchCriteria));
    }

    @Transactional(readOnly = true)
    public Result getLocation(Integer locationId)
    {
        String sql = "SELECT l FROM Location l " +
                "WHERE locationId = :locationId";
        Location location = jpaApi.em().createQuery(sql, Location.class).
                setParameter("locationId",locationId).getSingleResult();

        return ok(views.html.Location.location.render(location));
    }
    @Transactional
    public Result postLocation(Integer locationId)
    {
        String sql = "SELECT l FROM Location l " +
                "WHERE locationId = :locationId";

        Location location = jpaApi.em().createQuery(sql, Location.class)
                .setParameter("locationId", locationId).getSingleResult();
        DynamicForm form = formFactory.form().bindFromRequest();

        String locationName = form.get("locationName");
       location.setLocationName(locationName);


        jpaApi.em().persist(location);

        return redirect(routes.LocationController.getLocations());
    }

    public Result getNewLocation()
    {
        return ok(views.html.Location.newlocation.render());
    }

    @Transactional
    public Result postNewLocation()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String location1 = form.get("locationName");
        Location location = new Location();
        location.setLocationName(location1);
        jpaApi.em().persist(location);
        jpaApi.em().persist(location1);

        return redirect(routes.LocationController.getLocations());
    }
}
