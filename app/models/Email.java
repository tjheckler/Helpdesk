package models;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;

import java.security.SecureRandom;
import java.util.Random;


public class Email
{
    public static void sendPasswordEmail(String contents,String destinationEmail)
    {
        String sender = "timothy_heckler@outlook.com"; //Change to your actual email address for your help desk
        String subject = "Reset Password Link" ;
        try
        {
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder
                    .standard()
                    .withRegion(Regions.US_WEST_2)
                    //.withRegion(Regions.US_EAST_2)
                    .build();

            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(destinationEmail))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(contents)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(subject)))
                    .withSource(sender);

            client.sendEmail(request);


        }catch (Exception e)
        {
            System.out.println("Unable to send email "+ e.getMessage());
        }
    }
    public static void sendTicketEmail(String contents,String destinationEmail)
    {
        String sender = "timothy_heckler@outlook.com"; //Change to your actual email address for your help desk
        String subject = "New Ticket" ;
        try
        {
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder
                    .standard()
                    .withRegion(Regions.US_WEST_2)
                    //.withRegion(Regions.US_EAST_2)
                    .build();

            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(destinationEmail))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(contents)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(subject)))
                    .withSource(sender);

            client.sendEmail(request);


        }catch (Exception e)
        {
            System.out.println("Unable to send email "+ e.getMessage());
        }
    }
    public static void sendCustomerEmail(String contents,String destinationEmail)
    {
        String sender = "timothy_heckler@outlook.com"; //Change to your actual email address for your help desk
        String subject = "Ticket Update Notice" ;
        try
        {
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder
                    .standard()
                    .withRegion(Regions.US_WEST_2)
                    //.withRegion(Regions.US_EAST_2)
                    .build();

            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(destinationEmail))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(contents)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(subject)))
                    .withSource(sender);

            client.sendEmail(request);


        }catch (Exception e)
        {
            System.out.println("Unable to send email "+ e.getMessage());
        }
    }

    public static void sendUpdateEmail(String contents,String destinationEmail)
    {
        String sender = "timothy_heckler@outlook.com"; //Change to your actual email address for your help desk
        String subject = "Ticket Update Notice" ;
        try
        {
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder
                    .standard()
                    .withRegion(Regions.US_WEST_2)
                    //.withRegion(Regions.US_EAST_2)
                    .build();

            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(destinationEmail))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(contents)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(subject)))
                    .withSource(sender);

            client.sendEmail(request);


        }catch (Exception e)
        {
            System.out.println("Unable to send email "+ e.getMessage());
        }
    }

    //Random Password Generator when password is requested by Email
    private static final Random RANDOM = new SecureRandom();
    public static String generateRandomPassword() {
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ0123456789!@#$%^&*()_-+";

        String pw = "";
        for (int i = 0; i < 8; i++) {
            int index = (int) (RANDOM.nextDouble() * letters.length());
            pw += letters.substring(index, index + 1);
        }
        return pw;
    }
}
