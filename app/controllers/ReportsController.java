package controllers;

import models.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class ReportsController extends ApplicationController
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public ReportsController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result getReports()
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
        String categorySql = "SELECT  NEW TicketCategoryCount(c.categoryId, c.categoryName, COUNT(*)) " +
                "FROM Ticket t " +
                "JOIN Category c ON t.categoryId = c.categoryId " +
                "GROUP BY c.categoryName " +
                "ORDER BY c.categoryName";

        List<TicketCategoryCount> ticketCategoryCounts = jpaApi.em().
                createQuery(categorySql, TicketCategoryCount.class).getResultList();

        String siteAdminSql = "SELECT  NEW TicketSiteAdminCount(t.siteAdminId, s.siteAdminName, COUNT(*)) " +
                "FROM Ticket t " +
                "JOIN SiteAdmin s ON t.siteAdminId = s.siteAdminId " +
                "GROUP BY s.siteAdminName " +
                "ORDER BY s.siteAdminName";

        List<TicketSiteAdminCount> ticketSiteAdminCounts = jpaApi.em().
                createQuery(siteAdminSql, TicketSiteAdminCount.class).getResultList();

        String prioritySql = "SELECT  NEW TicketPriorityCount(p.priorityId, p.priorityName, COUNT(*)) " +
                "FROM Ticket t " +
                "JOIN Priority p ON t.priorityId = p.priorityId " +
                "GROUP BY p.priorityName " +
                "ORDER BY p.priorityName";

        List<TicketPriorityCount> ticketPriorityCounts = jpaApi.em().
                createQuery(prioritySql, TicketPriorityCount.class).getResultList();

        String locationSql = "SELECT  NEW TicketLocationCount(l.locationId, l.locationName, COUNT(*)) " +
                "FROM Ticket t " +
                "JOIN Location l ON t.locationId = l.locationId " +
                "GROUP BY l.locationName " +
                "ORDER BY l.locationName";

        List<TicketLocationCount> ticketLocationCounts = jpaApi.em().
                createQuery(locationSql, TicketLocationCount.class).getResultList();

        String regionSql = "SELECT  NEW TicketRegionCount(r.regionId, r.regionName, COUNT(*)) " +
                "FROM Ticket t " +
                "JOIN Location l ON l.locationId = t.locationId " +
                "JOIN Region r ON l.regionId = r.regionId " +
                "GROUP BY r.regionName " +
                "ORDER BY r.regionName";

        List<TicketRegionCount> ticketRegionCounts = jpaApi.em().
                createQuery(regionSql, TicketRegionCount.class).getResultList();

        String InventorySql = "SELECT  NEW InventoryLocationCount(l.locationId, l.locationName, COUNT(*)) " +
                "FROM Inventory i " +
                "JOIN Location l ON l.locationId = i.locationId " +
                "GROUP BY l.locationName " +
                "ORDER BY l.locationName";

        List<InventoryLocationCount> inventoryLocationCounts = jpaApi.em().
                createQuery(InventorySql, InventoryLocationCount.class).getResultList();

        return ok(views.html.Report.reports.render(ticketCategoryCounts, ticketSiteAdminCounts,
                ticketPriorityCounts, ticketLocationCounts, ticketRegionCounts, inventoryLocationCounts));
        } else
        {
            return ok(views.html.Administration.login.render("Login With Administrator Credentials to View " +
                    "Reports Page or go Back To Previous Page"));
        }
    }

    @Transactional
    public Result postReports()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String username = form.get("username");
        String password = form.get("password");

        String sql = "SELECT sa FROM SiteAdmin sa WHERE userName = :username";

        List<SiteAdmin> siteAdmins = jpaApi.em().createQuery(sql, SiteAdmin.class).
                setParameter("username", username).getResultList();


        // Make flag for database, check login and forgot password against flag
        //if flag set to false, login, if flag set to true by postForgotPassword, force set new password
        //use session with authorization for pages if logged in and not null

        if (siteAdmins.size() == 1)
        {
            SiteAdmin siteAdmin = siteAdmins.get(0);

            byte salt[] = siteAdmin.getPasswordSalt();
            byte hashedPassword[] = Password.hashPassword(password.toCharArray(), salt);

            if (Arrays.equals(hashedPassword, siteAdmin.getPassword()))
            {
                session().put("loggedin", ""+siteAdmin.getSiteAdminId());
                session().put("role",""+siteAdmin.getSiteRole());
                return redirect(routes.ReportsController.getReports());
            } else
            {
                return ok(views.html.Administration.login.render("Invalid username or password"));
            }
        } else
        {
            try
            {
                byte salt[] = Password.getNewSalt();
                Password.hashPassword(password.toCharArray(), salt);
            } catch (Exception e)
            {

            }
        }

        return ok(views.html.Administration.login.render("Invalid username or password"));

    }

}
