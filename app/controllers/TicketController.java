package controllers;


import com.google.common.io.Files;
import models.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.File;
import java.util.Date;
import java.util.List;

public class TicketController extends Controller
{
    private FormFactory formFactory;
    private JPAApi jpaApi;


    @Inject
    public TicketController(JPAApi jpaApi, FormFactory formFactory)
    {
        this.jpaApi = jpaApi;
        this.formFactory = formFactory;
    }

    @Transactional(readOnly = true)
    public Result getTickets()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        String sql = "SELECT t FROM Ticket t " +
                "WHERE name LIKE :searchCriteria " +
                "ORDER BY name ";
        String searchCriteria = form.get("searchCriteria");
        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        String queryParameter = searchCriteria + "%";

        List<Ticket> tickets = jpaApi.em()
                .createQuery(sql, Ticket.class).setParameter("searchCriteria", queryParameter).getResultList();

        String locationSql = "SELECT l FROM Location l ";
        Location location = jpaApi.em()
                .createQuery(locationSql, Location.class).getSingleResult();

        String statusSql = "SELECT s FROM Status s ";
        List<Status> statuses = jpaApi.em()
                .createQuery(statusSql, Status.class).getResultList();

        String adminSql = "SELECT sa FROM SiteAdmin sa ";
        List<SiteAdmin> siteAdmins = jpaApi.em()
                .createQuery(adminSql, SiteAdmin.class).getResultList();

        String prioritySql = "SELECT p FROM Priority p ";
        List<Priority> priority = jpaApi.em()
                .createQuery(prioritySql, Priority.class).getResultList();

