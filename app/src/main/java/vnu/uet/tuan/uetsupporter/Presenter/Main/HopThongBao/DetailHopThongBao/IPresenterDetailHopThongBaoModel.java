package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.DetailHopThongBao;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.DetailThongBao;
import vnu.uet.tuan.uetsupporter.Model.PushNotification;

/**
 * Created by vmtuan on 3/24/2017.
 */

public interface IPresenterDetailHopThongBaoModel {
    interface OnGetThongBaoFinish {
        void OnSuccess(DetailThongBao detailThongBao);

        void OnFailure(String fail);
    }

    interface OnCancelGetThongBaoFinish {
        void OnCancelSuccess();

        void OnCancelFailure();
    }

    void getDetailHopThongBao(String linkClass, OnGetThongBaoFinish listener);

    void cancelGetDetailHopThongBao(OnCancelGetThongBaoFinish listener);
}
