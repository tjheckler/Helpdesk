package controllers;


import models.Location;
import models.Region;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

import java.util.List;


public class LocationController extends ApplicationController
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public LocationController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getLocations()
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
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


            return ok(views.html.Location.locationList.render(locations, searchCriteria));
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
    }

    @Transactional(readOnly = true)
    public Result getLocation(Integer locationId)
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT l FROM Location l " +
                    "WHERE locationId = :locationId";
            Location location = jpaApi.em().createQuery(sql, Location.class).
                    setParameter("locationId", locationId).getSingleResult();
            String regionSql = "SELECT r FROM Region r ";

            List<Region> regions = jpaApi.em().createQuery
                    (regionSql, Region.class).getResultList();
            return ok(views.html.Location.location.render(location, regions));
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
    }

    @Transactional
    public Result postLocation(Integer locationId)
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT l FROM Location l " +
                    "WHERE locationId = :locationId";


            Location location = jpaApi.em().createQuery(sql, Location.class)
                    .setParameter("locationId", locationId).getSingleResult();
            DynamicForm form = formFactory.form().bindFromRequest();

            String locationName = form.get("locationName");
            location.setLocationName(locationName);
            //set region somehow
            int regionalId = Integer.parseInt(form.get("regionId"));
            location.setRegionId(regionalId);


            jpaApi.em().persist(location);

            return redirect(routes.LocationController.getLocations());
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
    }

    @Transactional(readOnly = true)
    public Result getNewLocation()
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            String regionSql = "SELECT r FROM Region r ";

            List<Region> regions = jpaApi.em().createQuery
                    (regionSql, Region.class).getResultList();
            return ok(views.html.Location.newlocation.render(regions));
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
    }

    @Transactional
    public Result postNewLocation()
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String location1 = form.get("locationName");
            Location location = new Location();
            location.setLocationName(location1);
            int regionalId = Integer.parseInt(form.get("regionId"));
            location.setRegionId(regionalId);
            //set region somehow
            jpaApi.em().persist(location);


            return redirect(routes.LocationController.getLocations());
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
    }

    @Transactional
    public Result deleteLocation(int locationId)
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT l FROM Location l " +
                    "WHERE locationId = :locationId";
            Location location = jpaApi.em().createQuery(sql, Location.class).
                    setParameter("LocationId", locationId).getSingleResult();
            jpaApi.em().remove(location);
            return redirect(routes.LocationController.getLocations());
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
    }

}
