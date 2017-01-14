package vnu.uet.tuan.uetsupporter.Firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import vnu.uet.tuan.uetsupporter.ResultActivity;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by Administrator on 12/01/2017.
 */

public class MyFirebaseMessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //create notyfication

        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        String auth = remoteMessage.getFrom();
//        String tag = remoteMessage.getNotification().getTag();

        //khoi tao noti
        setupNoti(title,auth);
        //
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
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(Config.IDNOTICATION, mBuilder.build());
    }

    public String getContentNoti(String title, String tag){
        return tag + " " + title;
    }
}
