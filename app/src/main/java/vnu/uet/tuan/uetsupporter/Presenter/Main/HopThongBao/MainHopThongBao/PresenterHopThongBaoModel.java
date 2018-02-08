package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.MainHopThongBao;

import android.content.Context;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;
import vnu.uet.tuan.uetsupporter.Utils.Utils;

/**
 * Created by vmtuan on 3/24/2017.
 */

public class PresenterHopThongBaoModel implements IPresenterHopThongBaoModel {

    private Context context;

    public PresenterHopThongBaoModel(Context context) {
        this.context = context;
    }

    @Override
    public void getPushNotification(OnGetPushNotificationFinish listener) {

        try {
            ArrayList<AnnouncementNotification> list = Utils.getPushNotification(context);
            if (list != null) {
                listener.OnSuccess(list);
            } else {
                listener.OnSuccess(new ArrayList<AnnouncementNotification>());
            }

        } catch (Exception e) {
            listener.OnFailure(e.getMessage());
        }


    }

}
