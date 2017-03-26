package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.MainEmail;

import android.content.Context;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Listener.OnCancelRequest;
import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.View.Main.HopThu.MainEmail.IViewMainEmail;

/**
 * Created by vmtuan on 3/25/2017.
 */

public class PresenterMainEmailLogic implements IPresenterMainEmailView, IPresenterMainEmailModel.OnLoadEmailFinish, OnCancelRequest {
    private PresenterMainEmailModel presenter;
    private IViewMainEmail iView;

    public PresenterMainEmailLogic(Context context, IViewMainEmail iView) {
        this.iView = iView;
        this.presenter = new PresenterMainEmailModel(context);
    }

    @Override
    public void excuteLoadEmail(String folder, int pager) {
        iView.onPreExecute();
        presenter.excuteLoadEmail(folder, pager, this);
    }

    @Override
    public void cancelLoadEmail() {
        presenter.cancelLoadingEmail(this);
    }

    @Override
    public void OnLoadEmailSuccess(List<Email> emails) {
        iView.onExecuteSuccess(emails);
    }

    @Override
    public void OnLoadEmailFailure(String fail) {
        iView.onExecuteFailure(fail);
    }

    @Override
    public void OnCancelSuccess(String s) {
        iView.onCancelExecuteSuccess(s);
    }

    @Override
    public void OnCancelFailure(String f) {
        iView.onCancelExecuteFailure(f);
    }
}
