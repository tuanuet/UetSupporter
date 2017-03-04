package vnu.uet.tuan.uetsupporter.Model.Mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;


/**
 * Created by mtv on 3/1/2017.
 */

public class MailSender {
    private enum Protocol {
        SMTP,
        SMTPS,
        TLS
    }

    private String user = "";
    private String pass = "";

    public MailSender(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public void sendEmail(Email email) {
        ArrayList<String> to = email.getRecipient();
        String title = email.getTitle();
        String content = email.getContent();
        String path = email.getFile().get(0);
        int port = 25;
        String host = "ctmail.vnu.edu.vn";
        String from = user + "@vnu.edu.vn";
        boolean auth = true;
        final String username = user;
        final String password = pass;
        MailSender.Protocol protocol = MailSender.Protocol.SMTP;// default SMTP
        boolean debug = true;

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        switch (protocol) {
            case SMTPS:
                props.put("mail.smtp.ssl.enable", true);
                break;
            case TLS:
                props.put("mail.smtp.starttls.enable", true);
                break;
        }

        Authenticator authenticator = null;
        if (auth) {
            //authentication
            props.put("mail.smtp.auth", true);
            authenticator = new Authenticator() {
                private PasswordAuthentication pa = new PasswordAuthentication(username, password);

                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return pa;
                }
            };
        }

        Session session = Session.getInstance(props, authenticator);
        session.setDebug(debug);

        MimeMessage message = new MimeMessage(session);
        try {
            //set header
            message.setFrom(new InternetAddress(from));
            ArrayList<InternetAddress> listAddress = new ArrayList<InternetAddress>();
            for (int i = 0; i < to.size(); i++) {
                String recipient = to.get(i);
                try {
                    listAddress.add(new InternetAddress(recipient));
                } catch (AddressException e) {
                    e.printStackTrace();
                }
            }
            InternetAddress[] address = new InternetAddress[listAddress.size()];
            address = listAddress.toArray(address);
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(title);
            message.setSentDate(new Date());
            if (path != null) {
                // import multi body
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(content);
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(path);
                messageBodyPart.setDataHandler(new DataHandler(source));
                String[] split = path.split("/");
                String fileName = split[split.length - 1];
                messageBodyPart.setFileName(fileName);
                multipart.addBodyPart(messageBodyPart);
                //set multi body to content
                message.setContent(multipart);
            } else message.setText(content);

            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}