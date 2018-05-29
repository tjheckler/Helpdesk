package controllers;

        import models.Priorities;
        import models.Priority;
        import play.data.FormFactory;
        import play.mvc.Result;

        import javax.inject.Inject;

        import static play.mvc.Results.ok;

public class PriorityController
{
    private FormFactory formFactory;

    @Inject
    public PriorityController(FormFactory formFactory)
    {
        this.formFactory=formFactory;
    }

    public Result getPriorities()
    {
        Priorities priorities = new Priorities();
        return ok(views.html.Priority.priorityList.render(priorities.getPriorities().values()));

    }
    public Result getPriority(int id)
    {
        Priorities priorities = new Priorities();
        Priority priority = priorities.getPriorities().get(id);
        return ok(views.html.Priority.priority.render(priority));
    }
}
