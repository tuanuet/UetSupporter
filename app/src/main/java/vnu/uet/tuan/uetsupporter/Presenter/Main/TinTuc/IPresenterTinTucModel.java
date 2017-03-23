package vnu.uet.tuan.uetsupporter.Presenter.Main.TinTuc;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Response.TinTuc;


/**
 * Created by vmtuan on 3/23/2017.
 */

public interface IPresenterTinTucModel {
    interface OnGetTinTucFinishListener {
        void OnSuccess(List<TinTuc> tinTucs);

        void OnFailure();
    }

    void sendRequest(int loaitintuc, int offset, OnGetTinTucFinishListener listener);
}
