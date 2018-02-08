package vnu.uet.tuan.uetsupporter.TemplateNotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import vnu.uet.tuan.uetsupporter.Broascast.BroadcastPushNotification;
import vnu.uet.tuan.uetsupporter.Model.NewNotification;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by FRAMGIA\vu.minh.tuan on 08/02/2018.
 */

public class NewMessageNotification {
    public static void notify(Context context, NewNotification notification) {
        Uri sound = Utils.getSoundNotification(context);

        long when = System.currentTimeMillis();

        Bitmap theBitmap = null;
        try {
            theBitmap = Glide.
                    with(context).
                    load(notification.getImageLink()).
                    asBitmap().
                    into(100, 100). // Width and height
                    get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle(notification.getTitle())
                        .setLargeIcon(theBitmap)
                        .setSound(sound)
                        .setWhen(when)
                        .setAutoCancel(true)
                        .setContentText(notification.getContent());

        mBuilder.addAction(R.drawable.ic_notifications_black_24dp,
                context.getString(R.string.chitiet),
                getPendingIntent(context, Config.ACTION_CHITIET, notification));

        mBuilder.addAction(R.drawable.ic_history_black_24dp,
                context.getString(R.string.xemsau),
                getPendingIntent(context, Config.ACTION_XEMSAU, notification));


        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        mNotificationManager.notify(m, mBuilder.build());
    }

    protected static PendingIntent getPendingIntent(Context context, String action, NewNotification notification) {
        Intent intent = new Intent(context,
                BroadcastPushNotification.class);

        intent.putExtra(Config.KEY_PUSHNOTIFICATION, notification);
        intent.setAction(action);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
