package vnu.uet.tuan.uetsupporter.Model.Response;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.util.Comparator;

/**
 * Created by Administrator on 19/01/2017.
 */

public class TinTuc implements Parcelable {
    private String _id;
    private String title;
    private String imageLink;
    private String link;
    private String content;
    private String[] tags;
    private String postAt;

    public TinTuc() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getPostAt() {
        return postAt;
    }

    public void setPostAt(String postAt) {
        this.postAt = postAt;
    }

    public TinTuc(String _id, String title, String imageLink, String link, String content, String[] tags, String postAt) {
        this._id = _id;
        this.title = title;
        this.imageLink = imageLink;
        this.link = link;
        this.content = content;
        this.tags = tags;
        this.postAt = postAt;
    }

    protected TinTuc(Parcel in) {
        _id = in.readString();
        title = in.readString();
        imageLink = in.readString();
        link = in.readString();
        content = in.readString();
        postAt = in.readString();
    }

    public static final Creator<TinTuc> CREATOR = new Creator<TinTuc>() {
        @Override
        public TinTuc createFromParcel(Parcel in) {
            return new TinTuc(in);
        }

        @Override
        public TinTuc[] newArray(int size) {
            return new TinTuc[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(title);
        dest.writeString(imageLink);
        dest.writeString(link);
        dest.writeString(content);
        dest.writeString(postAt);
    }
}
