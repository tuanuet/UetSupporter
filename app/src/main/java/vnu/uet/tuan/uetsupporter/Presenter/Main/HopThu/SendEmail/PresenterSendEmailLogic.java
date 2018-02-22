package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.SendEmail;

import android.content.Context;

import vnu.uet.tuan.uetsupporter.View.Main.HopThu.SendEmail.IViewSendEmail;

/**
 * Created by FRAMGIA\vu.minh.tuan on 22/02/2018.
 */

public class PresenterSendEmailLogic implements IPresenterSendEmailView, IPresenterSendEmailModel.OnLoadEmailFinish{
    PresenterSendEmailModel presenter;
    IViewSendEmail iView;
    public PresenterSendEmailLogic(Context context, IViewSendEmail iView) {
        this.iView = iView;
        this.presenter = new PresenterSendEmailModel(context);
    }
    @Override
    public void excuteSendEmail(String to,String from, String cc, String bcc, String subject, String body, String path) {
        iView.onPreExecute();
        presenter.excuteSendEmail(to,from,cc,bcc,subject,body,path,this);
    }

    @Override
    public void excuteSendEmail(String to,String from, String cc, String bcc, String subject, String body) {
        iView.onPreExecute();
        presenter.excuteSendEmail(to,from,cc,bcc,subject,body,this);
    }

    @Override
    public void OnSendEmailSuccess() {
        iView.onExecuteSuccess();
    }

    @Override
    public void OnSendEmailFailure(String fail) {
        iView.onExecuteFailure(fail);
    }
}
