package vnu.uet.tuan.uetsupporter.Presenter.Profile.MainProfile;

import android.content.Context;

import vnu.uet.tuan.uetsupporter.Listener.OnCancelRequest;
import vnu.uet.tuan.uetsupporter.Model.Student;
import vnu.uet.tuan.uetsupporter.View.Profile.MainProfile.IViewMainProfile;

/**
 * Created by vmtuan on 3/25/2017.
 */

public class PresenterMainProfileLogic implements IPresenterMainProfileView, IPresenterMainProfileModel.OnLoadStudentFinish, OnCancelRequest {
    private IViewMainProfile iView;
    private PresenterMainProfileModel presenter;

    public PresenterMainProfileLogic(Context context, IViewMainProfile iView) {
        this.iView = iView;
        this.presenter = new PresenterMainProfileModel(context);
    }

    @Override
    public void executeLoadStudent(String token) {
        iView.onPreExecute();
        presenter.executeLoadStudent(token, this);
    }

    @Override
    public void cancelLoadStudent() {
        presenter.cancelLoadStudent(this);
    }

    @Override
    public void OnLoadingSuccess(Student sinhVien) {
        iView.onExecuteSuccess(sinhVien);
    }

    @Override
    public void OnLoadingFailure(String fail) {
        iView.onExecuteFailure(fail);
    }

    @Override
    public void OnCancelSuccess(String s) {
        iView.onCancelExecuteSuccess(s);
    }

    @Override
    public void OnCancelFailure(String s) {
        iView.onCancelExecuteFailure(s);
    }
}
