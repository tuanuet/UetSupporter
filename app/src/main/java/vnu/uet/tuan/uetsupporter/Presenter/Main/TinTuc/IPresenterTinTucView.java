package vnu.uet.tuan.uetsupporter.Presenter.Main.TinTuc;

import vnu.uet.tuan.uetsupporter.Model.Response.TinTuc;

/**
 * Created by vmtuan on 3/23/2017.
 */

public interface IPresenterTinTucView {
    void executeTinTuc(int loaitintuc, int offset);

    void cancelExcute();
}
