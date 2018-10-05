package controllers;

import models.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class SiteRoleController extends ApplicationController
{
    private JPAApi jpaApi;
    private FormFactory formFactory;
    SiteRolesValues siteRole = new SiteRolesValues();

    @Inject
    public SiteRoleController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result getSiteRoles()
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals(siteRole.getAdmin()))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String sql = "SELECT sr FROM SiteRole sr " +
                    " WHERE siteRoleName LIKE :searchCriteria " +
                    "ORDER BY siteRoleId";
            String searchCriteria = form.get("searchCriteria");
            if (searchCriteria == null)
            {
                searchCriteria = "";
            }
            String queryParameter = searchCriteria + "%";

            List<SiteRole> siteRoles = jpaApi.em().createQuery(sql, SiteRole.class).
                    setParameter("searchCriteria", queryParameter).getResultList();


            return ok(views.html.SiteRole.siteroleList.render(siteRoles, searchCriteria));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }

    }

    @Transactional(readOnly = true)
    public Result getSiteRole(Integer SiteRoleId)
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals(siteRole.getAdmin()))
        {
            String sql = "SELECT sr FROM SiteRole sr " +
                    "WHERE siteRoleId = :siteRoleId";
            SiteRole siteRole = jpaApi.em().createQuery(sql,SiteRole.class).
                    setParameter("SiteRoleId", SiteRoleId).getSingleResult();
            return ok(views.html.SiteRole.siterole.render(siteRole,""));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result postSiteRole(Integer siteRoleId)
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals(siteRole.getAdmin()))
        {
            String sql = "SELECT sr FROM SiteRole sr " +
                    "WHERE siteRoleId = :siteRoleId";

            SiteRole siteRoles = jpaApi.em().createQuery(sql, SiteRole.class)
                    .setParameter("siteRoleId", siteRoleId).getSingleResult();
            DynamicForm form = formFactory.form().bindFromRequest();

            String siteRoleName = form.get("siteRoleName");
            if (siteRoleName != null)
            {
                siteRoles.setSiteRoleName(siteRoleName);
                jpaApi.em().persist(siteRoles);
            }else{
                return redirect(routes.SiteRoleController.getSiteRole(siteRoleId));
            }

            return redirect(routes.SiteRoleController.getSiteRoles());

        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }
    @Transactional(readOnly = true)
    public Result getNewSiteRole()
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals(siteRole.getAdmin()))
        {

            DynamicForm form = formFactory.form().bindFromRequest();
            String siteRoleName = form.get("category");

            String sql = "SELECT sr FROM SiteRole sr " +
                    "WHERE siteRoleName = :siteRoleName ";
            List<SiteRole> siteRoles = jpaApi.em().createQuery(sql, SiteRole.class).
                    setParameter("siteRoleName", siteRoleName).getResultList();

            return ok(views.html.SiteRole.newsiterole.render(siteRoles,""));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result postNewSiteRole()
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals(siteRole.getAdmin()))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String siteRoleName = form.get("siteRole");

            String sql = "SELECT sr FROM SiteRole sr " +
                    "WHERE siteRoleName = :siteRoleName ";
            List<SiteRole> siteRoles = jpaApi.em().createQuery(sql, SiteRole.class).
                    setParameter("siteRoleName", siteRoleName).getResultList();
            if (siteRoles.size() == 1)
            {
                return ok(views.html.SiteRole.newsiterole.render(siteRoles,
                        "Role Already Exists Try Another Role"));
            } else
            {
                SiteRole siteRole = new SiteRole();
                if (siteRoleName != null && siteRoleName.length() != 0 && siteRoleName.length() < 50)
                {
                    siteRole.setSiteRoleName(siteRoleName);

                } else
                {
                    return ok(views.html.SiteRole.newsiterole.render(siteRoles,
                            " Role Name Cannot be Empty or more than 50 Characters. "));
                }
                jpaApi.em().persist(siteRole);
            }
            return redirect(routes.SiteRoleController.getSiteRoles());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result deleteSiteRole(int siteRoleId)
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals(siteRole.getAdmin()))
        {
            String sql = "SELECT sr FROM SiteRole sr " +
                    "WHERE siteRoleId = :siteRoleId";
            SiteRole siteRole = jpaApi.em().createQuery(sql, SiteRole.class).
                    setParameter("siteRoleId", siteRoleId).getSingleResult();
            String ticketSql = "SELECT sa FROM SiteAdmin sa " +
                    "WHERE siteRoleId = :siteRoleId ";
            List<SiteAdmin> siteAdmins = jpaApi.em().createQuery(ticketSql, SiteAdmin.class).
                    setParameter("siteRoleId", siteRoleId).getResultList();


            if (siteAdmins.size() == 1)
            {
                //do nothing

            }
            else if(siteAdmins.size() == 0)
            {
                jpaApi.em().remove(siteRole);
                return redirect(routes.SiteRoleController.getSiteRoles());
            }
            return ok(views.html.SiteRole.siterole.render(siteRole,
                    "* Cannot Delete, Role is Assigned to a Site User *"));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }
}
