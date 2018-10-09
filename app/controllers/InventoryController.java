package controllers;

import models.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.Query;
import java.util.List;

public class InventoryController extends ApplicationController
{
    private FormFactory formFactory;
    private JPAApi jpaApi;
    SiteRolesValues siteRole = new SiteRolesValues();

    @Inject
    public InventoryController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result getInventories()
    {
        if (isLoggedIn() && (getLoggedInSiteAdminRole().equals(siteRole.getAdmin()) || getLoggedInSiteAdminRole()
                .equals(siteRole.getTechnician()) || getLoggedInSiteAdminRole().equals(siteRole.getManager())))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String buildingLocation1 = form.get("buildingLocation1");
            String currentUser1 = form.get("currentUser1");
            Integer location1 = null;


            String locationIdText = form.get("locationId1");
            if (locationIdText != null && !locationIdText.trim().equals(""))
            {
                location1 = Integer.parseInt(locationIdText);
            }


            String sql = "SELECT i FROM Inventory i " +
                    "JOIN Location l ON i.locationId = l.locationId ";
            String searchCriteria = form.get("searchCriteria");
            if (searchCriteria == null)
            {
                searchCriteria = "";
            }

            if (!searchCriteria.trim().equals(""))
            {
                sql += " AND (i.computerName LIKE :searchCriteria OR " +
                        "i.buildingLocation LIKE :searchCriteria OR " +
                        "i.assetTagNumber LIKE :searchCriteria OR " +
                        "i.currentUser LIKE :searchCriteria OR " +
                        "l.locationNameLIKE :searchCriteria ) ";
            }
            String queryParameter = searchCriteria + "%";


            String locationSql = "SELECT l FROM Location l  ";
            List<Location> locations = jpaApi.em()
                    .createQuery(locationSql, Location.class).getResultList();

            if (buildingLocation1 != null && !buildingLocation1.trim().equals(""))
            {
                sql += "AND i.buildingLocation = :buildingLocation ";

            }
            if (currentUser1 != null && !currentUser1.trim().equals(""))
            {
                sql += "AND i.currentUser = :currentUser ";

            }
            if (location1 != null)
            {
                sql += "AND l.locationId = :locationId ";

            }
            sql += "ORDER BY i.inventoryId desc";

            int pageNumber = 0;
            int pageSize = 25;

            Query inventoryDetailQuery = jpaApi.em().createQuery(sql, Inventory.class);
            if (!searchCriteria.trim().equals(""))
            {
                inventoryDetailQuery.setParameter("searchCriteria", queryParameter);
            }

            if (buildingLocation1 != null && !buildingLocation1.trim().equals(""))
            {
                inventoryDetailQuery.setParameter("buildingLocation", buildingLocation1);
            }

            if (currentUser1 != null && !currentUser1.trim().equals(""))
            {

                inventoryDetailQuery.setParameter("currentUser", currentUser1);
            }

            if (location1 != null)
            {
                inventoryDetailQuery.setParameter("locationId", location1);
            }

            inventoryDetailQuery.setFirstResult((pageNumber) * pageSize).setMaxResults(pageSize - pageNumber);

            // working on Paging

            List<Inventory> inventories = inventoryDetailQuery.getResultList();

            Paging.getPages(inventories, pageSize);
            //end working on paging

            return ok(views.html.Inventory.inventoryList.render(inventories, searchCriteria, locations));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("You Are Not Logged In"));
        }


    }

    @Transactional(readOnly = true)
    public Result getInventory(Integer inventoryId)
    {
        if (isLoggedIn() && (getLoggedInSiteAdminRole().equals(siteRole.getAdmin()) || getLoggedInSiteAdminRole()
                .equals(siteRole.getTechnician()) || getLoggedInSiteAdminRole().equals(siteRole.getManager())))
        {
            String sql = "SELECT i FROM Inventory i " +
                    "WHERE inventoryId = :inventoryId";
            //add a join
            Inventory inventory = jpaApi.em().createQuery(sql, Inventory.class).
                    setParameter("inventoryId", inventoryId).getSingleResult();

            String locationSql = "SELECT l FROM Location l ";
            List<Location> locations = jpaApi.em()
                    .createQuery(locationSql, Location.class).getResultList();
            return ok(views.html.Inventory.inventory.render(inventory, locations,
                    "* Indicates Required Fields"));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("You Are Not Logged In"));
        }
    }

    @Transactional
    public Result postInventory(Integer inventoryId)
    {
        if (isLoggedIn() && (getLoggedInSiteAdminRole().equals(siteRole.getAdmin()) || getLoggedInSiteAdminRole()
                .equals(siteRole.getTechnician()) || getLoggedInSiteAdminRole().equals(siteRole.getManager())))
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

            if (computerName != null && locationId > 0 && currentUser != null
                    && buildingLocation != null)
            {
                inventory.setComputerName(computerName);
                inventory.setBuildingLocation(buildingLocation);
                inventory.setCurrentUser(currentUser);
                inventory.setLocationId(locationId);
                jpaApi.em().persist(inventory);
            } else
            {
                return redirect(routes.InventoryController.getInventory(inventoryId));
            }

            return redirect(routes.InventoryController.getInventories());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("You Are Not Logged In"));
        }
    }

    @Transactional(readOnly = true)
    public Result getNewInventory()
    {
        if (isLoggedIn() && (getLoggedInSiteAdminRole().equals(siteRole.getAdmin()) || getLoggedInSiteAdminRole()
                .equals(siteRole.getTechnician()) || getLoggedInSiteAdminRole().equals(siteRole.getManager())))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            InventoryFormValues inventoryFormValues = new InventoryFormValues();
            inventoryFormValues.setInventoryComputerName(form.get("computerName"));
            inventoryFormValues.setInventoryLocationId(form.get("locationId"));
            inventoryFormValues.setInventoryAssetTagNumber(form.get("assetTag"));
            inventoryFormValues.setInventoryCurrentUser(form.get("currentUser"));
            inventoryFormValues.setInventoryBuildingLocation(form.get("buildingLocation"));

            String regionSql = "SELECT r FROM Region r ";
            List<Region> regions = jpaApi.em().createQuery
                    (regionSql, Region.class).getResultList();
            String locationSql = "SELECT l FROM Location l ";
            List<Location> locations = jpaApi.em().createQuery
                    (locationSql, Location.class).getResultList();
            return ok(views.html.Inventory.newinventory.render(regions, locations,
                    "* Indicates Required Fields", inventoryFormValues, true));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("You Are Not Logged In"));
        }
    }

    @Transactional
    public Result postNewInventory()
    {
        if (isLoggedIn() && (getLoggedInSiteAdminRole().equals(siteRole.getAdmin()) || getLoggedInSiteAdminRole()
                .equals(siteRole.getTechnician()) || getLoggedInSiteAdminRole().equals(siteRole.getManager())))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            InventoryFormValues inventoryFormValues = new InventoryFormValues();
            inventoryFormValues.setInventoryComputerName(form.get("computerName"));
            inventoryFormValues.setInventoryLocationId(form.get("locationId"));
            inventoryFormValues.setInventoryAssetTagNumber(form.get("assetTag"));
            inventoryFormValues.setInventoryCurrentUser(form.get("currentUser"));
            inventoryFormValues.setInventoryBuildingLocation(form.get("buildingLocation"));
            String regionSql = "SELECT r FROM Region r ";
            List<Region> regions = jpaApi.em().createQuery
                    (regionSql, Region.class).getResultList();
            String locationSql = "SELECT l FROM Location l ";
            List<Location> locations = jpaApi.em().createQuery
                    (locationSql, Location.class).getResultList();

            if (inventoryFormValues.isValid())
            {
                Inventory inventory = new Inventory();
                inventory.setComputerName(inventoryFormValues.getInventoryComputerName());
                inventory.setAssetTagNumber(inventoryFormValues.getInventoryAssetTagNumber());
                inventory.setBuildingLocation(inventoryFormValues.getInventoryBuildingLocation());
                inventory.setCurrentUser(inventoryFormValues.getInventoryCurrentUser());
                inventory.setLocationId(new Integer(inventoryFormValues.getInventoryLocationId()));
                //TODO add not null for fields
                jpaApi.em().persist(inventory);
            } else
            {
                return ok(views.html.Inventory.newinventory.render(regions, locations,
                        "* Indicates Required Fields", inventoryFormValues, false));
            }

            return redirect(routes.InventoryController.getInventories());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("You Are Not Logged In"));
        }
    }

    @Transactional
    public Result deleteInventory(int inventoryId)
    {
        if (isLoggedIn() && (getLoggedInSiteAdminRole().equals(siteRole.getAdmin()) || getLoggedInSiteAdminRole()
                .equals(siteRole.getTechnician()) || getLoggedInSiteAdminRole().equals(siteRole.getManager())))
        {
            String sql = "SELECT i FROM Inventory i " +
                    "WHERE inventoryId = :inventoryId";
            Inventory inventory = jpaApi.em().createQuery(sql, Inventory.class).
                    setParameter("inventoryId", inventoryId).getSingleResult();
            jpaApi.em().remove(inventory);
            return redirect(routes.InventoryController.getInventories());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

}
