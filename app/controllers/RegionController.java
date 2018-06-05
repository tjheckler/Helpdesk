package controllers;

import models.Category;
import models.Region;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class RegionController extends Controller
{
    private JPAApi jpaApi;
    private FormFactory formFactory;

    @Inject
    public RegionController(JPAApi jpaApi,FormFactory formFactory)
    {
        this.jpaApi=jpaApi;
        this.formFactory=formFactory;
    }
    @Transactional(readOnly = true)
    public Result getRegions()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String sql = "SELECT r FROM Region r " +
                " WHERE regionName LIKE :searchCriteria " +
                "ORDER BY regionName";
        String searchCriteria = form.get("searchCriteria");
        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        String queryParameter = searchCriteria + "%";

        List<Region> regions = jpaApi.em()
                .createQuery(sql,Region.class).setParameter("searchCriteria", queryParameter).getResultList();


        return ok(views.html.Region.regionList.render(regions, searchCriteria));
    }

    @Transactional(readOnly = true)
    public Result getRegion(Integer regionId)
    {
        String sql = "SELECT r FROM Region r " +
                "WHERE regionId = :regionId";
        Region region = jpaApi.em().createQuery(sql, Region.class).
                setParameter("regionId", regionId).getSingleResult();



        return ok(views.html.Region.region.render(region));
    }

    @Transactional
    public Result postRegion(Integer regionId)
    {
        String sql = "SELECT r FROM Region r " +
                "WHERE regionId = :regionId";

        Region region = jpaApi.em().createQuery(sql, Region.class)
                .setParameter("regionId", regionId).getSingleResult();
        DynamicForm form = formFactory.form().bindFromRequest();

        String regionName = form.get("regionName");
        region.setRegionName(regionName);


        jpaApi.em().persist(region);

        return redirect(routes.RegionController.getRegions());
    }
    public Result getNewRegion()
    {
        return ok(views.html.Region.newregion.render());
    }

    @Transactional
    public Result postNewRegion()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String region1 = form.get("region");
        Region region = new Region();
        region.setRegionName(region1);
        jpaApi.em().persist(region);


        return redirect(routes.RegionController.getRegions());
    }
}
