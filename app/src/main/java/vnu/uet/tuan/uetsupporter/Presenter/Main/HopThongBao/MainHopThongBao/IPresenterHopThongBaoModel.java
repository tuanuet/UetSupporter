package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.MainHopThongBao;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.PushNotification;

/**
 * Created by vmtuan on 3/24/2017.
 */

public interface IPresenterHopThongBaoModel {
    interface OnGetPushNotificationFinish {
        void OnSuccess(List<PushNotification> notifications);

        void OnFailure(String fail);
    }

    void getPushNotification(OnGetPushNotificationFinish listener);
}
