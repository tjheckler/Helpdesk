package controllers;

import models.Category;
import models.Ticket;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class CategoryController extends ApplicationController
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public CategoryController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result getCategories()
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String sql = "SELECT c FROM Category c " +
                    " WHERE categoryName LIKE :searchCriteria " +
                    "ORDER BY categoryName";
            String searchCriteria = form.get("searchCriteria");
            if (searchCriteria == null)
            {
                searchCriteria = "";
            }
            String queryParameter = searchCriteria + "%";

            List<Category> categories = jpaApi.em().createQuery(sql, Category.class).
                    setParameter("searchCriteria", queryParameter).getResultList();


            return ok(views.html.Category.categoryList.render(categories, searchCriteria));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }

    }

    @Transactional(readOnly = true)
    public Result getCategory(Integer categoryId)
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT c FROM Category c " +
                    "WHERE categoryId = :categoryId";
            Category category = jpaApi.em().createQuery(sql, Category.class).
                    setParameter("categoryId", categoryId).getSingleResult();
            return ok(views.html.Category.category.render(category,""));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result postCategory(Integer categoryId)
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT c FROM Category c " +
                    "WHERE categoryId = :categoryId";

            Category category = jpaApi.em().createQuery(sql, Category.class)
                    .setParameter("categoryId", categoryId).getSingleResult();
            DynamicForm form = formFactory.form().bindFromRequest();

            String categoryName = form.get("categoryName");
            if (categoryName != null)
            {
                category.setCategoryName(categoryName);
                jpaApi.em().persist(category);
            }else{
               return redirect(routes.CategoryController.getCategory(categoryId));
            }

            return redirect(routes.CategoryController.getCategories());

        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    public Result getNewCategory()
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            return ok(views.html.Category.newcategory.render());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result postNewCategory()
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String categoryName = form.get("category");
            Category category = new Category();
            if(categoryName != null)
            {
                category.setCategoryName(categoryName);
                jpaApi.em().persist(category);
            }else{
                return redirect(routes.CategoryController.getNewCategory());
            }
            return redirect(routes.CategoryController.getCategories());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result deleteCategory(int categoryId)
    {
        if (isLoggedIn()&& getLoggedInSiteAdminRole().equals("Admin"))
        {
        String sql = "SELECT c FROM Category c " +
                "WHERE categoryId = :categoryId";
        Category category = jpaApi.em().createQuery(sql, Category.class).
                setParameter("categoryId", categoryId).getSingleResult();
            String ticketSql = "SELECT t FROM Ticket t " +
                    "WHERE categoryId = :categoryId ";
            List<Ticket> tickets = jpaApi.em().createQuery(ticketSql, Ticket.class).
                    setParameter("categoryId", categoryId).getResultList();


            if (tickets.size() == 1)
            {
                //do nothing

            }
            else if(tickets.size() == 0)
            {
                jpaApi.em().remove(category);
                return redirect(routes.CategoryController.getCategories());
            }
            return ok(views.html.Category.category.render(category,
                    "* Cannot Delete, Category is Assigned to a Ticket *"));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }
}
