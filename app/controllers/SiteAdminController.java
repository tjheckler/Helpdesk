package controllers;

import com.google.common.io.Files;
import models.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.File;
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
                    "WHERE SiteAdminName LIKE :searchCriteria " +
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
            String locationSql = "SELECT l FROM Location l ";
            List<Location> location = jpaApi.em().createQuery(locationSql, Location.class).getResultList();

            return ok(views.html.SiteAdmin.siteadminList.render(siteAdmins, searchCriteria,location));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
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


            return ok(views.html.SiteAdmin.siteadmin.render(siteAdmin, location,
                    region, "* Indicates Required Field"));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("You Are Not Logged In"));
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
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
        return redirect(routes.SiteAdminController.getSiteAdmin(siteAdminId));
    }

    @Transactional(readOnly = true)
    public Result getNewSiteAdmin(String message2)
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
            return ok(views.html.SiteAdmin.newsiteadmin.render(regions,
                    locations, "* Indicates Required Field",""));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("You Are Not Logged In"));
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
            String sql = "SELECT s FROM SiteAdmin s " ;
            List<SiteAdmin> siteAdmins = jpaApi.em().createQuery(sql, SiteAdmin.class).
                    getResultList();
            for(int i = 0; i<siteAdmins.size() - 1; i++)
            {
            if (!siteAdmins.get(i).getUsername().equals(username) && !siteAdmins.get(i).getEmailAddress().equals(emailAddress) )
            {
                SiteAdmin siteAdmin = new SiteAdmin();
                if (siteAdminName != null && phoneNumber != null && username != null &&
                        role != null && emailAddress != null && password != null
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
                    return redirect(routes.SiteAdminController.getNewSiteAdmin("Some Values Were Empty Try Again"));
                }
            } else
            {
                String regionSql = "SELECT r FROM Region r ";

                List<Region> regions = jpaApi.em().createQuery
                        (regionSql, Region.class).getResultList();

                String locationSql = "SELECT l FROM Location l ";

                List<Location> locations = jpaApi.em().createQuery
                        (locationSql, Location.class).getResultList();
                return ok(views.html.SiteAdmin.newsiteadmin.render(regions,
                        locations, "* Indicates Required Field","User Already Exists Try Another Email Or Username"));

            }}
        }else
        {
            return redirect(routes.AdministrationController.getLogin("You Are Not Logged In"));
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
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional(readOnly = true)
    public Result getPicture(int siteAdminId)
    {
        String sql = "SELECT sa FROM SiteAdmin sa " +
                "WHERE siteAdminId = :siteAdminId";
        SiteAdmin siteAdmin = jpaApi.em().createQuery(sql, SiteAdmin.class).
                setParameter("siteAdminId", siteAdminId).getSingleResult();
        return ok(siteAdmin.getPicture()).as("image/jpg");
    }

    @Transactional
    public Result postPicture(int siteAdminId)
    {
        String sql = "SELECT sa FROM SiteAdmin sa " +
                "WHERE siteAdminId = :siteAdminId";
        SiteAdmin siteAdmin = jpaApi.em().createQuery(sql, SiteAdmin.class).
                setParameter("siteAdminId", siteAdminId).getSingleResult();
        Http.MultipartFormData<File> formData = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> filePart = formData.getFile("file");
        File file = filePart.getFile();

        if (file != null)
        {
            try
            {
                siteAdmin.setPicture(Files.toByteArray(file));
                jpaApi.em().persist(siteAdmin);
            } catch (Exception e)
            {
                //do nothing
            }
        }
        return redirect(routes.SiteAdminController.getSiteAdmin(siteAdminId));
    }
}
