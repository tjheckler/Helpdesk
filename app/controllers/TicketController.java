package controllers;


import models.Inventory;
import models.Ticket;
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

        return ok(views.html.Ticket.ticketList.render(tickets, searchCriteria));

    }

    public Result getTicket(int id)
    {

        return ok();
    }
    //work on tickets stuff
    //work on reply stuff
    //work on file stuff
}
