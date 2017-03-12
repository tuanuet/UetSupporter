package vnu.uet.tuan.uetsupporter.Broascast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import vnu.uet.tuan.uetsupporter.Activities.MainActivity;
import vnu.uet.tuan.uetsupporter.Activities.Result2Activity;
import vnu.uet.tuan.uetsupporter.Activities.ResultActivity;
import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.PushNotificationSQLHelper;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by Vu Minh Tuan on 2/14/2017.
 */

public class BroadcastNotification extends BroadcastReceiver {
    NotificationManager mNotificationManager;
    PushNotificationSQLHelper db;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(Config.IDNOTICATION);
        PushNotification notification = (PushNotification) intent.getSerializableExtra(Config.KEY_PUSHNOTIFICATION);
        int id = intent.getIntExtra(Config.POSITION_NOTIFICATION, 0);
        if (notification != null) {
            db = new PushNotificationSQLHelper(context);
            if (action.equals(Config.ACTION_CHITIET)) {
                db.updateReadByPosition(id, true);
                Intent i = new Intent(context, Result2Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Config.KEY_PUSHNOTIFICATION, notification);
                context.startActivity(i);
            } else if (action.equals(Config.ACTION_DAXEM)) {
                //set da xem true
                db.updateReadByPosition(id, false);
            } else if (action.equals(Config.ACTION_XEMSAU)) {
                //set da xem false
                db.updateReadByPosition(id, false);
            }
        } else {
            Toast.makeText(context, "Null", Toast.LENGTH_SHORT).show();
        }

    }
}
