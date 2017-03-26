package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.DetailHopThongBaoDiem;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Response.DiemResponse;

/**
 * Created by vmtuan on 3/24/2017.
 */

public interface IPresenterDetailHopThongBaoDiemModel {
    interface OnGetThongBaoDiemFinish {
        void OnSuccess(List<DiemResponse> diemResponses);

        void OnFailure(String fail);
    }

    interface OnCancelGetThongBaoDiemFinish {
        void OnCancelSuccess();

        void OnCancelFailure();
    }

    void getDetailHopThongBaoDiem(String linkClass, OnGetThongBaoDiemFinish listener);

    void cancelGetDetailHopThongBaoDiem(OnCancelGetThongBaoDiemFinish listener);
}
