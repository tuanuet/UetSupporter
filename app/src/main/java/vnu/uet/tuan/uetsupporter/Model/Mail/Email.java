package vnu.uet.tuan.uetsupporter.Model.Mail;

import java.util.ArrayList;

/**
 * Created by mtv on 3/1/2017.
 */

public class Email {
    private String from;
    private String sendDate;
    private ArrayList<String> recipient;
    private String receiveDate;
    private String importance;
    private String title;
    private String content;
    private String html;
    private boolean hasFile;
    private boolean isRead;
    private ArrayList<String> pathFile;

    public Email(String from, String sendDate, ArrayList<String> recipient,
                 String receiveDate, String importance, String title, String content,
                 String html, ArrayList<String> pathFile, boolean hasFile, boolean isRead) {
        this.from = from;
        this.sendDate = sendDate;
        this.recipient = recipient;
        this.receiveDate = receiveDate;
        this.importance = importance;
        this.title = title;
        this.content = content;
        this.html = html;
        this.pathFile = pathFile;
        this.hasFile = hasFile;
        this.isRead = isRead;
    }

    public Email(ArrayList<String> recipient, String title, String content, String pathFile) {// dung cho viec gui email
        this.from = "me";
        this.sendDate = "";
        this.recipient = recipient;
        this.receiveDate = "";
        this.importance = "";
        this.title = title;
        this.content = content;
        this.html = "";
        this.pathFile = new ArrayList<String>();
        this.pathFile.add(pathFile);
    }

    public Email() {// cho viec nhan email
        this.from = "";
        this.sendDate = "";
        this.recipient = new ArrayList<String>();
        this.receiveDate = "";
        this.importance = "";
        this.title = "";
        this.content = "";
        this.html = "";
        this.pathFile = new ArrayList<String>();
        this.hasFile = false;
        this.isRead = false;
    }

    public boolean isHasFile() {
        return hasFile;
    }

    public void setHasFile(boolean hasFile) {
        this.hasFile = hasFile;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public void setRecipient(ArrayList<String> recipient) {
        this.recipient = recipient;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = this.content + "\n" + content;
    }

    public void setHtml(String html) {
        this.html = this.html + "\n" + html;
    }

    public void setPathFile(ArrayList<String> pathFile) {
        this.pathFile = pathFile;
    }

    public String getFrom() {

        return from;
    }

    public String getSendDate() {
        return sendDate;
    }

    public ArrayList<String> getRecipient() {
        return recipient;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public String getImportance() {
        return importance;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getHtml() {
        return html;
    }

    public ArrayList<String> getPathFile() {
        return pathFile;
    }

    @Override
    public String toString() {
        return "Email{" +
                "\nfrom= " + from + '\n' +
                ", sendDate= " + sendDate + '\n' +
                ", recipient= " + recipient + '\n' +
                ", receiveDate= " + receiveDate + '\n' +
                ", importance= " + importance + '\n' +
                ", title= " + title + '\n' +
                ", content= " + content + '\n' +
                ", html= " + html + '\n' +
                ", pathFile= " + pathFile +
                '}';
    }
}