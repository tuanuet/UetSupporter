package vnu.uet.tuan.uetsupporter.View.Main.TinTuc;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Response.TinTuc;

/**
 * Created by vmtuan on 3/23/2017.
 */

public interface IViewTinTuc {
    void OnPreSendRequest();

    void OnGetReponseSuccess(List<TinTuc> tinTucs);

    void OnGetReponseFailure();

}
