package vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.DetailHopThongBao;

import vnu.uet.tuan.uetsupporter.Model.DetailThongBao;

/**
 * Created by vmtuan on 3/24/2017.
 */

public interface IViewDetailHopThongBao {
    void onPreExecute();

    void onExecuteSuccess(DetailThongBao detailThongBao);

    void onExecuteFailure(String fail);

    void onCancelExecuteSuccess();

    void onCancelExecuteFailure();
}
