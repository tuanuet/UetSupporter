package vnu.uet.tuan.uetsupporter.TemplateNotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.security.acl.LastOwnerException;

import vnu.uet.tuan.uetsupporter.Activities.FeedBackActivity;

import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;
import vnu.uet.tuan.uetsupporter.Model.Response.Feedback;

import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.PushNotificationSQLHelper;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by FRAMGIA\vu.minh.tuan on 24/04/2018.
 */

public class FeedbackMessageNotification {
    private static final String TAG = FeedbackMessageNotification.class.getSimpleName();

    public static PendingIntent getDismissIntent(int notificationId, Context context) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(notificationId);
        PendingIntent dismissIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return dismissIntent;
    }

    public static void notify(Context context, Feedback feedback) {
        Uri sound = Utils.getSoundNotification(context);
        long when = System.currentTimeMillis();
        int NOTIFICATION_ID = Utils.randomInt(0,1,10000);
        PendingIntent notifyPIntent =
                PendingIntent.getActivity(context, 0, new Intent(), 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle(feedback.getSender().getName())
                        .setSound(sound)
                        .setWhen(when)
                        .setAutoCancel(true)
                        .setContentIntent(notifyPIntent)
                        .setContentText(feedback.getContent());

        PushNotificationSQLHelper helper = new PushNotificationSQLHelper(context);
        AnnouncementNotification announcementNotification = helper.getAnnouncementById(feedback.getAnnouncementId());
        if (announcementNotification == null) {
            return;
        }

        mBuilder.addAction(R.drawable.ic_notifications_black_24dp,
                context.getString(R.string.chitiet),
                getPendingIntent(context, Config.ACTION_CHITIET, announcementNotification));

        mBuilder.addAction(R.drawable.ic_history_black_24dp,
                context.getString(R.string.xemsau),
                getDismissIntent(NOTIFICATION_ID,context));
//        mBuilder.addAction(R.drawable.ic_check_circle_black_24dp,
//                context.getString(R.string.daxem),
//                getPendingIntent(context, Config.ACTION_DAXEM, notification, position));


        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    protected static PendingIntent getPendingIntent(Context context, String action, AnnouncementNotification notification) {
        Intent intent = new Intent(context,
                FeedBackActivity.class);

        intent.putExtra(Config.KEY_PUSHNOTIFICATION, notification);
        intent.setAction(action);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
