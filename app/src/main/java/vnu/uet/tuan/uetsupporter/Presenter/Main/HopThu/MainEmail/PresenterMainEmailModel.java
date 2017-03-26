package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.MainEmail;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Listener.OnCancelRequest;

import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.Model.Mail.MailUet;
import vnu.uet.tuan.uetsupporter.R;


/**
 * Created by vmtuan on 3/25/2017.
 */

public class PresenterMainEmailModel implements IPresenterMainEmailModel {
    private Context context;
    private OnLoadEmailFinish loadEmailFinish;
    private int epr = 10;
    private String folder;
    private int pager;
    private ExecuteEmail mTask;

    public PresenterMainEmailModel(Context context) {
        this.context = context;

    }

    @Override
    public void excuteLoadEmail(final String folder, final int pager, OnLoadEmailFinish listener) {
        this.loadEmailFinish = listener;
        this.folder = folder;
        this.pager = pager;

        // load email
        this.mTask = new ExecuteEmail();
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                mTask.execute();
            }
        });
    }

    @Override
    public void cancelLoadingEmail(OnCancelRequest listener) {
        try {
            mTask.cancel(true);
            if (mTask.isCancelled()) {
                listener.OnCancelSuccess(context.getString(R.string.cancel_success));
            } else {
                listener.OnCancelSuccess(context.getString(R.string.cancel_failure));
            }
        } catch (Exception e) {
            listener.OnCancelSuccess(context.getString(R.string.cancel_failure));
        }
    }

    class ExecuteEmail extends AsyncTask<Void, Void, ArrayList<Email>> {
        private String messageFailure;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ArrayList<Email> doInBackground(Void... params) {
            try {
                return MailUet.getInstance(
//                Utils.getEmailUser(getActivity()),
//                Utils.getPassword(getActivity())
                        "14020521", "1391996"
                ).readEmails(folder)
                        .getMessage(pager * epr, pager * epr + epr);

            } catch (Exception e) {
                e.printStackTrace();
                this.messageFailure = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Email> emails) {
            super.onPostExecute(emails);
            if (emails != null) {
                loadEmailFinish.OnLoadEmailSuccess(emails);
            } else {
                loadEmailFinish.OnLoadEmailFailure(this.messageFailure);
            }
        }
    }
}
