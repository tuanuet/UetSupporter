package vnu.uet.tuan.uetsupporter.Model.Mail;

/**
 * Created by mtv on 3/1/2017.
 */


import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

public class MailUet {

    private static MailUet instance;
    private static Folder inbox = null;

    private static String user = "";
    private static String pass = "";

    private String typeFolder = "";
    private List<File> attachments = new ArrayList<File>();

    private String pathFile = "./tmp/";// chua duong dan chua file


    //singlenton
    public static MailUet getInstance(String user, String pass) {
        MailUet.user = user.trim();
        MailUet.pass = pass.trim();
        if (instance == null) {
            instance = new MailUet();
        }
        return instance;
    }

    private MailUet() {
    }

    public MailUet readEmails(String mailBox) {
        // Create all the needed properties - empty!
        Properties connectionProperties = new Properties();
        // Create the session
        Session session = Session.getDefaultInstance(connectionProperties, null);

        try {
            Log.e("MAIL", "Connecting to the IMAP server...");
            // Connecting to the server
            // Set the store depending on the parameter flag value
            String storeName = "imap";
            Store store = session.getStore(storeName);

            // Set the server depending on the parameter flag value
            String server = "ctmail.vnu.edu.vn";
            store.connect(server, user, pass);

            Log.e("MAIL", "done!");

            // Get the Inbox folder
            inbox = store.getFolder(mailBox);

            // Set the mode to the read-only mode
            inbox.open(Folder.READ_ONLY);
            this.typeFolder = mailBox;
            return this;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public ArrayList<Email> getMessage() {
        try {
            Message messages[] = inbox.getMessages();
            messages = (Message[]) MailUet.reverse(messages);
            ArrayList<Email> emails = this.covertIntoEmail(messages);
//            inbox.close(true);
            return emails;
        } catch (MessagingException m) {
            m.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Email> getMessage(int start, int stop) {
        try {
            Message messages[] = inbox.getMessages(inbox.getMessageCount() - stop, inbox.getMessageCount() - start);
            messages = (Message[]) MailUet.reverse(messages);
            ArrayList<Email> emails = this.covertIntoEmail(messages);
//            inbox.close(true);
            return emails;
        } catch (MessagingException m) {
            m.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Email getMessage(int i) {
        try {
            Message message = inbox.getMessage(i);
            Message[] messages = {message};
            ArrayList<Email> emails = this.covertIntoEmailWithFile(messages);
            return emails.get(0);
        } catch (MessagingException m) {
            m.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Email> getMessageUnRead() {
        try {
            FlagTerm flagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message[] messages = inbox.search(flagTerm);
            messages = (Message[]) MailUet.reverse(messages);
            ArrayList<Email> emails = this.covertIntoEmail(messages);
            inbox.close(true);
            return emails;
        } catch (MessagingException m) {
            m.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Boolean deleteEmail(int i) {
        try {
            Message message = inbox.getMessage(i);
            message.setFlag(Flags.Flag.DELETED, true);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean setRead(int i) {
        try {
            Message message = inbox.getMessage(i);
            message.setFlag(Flags.Flag.SEEN, true);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ArrayList<Email> covertIntoEmail(Message[] messages) {
        ArrayList<Email> emails = new ArrayList<Email>();
        try {
            for (Message message : messages) {
                Email email = new Email();
                //position
                email.setPosition(message.getMessageNumber());
                Log.e(TAG, "Postion: " + email.getPosition());
                //folder
                email.setFolder(typeFolder);
                // From
                for (Address a : message.getFrom()) {
                    //=?UTF-8?Q?Ph=c3=b2ng_CTSV?= <ctsv_dhcn@vnu.edu.vn>
                    String temp = a.toString();
                    if (temp.contains("<")) {
                        temp = temp.substring(temp.indexOf("<") + 1, temp.length() - 1);
                    }
                    email.setFrom(temp);
                }
                //send date
                email.setSendDate(message.getSentDate().toString());
                //Recipient
                if (message.getAllRecipients() != null)
                    for (Address a : message.getAllRecipients())
                        email.getRecipient().add(a.toString());
                //receive date
                email.setReceiveDate(message.getReceivedDate().toString());
                // Importance
                if (message.getHeader("Importance") != null)
                    for (String st : message.getHeader("Importance"))
                        email.setImportance(st.toString());
                //setISREAD
                email.setRead(message.getFlags().contains(Flags.Flag.SEEN));
                //Title
                email.setTitle(message.getSubject().toString());
                //content
                email = setFileExistAndGetContent(email, message);
//                email= getDetailEmail(email,message);
                emails.add(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emails;
    }

    private ArrayList<Email> covertIntoEmailWithFile(Message[] messages) {
        ArrayList<Email> emails = new ArrayList<Email>();
        try {
            for (Message message : messages) {
                Email email = new Email();
                //folder
                email.setFolder(typeFolder);
                //position
                email.setPosition(message.getMessageNumber());
                Log.e(TAG, "Postion: " + email.getPosition());
                // From
                for (Address a : message.getFrom()) {
                    //=?UTF-8?Q?Ph=c3=b2ng_CTSV?= <ctsv_dhcn@vnu.edu.vn>
                    String temp = a.toString();
                    if (temp.contains("<")) {
                        temp = temp.substring(temp.indexOf("<") + 1, temp.length() - 1);
                    }
                    email.setFrom(temp);
                }
                //send date
                email.setSendDate(message.getSentDate().toString());
                //Recipient
                if (message.getAllRecipients() != null)
                    for (Address a : message.getAllRecipients())
                        email.getRecipient().add(a.toString());
                //receive date
                email.setReceiveDate(message.getReceivedDate().toString());
                // Importance
                if (message.getHeader("Importance") != null)
                    for (String st : message.getHeader("Importance"))
                        email.setImportance(st.toString());
                //setISREAD
                email.setRead(message.getFlags().contains(Flags.Flag.SEEN));
                //Title
                email.setTitle(message.getSubject().toString());
                //content
//                email = setFileExistAndGetContent(email, message);
                email = getDetailEmail(email, message);
                emails.add(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emails;
    }

    // get text of content message
    private Email getDetailEmail(Email email, Part p) throws
            MessagingException, IOException {
        //check type mixed: mixed include alternative, html, text, file
        if (p.isMimeType("multipart/mixed")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart bp = mp.getBodyPart(i);
                if (Part.ATTACHMENT.equalsIgnoreCase(bp.getDisposition())) {
                    String fileName = bp.getFileName();
                    if (fileName.indexOf("utf-8?B") != -1) {
                        fileName = processFileName(fileName);
                    }
                    email.setHasFile(true);
                    InputStream is = bp.getInputStream();
                    email.addDataFile(fileName, is);
                } else if (bp.isMimeType("multipart/alternative"))
                    email = ProcessAlternative(email, bp);
                else if (bp.isMimeType("text/plain")) {
                    String s = processText(bp);
                    if (s != null) {
                        email.setContent(s);
                    }
                } else if (bp.isMimeType("text/html")) {
                    String s = processText(bp);
                    if (s != null) {
                        email.setHtml(s);
                    }
                }
            }
            return email;
        } else if (p.isMimeType("multipart/alternative")) {
            email = ProcessAlternative(email, p);
            return email;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                email = getDetailEmail(email, mp.getBodyPart(i));
            }
            return email;
        }
        email.setContent(processText(p));
        return email;
    }


    // get text of content message
    private Email setFileExistAndGetContent(Email email, Part p) throws
            MessagingException, IOException {
        //check type mixed: mixed include alternative, html, text, file
        if (p.isMimeType("multipart/mixed")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart bp = mp.getBodyPart(i);
                if (bp.isMimeType("multipart/alternative"))
                    email = ProcessAlternative(email, bp);
                else if (bp.isMimeType("text/plain")) {
                    String s = processText(bp);
                    if (s != null) {
                        email.setContent(s);
                    }
                } else if (bp.isMimeType("text/html")) {
                    String s = processText(bp);
                    if (s != null) {
                        email.setHtml(s);
                    }
                }
                if (Part.ATTACHMENT.equalsIgnoreCase(bp.getDisposition())) {
                    //set hasFile
                    email.setHasFile(true);
//                    String fileName = bp.getFileName();
//                    if (fileName.indexOf("utf-8?B") != -1) {
//                        fileName = processFileName(fileName);
//                    }
//                    InputStream is = bp.getInputStream();
//                    File f = new File(pathFile + fileName);
//                    email.getPathFile().add(pathFile + fileName);
//                    FileOutputStream fos = new FileOutputStream(f);
//                    byte[] buf = new byte[10000];
//                    int bytesRead;
//                    while ((bytesRead = is.read(buf)) != -1) {
//                        fos.write(buf, 0, bytesRead);
//                    }
//                    fos.close();
//                    attachments.add(f);
                }
            }
            return email;
        } else if (p.isMimeType("multipart/alternative")) {
            email = ProcessAlternative(email, p);
            return email;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                email = setFileExistAndGetContent(email, mp.getBodyPart(i));
            }
            return email;
        }
        email.setContent(processText(p));
        return email;
    }

    // Alternative include text/plain , text/html , image
    private Email ProcessAlternative(Email email, Part p) throws MessagingException, IOException {
        Multipart mp = (Multipart) p.getContent();
        for (int i = 0; i < mp.getCount(); i++) {
            Part bp = mp.getBodyPart(i);
            if (bp.isMimeType("multipart/alternative"))
                email = ProcessAlternative(email, bp);
            else if (bp.isMimeType("text/plain")) {
                String s = processText(bp);
                if (s != null) ;
                email.setContent(s);
            } else if (bp.isMimeType("text/html")) {
                String s = processText(bp);
                if (s != null)
                    email.setHtml(s);
            }
        }
        return email;
    }

    // get content text (text/*)
    private String processText(Part p) throws
            MessagingException, IOException {
        String s = (String) p.getContent();

        return s;
    }

    private final String TAG = this.getClass().getName();

    // reverse array message
    private static Object[] reverse(Object[] arr) {
        List<Object> list = Arrays.asList(arr);
        Collections.reverse(list);
        return list.toArray();
    }

    // process fileName base64 to Utf-8
    private String processFileName(String fileName) throws UnsupportedEncodingException {
        String result = "";
        int position = 0;
        for (String retval : fileName.split("=\\?utf-8\\?B\\?")) {
            retval = retval.replaceAll("==\\?=", "").replaceAll("=\\?=", "").replaceAll(" ", "").trim();
            if (position > 0) {
                byte[] asBytes = Base64.decode(retval, Base64.URL_SAFE);
                retval = new String(asBytes, "utf-8");
            }
            position++;
            result += retval;
        }
        return result;
    }
}