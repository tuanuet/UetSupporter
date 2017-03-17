package vnu.uet.tuan.uetsupporter.Firebase;



import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import vnu.uet.tuan.uetsupporter.Model.PushNotification;

import vnu.uet.tuan.uetsupporter.SQLiteHelper.PushNotificationSQLHelper;
import vnu.uet.tuan.uetsupporter.TemplateNotification.ThongBaoMessageNotification;
import vnu.uet.tuan.uetsupporter.Utils.Utils;



/**
 * Created by Vu Minh Tuan on 12/01/2017.
 */

public class MyFirebaseMessageService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map data = remoteMessage.getData();
        PushNotification pushNotification = getPushNotification(data);

        //lưu vao database
        PushNotificationSQLHelper db = new PushNotificationSQLHelper(getApplicationContext());
        int pos = db.insertOne(pushNotification);

        ThongBaoMessageNotification.notify(getApplicationContext(), pushNotification, pos);

    }

    private PushNotification getPushNotification(Map data) {
        PushNotification push = new PushNotification();
        push.setKind(getIntFromString(String.valueOf(data.get(PushNotification.KIND))));
        push.setLink((String) data.get(PushNotification.LINK));
        push.setNoiDung((String) data.get(PushNotification.NOIDUNG));
        push.setRead(false);
        push.setThoiGianNhan(Utils.getThoiGian(System.currentTimeMillis()));
        push.setTieuDe((String) data.get(PushNotification.TIEUDE));
        push.setHasFile((getIntFromString(String.valueOf(data.get(PushNotification.HASFILE))) == 1)); //fix lai
        push.setIdLoaiThongBao(getIntFromString(String.valueOf(data.get(PushNotification.IDLOAITHONGBAO))));
        push.setIdMucDoThongBao(getIntFromString(String.valueOf(data.get(PushNotification.IDMUCDOTHONGBAO))));
        return push;
    }

    private Integer getIntFromString(String integer) {
        return Integer.parseInt(integer);
    }
}
