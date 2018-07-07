package controllers;

import com.google.common.io.Files;
import javafx.scene.control.Alert;
import models.*;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.Query;
import java.io.File;
import java.util.Date;
import java.util.List;

public class TicketController extends ApplicationController
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


        //Filter & Search Begin
        Integer statuses1 = null;
        Integer priorities1 = null;
        Integer siteAdmins1 = null;
        Integer categories1 = null;
        Integer locations1 = null;


        String statusIdText = form.get("statusId1");
        if (statusIdText != null && !statusIdText.trim().equals(""))
        {
            statuses1 = Integer.parseInt(statusIdText);
        }
        String priorityIdText = form.get("priorityId1");
        if (priorityIdText != null && !priorityIdText.trim().equals(""))
        {

            priorities1 = Integer.parseInt(priorityIdText);
        }
        String siteAdminIdText = form.get("siteAdminId1");
        if (siteAdminIdText != null && !siteAdminIdText.trim().equals(""))
        {
            siteAdmins1 = Integer.parseInt(siteAdminIdText);
        }
        String categoryIdText = form.get("categoryId1");
        if (categoryIdText != null && !categoryIdText.trim().equals(""))
        {
            categories1 = Integer.parseInt(categoryIdText);
        }
        String locationIdText = form.get("locationId1");
        if (locationIdText != null && !locationIdText.trim().equals(""))
        {
            locations1 = Integer.parseInt(locationIdText);
        }

        String sql = "SELECT t FROM Ticket t " +
                "JOIN Location l ON t.locationId = l.locationId " +
                "JOIN Priority p ON t.priorityId = p.priorityId " +
                "JOIN Category c ON t.categoryId = c.categoryId " +
                "JOIN SiteAdmin sa ON t.siteAdminId = sa.siteAdminId " +
                "JOIN TicketStatus s ON t.statusId = s.statusId " +
                "WHERE 1=1 ";

        String locationSql = "SELECT l FROM Location l WHERE 1=1 ";
        List<Location> locations = jpaApi.em()
                .createQuery(locationSql, Location.class).getResultList();

        String statusSql = "SELECT s FROM TicketStatus s WHERE 1=1 ";
        List<TicketStatus> ticketStatuses = jpaApi.em()
                .createQuery(statusSql, TicketStatus.class).getResultList();

        String siteAdminSql = "SELECT sa FROM SiteAdmin sa WHERE 1=1 ";
        List<SiteAdmin> siteAdmins = jpaApi.em()
                .createQuery(siteAdminSql, SiteAdmin.class).getResultList();

        final String prioritySql = "SELECT p FROM Priority p";
        List<Priority> priority = jpaApi.em()
                .createQuery(prioritySql, Priority.class).getResultList();

        String categorySql = "SELECT c FROM Category c WHERE 1=1 ";
        List<Category> categories = jpaApi.em()
                .createQuery(categorySql, Category.class).getResultList();

        String searchCriteria = form.get("searchCriteria");
        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        if (!searchCriteria.trim().equals(""))
        {
            sql += " AND (t.name LIKE :searchCriteria OR " +
                    "t.subjectTitle LIKE :searchCriteria OR " +
                    "t.subjectTitle LIKE :searchCriteria OR " +
                    "l.locationName LIKE :searchCriteria OR " +
                    "p.priorityName LIKE :searchCriteria OR " +
                    "sa.siteAdminName LIKE :searchCriteria OR " +
                    "t.ticketsId LIKE :searchCriteria OR " +
                    "s.statusName LIKE :searchCriteria) ";
        }
        if (statuses1 != null)
        {
            sql += "AND t.statusId = :statusId ";

        }
        if (priorities1 != null)
        {
            sql += "AND t.priorityId = :priorityId ";

        }
        if (siteAdmins1 != null)
        {
            sql += "AND t.siteAdminId = :siteAdminId ";

        }
        if (categories1 != null)
        {
            sql += "AND t.categoryId = :categoryId ";

        }
        if (locations1 != null)
        {
            sql += "AND t.locationId = :locationId ";

        }
        String queryParameter = searchCriteria + "%";

        sql += "ORDER BY t.ticketsId desc ";
        Query ticketDetailQuery = jpaApi.em().createQuery(sql, Ticket.class);
        if (!searchCriteria.trim().equals(""))
        {
            ticketDetailQuery.setParameter("searchCriteria", queryParameter);
        }

        if (statuses1 != null)
        {
            ticketDetailQuery.setParameter("statusId", statuses1);
        }

        if (priorities1 != null)
        {

            ticketDetailQuery.setParameter("priorityId", priorities1);
        }

        if (siteAdmins1 != null)
        {
            ticketDetailQuery.setParameter("siteAdminId", siteAdmins1);
        }

        if (categories1 != null)
        {
            ticketDetailQuery.setParameter("categoryId", categories1);
        }

        if (locations1 != null)
        {
            ticketDetailQuery.setParameter("locationId", locations1);
        }

        List<Ticket> tickets = ticketDetailQuery.getResultList();
