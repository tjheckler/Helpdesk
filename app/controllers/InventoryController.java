package controllers;

import models.Inventory;
import models.Location;
import models.Region;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class InventoryController extends Controller
{
    private FormFactory formFactory;
    private JPAApi jpaApi;

    @Inject
    public InventoryController(FormFactory formFactory)
    {
        this.formFactory=formFactory;
    }
    @Transactional(readOnly = true)
    public Result getInventories()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String sql = "SELECT i  FROM Inventory i " +
                " WHERE computerName LIKE :searchCriteria " +
                "ORDER BY computerName";
        String searchCriteria = form.get("searchCriteria");
        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        String queryParameter = searchCriteria + "%";

        List<Inventory> inventories = jpaApi.em()
                .createQuery(sql,Inventory.class).setParameter("searchCriteria", queryParameter).getResultList();

        return ok(views.html.Inventory.inventoryList.render(inventories,searchCriteria));

    }
    @Transactional(readOnly = true)
    public Result getInventory(Integer inventoryId)
    {
        String sql = "SELECT i FROM Inventory i " +
                "WHERE inventoryId = :inventoryId";
        Inventory inventory = jpaApi.em().createQuery(sql, Inventory.class).
                setParameter("inventoryId", inventoryId).getSingleResult();



        return ok(views.html.Inventory.inventory.render(inventory));
    }
    @Transactional
    public Result postInventory(Integer inventoryId)
    {
        String sql = "SELECT i FROM Inventory i " +
                "WHERE inventoryId = :inventoryId";

        Inventory inventory = jpaApi.em().createQuery(sql, Inventory.class)
                .setParameter("inventoryId", inventoryId).getSingleResult();
        DynamicForm form = formFactory.form().bindFromRequest();

        String computerName = form.get("computerName");
        int locationId = Integer.parseInt(form.get("locationId"));
        int regionalId = Integer.parseInt(form.get("regionId"));
        String currentUser = form.get("currentUser");
        String buildingLocation = form.get("buildingLocation");
        inventory.setComputerName(computerName);
        inventory.setBuildingLocation(buildingLocation);
        inventory.setCurrentUser(currentUser);
        inventory.setLocationId(locationId);
        inventory.setRegionId(regionalId);
        jpaApi.em().persist(computerName);



        return redirect(routes.InventoryController.getInventories());
    }
    @Transactional(readOnly = true)
    public Result getNewInventory()
    {
        String sql = "SELECT i FROM Inventory i " +
                "WHERE inventoryId = :inventoryId";
        Inventory inventory = jpaApi.em().createQuery
                (sql, Inventory.class).getSingleResult();
        String locationSql = "SELECT l FROM Location l " +
                "WHERE locationId = :locationId";

        List<Location> locations = jpaApi.em().createQuery
                (locationSql, Location.class).getResultList();

        String regionSql = "SELECT r FROM Region r "+
                "WHERE regionId = :regionId";

        List<Region> region = jpaApi.em().createQuery
                (regionSql, Region.class).getResultList();
        return ok(views.html.Inventory.newinventory.render(inventory, locations,region));
    }

    @Transactional
    public Result postNewInventory()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String inventoryItem = form.get("inventory");
        Inventory inventory = new Inventory();
        inventory.setComputerName(inventoryItem);
        jpaApi.em().persist(inventory);


        return redirect(routes.InventoryController.getInventories());
    }
}
