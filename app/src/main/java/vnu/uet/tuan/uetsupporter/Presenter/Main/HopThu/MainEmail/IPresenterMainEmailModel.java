package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.MainEmail;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Listener.OnCancelRequest;
import vnu.uet.tuan.uetsupporter.Model.Mail.Email;

/**
 * Created by vmtuan on 3/25/2017.
 */

public interface IPresenterMainEmailModel {
    interface OnLoadEmailFinish {
        void OnLoadEmailSuccess(List<Email> emails);

        void OnLoadEmailFailure(String fail);
    }

    void excuteLoadEmail(String folder, int pager, OnLoadEmailFinish listener);

    void cancelLoadingEmail(OnCancelRequest listener);
}
