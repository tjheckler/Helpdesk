package controllers;


import models.*;
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
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
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

            List<Location> locations = jpaApi.em().createQuery(sql, Location.class).
                    setParameter("searchCriteria", queryParameter).getResultList();


            return ok(views.html.Location.locationList.render(locations, searchCriteria));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional(readOnly = true)
    public Result getLocation(Integer locationId)
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT l FROM Location l " +
                    "WHERE locationId = :locationId";
            Location location = jpaApi.em().createQuery(sql, Location.class).
                    setParameter("locationId", locationId).getSingleResult();
            String regionSql = "SELECT r FROM Region r ";

            List<Region> regions = jpaApi.em().createQuery
                    (regionSql, Region.class).getResultList();
            return ok(views.html.Location.location.render(location, regions, ""));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result postLocation(Integer locationId)
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT l FROM Location l " +
                    "WHERE locationId = :locationId";


            Location location = jpaApi.em().createQuery(sql, Location.class)
                    .setParameter("locationId", locationId).getSingleResult();
            DynamicForm form = formFactory.form().bindFromRequest();

            String locationName = form.get("locationName");
            //set region somehow
            int regionalId = Integer.parseInt(form.get("regionId"));
            if (locationName != null)
            {
                location.setLocationName(locationName);

                location.setRegionId(regionalId);
                jpaApi.em().persist(location);
            } else
            {
                return redirect(routes.LocationController.getLocation(locationId));
            }
            return redirect(routes.LocationController.getLocations());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional(readOnly = true)
    public Result getNewLocation()
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String locationName = form.get("locationName");
            String regionSql = "SELECT r FROM Region r ";
            String locationSql = "SELECT l FROM Location l " +
                    "WHERE locationName = :locationName";
            List<Location> locations = jpaApi.em().createQuery(locationSql,Location.class).
                    setParameter("locationName",locationName).getResultList();
            List<Region> regions = jpaApi.em().createQuery
                    (regionSql, Region.class).getResultList();
            return ok(views.html.Location.newlocation.render(locations,regions, "",""));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result postNewLocation()
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            DynamicForm form = formFactory.form().bindFromRequest();


            String regionSql = "SELECT r FROM Region r ";
            List<Region> regions = jpaApi.em().createQuery
                    (regionSql, Region.class).getResultList();


            String locationName = form.get("locationName");
            String locationSql = "SELECT l FROM Location l " +
                    "WHERE locationName = :locationName";
            List<Location> locations = jpaApi.em().createQuery(locationSql,Location.class).
                    setParameter("locationName",locationName).getResultList();
            if (locations.size() == 1)
            {
                return ok(views.html.Location.newlocation.render(locations,regions,
                        "","Location Already Exists Try Another Location"));
            } else
            {
                Location location = new Location();
                int regionalId = Integer.parseInt(form.get("regionId"));
                if (locationName != null && regionalId > 0 && locationName.length() !=0 && locationName.length() < 50)
                {
                    location.setLocationName(locationName);
                    location.setRegionId(regionalId);

                } else
                {
                    return ok(views.html.Location.newlocation.
                            render(locations, regions, "Location Name and Region are Required",
                                    " Location Name Must be less than 50 Characters. "));
                }
                jpaApi.em().persist(location);
            }
            return redirect(routes.LocationController.getLocations());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result deleteLocation(int locationId)
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT l FROM Location l " +
                    "WHERE locationId = :locationId";
            Location location = jpaApi.em().createQuery(sql, Location.class).
                    setParameter("locationId", locationId).getSingleResult();
            String regionSql = "SELECT r FROM Region r ";

            List<Region> regions = jpaApi.em().createQuery
                    (regionSql, Region.class).getResultList();

            String ticketSql = "SELECT t FROM Ticket t " +
                    "WHERE locationId = :locationId ";
            List<Ticket> tickets = jpaApi.em().createQuery(ticketSql, Ticket.class).
                    setParameter("locationId", locationId).getResultList();

            String siteAdminSql = "SELECT sa FROM SiteAdmin sa " +
                    "WHERE locationId = :locationId ";
            List<SiteAdmin> siteAdmins = jpaApi.em().createQuery(siteAdminSql, SiteAdmin.class).
                    setParameter("locationId", locationId).getResultList();

            String inventorySql = "SELECT i FROM Inventory i " +
                    "WHERE locationId = :locationId ";
            List<Inventory> inventories = jpaApi.em().createQuery(inventorySql, Inventory.class).
                    setParameter("locationId", locationId).getResultList();

            if (tickets.size() == 1)
            {
                //do nothing

            } else if (tickets.size() == 0)
            {
                jpaApi.em().remove(location);
                return redirect(routes.LocationController.getLocations());
            } else
            {
                return ok(views.html.Location.location.render(location, regions,
                        "   Cannot Delete, This Location is Assigned to a Ticket"));
            }
            if (siteAdmins.size() == 1)
            {
                //do nothing

            } else if (siteAdmins.size() == 0)
            {
                jpaApi.em().remove(location);
                return redirect(routes.LocationController.getLocations());
            } else
            {
                return ok(views.html.Location.location.render(location, regions,
                        "   Cannot Delete, This Location is Assigned to a Site Admin"));
            }
            if (inventories.size() == 1)
            {
                //do nothing

            } else if (inventories.size() == 0)
            {
                jpaApi.em().remove(location);
                return redirect(routes.LocationController.getLocations());
            }
            return ok(views.html.Location.location.render(location, regions,
                    "   Cannot Delete, This Location is Assigned to an Inventory Item"));

        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

}
