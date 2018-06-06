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

public class SiteAdminController extends Controller
{
    private JPAApi jpaApi;
    private FormFactory formFactory;
    @Inject
    public SiteAdminController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi=jpaApi;
        this.formFactory=formFactory;
    }
    @Transactional(readOnly = true)
    public Result getSiteAdmins()
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

        List<SiteAdmin> siteAdmins = jpaApi.em()
                .createQuery(sql, SiteAdmin.class).setParameter("searchCriteria", queryParameter).getResultList();


        return ok(views.html.SiteAdmin.siteadminList.render(siteAdmins, searchCriteria));

    }
    @Transactional(readOnly = true)
    public Result getSiteAdmin(Integer siteAdminId)
    {
        String sql = "SELECT s FROM SiteAdmin s " +
                "WHERE siteAdminId = :siteAdminId";
        SiteAdmin siteAdmin = jpaApi.em().createQuery(sql, SiteAdmin.class).
                setParameter("siteAdminId", siteAdminId).getSingleResult();



        return ok(views.html.SiteAdmin.siteadmin.render(siteAdmin));
    }
    @Transactional
    public Result postSiteAdmin(Integer siteAdminId)
    {
        String sql = "SELECT s FROM SiteAdmin s " +
                "WHERE siteAdminId = :siteAdminId";
//add a join
        SiteAdmin siteAdmin = jpaApi.em().createQuery(sql, SiteAdmin.class)
                .setParameter("siteAdminId", siteAdminId).getSingleResult();
        DynamicForm form = formFactory.form().bindFromRequest();

        String siteAdminName = form.get("siteAdmin");
        int locationId = Integer.parseInt(form.get("locationId"));
        int regionalId = Integer.parseInt(form.get("regionId"));
        int phoneNumber = Integer.parseInt(form.get("phoneNumber"));
        String emailAddress = form.get("emailAddress");
        String role = form.get("role");
        siteAdmin.setSiteAdminName(siteAdminName);
        siteAdmin.setPhoneNumber(phoneNumber);
        siteAdmin.setSiteRole(role);
        siteAdmin.setLocationId(locationId);
        siteAdmin.setRegionId(regionalId);
        siteAdmin.setEmailAddress(emailAddress);
        jpaApi.em().persist(siteAdmin);

        return redirect(routes.SiteAdminController.getSiteAdmins());
    }
    @Transactional(readOnly = true)
    public Result getNewSiteAdmin()
    {

        //add a join
        String regionSql = "SELECT r FROM Region r ";

        List<Region> regions = jpaApi.em().createQuery
                (regionSql,Region.class).getResultList();

        String locationSql = "SELECT l FROM Location l ";

        List<Location> locations = jpaApi.em().createQuery
                (locationSql,Location.class).getResultList();
        return ok(views.html.SiteAdmin.newsiteadmin.render(regions,locations));
    }

    @Transactional
    public Result postNewSiteAdmin()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String siteAdminName = form.get("siteAdmin");
        int locationId = Integer.parseInt(form.get("locationId"));
        int regionalId = Integer.parseInt(form.get("regionId"));
        int phoneNumber = Integer.parseInt(form.get("phoneNumber"));
        String emailAddress = form.get("emailAddress");
        String role = form.get("role");
        SiteAdmin siteAdmin = new SiteAdmin();
        siteAdmin.setSiteAdminName(siteAdminName);
        siteAdmin.setPhoneNumber(phoneNumber);
        siteAdmin.setSiteRole(role);
        siteAdmin.setLocationId(locationId);
        siteAdmin.setRegionId(regionalId);
        siteAdmin.setEmailAddress(emailAddress);
        jpaApi.em().persist(siteAdmin);


        return redirect(routes.SiteAdminController.getSiteAdmins());
    }
}
