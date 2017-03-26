package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.DetailEmail;


import android.content.Context;

import vnu.uet.tuan.uetsupporter.Listener.OnCancelRequest;
import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.View.Main.HopThu.DetailEmail.IViewDetailEmail;

/**
 * Created by vmtuan on 3/26/2017.
 */

public class PresenterDetailEmailLogic implements IPresenterDetailEmailView, IPresenterDetailEmailModel.OnLoadDetailEmailFinish, OnCancelRequest {
    private PresenterDetailEmailModel presenter;
    private IViewDetailEmail iView;

    public PresenterDetailEmailLogic(Context context, IViewDetailEmail iView) {
        this.iView = iView;
        this.presenter = new PresenterDetailEmailModel(context);
    }

    @Override
    public void excuteLoadEmail(String folder, int position) {
        presenter.excuteLoadEmail(folder, position, this);
    }

    @Override
    public void cancelLoadEmail() {
        presenter.cancelLoadingEmail(this);
    }

    @Override
    public void OnLoadEmailSuccess(Email email) {
        iView.onExecuteSuccess(email);
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