        return ok(views.html.Ticket.ticketList.render(tickets, searchCriteria,
                location, statuses, siteAdmins, priority));

    }

    @Transactional(readOnly = true)
    public Result getTicket(Integer ticketsId)
    {
        String sql = "SELECT t FROM Ticket t " +
                "WHERE ticketsId = :ticketsId";
        //add a join
        Ticket ticket = jpaApi.em().createQuery(sql, Ticket.class).
                setParameter("ticketsId", ticketsId).getSingleResult();

        String locationSql = "SELECT l FROM Location l ";
        List<Location> locations = jpaApi.em()
                .createQuery(locationSql, Location.class).getResultList();

        String statusSql = "SELECT s FROM Status s ";
        List<Status> statuses = jpaApi.em()
                .createQuery(statusSql, Status.class).getResultList();

        String adminSql = "SELECT sa FROM SiteAdmin sa ";
        List<SiteAdmin> siteAdmins = jpaApi.em()
                .createQuery(adminSql, SiteAdmin.class).getResultList();

        String prioritySql = "SELECT p FROM Priority p ";
        List<Priority> priorities = jpaApi.em()
                .createQuery(prioritySql, Priority.class).getResultList();

        String categorySql = "SELECT c FROM Category c ";
        List<Category> categories = jpaApi.em()
                .createQuery(categorySql, Category.class).getResultList();

        String regionSql = "SELECT r FROM Region r ";
        List<Region> regions = jpaApi.em()
                .createQuery(regionSql, Region.class).getResultList();

        String replySql = "SELECT r FROM Reply r " +
                "WHERE ticketsId = :ticketsId"; //needs to be case statement

       Reply reply = jpaApi.em().createQuery(replySql, Reply.class)
        .setParameter("ticketsId", ticketsId).getSingleResult();

        String fileSql = "SELECT f FROM FileDetail f " +
                "WHERE ticketsId = :ticketsId"; //needs to be case statement

        FileDetail fileDetail = jpaApi.em().createQuery(fileSql, FileDetail.class)
                .setParameter("ticketsId", ticketsId).getSingleResult();

        return ok(views.html.Ticket.ticket.render(ticket, locations, statuses,
                siteAdmins, priorities, categories, regions, reply, fileDetail));


    }

    @Transactional
    public Result postTicket(Integer ticketsId)
    {


        DynamicForm form = formFactory.form().bindFromRequest();
        Ticket ticket = new Ticket();
        String Name = form.get("Name");
        int phoneNumber = Integer.parseInt(form.get("phoneNumber"));
        String emailAddress = form.get("emailAddress");
        int assetTagNumber = Integer.parseInt(form.get("assetTagNumber"));
        String subjectTitle = form.get("subjectTitle");
        String description = form.get("description");
        String computerName = form.get("computerName");
        Date statusDateChanged = new Date();
        int locationId = Integer.parseInt(form.get("locationId"));
        int priorityId = Integer.parseInt(form.get("regionId"));
        int categoryId = Integer.parseInt(form.get("categoryId"));
        int statusId = Integer.parseInt(form.get("statusId"));
        int siteAdminId = Integer.parseInt(form.get("siteAdminId"));

        ticket.setComputerName(Name);
        ticket.setPhoneNumber(phoneNumber);
        ticket.setEmailAddress(emailAddress);
        ticket.setAssetTagNumber(assetTagNumber);
        ticket.setSubjectTitle(subjectTitle);
        ticket.setDescription(description);
        ticket.setComputerName(computerName);
        ticket.setPriority(priorityId);
        ticket.setCategory(categoryId);
        ticket.setStatusId(statusId);
        ticket.setLocation(locationId);
        ticket.setSiteAdmin(siteAdminId);
        ticket.setStatusDateChanged(statusDateChanged);
        jpaApi.em().persist(ticket);

        FileDetail newFileDetail = new FileDetail();
        Http.MultipartFormData<File> formData1 = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> filePart1 = formData1.getFile("file1");
        File file1 = filePart1.getFile();

        Http.MultipartFormData<File> formData2 = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> filePart2 = formData2.getFile("file2");
        File file2 = filePart2.getFile();

        Http.MultipartFormData<File> formData3 = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> filePart3 = formData3.getFile("file3");
        File file3 = filePart3.getFile();

        if (file1 != null)
        {
            try
            {
                newFileDetail.setAddedFiles(Files.toByteArray(file1));
                newFileDetail.setExtension(filePart1.getContentType());
                newFileDetail.setTicketId(ticketsId);
                jpaApi.em().persist(newFileDetail);
            } catch (Exception e)
            {
                //do nothing
            }
        }
        if (file2 != null)
        {
            try
            {
                newFileDetail.setAddedFiles(Files.toByteArray(file2));
                newFileDetail.setExtension(filePart2.getContentType());
                newFileDetail.setTicketId(ticketsId);
                jpaApi.em().persist(newFileDetail);
            } catch (Exception e)
            {
                //do nothing
            }
        }
        if (file3 != null)
        {
            try
            {
                newFileDetail.setAddedFiles(Files.toByteArray(file3));
                newFileDetail.setExtension(filePart3.getContentType());
                newFileDetail.setTicketId(ticketsId);
                jpaApi.em().persist(newFileDetail);
            } catch (Exception e)
            {
                //do nothing
            }
        }
        Reply reply1 = new Reply();
        if (reply1 != null)
        {
            try
            {
                reply1.setReply(form.get("reply"));
                reply1.setTicketsId(ticketsId);
                jpaApi.em().persist(reply1);
            }catch (Exception e)
            {
                //do nothing
            }
        }


        return redirect(routes.TicketController.getTickets());
    }

    @Transactional(readOnly = true)
    public Result getNewTicket()
    {

        String locationSql = "SELECT l FROM Location l ";
        List<Location> locations = jpaApi.em()
                .createQuery(locationSql, Location.class).getResultList();

        String statusSql = "SELECT s FROM Status s ";
        List<Status> statuses = jpaApi.em()
                .createQuery(statusSql, Status.class).getResultList();

        String adminSql = "SELECT sa FROM SiteAdmin sa ";
        List<SiteAdmin> siteAdmins = jpaApi.em()
                .createQuery(adminSql, SiteAdmin.class).getResultList();

        String prioritySql = "SELECT p FROM Priority p ";
        List<Priority> priorities = jpaApi.em()
                .createQuery(prioritySql, Priority.class).getResultList();

        String categorySql = "SELECT c FROM Category c ";
        List<Category> categories = jpaApi.em()
                .createQuery(categorySql, Category.class).getResultList();

        String regionSql = "SELECT r FROM Region r ";
        List<Region> regions = jpaApi.em()
                .createQuery(regionSql, Region.class).getResultList();

        return ok(views.html.Ticket.newticket.render(locations, statuses,
                siteAdmins, priorities, categories, regions));
    }

    @Transactional
    public Result postNewTicket()
    {
        // Add info to save
        Http.MultipartFormData<File> formData1 = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> filePart1 = formData1.getFile("file1");
        File file1 = filePart1.getFile();

        Http.MultipartFormData<File> formData2 = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> filePart2 = formData2.getFile("file2");
        File file2 = filePart2.getFile();

        Http.MultipartFormData<File> formData3 = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> filePart3 = formData3.getFile("file3");
        File file3 = filePart3.getFile();

        if (file1 != null)
        {
            try
            {
                //employee.setPicture(Files.toByteArray(file1));
            } catch (Exception e)
            {
                //do nothing
            }
        }
        if (file2 != null)
        {
            try
            {
                //employee.setPicture(Files.toByteArray(file2));
            } catch (Exception e)
            {
                //do nothing
            }
        }
        if (file3 != null)
        {
            try
            {
                //employee.setPicture(Files.toByteArray(file3));
            } catch (Exception e)
            {
                //do nothing
            }
        }
        Ticket ticket = new Ticket();

        return redirect(routes.TicketController.getTickets());
    }
}
