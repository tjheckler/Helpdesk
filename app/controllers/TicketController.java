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

       List<Reply> replies = jpaApi.em().createQuery(replySql, Reply.class)
        .setParameter("ticketsId", ticketsId).getResultList();

        String fileSql = "SELECT f FROM FileDetails f " +
                "WHERE ticketsId = :ticketsId"; //needs to be case statement

        List<FileDetails> fileDetails = jpaApi.em().createQuery(fileSql, FileDetails.class)
                .setParameter("ticketsId", ticketsId).getResultList();

        return ok(views.html.Ticket.ticket.render(ticket, locations, statuses,
                siteAdmins, priorities, categories, regions, replies, fileDetails));


    }

    @Transactional
    public Result postTicket(Integer ticketsId)
    {


        DynamicForm form = formFactory.form().bindFromRequest();
        String sql = "SELECT t FROM Ticket t " +
                "WHERE ticketsId = :ticketsId";

        Ticket ticket = jpaApi.em().createQuery(sql, Ticket.class)
                .setParameter("ticketsId", ticketsId).getSingleResult();

        jpaApi.em().persist(ticket);

        FileDetails newFileDetails = new FileDetails();
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
                newFileDetails.setAddedFiles(Files.toByteArray(file1));
            } catch (Exception e)
            {
                //do nothing
            }
            newFileDetails.setExtension(filePart1.getContentType());
            newFileDetails.setTicketId(ticketsId);
            jpaApi.em().persist(newFileDetails);
    }
        if (file2 != null)
        {
            try
            {
                newFileDetails.setAddedFiles(Files.toByteArray(file2));

            } catch (Exception e)
            {
                //do nothing
            }
            newFileDetails.setExtension(filePart2.getContentType());
            newFileDetails.setTicketId(ticketsId);
            jpaApi.em().persist(newFileDetails);
        }
        if (file3 != null)
        {
            try
            {
                newFileDetails.setAddedFiles(Files.toByteArray(file3));

            } catch (Exception e)
            {
                //do nothing
            }
            newFileDetails.setExtension(filePart3.getContentType());
            newFileDetails.setTicketId(ticketsId);
            jpaApi.em().persist(newFileDetails);
        }
        Reply reply1 = new Reply();

            reply1.setReply(form.get("reply"));
            reply1.setTicketsId(ticketsId);
            jpaApi.em().persist(reply1);



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

        FileDetails newFileDetails = new FileDetails();

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
                newFileDetails.setAddedFiles(Files.toByteArray(file1));
            } catch (Exception e)
            {
                //do nothing
            }
            newFileDetails.setExtension(filePart1.getContentType());
            newFileDetails.setTicketId(ticket.getTicketsId());
            jpaApi.em().persist(newFileDetails);
        }
        if (file2 != null)
        {
            try
            {
                newFileDetails.setAddedFiles(Files.toByteArray(file2));

            } catch (Exception e)
            {
                //do nothing
            }
            newFileDetails.setExtension(filePart2.getContentType());
            newFileDetails.setTicketId(ticket.getTicketsId());
            jpaApi.em().persist(newFileDetails);
        }
        if (file3 != null)
        {
            try
            {
                newFileDetails.setAddedFiles(Files.toByteArray(file3));

            } catch (Exception e)
            {
                //do nothing
            }
            newFileDetails.setExtension(filePart3.getContentType());
            newFileDetails.setTicketId(ticket.getTicketsId());
            jpaApi.em().persist(newFileDetails);
        }


        return redirect(routes.TicketController.getTickets());
    }
    public Result getFiles(int fileDetailId)
    {

        String sql = "SELECT f FROM FileDetails f " +
                "WHERE fileDetailId = :fileDetailId";
        FileDetails fileDetail = jpaApi.em().createQuery(sql, FileDetails.class).
                setParameter("fileDetailId", fileDetailId).getSingleResult();
        String contentType = fileDetail.getExtension();
        return ok(fileDetail.getAddedFiles()).as(contentType);
    }
}
