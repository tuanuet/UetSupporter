package vnu.uet.tuan.uetsupporter.Model;

import java.io.Serializable;
import java.util.Date;

import vnu.uet.tuan.uetsupporter.Model.Download.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.Model.interfaces.INotification;

/**
 * Created by FRAMGIA\vu.minh.tuan on 08/02/2018.
 */

public class NewNotification implements Serializable,INotification{
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String IMAGELINK = "imageLink";
    public static final String LINK = "link";
    public static final String TAGS = "tags";
    public static final String POSTAT = "postAt";
    public static final String KIND = "kind";
    public static final String TYPENOTIFICATION = "typeNotification";

    private String _id;
    private String title;
    private String content;
    private String imageLink;
    private String link;
    private LoaiTinTuc[] tags;
    private String postAt;
    private int kind;
    private int typeNotification;

    public NewNotification() {
        this._id = "";
        this.title = "";
        this.content = "";
        this.imageLink = "";
        this.link = "";
        this.tags = new LoaiTinTuc[100];
        this.postAt = "";
        this.kind = 1;
        this.typeNotification = 1;
    }

    public NewNotification(String _id, String title, String content, String imageLink, String link, LoaiTinTuc[] tags, String postAt, int kind, int typeNotification) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.imageLink = imageLink;
        this.link = link;
        this.tags = tags;
        this.postAt = postAt;
        this.kind = kind;
        this.typeNotification = typeNotification;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LoaiTinTuc[] getTags() {
        return tags;
    }

    public void setTags(LoaiTinTuc[] tags) {
        this.tags = tags;
    }

    public String getPostAt() {
        return postAt;
    }

    public void setPostAt(String postAt) {
        this.postAt = postAt;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(int typeNotification) {
        this.typeNotification = typeNotification;
    }
}
