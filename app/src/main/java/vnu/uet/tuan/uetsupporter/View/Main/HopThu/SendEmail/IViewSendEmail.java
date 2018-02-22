package vnu.uet.tuan.uetsupporter.View.Main.HopThu.SendEmail;

/**
 * Created by vu.minh.tuan on 22/02/2018.
 */

public interface IViewSendEmail {
    void onPreExecute();

    void onExecuteSuccess();

    void onExecuteFailure(String fail);

}
