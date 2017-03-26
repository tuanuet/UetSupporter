package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.DetailHopThongBao;

import android.content.Context;

import vnu.uet.tuan.uetsupporter.Model.DetailThongBao;
import vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.DetailHopThongBao.IViewDetailHopThongBao;

/**
 * Created by vmtuan on 3/24/2017.
 */

public class PresenterDetailHopThongBaoLogic implements IPresenterDetailHopThongBaoView, IPresenterDetailHopThongBaoModel.OnGetThongBaoFinish, IPresenterDetailHopThongBaoModel.OnCancelGetThongBaoFinish {

    private IViewDetailHopThongBao iViewDetailHopThongBao;
    private PresenterDetailHopThongBaoModel presenter;

    public PresenterDetailHopThongBaoLogic(Context context, IViewDetailHopThongBao iView) {
        this.iViewDetailHopThongBao = iView;
        this.presenter = new PresenterDetailHopThongBaoModel(context);
    }

    @Override
    public void executeDetailHopThongBao(String linkClass) {
        presenter.getDetailHopThongBao(linkClass, this);
    }

    @Override
    public void cancelExecuteDetailHopThongBao() {
        presenter.cancelGetDetailHopThongBao(this);
    }

    @Override
    public void OnSuccess(DetailThongBao detailThongBao) {
        iViewDetailHopThongBao.onExecuteSuccess(detailThongBao);
    }

    @Override
    public void OnFailure(String fail) {
        iViewDetailHopThongBao.onExecuteFailure(fail);
    }

    @Override
    public void OnCancelSuccess() {
        iViewDetailHopThongBao.onCancelExecuteSuccess();
    }

    @Override
    public void OnCancelFailure() {
        iViewDetailHopThongBao.onCancelExecuteFailure();
    }
}
