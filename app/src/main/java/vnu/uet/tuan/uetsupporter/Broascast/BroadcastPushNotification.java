package vnu.uet.tuan.uetsupporter.Broascast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import me.leolin.shortcutbadger.ShortcutBadger;
import vnu.uet.tuan.uetsupporter.Activities.Result2Activity;
import vnu.uet.tuan.uetsupporter.Activities.ResultActivity;
import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;
import vnu.uet.tuan.uetsupporter.Model.NewNotification;
import vnu.uet.tuan.uetsupporter.Model.interfaces.INotification;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.PushNotificationSQLHelper;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by Vu Minh Tuan on 2/14/2017.
 */

public class BroadcastPushNotification extends BroadcastReceiver {
    private final String TAG = this.getClass().getSimpleName();
    NotificationManager mNotificationManager;
    PushNotificationSQLHelper db;

    @Override
    public void onReceive(Context context, Intent activityIntent) {
        String action = activityIntent.getAction();
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(Config.IDNOTICATION);
        INotification iNotification = (INotification) activityIntent.getSerializableExtra(Config.KEY_PUSHNOTIFICATION);
        if (iNotification != null) {
            db = new PushNotificationSQLHelper(context);

            if (action.equals(Config.ACTION_CHITIET)) {

                Intent intent = null;
                if( iNotification.getClass() == AnnouncementNotification.class ) {
                    /**
                     * Announcement notification
                     */
                    int id = activityIntent.getIntExtra(Config.POSITION_NOTIFICATION, 0);
                    intent = new Intent(context, Result2Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    db.updateReadByPosition(id, true);
                    intent.putExtra(Config.KEY_PUSHNOTIFICATION, (AnnouncementNotification) iNotification );
                } else if (iNotification.getClass() == NewNotification.class) {
                    /**
                     * New notification
                     */
                    intent = new Intent(context, ResultActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.e(TAG,((NewNotification) iNotification).getLink());
                    intent.putExtra(Config.KEY_URL, ((NewNotification) iNotification).getLink() );
                } else {
                    /**
                     * false
                     */
                    intent = new Intent();
                }

                context.startActivity(intent);
            } else if (action.equals(Config.ACTION_DAXEM)) {
                //set da xem true
                if( iNotification.getClass() == AnnouncementNotification.class ) {
                    int id = activityIntent.getIntExtra(Config.POSITION_NOTIFICATION, 0);
                    db.updateReadByPosition(id, true);
                } else if (iNotification.getClass() == NewNotification.class) {

                }

            } else if (action.equals(Config.ACTION_XEMSAU)) {
                //set da xem false
                if( iNotification.getClass() == AnnouncementNotification.class ) {
                    int id = activityIntent.getIntExtra(Config.POSITION_NOTIFICATION, 0);
                    db.updateReadByPosition(id, false);
                } else if (iNotification.getClass() == NewNotification.class) {

                }

            }
        } else {
            Toast.makeText(context, "Null", Toast.LENGTH_SHORT).show();
        }

        // send to Fragment
//        EventBus.getDefault().post(notification);

        //setBage
        initShortcutBadger(context);

    }

    private void initShortcutBadger(Context context) {
        int badgeCount = Utils.getNumberOnNav(context);
        Log.e(TAG,"Is support badge: "+ShortcutBadger.isBadgeCounterSupported(context));
        ShortcutBadger.applyCount(context, badgeCount); //for 1.1.4+

    }
}
