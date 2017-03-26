package vnu.uet.tuan.uetsupporter.View.Main.HopThu.DetailEmail;


import vnu.uet.tuan.uetsupporter.Model.Mail.Email;

/**
 * Created by vmtuan on 3/26/2017.
 */

public interface IViewDetailEmail {
    void onPreExecute();

    void onExecuteSuccess(Email email);

    void onExecuteFailure(String fail);

    void onCancelExecuteSuccess(String success);

    void onCancelExecuteFailure(String fail);
}
