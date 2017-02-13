package vnu.uet.tuan.uetsupporter.Firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import vnu.uet.tuan.uetsupporter.Activities.ResultActivity;
import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.Model.PushNotificationDiem;
import vnu.uet.tuan.uetsupporter.Model.PushNotificationThongBao;
import vnu.uet.tuan.uetsupporter.Model.PushNotificationTinTuc;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by Vu Minh Tuan on 12/01/2017.
 */

public class MyFirebaseMessageService extends FirebaseMessagingService {

    private Notification mNotification;
    private NotificationManager mNotifiManager;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map data = remoteMessage.getData();
        PushNotification pushNotification = getPushNotification(data);

        if (pushNotification instanceof PushNotificationThongBao) {

            PushNotificationThongBao thongBao = (PushNotificationThongBao) pushNotification;
            setupNoti(thongBao.getTieuDe(), thongBao.getMucDoThongBao());

        } else if (pushNotification instanceof PushNotificationDiem) {
            PushNotificationDiem diem = (PushNotificationDiem) pushNotification;
            setupNoti(diem.getTieuDe(), diem.getTenLopMonHoc());

        } else if (pushNotification instanceof PushNotificationTinTuc) {

        }

    }

    private PushNotification getPushNotification(Map data) {
        int kind = Integer.parseInt(String.valueOf(data.get(PushNotification.KIND)));
        switch (kind) {
            case 1: {   //PushNotificaionThongBao
                PushNotificationThongBao thongBao = new PushNotificationThongBao();
                thongBao.setTieuDe((String) data.get(PushNotification.TIEUDE));
                thongBao.setKind(Integer.parseInt((String) data.get(PushNotification.KIND)));
                thongBao.setNoiDung((String) data.get(PushNotificationThongBao.NOIDUNG));
                thongBao.setLinkFile((String) data.get(PushNotificationThongBao.LINKFILE));
                thongBao.setIdloaiThongBaos(Integer.parseInt((String) data.get(PushNotificationThongBao.IDLOAITHONGBAO)));
                thongBao.setMucDoThongBao((String) data.get(PushNotificationThongBao.MUCDOTHONGBAO));
                return thongBao;
            }
            case 2: {   //PushNotificaionDiem
                PushNotificationDiem diem = new PushNotificationDiem();
                diem.setTieuDe((String) data.get(PushNotification.TIEUDE));
                diem.setKind(Integer.parseInt((String) data.get(PushNotification.KIND)));
                diem.setDiemCuoiKi(Integer.parseInt((String) data.get(PushNotificationDiem.DIEMCUOIKI)));
                diem.setDiemThanhPhan(Integer.parseInt((String) data.get(PushNotificationDiem.DIEMTHANHPHAN)));
                diem.setMSV(Integer.parseInt((String) data.get(PushNotificationDiem.MSV)));
                diem.setMonHoc((String) data.get(PushNotificationDiem.TENMONHOC));
                diem.setTenLopMonHoc((String) data.get(PushNotificationDiem.TENLOPMONHOC));
                diem.setTenGiangVien((String) data.get(PushNotificationDiem.TENGIANVIEN));
                return diem;
            }
            case 3: {   //PushNotificaionTinTuc

                break;
            }
        }
        return null;
    }

    private void setupAndPushNotification(PushNotification pushNotification) {

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        mNotification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(sound)
                .setOngoing(true).build();

        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification_thongbao);

//        remoteView.setTextViewText();
//        remoteView.setTextViewText();
//        remoteView.setTextViewText();
//        remoteView.setImageViewResource();
//
//        remoteView.setOnClickPendingIntent();
//        remoteView.setOnClickPendingIntent();
//        remoteView.setOnClickPendingIntent();

        mNotification.bigContentView = remoteView;
        mNotifiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifiManager.notify(Config.IDNOTICATION, mNotification);
    }


    private PendingIntent setPendingIntent() {

        return null;
    }
    private void setupNoti(String title, String auth) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.btn_plus)
                        .setContentTitle(title)
                        .setContentText(auth);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, ResultActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ResultActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_IMMUTABLE
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(Config.IDNOTICATION, mBuilder.build());
    }
}
