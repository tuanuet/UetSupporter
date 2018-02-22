package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.SendEmail;

import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import javax.mail.MessagingException;

import vnu.uet.tuan.uetsupporter.Model.Mail.MailUet;

/**
 * Created by FRAMGIA\vu.minh.tuan on 22/02/2018.
 */

public class PresenterSendEmailModel implements IPresenterSendEmailModel {
    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private MailUet mailer;
    public PresenterSendEmailModel(Context context) {
        this.context = context;
    }
    private OnLoadEmailFinish listener;

    @Override
    public void excuteSendEmail(final String to, final String from, final String cc, final String bcc, final String subject, final String body, OnLoadEmailFinish listener) {
        mailer = MailUet.getInstance("14020521","1391996");
        this.listener = listener;
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                new TaskSendMail().execute(to,from,cc,bcc,subject,body);
            }
        });
    }
    @Override
    public void excuteSendEmail(final String to, final String from, final String cc, final String bcc, final String subject, final String body, final String path, OnLoadEmailFinish listener) {
        mailer = MailUet.getInstance("14020521","1391996");
        this.listener = listener;
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                new TaskSendMailAttach().execute(to,from,cc,bcc,subject,body,path);
            }
        });
    }
    private class TaskSendMail extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            MailUet mailer = MailUet.getInstance("14020521","1391996");
            try {
                mailer.sendEmail(strings[0],strings[1],strings[2],strings[3],strings[4],strings[5]);
                return true;
            } catch (MessagingException e) {
                e.printStackTrace();
                Log.e(TAG,e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if(success)
                listener.OnSendEmailSuccess();
            else {
                listener.OnSendEmailFailure("Just failure.");
            }
        }
    }

    private class TaskSendMailAttach extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            MailUet mailer = MailUet.getInstance("14020521","1391996");
            try {
                mailer.sendEmail(strings[0],strings[1],strings[2],strings[3],strings[4],strings[5],strings[6]);
                return true;
            } catch (MessagingException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            if(success)
                listener.OnSendEmailSuccess();
            else {
                listener.OnSendEmailFailure("Just failure.");
            }
        }
    }
}
