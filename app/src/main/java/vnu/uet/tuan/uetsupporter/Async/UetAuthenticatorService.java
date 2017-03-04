package vnu.uet.tuan.uetsupporter.Async;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by vmtuan on 3/2/2017.
 */

public class UetAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private UetAuthenticatior mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new UetAuthenticatior(this);

    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}