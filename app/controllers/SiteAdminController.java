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

public class SiteAdminController extends ApplicationController
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public SiteAdminController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result getSiteAdmins()
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String sql = "SELECT s FROM SiteAdmin s " +
                    " WHERE SiteAdminName LIKE :searchCriteria " +
                    "ORDER BY SiteAdminName";

            // add a join and search region, category, phone number role and email address
            String searchCriteria = form.get("searchCriteria");
            if (searchCriteria == null)
            {
                searchCriteria = "";
            }
            String queryParameter = searchCriteria + "%";

            List<SiteAdmin> siteAdmins = jpaApi.em().createQuery(sql, SiteAdmin.class).
                    setParameter("searchCriteria", queryParameter).getResultList();


            return ok(views.html.SiteAdmin.siteadminList.render(siteAdmins, searchCriteria));
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }

    }

    @Transactional(readOnly = true)
    public Result getSiteAdmin(Integer siteAdminId)
    {
        if (isLoggedIn() && siteAdminId == getLoggedInSiteAdminId()
                || isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT s FROM SiteAdmin s " +
                    "WHERE siteAdminId = :siteAdminId";
            SiteAdmin siteAdmin = jpaApi.em().createQuery(sql, SiteAdmin.class).
                    setParameter("siteAdminId", siteAdminId).getSingleResult();

            String locationSql = "SELECT l FROM Location l ";
            List<Location> location = jpaApi.em().createQuery(locationSql, Location.class).getResultList();

            String regionSql = "SELECT r FROM Region r ";
            List<Region> region = jpaApi.em().createQuery(regionSql, Region.class).getResultList();


            return ok(views.html.SiteAdmin.siteadmin.render(siteAdmin, location, region, "* Indicates Required Field"));
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
    }

    @Transactional
    public Result postSiteAdmin(Integer siteAdminId)
    {
        if (isLoggedIn() && siteAdminId == getLoggedInSiteAdminId()
                || isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT s FROM SiteAdmin s " +
                    "WHERE siteAdminId = :siteAdminId";
//add a join
            SiteAdmin siteAdmin = jpaApi.em().createQuery(sql, SiteAdmin.class)
                    .setParameter("siteAdminId", siteAdminId).getSingleResult();
            DynamicForm form = formFactory.form().bindFromRequest();

            String siteAdminName = form.get("siteAdminName");
            int locationId = Integer.parseInt(form.get("locationId"));

            String phoneNumber = form.get("phoneNumber");
            String emailAddress = form.get("emailAddress");
            String role = form.get("siteRole");
            String username = form.get("username");
            String password = form.get("password");

            if (siteAdminName != null && phoneNumber != null && emailAddress != null &&
                    role != null && username != null && password != null
                    && locationId > 0 && siteAdminId > 0)
            {
                try
                {
                    byte salt[] = Password.getNewSalt();
                    byte hashedPassword[] = Password.hashPassword(password.toCharArray(), salt);

                    siteAdmin.setSiteAdminName(siteAdminName);
                    siteAdmin.setPhoneNumber(phoneNumber);
                    siteAdmin.setSiteRole(role);
                    siteAdmin.setLocationId(locationId);
                    siteAdmin.setEmailAddress(emailAddress);
                    siteAdmin.setPasswordSalt(salt);
                    siteAdmin.setPassword(hashedPassword);
                    siteAdmin.setUsername(username);
                } catch (Exception e)
                {

                }

                jpaApi.em().persist(siteAdmin);
            } else
            {
                return redirect(routes.SiteAdminController.getSiteAdmins());
            }
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
        return redirect(routes.SiteAdminController.getSiteAdmin(siteAdminId));
    }

    @Transactional(readOnly = true)
    public Result getNewSiteAdmin()
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            //add a join
            String regionSql = "SELECT r FROM Region r ";

            List<Region> regions = jpaApi.em().createQuery
                    (regionSql, Region.class).getResultList();

            String locationSql = "SELECT l FROM Location l ";

            List<Location> locations = jpaApi.em().createQuery
                    (locationSql, Location.class).getResultList();
            return ok(views.html.SiteAdmin.newsiteadmin.render(regions, locations, "* Indicates Required Field"));
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
    }

    @Transactional
    public Result postNewSiteAdmin()
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String siteAdminName = form.get("siteAdmin");
            int locationId = Integer.parseInt(form.get("locationId"));

            String phoneNumber = form.get("phoneNumber");
            String emailAddress = form.get("emailAddress");
            String role = form.get("role");
            String username = form.get("username");
            String password = form.get("password");
            String flag = "True";
            SiteAdmin siteAdmin = new SiteAdmin();
            if (siteAdminName != null && phoneNumber != null && emailAddress != null &&
                    role != null && username != null && password != null
                    && locationId > 0)
            {
                try
                {
                    byte salt[] = Password.getNewSalt();
                    byte hashedPassword[] = Password.hashPassword(password.toCharArray(), salt);

                    siteAdmin.setSiteAdminName(siteAdminName);
                    siteAdmin.setPhoneNumber(phoneNumber);
                    siteAdmin.setSiteRole(role);
                    siteAdmin.setLocationId(locationId);
                    siteAdmin.setEmailAddress(emailAddress);
                    siteAdmin.setPasswordSalt(salt);
                    siteAdmin.setPassword(hashedPassword);
                    siteAdmin.setUsername(username);
                    siteAdmin.setFlag(flag);
                } catch (Exception e)
                {

                }
                jpaApi.em().persist(siteAdmin);


            } else
            {
                return redirect(routes.SiteAdminController.getNewSiteAdmin());
            }
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
        return redirect(routes.SiteAdminController.getSiteAdmins());
    }

    @Transactional
    public Result deleteSiteAdmin(int siteAdminId)
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT sa FROM SiteAdmin sa " +
                    "WHERE siteAdminId = :siteAdminId";
            SiteAdmin siteAdmin = jpaApi.em().createQuery(sql, SiteAdmin.class).
                    setParameter("siteAdminId", siteAdminId).getSingleResult();
            jpaApi.em().remove(siteAdmin);
            return redirect(routes.SiteAdminController.getSiteAdmins());
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
    }
}
