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

public class RegionController extends ApplicationController
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public RegionController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result getRegions()
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String sql = "SELECT r FROM Region r " +
                    " WHERE regionName LIKE :searchCriteria " +
                    "ORDER BY regionName";
            String searchCriteria = form.get("searchCriteria");
            if (searchCriteria == null)
            {
                searchCriteria = "";
            }
            String queryParameter = searchCriteria + "%";

            List<Region> regions = jpaApi.em().createQuery(sql, Region.class).
                    setParameter("searchCriteria", queryParameter).getResultList();


            return ok(views.html.Region.regionList.render(regions, searchCriteria));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional(readOnly = true)
    public Result getRegion(Integer regionId)
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT r FROM Region r " +
                    "WHERE regionId = :regionId";
            Region region = jpaApi.em().createQuery(sql, Region.class).
                    setParameter("regionId", regionId).getSingleResult();

            return ok(views.html.Region.region.render(region,""));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result postRegion(Integer regionId)
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT r FROM Region r " +
                    "WHERE regionId = :regionId";

            Region region = jpaApi.em().createQuery(sql, Region.class)
                    .setParameter("regionId", regionId).getSingleResult();
            DynamicForm form = formFactory.form().bindFromRequest();

            String regionName = form.get("regionName");

            if (regionName != null)
            {
                region.setRegionName(regionName);
                jpaApi.em().persist(region);
            }else{
                return redirect(routes.RegionController.getRegion(regionId));
            }

            return redirect(routes.RegionController.getRegions());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }
    @Transactional(readOnly = true)
    public Result getNewRegion()
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String regionName = form.get("region");
            String sql = "SELECT r FROM Region r " +
                    "WHERE regionName = :regionName ";


            List<Region> regions = jpaApi.em().createQuery(sql, Region.class).
                    setParameter("regionName", regionName).getResultList();

            return ok(views.html.Region.newregion.render(regions,""));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result postNewRegion()
    {

        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String regionName = form.get("region");


            String sql = "SELECT r FROM Region r " +
                    "WHERE regionName = :regionName ";
            List<Region> regions = jpaApi.em().createQuery(sql, Region.class).
                    setParameter("regionName", regionName).getResultList();
            if (regions.size() == 1)
            {
                return ok(views.html.Region.newregion.render(regions, "Region Already Exists Try Another Region"));
            } else
            {

                Region region = new Region();
                if (regionName != null)
                {
                    region.setRegionName(regionName);
                    jpaApi.em().persist(region);
                } else
                {
                    return redirect(routes.RegionController.getNewRegion());
                }
            }
            return redirect(routes.RegionController.getRegions());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result deleteRegion(int regionId)
    {

        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT r FROM Region r " +
                    "WHERE regionId = :regionId";
            Region region = jpaApi.em().createQuery(sql, Region.class).
                    setParameter("regionId", regionId).getSingleResult();
            String locationSql = "SELECT l FROM Location l " +
                    "WHERE regionId = :regionId ";
            List<Location> locations = jpaApi.em().createQuery(locationSql, Location.class).
                    setParameter("regionId", regionId).getResultList();


            if (locations.size() == 1)
            {
                //do nothing

            }
            else if(locations.size() == 0)
            {
                jpaApi.em().remove(region);
                return redirect(routes.RegionController.getRegions());
            }
            return ok(views.html.Region.region.render(region,
                    "   Cannot Delete, This Region is Assigned to a Location"));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }
}
