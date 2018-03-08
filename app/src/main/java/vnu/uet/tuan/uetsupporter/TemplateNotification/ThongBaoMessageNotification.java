package vnu.uet.tuan.uetsupporter.TemplateNotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import vnu.uet.tuan.uetsupporter.Broascast.BroadcastPushNotification;
import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by Vu Minh Tuan on 2/24/2017.
 */

public class ThongBaoMessageNotification {

    public static void notify(Context context, AnnouncementNotification notification, int position) {
        Uri sound = Utils.getSoundNotification(context);

        long when = System.currentTimeMillis();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle(notification.getTieuDe())
                        .setSound(sound)
                        .setWhen(when)
                        .setAutoCancel(true)
                        .setOngoing(true)
                        .setContentText(notification.getDescription());

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
                context.getString(R.string.chitiet),
                getPendingIntent(context, Config.ACTION_CHITIET, notification, position));

        mBuilder.addAction(R.drawable.ic_history_black_24dp,
                context.getString(R.string.xemsau),
                getPendingIntent(context, Config.ACTION_XEMSAU, notification, position));
//        mBuilder.addAction(R.drawable.ic_check_circle_black_24dp,
//                context.getString(R.string.daxem),
//                getPendingIntent(context, Config.ACTION_DAXEM, notification, position));


        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(Config.IDNOTICATION, mBuilder.build());
    }

    protected static PendingIntent getPendingIntent(Context context, String action, AnnouncementNotification notification, int position) {
        Intent intent = new Intent(context,
                BroadcastPushNotification.class);

        intent.putExtra(Config.KEY_PUSHNOTIFICATION, notification);
        intent.putExtra(Config.POSITION_NOTIFICATION, position);
        intent.setAction(action);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
