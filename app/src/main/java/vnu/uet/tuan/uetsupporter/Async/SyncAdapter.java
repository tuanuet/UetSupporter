package vnu.uet.tuan.uetsupporter.Async;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.Model.Mail.MailUet;
import vnu.uet.tuan.uetsupporter.Model.Response.DataSync;
import vnu.uet.tuan.uetsupporter.Presenter.Fetching.IPresenterFetchingModel;
import vnu.uet.tuan.uetsupporter.Presenter.Fetching.PresenterFetchingModel;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.EmailSQLHelper;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.PushNotificationSQLHelper;
import vnu.uet.tuan.uetsupporter.TemplateNotification.InboxMessageNotification;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by vmtuan on 3/2/2017.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private final static String TAG = "SyncAdapter";
    // Interval at which to sync with the weather, in milliseconds.
    // 60 seconds (1 minute)  180 = 3 minutes
    public static final long SYNC_INTERVAL = 60 * 2;
    public static final long SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    private boolean isFirstTime = true;
    private EmailSQLHelper emailSQLHelper;
    private PushNotificationSQLHelper pushNotificationSQLHelper;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        Log.e(TAG, "onPerformSync Called.");
        emailSQLHelper = new EmailSQLHelper(getContext());
        pushNotificationSQLHelper = new PushNotificationSQLHelper(getContext());
        //run to get 10 email/
        MailUet sent = MailUet.getInstance(
//                Utils.getEmailUser(getActivity()),
//                Utils.getPassword(getActivity())
                "14020521", "1391996"
        ).readEmails(Config.MailBox.Inbox.toString());

        //sync email
        int countNew = sent.getNewMessageCount();
        new ExecueEmail().execute(countNew);

        //sync notification and new
        syncNotification();
    }

    private void syncNotification() {
        IPresenterFetchingModel present = new PresenterFetchingModel(getContext());
        String[] kindAnnouncements = Utils.getKindAnnouncements(getContext());
        String lastTimeAnnoun = Utils.getLastTimeAnnouncement(getContext());
        String[] kindNews = Utils.getKindNews(getContext());
        String lastTimeNew = Utils.getLastTimeNew(getContext());
        present.fetching(lastTimeAnnoun, kindAnnouncements, lastTimeNew, kindNews, new IPresenterFetchingModel.OnFinishedListener() {
            @Override
            public void OnSuccess(DataSync dataSync) {
                String[] clientAnnouncements = pushNotificationSQLHelper.getServerId();
                for (String str :
                        clientAnnouncements) {
                    Log.e(TAG,str);
                }
            }

            @Override
            public void OnFailure(String failure) {
                Log.e(TAG,failure);
            }
        });
    }

    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));
        Log.e(TAG, "getSyncAccount");
        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, long syncInterval, long flexTime) {
        Account account = getSyncAccount(context);
        Log.e(TAG, "configurePeriodicSync");
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }

    }


    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        SyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    private class ExecueEmail extends AsyncTask<Integer, Void, ArrayList<Email>> {

        @Override
        protected ArrayList<Email> doInBackground(Integer... params) {
            MailUet sent = MailUet.getInstance(
//                Utils.getEmailUser(getActivity()),
//                Utils.getPassword(getActivity())
                    "14020521", "1391996"
            ).readEmails(Config.MailBox.Inbox.toString());

            try {
                return sent.getMessage(0, params[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }

        //đẻ lại 10 cái email
        @Override
        protected void onPostExecute(ArrayList<Email> list) {
            super.onPostExecute(list);
            //update UI
            if (list != null && list.size() != 0) {
                ArrayList<Email> idInsert = emailSQLHelper.insertBulkGetArrayId(list);
                if (isFirstTime) {
                    //lan dau tien
                } else {

                }

                //=================
                isFirstTime = false;
                if (idInsert.size() != 0) {
                    //khong phai lan dau va khong phai la lan khong check duoc email nao
                    if (idInsert.size() > 1) {
                        InboxMessageNotification.notify(getContext(), "Bạn có thư mới", idInsert.size());
                    } else {
                        InboxMessageNotification.notify(getContext(), idInsert.get(0).getTitle(), 0);
                    }
                } else {
                    //khong insert dc email nao
                }

                //===============
                if (emailSQLHelper.getAll().getCount() > 15) {
                    emailSQLHelper.deleteEmailOffset(15);
                }
            }
        }
    }

}
