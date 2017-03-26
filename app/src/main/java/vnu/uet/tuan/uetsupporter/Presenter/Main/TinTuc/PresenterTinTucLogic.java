package vnu.uet.tuan.uetsupporter.Presenter.Main.TinTuc;

import android.content.Context;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Listener.OnCancelRequest;
import vnu.uet.tuan.uetsupporter.Model.Response.TinTuc;
import vnu.uet.tuan.uetsupporter.View.Main.TinTuc.IViewTinTuc;

/**
 * Created by vmtuan on 3/23/2017.
 */

public class PresenterTinTucLogic implements IPresenterTinTucView, IPresenterTinTucModel.OnGetTinTucFinishListener, OnCancelRequest {

    private IViewTinTuc iViewTinTuc;
    private PresenterTinTucModel presenterTinTucModel;

    public PresenterTinTucLogic(Context context, IViewTinTuc iViewTinTuc) {
        this.iViewTinTuc = iViewTinTuc;
        this.presenterTinTucModel = new PresenterTinTucModel(context);
    }

    @Override
    public void executeTinTuc(int loaitintuc, int offset) {
        iViewTinTuc.OnPreSendRequest();
        presenterTinTucModel.sendRequest(loaitintuc, offset, this);
    }

    @Override
    public void cancelExcute() {
        presenterTinTucModel.cancelSendRequest(this);
    }

    @Override
    public void OnSuccess(List<TinTuc> tinTucs) {
        iViewTinTuc.OnGetReponseSuccess(tinTucs);
    }

    @Override
    public void OnFailure(String fail) {
        iViewTinTuc.OnGetReponseFailure(fail);
    }

    @Override
    public void OnCancelSuccess(String s) {
        iViewTinTuc.onCancelSuccess(s);
    }

    @Override
    public void OnCancelFailure(String s) {
        iViewTinTuc.onCancelFailure(s);
    }
}
