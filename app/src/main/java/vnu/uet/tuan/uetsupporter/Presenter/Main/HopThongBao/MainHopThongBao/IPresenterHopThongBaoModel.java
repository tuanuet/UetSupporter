package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.MainHopThongBao;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;

/**
 * Created by vmtuan on 3/24/2017.
 */

public interface IPresenterHopThongBaoModel {
    interface OnGetPushNotificationFinish {
        void OnSuccess(List<AnnouncementNotification> notifications);

        void OnFailure(String fail);
    }

    interface OnReactFinish {
        void OnReactSuccess(int code);

        void OnReactFailure(String fail);
    }

    void getPushNotification(OnGetPushNotificationFinish listener);
    void pushReact(String _id,int code, OnReactFinish listener);
}
