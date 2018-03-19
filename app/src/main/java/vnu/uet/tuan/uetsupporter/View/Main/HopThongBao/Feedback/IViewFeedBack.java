package vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.Feedback;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Response.Feedback;
import vnu.uet.tuan.uetsupporter.Model.Response.Message;

/**
 * Created by FRAMGIA\vu.minh.tuan on 19/03/2018.
 */

public interface IViewFeedBack {
    void onPreExecute();

    void onExecuteSuccess(List<Feedback> feedbacks);

    void onExecuteFailure(String fail);

    void onPostSuccess(Feedback feedback);
    void onPostFailure(String failure);
}
