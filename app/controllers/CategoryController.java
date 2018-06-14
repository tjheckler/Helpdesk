package controllers;

import models.Category;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class CategoryController extends Controller
{
    private JPAApi jpaApi;
    private FormFactory formFactory;
    @Inject
    public CategoryController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi=jpaApi;
        this.formFactory=formFactory;
    }
    @Transactional(readOnly = true)
    public Result getCategories()
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

        List<Category> categories = jpaApi.em()
                .createQuery(sql,Category.class).setParameter("searchCriteria", queryParameter).getResultList();


        return ok(views.html.Category.categoryList.render(categories, searchCriteria));

    }
    @Transactional(readOnly = true)
    public Result getCategory(Integer categoryId)
    {
        String sql = "SELECT c FROM Category c " +
                "WHERE categoryId = :categoryId";
        Category category = jpaApi.em().createQuery(sql, Category.class).
                setParameter("categoryId", categoryId).getSingleResult();



        return ok(views.html.Category.category.render(category));
    }
    @Transactional
    public Result postCategory(Integer categoryId)
    {
        String sql = "SELECT c FROM Category c " +
                "WHERE categoryId = :categoryId";

        Category category = jpaApi.em().createQuery(sql, Category.class)
                .setParameter("categoryId", categoryId).getSingleResult();
        DynamicForm form = formFactory.form().bindFromRequest();

        String categoryName = form.get("categoryName");
        category.setCategoryName(categoryName);


        jpaApi.em().persist(category);

        return redirect(routes.CategoryController.getCategories());
    }
    public Result getNewCategory()
    {
        return ok(views.html.Category.newcategory.render());
    }

    @Transactional
    public Result postNewCategory()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String category1 = form.get("category");
        Category category = new Category();
        category.setCategoryName(category1);
        jpaApi.em().persist(category);


        return redirect(routes.CategoryController.getCategories());
    }
    @Transactional
    public Result deleteCategory(int categoryId)
    {
        String sql = "SELECT c FROM Category c " +
                "WHERE categoryId = :categoryId";
        Category category = jpaApi.em().createQuery(sql, Category.class).
                setParameter("categoryId", categoryId).getSingleResult();
        jpaApi.em().remove(category);
        return redirect(routes.CategoryController.getCategories());
    }
}
