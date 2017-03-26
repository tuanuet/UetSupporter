package vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.MainHopThongBao;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.PushNotification;

/**
 * Created by vmtuan on 3/24/2017.
 */

public interface IViewHopThongBao {
    void OnPreExcute();

    void OnGetHopThongBaoSuccess(List<PushNotification> notifications);

    void OnGetHopThongBaoFailure(String fail);
}
