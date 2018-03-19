package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.Feedback;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Response.Feedback;
import vnu.uet.tuan.uetsupporter.Model.Response.Message;

/**
 * Created by FRAMGIA\vu.minh.tuan on 19/03/2018.
 */

public interface IPresenterFeedBackModel {
    interface OnFetchFinish {
        public void OnFetchSuccess(List<Feedback> feedbacks);
        public void OnFetchFailure(String failure);
    }

    interface OnPostFinish {
        public void OnPostSuccess(Feedback feedback);
        public void OnPostFailure(String failure);
    }

    public void findFeedback(String announcementId, OnFetchFinish listener);
    public void postSubFeedBack(String announcement,String feedback,String subFeedback,OnPostFinish listener);
    public void postSubFeedBack(String announcement,String feedback,OnPostFinish listener);
}
