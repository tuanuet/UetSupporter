package vnu.uet.tuan.uetsupporter.Model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 13/01/2017.
 */

public class Notification implements Parcelable{
    private String title;
    private String message;
    private String author;
    private String link;
    private int icon;
    private int typeNotification;

    public Notification() {
    }

    public Notification(String title, String message, String author, String link, int icon, int typeNotification) {
        this.title = title;
        this.message = message;
        this.author = author;
        this.link = link;
        this.icon = icon;
        this.typeNotification = typeNotification;
    }

    protected Notification(Parcel in) {
        title = in.readString();
        message = in.readString();
        author = in.readString();
        link = in.readString();
        icon = in.readInt();
        typeNotification = in.readInt();
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(int typeNotification) {
        this.typeNotification = typeNotification;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(author);
        dest.writeString(link);
        dest.writeInt(icon);
        dest.writeInt(typeNotification);
    }
}
