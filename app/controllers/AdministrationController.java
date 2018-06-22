package controllers;

import javafx.scene.control.Alert;
import models.Email;
import models.Password;
import models.SiteAdmin;
import models.Ticket;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Arrays;
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

        String sql = "SELECT sa FROM SiteAdmin sa WHERE userName = :username";

        List<SiteAdmin> siteAdmins = jpaApi.em()
                .createQuery(sql, SiteAdmin.class).setParameter("username", username).getResultList();


        if (siteAdmins.size() == 1)
        {
            SiteAdmin siteAdmin = siteAdmins.get(0);

            byte salt[] = siteAdmin.getPasswordSalt();
            byte hashedPassword[] = Password.hashPassword(password.toCharArray(), salt);

            if (Arrays.equals(hashedPassword, siteAdmin.getPassword()))
            {
                return redirect(routes.TicketController.getTickets());
            } else
            {
                return ok(views.html.Administration.login.render("Invalid username or password"));
            }
        } else
        {
            try
            {
                byte salt[] = Password.getNewSalt();
                Password.hashPassword(password.toCharArray(), salt);
            } catch (Exception e)
            {

            }
        }

        return ok(views.html.Administration.login.render("Invalid username or password"));

    }


    @Transactional(readOnly = true)
    public Result getNewPassword(Integer siteAdminId)
    {
        String sql = "SELECT s FROM SiteAdmin s " +
                "WHERE siteAdminId = :siteAdminId";
        SiteAdmin siteAdmin = jpaApi.em().createQuery(sql, SiteAdmin.class).
                setParameter("siteAdminId", siteAdminId).getSingleResult();


        return ok(views.html.Administration.newpassword.render("", siteAdmin));
    }

    @Transactional
    public Result postNewPassword(Integer siteAdminId)
    {
        DynamicForm form = formFactory.form().bindFromRequest();


        String sql = "SELECT s FROM SiteAdmin s " +
                "WHERE siteAdminId = :siteAdminId";
        SiteAdmin siteAdmin = jpaApi.em().createQuery(sql, SiteAdmin.class).
                setParameter("siteAdminId", siteAdminId).getSingleResult();


        String password = form.get("password");
        String passwordMatch = form.get("passwordMatch");

        if (Arrays.equals(password.toCharArray(), passwordMatch.toCharArray()))
        {
            try
            {
                byte salt[] = Password.getNewSalt();
                siteAdmin.setPasswordSalt(salt);
                siteAdmin.setPassword(Password.hashPassword(passwordMatch.toCharArray(), salt));
                jpaApi.em().persist(siteAdmin);
                return redirect(routes.SiteAdminController.getSiteAdmin(siteAdminId));
            } catch (Exception e)
            {

            }
        } else
        {

        }
        return ok(views.html.Administration.newpassword.render("Password Does Not Match", siteAdmin));
    }

    @Transactional(readOnly = true)
    public Result getForgotPassword()
    {
        String sql = "SELECT s FROM SiteAdmin s ";

        List<SiteAdmin> siteAdmins = jpaApi.em().createQuery(sql, SiteAdmin.class).getResultList();

        return ok(views.html.Administration.forgotpassword.render("",siteAdmins));
    }

    @Transactional
    public Result postForgotPassword()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String email = form.get("emailAddress");

        String sql = "SELECT s FROM SiteAdmin s ";

        List<SiteAdmin> siteAdmins = jpaApi.em().createQuery(sql, SiteAdmin.class).getResultList();

        //this may be broken below
            for(SiteAdmin siteAdmin:siteAdmins)
            {
                if (siteAdmin.getEmailAddress().equals(email))
                {
                    try
                    {
                        byte salt[] = Password.getNewSalt();
                        siteAdmin.setPasswordSalt(salt);
                        siteAdmin.setPassword(Password.hashPassword(Email.generateRandomPassword().toCharArray(), salt));
                        jpaApi.em().persist(siteAdmin);
                        Email.sendEmail("Enter this temporary password to login : "+ Email.generateRandomPassword() +
                                " you will be prompt to change it immediately.", email);
                    } catch (Exception e)
                    {

                    }
                } else
                {

                }
            }
        return ok(views.html.Administration.emailsent.render());
    }


}
