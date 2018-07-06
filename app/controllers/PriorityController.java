package controllers;


import models.Priority;
import models.Region;
import models.Ticket;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Result;

import javax.inject.Inject;

import java.util.List;

import static play.mvc.Results.ok;
import static play.mvc.Results.redirect;

public class PriorityController extends ApplicationController
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public PriorityController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result getPriorities()
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String sql = "SELECT p FROM Priority p " +
                    " WHERE priorityName LIKE :searchCriteria " +
                    "ORDER BY priorityName";
            String searchCriteria = form.get("searchCriteria");
            if (searchCriteria == null)
            {
                searchCriteria = "";
            }
            String queryParameter = searchCriteria + "%";

            List<Priority> priorities = jpaApi.em().createQuery(sql, Priority.class).
                    setParameter("searchCriteria", queryParameter).getResultList();


            return ok(views.html.Priority.priorityList.render(priorities, searchCriteria));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }

    }

    @Transactional(readOnly = true)
    public Result getPriority(Integer priorityId)
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT p FROM Priority p " +
                    "WHERE priorityId = :priorityId";
            Priority priority = jpaApi.em().createQuery(sql, Priority.class).
                    setParameter("priorityId", priorityId).getSingleResult();

            return ok(views.html.Priority.priority.render(priority,""));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result postPriority(Integer priorityId)
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT p FROM Priority p " +
                    "WHERE priorityId = :priorityId";

            Priority priority = jpaApi.em().createQuery(sql, Priority.class)
                    .setParameter("priorityId", priorityId).getSingleResult();
            DynamicForm form = formFactory.form().bindFromRequest();

            String priorityName = form.get("priorityName");

            if (priorityName != null)
            {
                priority.setPriorityName(priorityName);
                jpaApi.em().persist(priority);
            } else
            {
                return redirect(routes.PriorityController.getPriority(priorityId));
            }

            return redirect(routes.PriorityController.getPriorities());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }
    @Transactional(readOnly = true)
    public Result getNewPriority()
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String priorityName = form.get("priority");


            String sql = "SELECT p FROM Priority p " +
                    "WHERE priorityName = :priorityName ";


            List<Priority> priorities = jpaApi.em().createQuery(sql, Priority.class).
                    setParameter("priorityName", priorityName).getResultList();

            return ok(views.html.Priority.newpriority.render(priorities,""));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result postNewPriority()
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String priorityName = form.get("priority");


            String sql = "SELECT p FROM Priority p " +
                    "WHERE priorityName = :priorityName ";


            List<Priority> priorities = jpaApi.em().createQuery(sql, Priority.class).
                    setParameter("priorityName", priorityName).getResultList();
            if (priorities.size() == 1)
            {
                return ok(views.html.Priority.newpriority.render(priorities,
                        "Priority Already Exists Try Another Priority"));
            } else
            {

                Priority priority = new Priority();
                if (priorityName != null && priorityName.length() != 0 && priorityName.length() < 50)
                {
                    priority.setPriorityName(priorityName);

                } else
                {
                    return ok(views.html.Priority.newpriority.render(priorities,
                            "Priority Cannot be Empty and must be less than 50 Characters"));
                }
                jpaApi.em().persist(priority);
            }
            return redirect(routes.PriorityController.getPriorities());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result deletePriority(int priorityId)
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT p FROM Priority p " +
                    "WHERE priorityId = :priorityId";
            Priority priority = jpaApi.em().createQuery(sql, Priority.class).
                    setParameter("priorityId", priorityId).getSingleResult();
            String ticketSql = "SELECT t FROM Ticket t " +
                    "WHERE priorityId = :priorityId ";
            List<Ticket> tickets = jpaApi.em().createQuery(ticketSql, Ticket.class).
                    setParameter("priorityId", priorityId).getResultList();


            if (tickets.size() == 1)
            {
                //do nothing

            }
            else if(tickets.size() == 0)
            {
                jpaApi.em().remove(priority);
                return redirect(routes.PriorityController.getPriorities());
            }
            return ok(views.html.Priority.priority.render(priority,
                    "* Cannot Delete, User is Assigned to a Ticket *"));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }
}
