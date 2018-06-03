package controllers;

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

        return ok();

    }
    public Result getPriority(int id)
    {

        return ok();
    }
}
