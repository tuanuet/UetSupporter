package vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.DetailHopThongBaoDiem;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Response.DiemResponse;

/**
 * Created by vmtuan on 3/24/2017.
 */

public interface IViewDetailHopThongBaoDiem {
    void onPreExecute();

    void onExecuteSuccess(List<DiemResponse> diemResponses);

    void onExecuteFailure(String fail);

    void onCancelExecuteSuccess();

    void onCancelExecuteFailure();
}
