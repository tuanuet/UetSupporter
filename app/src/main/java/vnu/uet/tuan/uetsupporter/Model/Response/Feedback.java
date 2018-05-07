package vnu.uet.tuan.uetsupporter.Model.Response;

import android.util.Log;

import org.joda.time.DateTime;

import java.util.Map;

import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;
import vnu.uet.tuan.uetsupporter.Model.Student;
import vnu.uet.tuan.uetsupporter.Utils.Utils;

/**
 * Created by FRAMGIA\vu.minh.tuan on 19/03/2018.
 */

public class Feedback {
    public static final String _ID = "_id";
    public static final String CREATEDAT = "createdAt";
    public static final String ANNOUNCEMENTID = "announcementId";
    public static final String CONTENT = "content";
    public static final String SUBFEEDBACK = "subFeedback";
    public static final String SENDER = "sender";
    public static final String KINDSENDER = "kindSender";
    private static final String TAG = Feedback.class.getSimpleName();

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

    public static Feedback fromMap(Map data){
        try {
            Feedback feedback = new Feedback();
            feedback.set_id((String) data.get(Feedback._ID));
            feedback.setAnnouncementId((String) data.get(Feedback.ANNOUNCEMENTID));
            feedback.setContent((String) data.get(Feedback.CONTENT));
            feedback.setKindSender((String) data.get(Feedback.KINDSENDER));
            feedback.setSubFeedback((String) data.get(Feedback.SUBFEEDBACK));
            feedback.setCreatedAt((String) data.get(Feedback.CREATEDAT));
            Sender sender = Sender.fromJSONString((String)data.get(Feedback.SENDER));
            feedback.setSender(sender);
            return feedback;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public String toString() {
        return this._id+"===="+this.subFeedback;
    }
}
