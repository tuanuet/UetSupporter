package vnu.uet.tuan.uetsupporter.Model.Response;

import vnu.uet.tuan.uetsupporter.Model.Student;

/**
 * Created by FRAMGIA\vu.minh.tuan on 19/03/2018.
 */

public class Feedback {
    private String _id;
    private String createdAt;
    private String announcementId;
    private String kindSender;
    private String content;
    private Sender sender;
    private String subFeedback;

    public Feedback() {
    }

    public String getSubFeedback() {
        return subFeedback;
    }

    public void setSubFeedback(String subFeedback) {
        this.subFeedback = subFeedback;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

    public String getKindSender() {
        return kindSender;
    }

    public void setKindSender(String kindSender) {
        this.kindSender = kindSender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return this._id+"===="+this.subFeedback;
    }
}
