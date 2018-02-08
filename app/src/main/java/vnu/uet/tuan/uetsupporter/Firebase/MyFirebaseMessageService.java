package vnu.uet.tuan.uetsupporter.Firebase;



import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import vnu.uet.tuan.uetsupporter.Model.Download.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.Model.NewNotification;
import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;

import vnu.uet.tuan.uetsupporter.SQLiteHelper.PushNotificationSQLHelper;
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
        Integer kindNotification = Integer.parseInt((String) data.get("typeNotification"));

        switch (kindNotification) {
            case 1 : {
                AnnouncementNotification announcementNotification = getPushNotification(data);
                //lưu vao database
                PushNotificationSQLHelper db = new PushNotificationSQLHelper(getApplicationContext());
                int pos = db.insertOne(announcementNotification);

                ThongBaoMessageNotification.notify(getApplicationContext(), announcementNotification, pos);
                break;
            }
            case 2 : {
                NewNotification newNotification = getNewNotification(data);
                NewMessageNotification.notify(getApplicationContext(),newNotification);
                break;
            }
        }



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private NewNotification getNewNotification(Map data) {
        NewNotification push = new NewNotification();
        push.setTitle((String) data.get(NewNotification.TITLE));
        push.setKind(Integer.parseInt((String) data.get(NewNotification.KIND)));
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
//        Log.e(TAG, "getNewNotification: "+ tags.get(0).getKind());
        push.setTags(tags.toArray(new LoaiTinTuc[tags.size()]));
        push.setTypeNotification(Integer.parseInt((String) data.get(NewNotification.TYPENOTIFICATION)));
        return push;
    }

    private AnnouncementNotification getPushNotification(Map data) {

        AnnouncementNotification push = new AnnouncementNotification();
        push.setKind(getIntFromString(String.valueOf(data.get(AnnouncementNotification.KIND))));
        push.setLink((String) data.get(AnnouncementNotification.LINK));
        push.setNoiDung((String) data.get(AnnouncementNotification.NOIDUNG));
        push.setRead(false);
        push.setThoiGianNhan(Utils.getThoiGian(System.currentTimeMillis()));
        push.setTieuDe((String) data.get(AnnouncementNotification.TIEUDE));
        push.setHasFile((getIntFromString(String.valueOf(data.get(AnnouncementNotification.HASFILE))) == 1)); //fix lai
        push.setIdLoaiThongBao(String.valueOf(data.get(AnnouncementNotification.IDLOAITHONGBAO)));
        push.setIdMucDoThongBao(String.valueOf(data.get(AnnouncementNotification.IDMUCDOTHONGBAO)));
        push.setIdSender(String.valueOf(data.get(AnnouncementNotification.IDSENDER)));
        push.setNameSender(String.valueOf(data.get(AnnouncementNotification.NAMESENDER)));
        return push;
    }

    private Integer getIntFromString(String integer) {
        return Integer.parseInt(integer);
    }
}
