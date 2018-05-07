package vnu.uet.tuan.uetsupporter.Firebase;



import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import vnu.uet.tuan.uetsupporter.Model.Download.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.Model.NewNotification;
import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;

import vnu.uet.tuan.uetsupporter.Model.Response.Feedback;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.PushNotificationSQLHelper;
import vnu.uet.tuan.uetsupporter.TemplateNotification.FeedbackMessageNotification;
import vnu.uet.tuan.uetsupporter.TemplateNotification.NewMessageNotification;
import vnu.uet.tuan.uetsupporter.TemplateNotification.ThongBaoMessageNotification;
import vnu.uet.tuan.uetsupporter.Utils.Utils;



/**
 * Created by Vu Minh Tuan on 12/01/2017.
 */

public class MyFirebaseMessageService extends FirebaseMessagingService {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map data = remoteMessage.getData();

//        export const KIND_ANNOUNCEMENT_NOTIFICATION = 1;
//        export const KIND_NEW_NOTIFICATION = 2;
//        export const KIND_MARK_NOTIFICATION = 3;
//        export const KIND_FEEDBACK_NOTIFICATION = 4;
        Log.d(TAG,data.toString());

        Integer kindNotification = Integer.parseInt((String) data.get(AnnouncementNotification.TYPENOTIFICATION));
        //lưu vao database
        PushNotificationSQLHelper db = new PushNotificationSQLHelper(getApplicationContext());
        switch (kindNotification) {
            case 1 : {
                AnnouncementNotification announcementNotification = getPushNotification(data);
                int pos = db.insertOne(announcementNotification);
                ThongBaoMessageNotification.notify(getApplicationContext(), announcementNotification, pos);
                break;
            }
            case 2 : {
                NewNotification newNotification = getNewNotification(data);
                NewMessageNotification.notify(getApplicationContext(),newNotification);
                break;
            }
            case 3 : {
                AnnouncementNotification announcementNotification = getMarkNotification(data);
                int pos = db.insertOne(announcementNotification);
                ThongBaoMessageNotification.notify(getApplicationContext(), announcementNotification, pos);
                break;
            }
            case 4: {
                Feedback feedback = Feedback.fromMap(data);
                EventBus.getDefault().post(feedback);
                FeedbackMessageNotification.notify(getApplicationContext(),feedback);
                break;
            }
        }



    }

    private AnnouncementNotification getMarkNotification(Map data) {
        AnnouncementNotification push = new AnnouncementNotification();
        push.set_id((String) data.get(AnnouncementNotification._ID));
        push.setLink((String) data.get(AnnouncementNotification.LINK));
        push.setNoiDung("");
        push.setRead(false);
        push.setCodeMucDoThongBao("canh_bao");
        push.setThoiGianNhan(new DateTime().toString());
        push.setTieuDe((String) data.get(AnnouncementNotification.TIEUDE));
        push.setHasFile(false); //fix lai
        push.setIdLoaiThongBao("");
        push.setIdMucDoThongBao("");
        push.setIdSender(String.valueOf(data.get(AnnouncementNotification.IDSENDER)));
        push.setTypeNotification(getIntFromString(String.valueOf(data.get(AnnouncementNotification.TYPENOTIFICATION))));
        push.setNameSender(String.valueOf(data.get(AnnouncementNotification.NAMESENDER)));
        push.setDescription((String) data.get(AnnouncementNotification.NOIDUNG));
        push.setDescriptionImages(Utils.convertStringToArrayFromServer(""));
        return push;

    }

    private NewNotification getNewNotification(Map data) {
        NewNotification push = new NewNotification();
        push.setTitle((String) data.get(NewNotification.TITLE));
        push.setLink((String) data.get(NewNotification.LINK));
        push.set_id((String) data.get(NewNotification._ID));
        push.setContent((String) data.get(NewNotification.CONTENT));
        push.setImageLink((String) data.get(NewNotification.IMAGELINK));
        push.setPostAt((String) data.get(NewNotification.POSTAT));
        ArrayList<LoaiTinTuc> tags = new ArrayList<LoaiTinTuc>();
        try {
            JSONArray jsonTags = new JSONArray(String.valueOf(data.get(NewNotification.TAGS)));
            for (int i=0; i<jsonTags.length();i++) {
                JSONObject jsonTag = jsonTags.getJSONObject(i);
                String _id = jsonTag.getString(NewNotification._ID);
                String name = jsonTag.getString("name");
                LoaiTinTuc tag = new LoaiTinTuc(_id,name);
                tags.add(tag);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        push.setTags(tags.toArray(new LoaiTinTuc[tags.size()]));
        push.setTypeNotification(Integer.parseInt((String) data.get(NewNotification.TYPENOTIFICATION)));
        return push;
    }

    private AnnouncementNotification getPushNotification(Map data) {

        AnnouncementNotification push = new AnnouncementNotification();
        push.set_id((String) data.get(AnnouncementNotification._ID));
        push.setLink((String) data.get(AnnouncementNotification.LINK));
        push.setNoiDung((String) data.get(AnnouncementNotification.NOIDUNG));
        push.setRead(false);
        push.setDescription((String) data.get(AnnouncementNotification.DESCRIPTION));
        push.setCodeMucDoThongBao((String) data.get(AnnouncementNotification.CODEKINDANNOUNCE));
        push.setThoiGianNhan(new DateTime().toString());
        push.setTieuDe((String) data.get(AnnouncementNotification.TIEUDE));
        push.setHasFile((getIntFromString(String.valueOf(data.get(AnnouncementNotification.HASFILE))) == 1)); //fix lai
        push.setIdLoaiThongBao(String.valueOf(data.get(AnnouncementNotification.IDLOAITHONGBAO)));
        push.setIdMucDoThongBao(String.valueOf(data.get(AnnouncementNotification.IDMUCDOTHONGBAO)));
        push.setIdSender(String.valueOf(data.get(AnnouncementNotification.IDSENDER)));
        push.setTypeNotification(getIntFromString(String.valueOf(data.get(AnnouncementNotification.TYPENOTIFICATION))));
        push.setNameSender(String.valueOf(data.get(AnnouncementNotification.NAMESENDER)));
        push.setDescriptionImages(Utils.convertStringToArrayFromServer((String) data.get(AnnouncementNotification.DESCRIPTION_IMAGES)));
        return push;
    }

    private Integer getIntFromString(String integer) {
        return Integer.parseInt(integer);
    }
}
