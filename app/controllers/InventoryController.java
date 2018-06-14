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
    public InventoryController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result getInventories()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String sql = "SELECT i FROM Inventory i " +
                "JOIN Location l ON i.locationId = l.locationId "+
                "WHERE i.computerName LIKE :searchCriteria OR " +
                "l.locationName LIKE :searchCriteria OR "+
                "i.buildingLocation LIKE :searchCriteria OR "+
                "i.currentUser LIKE :searchCriteria OR "+
                "i.assetTagNumber LIKE :searchCriteria "+
                "ORDER BY i.inventoryId";
        String searchCriteria = form.get("searchCriteria");
        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        String queryParameter = searchCriteria + "%";

        List<Inventory> inventories = jpaApi.em()
                .createQuery(sql, Inventory.class).setParameter("searchCriteria", queryParameter).getResultList();

        String locationSql = "SELECT l FROM Location l ";
        List<Location> locations = jpaApi.em()
                .createQuery(locationSql, Location.class).getResultList();

        return ok(views.html.Inventory.inventoryList.render(inventories, searchCriteria,locations));

    }

    @Transactional(readOnly = true)
    public Result getInventory(Integer inventoryId)
    {
        String sql = "SELECT i FROM Inventory i " +
                "WHERE inventoryId = :inventoryId";
        //add a join
        Inventory inventory = jpaApi.em().createQuery(sql, Inventory.class).
                setParameter("inventoryId", inventoryId).getSingleResult();

        String locationSql = "SELECT l FROM Location l ";
       List<Location> locations = jpaApi.em()
                .createQuery(locationSql, Location.class).getResultList();
        return ok(views.html.Inventory.inventory.render(inventory,locations));
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

        String currentUser = form.get("currentUser");
        String buildingLocation = form.get("buildingLocation");
        inventory.setComputerName(computerName);
        inventory.setBuildingLocation(buildingLocation);
        inventory.setCurrentUser(currentUser);
        inventory.setLocationId(locationId);

        jpaApi.em().persist(inventory);


        return redirect(routes.InventoryController.getInventories());
    }

    @Transactional(readOnly = true)
    public Result getNewInventory()
    {

        //add a join
        String regionSql = "SELECT r FROM Region r ";

        List<Region> regions = jpaApi.em().createQuery
                (regionSql, Region.class).getResultList();

        String locationSql = "SELECT l FROM Location l ";

        List<Location> locations = jpaApi.em().createQuery
                (locationSql, Location.class).getResultList();
        return ok(views.html.Inventory.newinventory.render(regions, locations));
    }

    @Transactional
    public Result postNewInventory()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String computerName = form.get("computerName");
        int locationId = Integer.parseInt(form.get("locationId"));
        int assetTagNumber = Integer.parseInt(form.get("assetTag"));
        String currentUser = form.get("currentUser");
        String buildingLocation = form.get("buildingLocation");


        Inventory inventory = new Inventory();
        inventory.setComputerName(computerName);
        inventory.setAssetTagNumber(assetTagNumber);
        inventory.setBuildingLocation(buildingLocation);
        inventory.setCurrentUser(currentUser);
        inventory.setLocationId(locationId);

        jpaApi.em().persist(inventory);


        return redirect(routes.InventoryController.getInventories());
    }
    @Transactional
    public Result deleteInventory(int inventoryId)
    {
        String sql = "SELECT i FROM Inventory i " +
                "WHERE inventoryId = :inventoryId";
        Inventory inventory = jpaApi.em().createQuery(sql, Inventory.class).
                setParameter("inventoryId", inventoryId).getSingleResult();
        jpaApi.em().remove(inventory);
        return redirect(routes.InventoryController.getInventories());
    }
}
