package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.DetailHopThongBaoDiem;

import android.content.Context;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.DetailThongBao;
import vnu.uet.tuan.uetsupporter.Model.Response.DiemResponse;
import vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.DetailHopThongBao.IViewDetailHopThongBao;
import vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.DetailHopThongBaoDiem.IViewDetailHopThongBaoDiem;

/**
 * Created by vmtuan on 3/24/2017.
 */

public class PresenterDetailHopThongBaoDiemLogic implements IPresenterDetailHopThongBaoDiemView,
        IPresenterDetailHopThongBaoDiemModel.OnCancelGetThongBaoDiemFinish, IPresenterDetailHopThongBaoDiemModel.OnGetThongBaoDiemFinish {

    private IViewDetailHopThongBaoDiem iView;
    private PresenterDetailHopThongBaoDiemModel presenter;

    public PresenterDetailHopThongBaoDiemLogic(Context context, IViewDetailHopThongBaoDiem iView) {
        this.iView = iView;
        this.presenter = new PresenterDetailHopThongBaoDiemModel(context);
    }

    @Override
    public void executeDetailHopThongBaoDiem(String linkClass) {
        presenter.getDetailHopThongBaoDiem(linkClass, this);
    }

    @Override
    public void cancelExecuteDetailHopThongBaoDiem() {
        presenter.cancelGetDetailHopThongBaoDiem(this);
    }

    @Override
    public void OnSuccess(List<DiemResponse> diemResponses) {
        iView.onExecuteSuccess(diemResponses);
    }

    @Override
    public void OnFailure(String fail) {
        iView.onExecuteFailure(fail);
    }

    @Override
    public void OnCancelSuccess() {
        iView.onCancelExecuteSuccess();
    }

    @Override
    public void OnCancelFailure() {
        iView.onCancelExecuteFailure();
    }
}
