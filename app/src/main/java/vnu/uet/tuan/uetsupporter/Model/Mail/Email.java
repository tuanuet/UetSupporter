package vnu.uet.tuan.uetsupporter.Model.Mail;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.BodyPart;

import retrofit2.http.Body;

/**
 * Created by mtv on 3/1/2017.
 */

public class Email implements Serializable {
    private Integer position;
    private String from;
    private String sendDate;
    private ArrayList<String> recipient;
    private Date receiveDate;
    private String importance;
    private String title;
    private String content;
    private String html;
    private boolean hasFile;
    private boolean isRead;
    private ArrayList<BodyPart> files;
    private String folder;

    private String pathFile = "./tmp/";// chua duong dan chua file

    public Email(Integer position, String from, String sendDate, ArrayList<String> recipient,
                 Date receiveDate, String importance, String title, String content,
                 String html,ArrayList<BodyPart> files, boolean hasFile, boolean isRead, String folder) {
        this.position = position;
        this.from = from;
        this.sendDate = sendDate;
        this.recipient = recipient;
        this.receiveDate = receiveDate;
        this.importance = importance;
        this.title = title;
        this.content = content;
        this.html = html;
        this.files = files;
        this.hasFile = hasFile;
        this.isRead = isRead;
        this.folder = folder;
    }

    public Email() {// cho viec nhan email
        this.position = 0;
        this.from = "";
        this.sendDate = "";
        this.recipient = new ArrayList<String>();
        this.receiveDate = new Date();
        this.importance = "";
        this.title = "";
        this.content = "";
        this.html = "";
        this.files = new ArrayList<BodyPart>();
        this.hasFile = false;
        this.isRead = false;
        this.folder = "";
    }


    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
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

    public void setReceiveDate(Date receiveDate) {
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


    public String getFrom() {

        return from;
    }

    public String getSendDate() {
        return sendDate;
    }

    public ArrayList<String> getRecipient() {
        return recipient;
    }

    public Date getReceiveDate() {
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

    public ArrayList<BodyPart> getFile() {
        return files;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public void setFile(ArrayList<BodyPart> files) {
        this.files = files;
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