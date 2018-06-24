package controllers;


        import models.Priority;
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
    public PriorityController(JPAApi jpaApi,FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }
    @Transactional(readOnly = true)
    public Result getPriorities()
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
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

        List<Priority> priorities = jpaApi.em().createQuery(sql,Priority.class).
                setParameter("searchCriteria", queryParameter).getResultList();


        return ok(views.html.Priority.priorityList.render(priorities, searchCriteria));
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }

    }
    @Transactional(readOnly = true)
    public Result getPriority(Integer priorityId)
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
        String sql = "SELECT p FROM Priority p " +
                "WHERE priorityId = :priorityId";
        Priority priority = jpaApi.em().createQuery(sql, Priority.class).
                setParameter("priorityId", priorityId).getSingleResult();

        return ok(views.html.Priority.priority.render(priority));
    } else
    {
        return redirect(routes.AdministrationController.getLogin());
    }
    }

    @Transactional
    public Result postPriority(Integer priorityId)
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
        String sql = "SELECT p FROM Priority p " +
                "WHERE priorityId = :priorityId";

        Priority priority = jpaApi.em().createQuery(sql, Priority.class)
                .setParameter("priorityId", priorityId).getSingleResult();
        DynamicForm form = formFactory.form().bindFromRequest();

        String priorityName = form.get("priorityName");
        priority.setPriorityName(priorityName);


        jpaApi.em().persist(priority);

        return redirect(routes.PriorityController.getPriorities());
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
    }
    public Result getNewPriority()
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
        return ok(views.html.Priority.newpriority.render());
    } else
    {
        return redirect(routes.AdministrationController.getLogin());
    }
    }

    @Transactional
    public Result postNewPriority()
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
        DynamicForm form = formFactory.form().bindFromRequest();
        String priority1 = form.get("priority");
        Priority priority = new Priority();
        priority.setPriorityName(priority1);
        jpaApi.em().persist(priority);
        return redirect(routes.PriorityController.getPriorities());
    } else
    {
        return redirect(routes.AdministrationController.getLogin());
    }
    }
    @Transactional
    public Result deletePriority(int priorityId)
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
        String sql = "SELECT p FROM Priority p " +
                "WHERE priorityId = :priorityId";
        Priority priority = jpaApi.em().createQuery(sql, Priority.class).
                setParameter("priorityId", priorityId).getSingleResult();
        jpaApi.em().remove(priority);
        return redirect(routes.PriorityController.getPriorities());
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
    }
}
