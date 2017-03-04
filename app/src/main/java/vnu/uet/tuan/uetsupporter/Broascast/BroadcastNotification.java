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
        if (notification != null) {
            db = new PushNotificationSQLHelper(context);
            if (action.equals(Config.ACTION_CHITIET)) {
                notification.setRead(true);
                int pos = db.insertOne(notification);
                Log.e("db", "Save: " + pos);
                Intent i = new Intent(context, Result2Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Config.KEY_PUSHNOTIFICATION, notification);
                context.startActivity(i);
            } else if (action.equals(Config.ACTION_DAXEM)) {
                notification.setRead(true);
                int pos = db.insertOne(notification);
                Log.e("db", "Save: " + pos);
            } else if (action.equals(Config.ACTION_XEMSAU)) {
                notification.setRead(false);
                int pos = db.insertOne(notification);
                Log.e("db", "Save: " + pos);
            }
        } else {
            Toast.makeText(context, "Null", Toast.LENGTH_SHORT).show();
        }

    }
}
