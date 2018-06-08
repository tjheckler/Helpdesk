package controllers;

import models.Status;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class StatusController extends Controller
{
    private JPAApi jpaApi;
    private FormFactory formFactory;
    @Inject
    public StatusController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi=jpaApi;
        this.formFactory=formFactory;
    }
    @Transactional(readOnly = true)
    public Result getStatuses()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String sql = "SELECT s FROM Status s " +
                " WHERE statusName LIKE :searchCriteria " +
                "ORDER BY statusName";
        String searchCriteria = form.get("searchCriteria");
        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        String queryParameter = searchCriteria + "%";

        List<Status> statuses = jpaApi.em()
                .createQuery(sql,Status.class).setParameter("searchCriteria", queryParameter).getResultList();


        return ok(views.html.Status.statusList.render(statuses, searchCriteria));

    }
    @Transactional(readOnly = true)
    public Result getStatus(Integer statusId)
    {
        String sql = "SELECT s FROM Status s " +
                "WHERE statusId = :statusId";
        Status status = jpaApi.em().createQuery(sql, Status.class).
                setParameter("statusId", statusId).getSingleResult();



        return ok(views.html.Status.status.render(status));
    }
    @Transactional
    public Result postStatus(Integer statusId)
    {
        String sql = "SELECT s FROM Status s " +
                "WHERE statusId = :statusId";

        Status status = jpaApi.em().createQuery(sql, Status.class)
                .setParameter("statusId", statusId).getSingleResult();
        DynamicForm form = formFactory.form().bindFromRequest();

        String statusName = form.get("statusName");
        status.setStatusName(statusName);


        jpaApi.em().persist(status);

        return redirect(routes.StatusController.getStatuses());
    }
    public Result getNewStatus()
    {
        return ok(views.html.Status.newstatus.render());
    }

    @Transactional
    public Result postNewStatus()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String status1 = form.get("status");
        Status status = new Status();
        status.setStatusName(status1);
        jpaApi.em().persist(status);


        return redirect(routes.StatusController.getStatuses());
    }
}
