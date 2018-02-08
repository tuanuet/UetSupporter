package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.DetailHopThongBao;

import vnu.uet.tuan.uetsupporter.Model.DetailThongBao;

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
