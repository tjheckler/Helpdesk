package controllers;


import com.google.common.io.Files;
import javafx.scene.control.Alert;
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

        String sql = "SELECT t FROM Ticket t " +
                "JOIN Location l ON t.locationId = l.locationId " +
                "JOIN Priority p ON t.priorityId = p.priorityId " +
                "JOIN Category c ON t.categoryId = c.categoryId " +
                "JOIN SiteAdmin sa ON t.siteAdminId = sa.siteAdminId " +
                "JOIN TicketStatus s ON t.statusId = s.statusId " +
                "WHERE t.name LIKE :searchCriteria  OR " +
                "t.subjectTitle LIKE :searchCriteria OR " +
                "t.subjectTitle LIKE :searchCriteria OR " +
                "l.locationName LIKE :searchCriteria OR " +
                "p.priorityName LIKE :searchCriteria OR " +
                "sa.siteAdminName LIKE :searchCriteria OR " +
                "t.ticketsId LIKE :searchCriteria OR " +
                "s.statusName Like :searchCriteria " +
                "ORDER BY t.statusId";
        //Text Search Begin
        String searchCriteria = form.get("searchCriteria");
        if (searchCriteria == null)
        {
            searchCriteria = "";
        }
        String queryParameter = searchCriteria + "%";
        List<Ticket> tickets = jpaApi.em().createQuery(sql, Ticket.class).
                setParameter("searchCriteria", queryParameter).getResultList();

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
        List<Priority> priority = jpaApi.em()
                .createQuery(prioritySql, Priority.class).getResultList();

        String categorySql = "SELECT c FROM Category c ";
        List<Category> categories = jpaApi.em()
                .createQuery(categorySql, Category.class).getResultList();

        //Text Search End


        if (isLoggedIn())
        {
            return ok(views.html.Ticket.ticketList.render(tickets, searchCriteria, locations,
                    ticketStatuses, siteAdmins, priority, categories));
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }

    }

    @Transactional(readOnly = true)
    public Result getTicket(Integer ticketsId)
    {
        if (isLoggedIn())
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
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }

    }

    @Transactional
    public Result postTicket(Integer ticketsId)
    {
        if (isLoggedIn())
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


            Date statusDateChanged = new Date();

            int statusId = Integer.parseInt(form.get("statusId"));

            ticket.setStatusId(statusId);

            int priorityId = Integer.parseInt(form.get("priorityId"));

            ticket.setPriority(priorityId);

            int siteAdminId = Integer.parseInt(form.get("siteAdminId"));

            ticket.setSiteAdmin(siteAdminId);


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
                    //do nothing
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
                    //do nothing
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
                    //do nothing
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
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
    }

    @Transactional(readOnly = true)
    public Result getNewTicket()
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

            return ok(views.html.Ticket.newticket.render(locations, ticketStatuses,
                    siteAdmins, priorities, categories, regions,"* Indicates Required Field"));
        } else
        {
            return redirect(routes.AdministrationController.getLogin());
        }
    }

    @Transactional
    public Result postNewTicket()
    {
        if (isLoggedIn())
        {
            DynamicForm form = formFactory.form().bindFromRequest();
            Ticket ticket = new Ticket();
            String name = form.get("name");
            String phoneNumber = form.get("phoneNumber");
            String emailAddress = form.get("emailAddress");
            String assetTagNumber = form.get("assetTagNumber");
            String subjectTitle = form.get("subjectTitle");
            String description = form.get("description");
            String computerName = form.get("computerName");
            Date statusDateChanged = new Date();
            int locationId = Integer.parseInt(form.get("locationId"));
            int categoryId = Integer.parseInt(form.get("categoryId"));
            int statusId = Integer.parseInt(form.get("statusId"));
            int priorityId = Integer.parseInt(form.get("priorityId"));
            int siteAdminId = Integer.parseInt(form.get("siteAdminId"));
            if (name != null && phoneNumber != null && emailAddress != null &&
                    subjectTitle != null && computerName != null && assetTagNumber != null
                    && description !=null && locationId > 0 && categoryId > 0
                    && statusId > 0 && priorityId > 0 && siteAdminId > 0)
            {
                ticket.setName(name);
                ticket.setPhoneNumber(phoneNumber);
                ticket.setEmailAddress(emailAddress);
                ticket.setAssetTagNumber(assetTagNumber);
                ticket.setSubjectTitle(subjectTitle);
                ticket.setDescription(description);
                ticket.setComputerName(computerName);
                ticket.setLocation(locationId);
                ticket.setCategory(categoryId);
                ticket.setStatusId(statusId);
                ticket.setPriority(priorityId);
                ticket.setSiteAdmin(siteAdminId);
                ticket.setStatusDateChanged(statusDateChanged);
                jpaApi.em().persist(ticket);
            } else
            {
                return redirect(routes.TicketController.getNewTicket());
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

            if (file1 != null && filePart1.getFilename().length() > 0
                    && !filePart1.getContentType().equals("zip") && !filePart1.getContentType().equals("exe"))
            {
                FileDetails newFileDetails = new FileDetails();
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
            if (file2 != null && filePart2.getFilename().length() > 0
                    && !filePart2.getContentType().equals("zip") && !filePart2.getContentType().equals("exe"))
            {
                FileDetails newFileDetails = new FileDetails();
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
            if (file3 != null && filePart3.getFilename().length() > 0
                    && !filePart3.getContentType().equals("zip") && !filePart3.getContentType().equals("exe"))
            {
                FileDetails newFileDetails = new FileDetails();
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
            String adminSql = "SELECT sa FROM SiteAdmin sa ";
            List<SiteAdmin> siteAdmins = jpaApi.em()
                    .createQuery(adminSql, SiteAdmin.class).getResultList();
            for (int i = 0; i < siteAdmins.size() - 1; i++)
            {
                if (ticket.getSiteAdminId() == siteAdmins.get(i).getSiteAdminId())
                {
                    //make a real url that consists of ticket number
                    String url = "localhost:9000/tickets/" + ticket.getTicketsId();
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
            return redirect(routes.AdministrationController.getLogin());
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
            return redirect(routes.AdministrationController.getLogin());
        }
    }

    @Transactional(readOnly = true)
    public Result getCustomerTicket()
    {

        String prioritySql = "SELECT p FROM Priority p ";
        List<Priority> priorities = jpaApi.em()
                .createQuery(prioritySql, Priority.class).getResultList();

        String categorySql = "SELECT c FROM Category c ";
        List<Category> categories = jpaApi.em()
                .createQuery(categorySql, Category.class).getResultList();

        String locationSql = "SELECT l FROM Location l ";
        List<Location> locations = jpaApi.em()
                .createQuery(locationSql, Location.class).getResultList();

       /* String statusSql = "SELECT s FROM TicketStatus s ";
        List<TicketStatus> ticketStatuses = jpaApi.em()
                .createQuery(statusSql, TicketStatus.class).getResultList();

        String adminSql = "SELECT sa FROM SiteAdmin sa ";
        List<SiteAdmin> siteAdmins = jpaApi.em()
                .createQuery(adminSql, SiteAdmin.class).getResultList();


        String regionSql = "SELECT r FROM Region r ";
        List<Region> regions = jpaApi.em()
                .createQuery(regionSql, Region.class).getResultList();*/

        return ok(views.html.CustomerTicket.customerticket.render(locations,
                priorities, categories,"* Indicates Required Field"));
    }

    @Transactional
    public Result postCustomerTicket()
    {
        DynamicForm form = formFactory.form().bindFromRequest();
        Ticket ticket = new Ticket();
        String name = form.get("name");
        String phoneNumber = form.get("phoneNumber");
        String emailAddress = form.get("emailAddress");
        String assetTagNumber = form.get("assetTagNumber");
        String subjectTitle = form.get("subjectTitle");
        String description = form.get("description");
        String computerName = form.get("computerName");
        Date statusDateChanged = new Date();
        int locationId = Integer.parseInt(form.get("locationId"));
        int categoryId = Integer.parseInt(form.get("categoryId"));
        int statusId = Integer.parseInt(form.get("statusId"));
        int priorityId = Integer.parseInt(form.get("priorityId"));
        int siteAdminId = Integer.parseInt(form.get("siteAdminId"));

        if (name != null && phoneNumber != null && emailAddress != null &&
                subjectTitle != null && description !=null && locationId > 0
                && categoryId > 0 && statusId > 0 && priorityId > 0 && siteAdminId > 0)
        {
        ticket.setName(name);
        ticket.setPhoneNumber(phoneNumber);
        ticket.setEmailAddress(emailAddress);
        ticket.setAssetTagNumber(assetTagNumber);
        ticket.setSubjectTitle(subjectTitle);
        ticket.setDescription(description);
        ticket.setComputerName(computerName);
        ticket.setLocation(locationId);
        ticket.setCategory(categoryId);
        ticket.setStatusId(statusId);
        ticket.setPriority(priorityId);
        ticket.setSiteAdmin(siteAdminId);
        ticket.setStatusDateChanged(statusDateChanged);
        jpaApi.em().persist(ticket);
        } else
        {
            return redirect(routes.TicketController.getCustomerTicket());
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
                //do nothing
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
                //do nothing
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
                // do nothing
            }
            newFileDetails.setExtension(filePart3.getContentType());
            newFileDetails.setTicketId(ticket.getTicketsId());
            jpaApi.em().persist(newFileDetails);
        }
        String adminSql = "SELECT sa FROM SiteAdmin sa ";
        List<SiteAdmin> siteAdmins = jpaApi.em()
                .createQuery(adminSql, SiteAdmin.class).getResultList();
        for (int i = 0; i < siteAdmins.size() - 1; i++)
        {
            if (ticket.getSiteAdminId() == siteAdmins.get(i).getSiteAdminId())
            {
                //make a real url that consists of ticket number
                String url = "localhost:9000/tickets/" + ticket.getTicketsId();
                String email = siteAdmins.get(i).getEmailAddress();

                Email.sendTicketEmail("A new ticket has been assigned to you. You can view this ticket " +
                        "by the following link, copy and paste to browser " + url, email);
            } else
            {
                //do nothing
            }

        }
        return redirect(routes.HomeController.index());
    }

}
