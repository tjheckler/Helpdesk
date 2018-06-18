package controllers;

import javafx.scene.control.Alert;
import models.SiteAdmin;
import models.Ticket;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class AdministrationController extends Controller
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public AdministrationController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }


    public Result getAdministration()
    {

        return ok(views.html.Administration.admin.render());
    }

    @Transactional(readOnly = true)
    public Result getNewPassword()
    {
        return ok(views.html.Administration.newpassword.render(""));
    }
    @Transactional
    public Result postNewPassword(Integer siteAdminId)
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String sql = "SELECT sa FROM SiteAdmin sa ";
        String password = form.get("password");
        String passwordMatch = form.get("passwordMatch");
        if(passwordMatch.equals(password))
        {
            SiteAdmin siteAdmin = jpaApi.em().createQuery(sql,SiteAdmin.class).setParameter("passwordMatch",passwordMatch).getSingleResult();
            jpaApi.em().persist(siteAdmin);
            return redirect(routes.SiteAdminController.getSiteAdmin(siteAdminId));
        }
        else
        {
            return ok(views.html.Administration.newpassword.render("Password Does Not Match"));
        }

    }

    @Transactional(readOnly = true)
    public Result getLogin()
    {


        return ok(views.html.Administration.login.render(""));
    }

    @Transactional
    public Result postLogin()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String username = form.get("username");
        String password = form.get("password");

        String sql = "SELECT sa FROM SiteAdmin sa WHERE userName = :username AND password = :password";

        List<SiteAdmin> siteAdmins = jpaApi.em()
                .createQuery(sql, SiteAdmin.class).setParameter("username", username).setParameter("password", password).getResultList();
        if (siteAdmins.size() == 0)
        {
            return ok(views.html.Administration.login.render("Invalid Username or Password"));
        } else
        {
            return redirect(routes.TicketController.getTickets());
        }
    }
}
