package vnu.uet.tuan.uetsupporter.Async;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


/**
 * Created by vmtuan on 3/2/2017.
 */

public class EmailSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static EmailSyncAdapter sEmailSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("SunshineSyncService", "onCreate - SunshineSyncService");
        synchronized (sSyncAdapterLock) {
            if (sEmailSyncAdapter == null) {
                sEmailSyncAdapter = new EmailSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sEmailSyncAdapter.getSyncAdapterBinder();
    }
}
