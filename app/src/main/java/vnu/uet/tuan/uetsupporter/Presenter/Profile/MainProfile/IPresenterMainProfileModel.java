package vnu.uet.tuan.uetsupporter.Presenter.Profile.MainProfile;

import vnu.uet.tuan.uetsupporter.Listener.OnCancelRequest;
import vnu.uet.tuan.uetsupporter.Model.SinhVien;

/**
 * Created by vmtuan on 3/25/2017.
 */

public interface IPresenterMainProfileModel {
    interface OnLoadStudentFinish {
        void OnLoadingSuccess(SinhVien sinhVien);

        void OnLoadingFailure(String fail);
    }

    void executeLoadStudent(String token, OnLoadStudentFinish listener);

    void cancelLoadStudent(OnCancelRequest cancelRequest);
}
