package vnu.uet.tuan.uetsupporter.Firebase;


import android.app.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import vnu.uet.tuan.uetsupporter.Broascast.BroadcastNotification;
import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.PushNotificationSQLHelper;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;


/**
 * Created by Vu Minh Tuan on 12/01/2017.
 */

public class MyFirebaseMessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map data = remoteMessage.getData();
        PushNotification pushNotification = getPushNotification(data);

        setupNoti(pushNotification);
        //lưu vao database

    }

    private PushNotification getPushNotification(Map data) {
        PushNotification push = new PushNotification();
        push.setKind(Integer.parseInt(String.valueOf(data.get(PushNotification.KIND))));
        push.setLink((String) data.get(PushNotification.LINK));
        push.setNoiDung((String) data.get(PushNotification.NOIDUNG));
        push.setRead(false);
        push.setThoiGianNhan(Utils.getThoiGian(System.currentTimeMillis()));
        push.setTieuDe((String) data.get(PushNotification.TIEUDE));
        return push;
    }

    protected PendingIntent getPendingIntent(String action, PushNotification notification) {
        Intent intent = new Intent(MyFirebaseMessageService.this,
                BroadcastNotification.class);

        intent.putExtra(Config.KEY_PUSHNOTIFICATION, notification);
        intent.setAction(action);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getBroadcast(MyFirebaseMessageService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    private void setupNoti(PushNotification notification) {
        Uri sound = Utils.getSoundNotification(getApplicationContext());

        long when = System.currentTimeMillis();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle(notification.getTieuDe())
                        .setSound(sound)
                        .setWhen(when)
                        .setAutoCancel(true)
                        .setOngoing(true)
                        .setContentText(notification.getNoiDung());

//        // Creates an explicit intent for an Activity in your app
//        Intent resultIntent = new Intent(this, ResultActivity.class);
//
//        // The stack builder object will contain an artificial back stack for the
//        // started Activity.
//        // This ensures that navigating backward from the Activity leads out of
//        // your application to the Home screen.
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        // Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(ResultActivity.class);
//        // Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_IMMUTABLE
//                );
//        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.addAction(R.drawable.ic_notifications_black_24dp,
                getString(R.string.chitiet),
                getPendingIntent(Config.ACTION_CHITIET, notification));

        mBuilder.addAction(R.drawable.ic_history_black_24dp,
                getString(R.string.xemsau),
                getPendingIntent(Config.ACTION_XEMSAU, notification));
        mBuilder.addAction(R.drawable.ic_check_circle_black_24dp,
                getString(R.string.daxem),
                getPendingIntent(Config.ACTION_DAXEM, notification));


        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(Config.IDNOTICATION, mBuilder.build());
    }

    //    private PendingIntent setPendingIntent(int id) {
//
//        switch (id){
//            case R.id.action_xemchitiet :{
//                return getPendingIntent(Config.ACTION_CHITIET);
//            }
//            case R.id.action_xemsau:{
//                return getPendingIntent(Config.ACTION_XEMSAU);
//            }
//            case R.id.action_daxem :{
//                return getPendingIntent(Config.ACTION_DAXEM);
//            }
////            case R.id.action_close :{
////                return getPendingIntent(Config.ACTION_CLOSE);
////            }
//        }
//        return null;
//    }
//
    //    private PushNotification getPushNotification(Map data) {
//        int kind = Integer.parseInt(String.valueOf(data.get(PushNotification.KIND)));
//        switch (kind) {
//            case 1: {   //PushNotificaionThongBao
//                PushNotificationThongBao thongBao = new PushNotificationThongBao();
//                thongBao.setTieuDe((String) data.get(PushNotification.TIEUDE));
//                thongBao.setKind(Integer.parseInt((String) data.get(PushNotification.KIND)));
//                thongBao.setNoiDung((String) data.get(PushNotificationThongBao.NOIDUNG));
//                thongBao.setLinkFile((String) data.get(PushNotificationThongBao.LINKFILE));
//                thongBao.setIdloaiThongBaos(Integer.parseInt((String) data.get(PushNotificationThongBao.IDLOAITHONGBAO)));
//                thongBao.setMucDoThongBao((String) data.get(PushNotificationThongBao.MUCDOTHONGBAO));
//                return thongBao;
//            }
////            case 2: {   //PushNotificaionDiem
////                PushNotificationDiem diem = new PushNotificationDiem();
////                diem.setTieuDe((String) data.get(PushNotification.TIEUDE));
////                diem.setKind(Integer.parseInt((String) data.get(PushNotification.KIND)));
////                diem.setDiemCuoiKi(Integer.parseInt((String) data.get(PushNotificationDiem.DIEMCUOIKI)));
////                diem.setDiemThanhPhan(Integer.parseInt((String) data.get(PushNotificationDiem.DIEMTHANHPHAN)));
////                diem.setMSV(Integer.parseInt((String) data.get(PushNotificationDiem.MSV)));
////                diem.setMonHoc((String) data.get(PushNotificationDiem.TENMONHOC));
////                diem.setTenLopMonHoc((String) data.get(PushNotificationDiem.TENLOPMONHOC));
////                diem.setTenGiangVien((String) data.get(PushNotificationDiem.TENGIANVIEN));
////                return diem;
////            }
//            case 3: {   //PushNotificaionTinTuc
//
//                break;
//            }
//        }
//        return null;
//    }
}