//Filter & Search End
        if (isLoggedIn())
        {
            return ok(views.html.Ticket.ticketList.render(tickets, searchCriteria, locations,
                    ticketStatuses, siteAdmins, priority, categories, statuses1,
                    priorities1, siteAdmins1, categories1, locations1));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }

    }

    @Transactional(readOnly = true)
    public Result getTicket(Integer ticketsId)
    {

        String sql = "SELECT t FROM Ticket t " +
                "WHERE t.ticketsId = :ticketsId ";
        //add a join
        Ticket ticket = jpaApi.em().createQuery(sql, Ticket.class).
                setParameter("ticketsId", ticketsId).getSingleResult();

        String locationSql = "SELECT l FROM Location l ";
        List<Location> locations = jpaApi.em()
                .createQuery(locationSql, Location.class).getResultList();

        String statusSql = "SELECT s FROM TicketStatus s ";
        List<TicketStatus> ticketStatuses = jpaApi.em()
                .createQuery(statusSql, TicketStatus.class).getResultList();

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
                "WHERE ticketsId = :ticketsId";

        List<FileDetails> fileDetails = jpaApi.em().createQuery(fileSql, FileDetails.class)
                .setParameter("ticketsId", ticketsId).getResultList();

        return ok(views.html.Ticket.ticket.render(ticket, locations, ticketStatuses,
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

        String locationSql = "SELECT l FROM Location l ";
        List<Location> locations = jpaApi.em()
                .createQuery(locationSql, Location.class).getResultList();

        String statusSql = "SELECT s FROM TicketStatus s ";
        List<TicketStatus> ticketStatuses = jpaApi.em()
                .createQuery(statusSql, TicketStatus.class).getResultList();

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
                "WHERE ticketsId = :ticketsId";

        List<Reply> replies = jpaApi.em().createQuery(replySql, Reply.class)
                .setParameter("ticketsId", ticketsId).getResultList();

        String fileSql = "SELECT f FROM FileDetails f " +
                "WHERE ticketsId = :ticketsId";

        List<FileDetails> fileDetails = jpaApi.em().createQuery(fileSql, FileDetails.class)
                .setParameter("ticketsId", ticketsId).getResultList();
        try
        {
            Date statusDateChanged = new Date();
            int statusId = Integer.parseInt(form.get("statusId"));
            int priorityId = Integer.parseInt(form.get("priorityId"));
            int siteAdminId = Integer.parseInt(form.get("siteAdminId"));
            ticket.setStatusId(statusId);
            ticket.setPriority(priorityId);
            ticket.setSiteAdmin(siteAdminId);
            ticket.setStatusDateChanged(statusDateChanged);
        } catch (Exception e)
        {
            e.getCause();
        }
        for (int i = 0; i < siteAdmins.size() - 1; i++)
        {
            if (ticket.getSiteAdminId() == siteAdmins.get(i).getSiteAdminId())
            {
                //make a real url that consists of ticket number
                String url = "localhost:9000/ticket/" + ticket.getTicketsId();
                String email = siteAdmins.get(i).getEmailAddress();
                String customerEmail = ticket.getEmailAddress();
                if (isLoggedIn())
                {
                    Email.sendCustomerEmail("Ticket Number " + ticket.getTicketsId()
                            + " Your ticket has been updated, you can review at " + url, customerEmail);
                    jpaApi.em().persist(ticket);
                } else if (!isLoggedIn())
                {
                    Email.sendUpdateEmail("Ticket Number " + ticket.getTicketsId()
                            + " has been assigned to you or updated. You can view this ticket " +
                            "by the following link, copy and paste to browser " + url, email);
                    jpaApi.em().persist(ticket);
                } else
                {
                    //do nothing
                }
            } else
            {
                //do nothing
            }

        }

        Http.MultipartFormData<File> formData1 = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> filePart1 = formData1.getFile("file1");
        File file1 = filePart1.getFile();

        Http.MultipartFormData<File> formData2 = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> filePart2 = formData2.getFile("file2");
        File file2 = filePart2.getFile();

        Http.MultipartFormData<File> formData3 = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> filePart3 = formData3.getFile("file3");
        File file3 = filePart3.getFile();

        if (file1 != null && filePart1.getFilename().length() > 0 && !filePart1.getContentType().equals("zip")
                && !filePart1.getContentType().equals("exe"))
        {
            FileDetails newFileDetails = new FileDetails();
            try
            {
                newFileDetails.setAddedFiles(Files.toByteArray(file1));
            } catch (Exception e)
            {
                e.getCause();
            }

            newFileDetails.setExtension(filePart1.getContentType());
            newFileDetails.setTicketId(ticketsId);
            jpaApi.em().persist(newFileDetails);
        }
        if (file2 != null && filePart2.getFilename().length() > 0 && !filePart2.getContentType().equals("zip")
                && !filePart2.getContentType().equals("exe"))
        {
            FileDetails newFileDetails = new FileDetails();
            try
            {
                newFileDetails.setAddedFiles(Files.toByteArray(file2));

            } catch (Exception e)
            {
                e.getCause();
            }
            newFileDetails.setExtension(filePart2.getContentType());
            newFileDetails.setTicketId(ticketsId);
            jpaApi.em().persist(newFileDetails);
        }
        if (file3 != null && filePart3.getFilename().length() > 0 && !filePart3.getContentType().equals("zip")
                && !filePart3.getContentType().equals("exe"))
        {
            FileDetails newFileDetails = new FileDetails();
            try
            {
                newFileDetails.setAddedFiles(Files.toByteArray(file3));

            } catch (Exception e)
            {
                e.getCause();
            }
            newFileDetails.setExtension(filePart3.getContentType());
            newFileDetails.setTicketId(ticketsId);
            jpaApi.em().persist(newFileDetails);
        }

        String replyText = form.get("reply");
        if (replyText != null && replyText.length() > 0)
        {
            Reply reply1 = new Reply();

            reply1.setReply(form.get("reply"));
            reply1.setTicketsId(ticketsId);
            jpaApi.em().persist(reply1);
        }

        return ok(views.html.Ticket.ticket.render(ticket, locations, ticketStatuses, siteAdmins,
                priorities, categories, regions, replies, fileDetails));

    }

    @Transactional(readOnly = true)
    public Result getTicketEdit(Integer ticketsId, String message)
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT t FROM Ticket t " +
                    "WHERE t.ticketsId = :ticketsId ";
            //add a join
            Ticket ticket = jpaApi.em().createQuery(sql, Ticket.class).
                    setParameter("ticketsId", ticketsId).getSingleResult();

            String locationSql = "SELECT l FROM Location l ";
            List<Location> locations = jpaApi.em()
                    .createQuery(locationSql, Location.class).getResultList();

            String statusSql = "SELECT s FROM TicketStatus s ";
            List<TicketStatus> ticketStatuses = jpaApi.em()
                    .createQuery(statusSql, TicketStatus.class).getResultList();

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
                    "WHERE ticketsId = :ticketsId";

            List<FileDetails> fileDetails = jpaApi.em().createQuery(fileSql, FileDetails.class)
                    .setParameter("ticketsId", ticketsId).getResultList();

            return ok(views.html.Ticket.ticketedit.render(ticket, locations, ticketStatuses,
                    siteAdmins, priorities, categories, regions, replies, fileDetails));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }

    }

    @Transactional
    public Result postTicketEdit(Integer ticketsId, String message)
    {

        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            String sql = "SELECT t FROM Ticket t " +
                    "WHERE ticketsId = :ticketsId";

            Ticket ticket = jpaApi.em().createQuery(sql, Ticket.class)
                    .setParameter("ticketsId", ticketsId).getSingleResult();

            String locationSql = "SELECT l FROM Location l ";
            List<Location> locations = jpaApi.em()
                    .createQuery(locationSql, Location.class).getResultList();

            String statusSql = "SELECT s FROM TicketStatus s ";
            List<TicketStatus> ticketStatuses = jpaApi.em()
                    .createQuery(statusSql, TicketStatus.class).getResultList();

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
                    "WHERE ticketsId = :ticketsId";

            List<FileDetails> fileDetails = jpaApi.em().createQuery(fileSql, FileDetails.class)
                    .setParameter("ticketsId", ticketsId).getResultList();
            try
            {
                Date statusDateChanged = new Date();
                int statusId = Integer.parseInt(form.get("statusId"));
                int priorityId = Integer.parseInt(form.get("priorityId"));
                int siteAdminId = Integer.parseInt(form.get("siteAdminId"));
                int locationId = Integer.parseInt(form.get("locationId"));
                String computerName = form.get("computerName");
                String assetTagNumber = form.get("assetTagNumber");
                String subjectTitle = form.get("subjectTitle");
                String emailAddress = form.get("emailAddress");
                String name = form.get("name");

                ticket.setLocation(locationId);
                ticket.setComputerName(computerName);
                ticket.setAssetTagNumber(assetTagNumber);
                ticket.setSubjectTitle(subjectTitle);
                ticket.setEmailAddress(emailAddress);
                ticket.setName(name);
                ticket.setPriority(priorityId);
                ticket.setStatusId(statusId);
                ticket.setSiteAdmin(siteAdminId);
                ticket.setStatusDateChanged(statusDateChanged);
            } catch (Exception e)
            {
                e.getCause();
            }
            for (int i = 0; i < siteAdmins.size() - 1; i++)
            {
                if (ticket.getSiteAdminId() == siteAdmins.get(i).getSiteAdminId())
                {
                    //make a real url that consists of ticket number
                    String url = "localhost:9000/ticket/" + ticket.getTicketsId();
                    String email = siteAdmins.get(i).getEmailAddress();
                    if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
                    {
                        Email.sendUpdateEmail("Ticket Number " + ticket.getTicketsId()
                                + " has been assigned to you or updated. You can view this ticket " +
                                "by the following link, copy and paste to browser " + url, email);
                        jpaApi.em().persist(ticket);
                    } else
                    {
                        //do nothing
                    }
                } else
                {
                    //do nothing
                }

            }

            Http.MultipartFormData<File> formData1 = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart<File> filePart1 = formData1.getFile("file1");
            File file1 = filePart1.getFile();

            Http.MultipartFormData<File> formData2 = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart<File> filePart2 = formData2.getFile("file2");
            File file2 = filePart2.getFile();

            Http.MultipartFormData<File> formData3 = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart<File> filePart3 = formData3.getFile("file3");
            File file3 = filePart3.getFile();

            if (file1 != null && filePart1.getFilename().length() > 0 && !filePart1.getContentType().equals("zip")
                    && !filePart1.getContentType().equals("exe"))
            {
                FileDetails newFileDetails = new FileDetails();
                try
                {
                    newFileDetails.setAddedFiles(Files.toByteArray(file1));
                } catch (Exception e)
                {
                    e.getCause();
                }

                newFileDetails.setExtension(filePart1.getContentType());
                newFileDetails.setTicketId(ticketsId);
                jpaApi.em().persist(newFileDetails);
            }
            if (file2 != null && filePart2.getFilename().length() > 0 && !filePart2.getContentType().equals("zip")
                    && !filePart2.getContentType().equals("exe"))
            {
                FileDetails newFileDetails = new FileDetails();
                try
                {
                    newFileDetails.setAddedFiles(Files.toByteArray(file2));

                } catch (Exception e)
                {
                    e.getCause();
                }
                newFileDetails.setExtension(filePart2.getContentType());
                newFileDetails.setTicketId(ticketsId);
                jpaApi.em().persist(newFileDetails);
            }
            if (file3 != null && filePart3.getFilename().length() > 0 && !filePart3.getContentType().equals("zip")
                    && !filePart3.getContentType().equals("exe"))
            {
                FileDetails newFileDetails = new FileDetails();
                try
                {
                    newFileDetails.setAddedFiles(Files.toByteArray(file3));

                } catch (Exception e)
                {
                    e.getCause();
                }
                newFileDetails.setExtension(filePart3.getContentType());
                newFileDetails.setTicketId(ticketsId);
                jpaApi.em().persist(newFileDetails);
            }

            String replyText = form.get("reply");
            if (replyText != null && replyText.length() > 0)
            {
                Reply reply1 = new Reply();

                reply1.setReply(form.get("reply"));
                reply1.setTicketsId(ticketsId);
                jpaApi.em().persist(reply1);
            }

            return ok(views.html.Ticket.ticketedit.render(ticket, locations, ticketStatuses, siteAdmins,
                    priorities, categories, regions, replies, fileDetails));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }

    }

    @Transactional(readOnly = true)
    public Result getNewTicket()
    {
        if (isLoggedIn())
        {
            TicketFormValues ticketFormValues = new TicketFormValues();

            String locationSql = "SELECT l FROM Location l ";
            List<Location> locations = jpaApi.em()
                    .createQuery(locationSql, Location.class).getResultList();

            String statusSql = "SELECT s FROM TicketStatus s ";
            List<TicketStatus> ticketStatuses = jpaApi.em()
                    .createQuery(statusSql, TicketStatus.class).getResultList();

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

            return ok(views.html.Ticket.newticket.render(locations, ticketStatuses,
                    siteAdmins, priorities, categories, regions, ticketFormValues,
                    "* Indicates Required Field. ", true));
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result postNewTicket()
    {
        if (isLoggedIn())
        {
            String locationSql = "SELECT l FROM Location l ";
            List<Location> locations = jpaApi.em()
                    .createQuery(locationSql, Location.class).getResultList();

            String statusSql = "SELECT s FROM TicketStatus s ";
            List<TicketStatus> ticketStatuses = jpaApi.em()
                    .createQuery(statusSql, TicketStatus.class).getResultList();

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
            DynamicForm form = formFactory.form().bindFromRequest();


            TicketFormValues ticketFormValues = new TicketFormValues();
            ticketFormValues.setTicketName(form.get("name"));
            ticketFormValues.setTicketPhoneNumber(form.get("phoneNumber"));
            ticketFormValues.setTicketEmailAddress(form.get("emailAddress"));
            ticketFormValues.setTicketAssetTagNumber(form.get("assetTagNumber"));
            ticketFormValues.setTicketSubjectTitle(form.get("subjectTitle"));
            ticketFormValues.setTicketDescription(form.get("description"));
            ticketFormValues.setTicketComputerName(form.get("computerName"));
            Date statusDateChanged = new Date();
            ticketFormValues.setTicketLocationId(form.get("locationId"));
            ticketFormValues.setTicketCategoryId(form.get("categoryId"));
            ticketFormValues.setTicketStatusId(form.get("statusId"));
            ticketFormValues.setTicketPriorityId(form.get("priorityId"));
            ticketFormValues.setTicketSiteAdminId(form.get("siteAdminId"));

            if (ticketFormValues.isValid())
            {
                Ticket ticket = new Ticket();
                ticket.setName(ticketFormValues.getTicketName());
                ticket.setPhoneNumber(ticketFormValues.getTicketPhoneNumber());
                ticket.setEmailAddress(ticketFormValues.getTicketEmailAddress());
                ticket.setAssetTagNumber(ticketFormValues.getTicketAssetTagNumber());
                ticket.setSubjectTitle(ticketFormValues.getTicketSubjectTitle());
                ticket.setDescription(ticketFormValues.getTicketDescription());
                ticket.setComputerName(ticketFormValues.getTicketComputerName());
                try
                {
                    ticket.setLocation(new Integer(ticketFormValues.getTicketLocationId()));
                    ticket.setCategory(new Integer(ticketFormValues.getTicketCategoryId()));
                    ticket.setStatusId(new Integer(ticketFormValues.getTicketStatusId()));
                    ticket.setPriority(new Integer(ticketFormValues.getTicketPriorityId()));
                    ticket.setSiteAdmin(new Integer(ticketFormValues.getTicketSiteAdminId()));
                } catch (Exception e)
                {
                    e.getCause();
                }
                ticket.setStatusDateChanged(statusDateChanged);
                jpaApi.em().persist(ticket);


                Http.MultipartFormData<File> formData1 = request().body().asMultipartFormData();
                Http.MultipartFormData.FilePart<File> filePart1 = formData1.getFile("file1");
                File file1 = filePart1.getFile();

                Http.MultipartFormData<File> formData2 = request().body().asMultipartFormData();
                Http.MultipartFormData.FilePart<File> filePart2 = formData2.getFile("file2");
                File file2 = filePart2.getFile();

                Http.MultipartFormData<File> formData3 = request().body().asMultipartFormData();
                Http.MultipartFormData.FilePart<File> filePart3 = formData3.getFile("file3");
                File file3 = filePart3.getFile();

                if (file1 != null && filePart1.getFilename().length() > 0
                        && !filePart1.getContentType().equals("zip") && !filePart1.getContentType().equals("exe"))
                {
                    FileDetails newFileDetails = new FileDetails();
                    try
                    {
                        newFileDetails.setAddedFiles(Files.toByteArray(file1));
                    } catch (Exception e)
                    {
                        e.getCause();
                    }

                    newFileDetails.setExtension(filePart1.getContentType());
                    newFileDetails.setTicketId(ticket.getTicketsId());
                    jpaApi.em().persist(newFileDetails);
                }
                if (file2 != null && filePart2.getFilename().length() > 0
                        && !filePart2.getContentType().equals("zip") && !filePart2.getContentType().equals("exe"))
                {
                    FileDetails newFileDetails = new FileDetails();
                    try
                    {
                        newFileDetails.setAddedFiles(Files.toByteArray(file2));

                    } catch (Exception e)
                    {
                        e.getCause();
                    }
                    newFileDetails.setExtension(filePart2.getContentType());
                    newFileDetails.setTicketId(ticket.getTicketsId());
                    jpaApi.em().persist(newFileDetails);
                }
                if (file3 != null && filePart3.getFilename().length() > 0
                        && !filePart3.getContentType().equals("zip") && !filePart3.getContentType().equals("exe"))
                {
                    FileDetails newFileDetails = new FileDetails();
                    try
                    {
                        newFileDetails.setAddedFiles(Files.toByteArray(file3));

                    } catch (Exception e)
                    {
                        e.getCause();
                    }
                    newFileDetails.setExtension(filePart3.getContentType());
                    newFileDetails.setTicketId(ticket.getTicketsId());
                    jpaApi.em().persist(newFileDetails);
                }

                for (int i = 0; i < siteAdmins.size() - 1; i++)
                {
                    if (ticket.getSiteAdminId() == siteAdmins.get(i).getSiteAdminId())
                    {
                        //make a real url that consists of ticket number
                        String url = "localhost:9000/ticket/" + ticket.getTicketsId();
                        String email = siteAdmins.get(i).getEmailAddress();

                        Email.sendTicketEmail("A new ticket has been assigned to you. " +
                                "You can view this ticket by the following link, copy and paste " +
                                "to browser " + url, email);
                    } else
                    {
                        //do nothing
                    }

                }
                return redirect(routes.TicketController.getTickets());
            } else
            {
                return ok(views.html.Ticket.newticket.render(locations, ticketStatuses, siteAdmins,
                        priorities, categories, regions, ticketFormValues,
                        "", false));
            }

        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional
    public Result getFiles(int fileDetailId)
    {

        String sql = "SELECT f FROM FileDetails f " +
                "WHERE fileDetailId = :fileDetailId";
        FileDetails fileDetail = jpaApi.em().createQuery(sql, FileDetails.class).
                setParameter("fileDetailId", fileDetailId).getSingleResult();
        String contentType = fileDetail.getExtension();
        return ok(fileDetail.getAddedFiles()).as(contentType);
    }

    @Transactional
    public Result deleteTicket(int ticketsId)
    {
        if (isLoggedIn() && getLoggedInSiteAdminRole().equals("Admin"))
        {
            String sql = "SELECT t FROM Ticket t " +
                    "WHERE ticketsId = :ticketsId";
            Ticket ticket = jpaApi.em().createQuery(sql, Ticket.class).
                    setParameter("ticketsId", ticketsId).getSingleResult();
            jpaApi.em().remove(ticket);
            return redirect(routes.TicketController.getTickets());
        } else
        {
            return redirect(routes.AdministrationController.getLogin("Login As Administrator"));
        }
    }

    @Transactional(readOnly = true)
    public Result getCustomerTicket()
    {

        String locationSql = "SELECT l FROM Location l ";
        List<Location> locations = jpaApi.em()
                .createQuery(locationSql, Location.class).getResultList();

        String statusSql = "SELECT s FROM TicketStatus s ";
        List<TicketStatus> ticketStatuses = jpaApi.em()
                .createQuery(statusSql, TicketStatus.class).getResultList();

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

        TicketFormValues ticketFormValues = new TicketFormValues();

        return ok(views.html.CustomerTicket.customerticket.render(locations, ticketStatuses, siteAdmins,
                priorities, categories, regions, ticketFormValues,
                "* Indicates Required Field", true));

    }

    @Transactional
    public Result postCustomerTicket()
    {
        String locationSql = "SELECT l FROM Location l ";
        List<Location> locations = jpaApi.em()
                .createQuery(locationSql, Location.class).getResultList();

        String statusSql = "SELECT s FROM TicketStatus s ";
        List<TicketStatus> ticketStatuses = jpaApi.em()
                .createQuery(statusSql, TicketStatus.class).getResultList();

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
        DynamicForm form = formFactory.form().bindFromRequest();

        TicketFormValues ticketFormValues = new TicketFormValues();

        ticketFormValues.setTicketName(form.get("name"));
        ticketFormValues.setTicketPhoneNumber(form.get("phoneNumber"));
        ticketFormValues.setTicketEmailAddress(form.get("emailAddress"));
        ticketFormValues.setTicketAssetTagNumber(form.get("assetTagNumber"));
        ticketFormValues.setTicketSubjectTitle(form.get("subjectTitle"));
        ticketFormValues.setTicketDescription(form.get("description"));
        ticketFormValues.setTicketComputerName(form.get("computerName"));
        Date statusDateChanged = new Date();
        ticketFormValues.setTicketLocationId(form.get("locationId"));
        ticketFormValues.setTicketCategoryId(form.get("categoryId"));
        ticketFormValues.setTicketStatusId(form.get("statusId"));
        ticketFormValues.setTicketPriorityId(form.get("priorityId"));
        ticketFormValues.setTicketSiteAdminId(form.get("siteAdminId"));
        if (ticketFormValues.isValid())
        {
            Ticket ticket = new Ticket();
            ticket.setName(ticketFormValues.getTicketName());
            ticket.setPhoneNumber(ticketFormValues.getTicketPhoneNumber());
            ticket.setEmailAddress(ticketFormValues.getTicketEmailAddress());
            ticket.setAssetTagNumber(ticketFormValues.getTicketAssetTagNumber());
            ticket.setSubjectTitle(ticketFormValues.getTicketSubjectTitle());
            ticket.setDescription(ticketFormValues.getTicketDescription());
            ticket.setComputerName(ticketFormValues.getTicketComputerName());
            try
            {
                ticket.setLocation(new Integer(ticketFormValues.getTicketLocationId()));
                ticket.setCategory(new Integer(ticketFormValues.getTicketCategoryId()));
                ticket.setStatusId(new Integer(ticketFormValues.getTicketStatusId()));
                ticket.setPriority(new Integer(ticketFormValues.getTicketPriorityId()));
                ticket.setSiteAdmin(new Integer(ticketFormValues.getTicketSiteAdminId()));
            } catch (Exception e)
            {
                e.getCause();
            }
            ticket.setStatusDateChanged(statusDateChanged);
            jpaApi.em().persist(ticket);


            Http.MultipartFormData<File> formData1 = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart<File> filePart1 = formData1.getFile("file1");
            File file1 = filePart1.getFile();

            Http.MultipartFormData<File> formData2 = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart<File> filePart2 = formData2.getFile("file2");
            File file2 = filePart2.getFile();

            Http.MultipartFormData<File> formData3 = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart<File> filePart3 = formData3.getFile("file3");
            File file3 = filePart3.getFile();

            if (file1 != null && filePart1.getFilename().length() > 0 && !filePart1.getContentType().equals("zip")
                    && !filePart1.getContentType().equals("exe"))
            {
                FileDetails newFileDetails = new FileDetails();
                try
                {
                    newFileDetails.setAddedFiles(Files.toByteArray(file1));
                } catch (Exception e)
                {
                    e.getCause();
                }

                newFileDetails.setExtension(filePart1.getContentType());
                newFileDetails.setTicketId(ticket.getTicketsId());
                jpaApi.em().persist(newFileDetails);
            }
            if (file2 != null && filePart2.getFilename().length() > 0 && !filePart2.getContentType().equals("zip")
                    && !filePart2.getContentType().equals("exe"))
            {
                FileDetails newFileDetails = new FileDetails();
                try
                {
                    newFileDetails.setAddedFiles(Files.toByteArray(file2));

                } catch (Exception e)
                {
                    e.getCause();
                }
                newFileDetails.setExtension(filePart2.getContentType());
                newFileDetails.setTicketId(ticket.getTicketsId());
                jpaApi.em().persist(newFileDetails);
            }
            if (file3 != null && filePart3.getFilename().length() > 0 && !filePart3.getContentType().equals("zip")
                    && !filePart3.getContentType().equals("exe"))
            {
                FileDetails newFileDetails = new FileDetails();
                try
                {
                    newFileDetails.setAddedFiles(Files.toByteArray(file3));

                } catch (Exception e)
                {
                    e.getCause();
                }
                newFileDetails.setExtension(filePart3.getContentType());
                newFileDetails.setTicketId(ticket.getTicketsId());
                jpaApi.em().persist(newFileDetails);
            }

            for (int i = 0; i < siteAdmins.size() - 1; i++)
            {
                if (ticket.getSiteAdminId() == siteAdmins.get(i).getSiteAdminId())
                {
                    //make a real url that consists of ticket number
                    String url = "localhost:9000/ticket/" + ticket.getTicketsId();
                    String email = siteAdmins.get(i).getEmailAddress();

                    Email.sendTicketEmail("A new ticket has been assigned" +
                            " to you. You can view this ticket " +
                            "by the following link, copy and paste to browser " + url, email);
                } else
                {
                    //do nothing
                }

            }
            return redirect(routes.HomeController.getTicketSent());

        } else
        {
            return ok(views.html.CustomerTicket.customerticket.render(locations, ticketStatuses,
                    siteAdmins, priorities, categories, regions, ticketFormValues,
                    "", false));
        }

    }

}
