package vnu.uet.tuan.uetsupporter.View.Main.HopThu.MainEmail;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Mail.Email;

/**
 * Created by vmtuan on 3/25/2017.
 */

public interface IViewMainEmail {

    void onPreExecute();

    void onExecuteSuccess(List<Email> emails);

    void onExecuteFailure(String fail);

    void onCancelExecuteSuccess(String success);

    void onCancelExecuteFailure(String fail);
}
