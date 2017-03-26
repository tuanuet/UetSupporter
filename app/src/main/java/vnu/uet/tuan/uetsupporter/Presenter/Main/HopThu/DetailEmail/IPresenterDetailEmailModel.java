package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.DetailEmail;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Listener.OnCancelRequest;
import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.MainEmail.IPresenterMainEmailModel;

/**
 * Created by vmtuan on 3/26/2017.
 */

public interface IPresenterDetailEmailModel {
    interface OnLoadDetailEmailFinish {
        void OnLoadEmailSuccess(Email email);

        void OnLoadEmailFailure(String fail);
    }

    void excuteLoadEmail(String folder, int position, OnLoadDetailEmailFinish listener);

    void cancelLoadingEmail(OnCancelRequest listener);
}
