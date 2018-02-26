package vnu.uet.tuan.uetsupporter.View.Profile.MainProfile;


import vnu.uet.tuan.uetsupporter.Model.Student;

/**
 * Created by vmtuan on 3/25/2017.
 */

public interface IViewMainProfile {
    void onPreExecute();

    void onExecuteSuccess(Student sinhVien);

    void onExecuteFailure(String fail);

    void onCancelExecuteSuccess(String success);

    void onCancelExecuteFailure(String fail);
}
