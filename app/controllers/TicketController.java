package controllers;


import models.Ticket;
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

        return ok();

    }
    public Result getTicket(int id)
    {

        return ok();
    }
    //work on tickets stuff
    //work on reply stuff
    //work on file stuff
}
