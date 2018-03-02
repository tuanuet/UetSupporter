package vnu.uet.tuan.uetsupporter.Model.Mail;

/**
 * Created by FRAMGIA\vu.minh.tuan on 02/03/2018.
 */

import android.util.Log;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Date;
import java.util.Properties;

public class MailSender extends javax.mail.Authenticator {
    private final String TAG = this.getClass().getSimpleName();
    private String mailhost = "ctmail.vnu.edu.vn";
    private String user;
    private String password;
    private Session session;

    static {
        Security.addProvider(new JSSEProvider());
    }

    public MailSender(String user, String password) {
        this.user = user;
        this.password = password;

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.socketFactory.port", "25");

//        props.put("mail.smtp.socketFactory.class",
//                "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.quitwait", "false");

//        props.put("mail.smtp.host", mailhost);
//        props.put("mail.smtp.port", 25);
//        props.put("mail.smtp.auth", true);

        session = Session.getInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized boolean sendEmail(String to, String from, String cc, String bcc, String subject,String bodyText) {
        try {
            InternetAddress[] myToList = InternetAddress.parse(to);
            InternetAddress[] myBccList = InternetAddress.parse(bcc);
            InternetAddress[] myCcList = InternetAddress.parse(cc);

            MimeMessage message = new MimeMessage(session);


            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,myToList);
            message.addRecipients(Message.RecipientType.BCC,myBccList);
            message.addRecipients(Message.RecipientType.CC,myCcList);

            // Set Subject: header field
            message.setSubject(subject);
            message.setSentDate(new Date());
            // set plain text message
            message.setContent(bodyText, "text/html");

            Transport.send(message);
            return true;
        } catch (Exception ex) {
            Log.e(TAG,ex.getMessage());
            return false;
        }


    }

    public synchronized boolean sendEmail(String to, String from, String cc, String bcc, String subject, String bodyText, String pathFile) {
        try {
            InternetAddress[] myToList = InternetAddress.parse(to);
            InternetAddress[] myBccList = InternetAddress.parse(bcc);
            InternetAddress[] myCcList = InternetAddress.parse(cc);

            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,myToList);
            message.addRecipients(Message.RecipientType.BCC,myBccList);
            message.addRecipients(Message.RecipientType.CC,myCcList);

            // Set Subject: header field
            message.setSubject(subject);

            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Fill the message
            messageBodyPart.setText(bodyText);
            String filename = pathFile;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);
            Transport.send(message);
            return true;
        } catch (Exception ex) {
            Log.e(TAG,ex.getMessage());
            return false;
        }
    }

    public synchronized boolean sendMail(String subject, String body, String sender, String recipients) {
        try{
            MimeMessage message = new MimeMessage(session);
            DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
            message.setSender(new InternetAddress(sender));
            message.setSubject(subject);
            message.setDataHandler(handler);
            if (recipients.indexOf(',') > 0)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
            else
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
            Transport.send(message);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}