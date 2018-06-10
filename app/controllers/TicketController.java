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

public class TicketController extends Controller
{
    private FormFactory formFactory;
    private JPAApi jpaApi;


    @Inject
    public TicketController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory=formFactory;
    }

    @Transactional(readOnly = true)
    public Result getTickets()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String sql = "SELECT t FROM Ticket t " +
                "WHERE name LIKE :searchCriteria " +
                "ORDER BY name ";
        String searchCriteria = form.get("searchCriteria");
        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        String queryParameter = searchCriteria + "%";

        List<Ticket> tickets = jpaApi.em()
                .createQuery(sql,Ticket.class).setParameter("searchCriteria", queryParameter).getResultList();

        String locationSql = "SELECT l FROM Location l ";
        Location location = jpaApi.em()
                .createQuery(locationSql, Location.class).getSingleResult();

        String statusSql = "SELECT s FROM Status s ";
        List<Status> statuses = jpaApi.em()
                .createQuery(statusSql, Status.class).getResultList();

        String adminSql = "SELECT sa FROM SiteAdmin sa ";
        List<SiteAdmin> siteAdmins = jpaApi.em()
                .createQuery(adminSql, SiteAdmin.class).getResultList();

        String prioritySql = "SELECT p FROM Priority p ";
        List<Priority> priority = jpaApi.em()
                .createQuery(prioritySql, Priority.class).getResultList();

        return ok(views.html.Ticket.ticketList.render(tickets, searchCriteria,
                location, statuses,siteAdmins,priority));

    }

    @Transactional(readOnly = true)
    public Result getTicket(Integer ticketsId)
    {
        String sql = "SELECT t FROM Ticket t " +
                "WHERE ticketsId = :ticketsId";
        //add a join
       Ticket ticket = jpaApi.em().createQuery(sql, Ticket.class).
                setParameter("ticketsId", ticketsId).getSingleResult();

        String locationSql = "SELECT l FROM Location l ";
        List<Location> locations = jpaApi.em()
                .createQuery(locationSql, Location.class).getResultList();

        String statusSql = "SELECT s FROM Status s ";
        List<Status> statuses = jpaApi.em()
                .createQuery(statusSql, Status.class).getResultList();

        String adminSql = "SELECT sa FROM SiteAdmin sa ";
        List<SiteAdmin> siteAdmins = jpaApi.em()
                .createQuery(adminSql, SiteAdmin.class).getResultList();

        String prioritySql = "SELECT p FROM Priority p ";
        List<Priority> priorities = jpaApi.em()
                .createQuery(prioritySql, Priority.class).getResultList();

        String categorySql = "SELECT c FROM Category c ";
        List<Category> categories = jpaApi.em()
                .createQuery(categorySql, Category.class).getResultList();

        String regionSql = "SELECT r FROM Region r ";
        List<Region> regions = jpaApi.em()
                .createQuery(regionSql, Region.class).getResultList();
        return ok(views.html.Ticket.ticket.render(ticket,locations,statuses,
                siteAdmins,priorities,categories,regions));
    }
    @Transactional
    public Result postTicket(Integer ticketId)
    {
        //work on reply stuff
        //work on file stuff

        return redirect(routes.TicketController.getTickets());
    }

    @Transactional(readOnly = true)
    public Result getNewTicket()
    {

        String locationSql = "SELECT l FROM Location l ";
        List<Location> locations = jpaApi.em()
                .createQuery(locationSql, Location.class).getResultList();

        String statusSql = "SELECT s FROM Status s ";
        List<Status> statuses = jpaApi.em()
                .createQuery(statusSql, Status.class).getResultList();

        String adminSql = "SELECT sa FROM SiteAdmin sa ";
        List<SiteAdmin> siteAdmins = jpaApi.em()
                .createQuery(adminSql, SiteAdmin.class).getResultList();

        String prioritySql = "SELECT p FROM Priority p ";
        List<Priority> priorities = jpaApi.em()
                .createQuery(prioritySql, Priority.class).getResultList();

        String categorySql = "SELECT c FROM Category c ";
        List<Category> categories = jpaApi.em()
                .createQuery(categorySql, Category.class).getResultList();

        String regionSql = "SELECT r FROM Region r ";
        List<Region> regions = jpaApi.em()
                .createQuery(regionSql, Region.class).getResultList();

        return ok(views.html.Ticket.newticket.render(locations,statuses,siteAdmins,priorities,categories,regions));
    }

    @Transactional
    public Result postNewTicket()
    {


        return redirect(routes.TicketController.getTickets());
    }
}
