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

/**
 * Created by mtv on 3/1/2017.
 */

public class Email implements Parcelable, Serializable {
    private Integer position;
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
    private ArrayList<String> file;
    private ArrayList<InputStream> dataFiles;
    private String folder;

    private String pathFile = "./tmp/";// chua duong dan chua file

    public Email(Integer position, String from, String sendDate, ArrayList<String> recipient,
                 String receiveDate, String importance, String title, String content,
                 String html, ArrayList<String> file, boolean hasFile, boolean isRead, String folder) {
        this.position = position;
        this.from = from;
        this.sendDate = sendDate;
        this.recipient = recipient;
        this.receiveDate = receiveDate;
        this.importance = importance;
        this.title = title;
        this.content = content;
        this.html = html;
        this.file = file;
        this.hasFile = hasFile;
        this.isRead = isRead;
        this.folder = folder;
    }

    public Email(ArrayList<String> recipient, String title, String content, String path) {// dung cho viec gui email
        this.from = "me";
        this.sendDate = "";
        this.recipient = recipient;
        this.receiveDate = "";
        this.importance = "";
        this.title = title;
        this.content = content;
        this.html = "";
        this.dataFiles = new ArrayList<>();
        this.file = new ArrayList<>();
        if (path != null) {
            String[] split = path.split("/");
            String fileName = split[split.length - 1];

            try {
                File f = new File(path);
                if (f.exists() && !f.isDirectory()) {
                    InputStream is = new FileInputStream(f);
                    addDataFile(fileName, is);
                    readFile(fileName);
                } else this.file.add(null);
            } catch (Exception e) {
                this.file.add(null);
            }
        } else this.file.add(null);
    }

    public Email() {// cho viec nhan email
        this.position = 0;
        this.from = "";
        this.sendDate = "";
        this.recipient = new ArrayList<String>();
        this.receiveDate = "";
        this.importance = "";
        this.title = "";
        this.content = "";
        this.html = "";
        this.file = new ArrayList<String>();
        this.hasFile = false;
        this.isRead = false;
        this.folder = "";
    }


    protected Email(Parcel in) {
        from = in.readString();
        sendDate = in.readString();
        recipient = in.createStringArrayList();
        receiveDate = in.readString();
        importance = in.readString();
        title = in.readString();
        content = in.readString();
        html = in.readString();
        hasFile = in.readByte() != 0;
        isRead = in.readByte() != 0;
        file = in.createStringArrayList();
        pathFile = in.readString();
        folder = in.readString();
    }

    public static final Creator<Email> CREATOR = new Creator<Email>() {
        @Override
        public Email createFromParcel(Parcel in) {
            return new Email(in);
        }

        @Override
        public Email[] newArray(int size) {
            return new Email[size];
        }
    };

    public void addDataFile(String fileName, InputStream is) {
        try {
            file.add(fileName);
            dataFiles.add(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File readFile(String fileName) {
        try {
            int index = file.indexOf(fileName);
            if (index != -1) {
                InputStream is = dataFiles.get(index);
                File f = new File(pathFile + fileName);
                if (!f.exists() && !f.isDirectory()) {
                    FileOutputStream fos = new FileOutputStream(f);
                    byte[] buf = new byte[10000];
                    int bytesRead;
                    while ((bytesRead = is.read(buf)) != -1) {
                        fos.write(buf, 0, bytesRead);
                    }
                    fos.close();
                    return f;
                } else return f;
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    public ArrayList<String> getFile() {
        return file;
    }

    public Email(Integer position, String from, String sendDate, ArrayList<String> recipient,
                 String receiveDate, String importance, String title, String content,
                 String html, boolean hasFile, boolean isRead, ArrayList<String> file,
                 ArrayList<InputStream> dataFiles, String pathFile) {
        this.position = position;
        this.from = from;
        this.sendDate = sendDate;
        this.recipient = recipient;
        this.receiveDate = receiveDate;
        this.importance = importance;
        this.title = title;
        this.content = content;
        this.html = html;
        this.hasFile = hasFile;
        this.isRead = isRead;
        this.file = file;
        this.dataFiles = dataFiles;
        this.pathFile = pathFile;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Email(Integer position, String from, String sendDate, ArrayList<String> recipient, String receiveDate, String importance, String title, String content, String html, boolean hasFile, boolean isRead, ArrayList<String> file, ArrayList<InputStream> dataFiles, String folder, String pathFile) {
        this.position = position;
        this.from = from;
        this.sendDate = sendDate;
        this.recipient = recipient;
        this.receiveDate = receiveDate;
        this.importance = importance;
        this.title = title;
        this.content = content;

        this.html = html;
        this.hasFile = hasFile;
        this.isRead = isRead;
        this.file = file;
        this.dataFiles = dataFiles;
        this.folder = folder;
        this.pathFile = pathFile;
    }

    public void setFile(ArrayList<String> file) {
        this.file = file;
    }

    public ArrayList<InputStream> getDataFiles() {
        return dataFiles;
    }

    public void setDataFiles(ArrayList<InputStream> dataFiles) {
        this.dataFiles = dataFiles;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(sendDate);
        dest.writeStringList(recipient);
        dest.writeString(receiveDate);
        dest.writeString(importance);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(html);
        dest.writeByte((byte) (hasFile ? 1 : 0));
        dest.writeByte((byte) (isRead ? 1 : 0));
        dest.writeStringList(file);
        dest.writeString(folder);
    }
}