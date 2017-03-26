package vnu.uet.tuan.uetsupporter.View.Profile.DetailLopMonHoc;

import vnu.uet.tuan.uetsupporter.Model.LopMonHoc;

/**
 * Created by vmtuan on 3/25/2017.
 */

public interface IViewDetailLopMonHoc {
    void onPreExecute();

    void onExecuteSuccess(LopMonHoc lopMonHoc);

    void onExecuteFailure(String fail);

    void onCancelExecuteSuccess(String success);

    void onCancelExecuteFailure(String fail);
}
