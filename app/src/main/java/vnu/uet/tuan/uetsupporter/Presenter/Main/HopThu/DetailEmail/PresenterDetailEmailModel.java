package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.DetailEmail;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Listener.OnCancelRequest;
import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.Model.Mail.MailUet;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.MainEmail.IPresenterMainEmailModel;
import vnu.uet.tuan.uetsupporter.R;

/**
 * Created by vmtuan on 3/26/2017.
 */

public class PresenterDetailEmailModel implements IPresenterDetailEmailModel {
    private OnLoadDetailEmailFinish loadFinish;
    private Context context;
    private String folder;
    private int postion;
    private ExecuteEmail mTask;

    public PresenterDetailEmailModel(Context context) {
        this.context = context;

    }

    @Override
    public void excuteLoadEmail(String folder, int position, OnLoadDetailEmailFinish listener) {
        this.folder = folder;
        this.postion = position;
        this.loadFinish = listener;


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

    class ExecuteEmail extends AsyncTask<String, Void, Email> {
        private String messageFailure;

        @Override
        protected Email doInBackground(String... params) {
            try {
                return MailUet.getInstance(
//                Utils.getEmailUser(getActivity()),
//                Utils.getPassword(getActivity())
                        "14020521", "1391996"
                ).readEmails(folder).getMessage(postion);

            } catch (Exception e) {
                e.printStackTrace();
                this.messageFailure = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Email email) {
            super.onPostExecute(email);
            if (email != null) {
                loadFinish.OnLoadEmailSuccess(email);
            } else {
                loadFinish.OnLoadEmailFailure(this.messageFailure);
            }
        }
    }
}
