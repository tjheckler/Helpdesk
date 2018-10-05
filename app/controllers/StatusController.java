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

public class StatusController extends ApplicationController
{
    private JPAApi jpaApi;
    private FormFactory formFactory;
    SiteRolesValues siteRole = new SiteRolesValues();

    @Inject
    public StatusController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result getStatuses()
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals(siteRole.getAdmin()))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String sql = "SELECT s FROM TicketStatus s " +
                    " WHERE statusName LIKE :searchCriteria " +
                    "ORDER BY statusName";
            String searchCriteria = form.get("searchCriteria");
            if (searchCriteria == null)
            {
                searchCriteria = "";
            }
            String queryParameter = searchCriteria + "%";

            List<TicketStatus> ticketStatuses = jpaApi.em().createQuery(sql, TicketStatus.class).
                    setParameter("searchCriteria", queryParameter).getResultList();


            return ok(views.html.Status.statusList.render(ticketStatuses, searchCriteria));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }

    }

    @Transactional(readOnly = true)
    public Result getStatus(Integer statusId)
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals(siteRole.getAdmin()))
        {
            String sql = "SELECT s FROM TicketStatus s " +
                    "WHERE statusId = :statusId";
            TicketStatus ticketStatus = jpaApi.em().createQuery(sql, TicketStatus.class).
                    setParameter("statusId", statusId).getSingleResult();


            return ok(views.html.Status.status.render(ticketStatus,""));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result postStatus(Integer statusId)
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals(siteRole.getAdmin()))
        {
            String sql = "SELECT s FROM TicketStatus s " +
                    "WHERE statusId = :statusId";

            TicketStatus ticketStatus = jpaApi.em().createQuery(sql, TicketStatus.class)
                    .setParameter("statusId", statusId).getSingleResult();
            DynamicForm form = formFactory.form().bindFromRequest();

            String statusName = form.get("statusName");

            if (statusName != null)
            {
                ticketStatus.setStatusName(statusName);
                jpaApi.em().persist(ticketStatus);
            } else
            {
                return redirect(routes.StatusController.getStatus(statusId));
            }

            return redirect(routes.StatusController.getStatuses());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }
    @Transactional(readOnly = true)
    public Result getNewStatus()
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals(siteRole.getAdmin()))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String statusName = form.get("statusName");
            String sql = "SELECT s FROM TicketStatus s " +
                    "WHERE statusName = :statusName ";


            List<TicketStatus> statuses = jpaApi.em().createQuery(sql, TicketStatus.class).
                    setParameter("statusName", statusName).getResultList();


            return ok(views.html.Status.newstatus.render(statuses,""));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result postNewStatus()
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals(siteRole.getAdmin()))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String statusName = form.get("statusName");
            String sql = "SELECT s FROM TicketStatus s " +
                    "WHERE statusName = :statusName ";


            List<TicketStatus> statuses = jpaApi.em().createQuery(sql, TicketStatus.class).
                    setParameter("statusName", statusName).getResultList();

            if (statuses.size() == 1)
            {
                return ok(views.html.Status.newstatus.render(statuses,
                        "Status Name Already Exists Try Another Status Name"));
            } else
            {
                TicketStatus ticketStatus = new TicketStatus();

                if (statusName != null && statusName.length() != 0 && statusName.length() < 50)
                {
                    ticketStatus.setStatusName(statusName);

                } else
                {
                    return ok(views.html.Status.newstatus.render(statuses,
                            "Status Name Cannot be Empty and Must be less than 50 Characters"));
                }
                jpaApi.em().persist(ticketStatus);
            }
            return redirect(routes.StatusController.getStatuses());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result deleteStatus(int statusId)
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals(siteRole.getAdmin()))
        {

            String sql = "SELECT ts FROM TicketStatus ts " +
                    "WHERE statusId = :statusId";
            TicketStatus ticketStatus = jpaApi.em().createQuery(sql, TicketStatus.class).
                    setParameter("statusId", statusId).getSingleResult();

            String ticketSql = "SELECT t FROM Ticket t " +
                    "WHERE statusId = :statusId ";
            List<Ticket> tickets = jpaApi.em().createQuery(ticketSql, Ticket.class).
                    setParameter("statusId", statusId).getResultList();


            if (tickets.size() == 1)
            {
               //do nothing

            }
            else if(tickets.size() == 0)
            {
                jpaApi.em().remove(ticketStatus);
                return redirect(routes.StatusController.getStatuses());
            }
            return ok(views.html.Status.status.render(ticketStatus,
                      "   Cannot Delete, This Status is Assigned to a Ticket"));

        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }
}
