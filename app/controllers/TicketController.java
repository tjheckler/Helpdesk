package controllers;


import models.Ticket;
import models.Tickets;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

public class TicketController extends Controller
{
    private FormFactory formFactory;

    @Inject
    public TicketController(FormFactory formFactory)
    {
        this.formFactory=formFactory;
    }

    public Result getTickets()
    {
        Tickets tickets = new Tickets();
        return ok(views.html.Ticket.ticketList.render(tickets.getTickets().values()));

    }
    public Result getTicket(int id)
    {
        Tickets tickets = new Tickets();
        Ticket ticket = tickets.getTickets().get(id);
        return ok(views.html.Ticket.ticket.render(ticket));
    }
}
