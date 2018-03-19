package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.Feedback;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Response.Feedback;

/**
 * Created by FRAMGIA\vu.minh.tuan on 19/03/2018.
 */

public interface IPresenterFeedBackModel {
    interface OnFetchFinish {
        public void OnFetchSuccess(List<Feedback> feedbacks);
        public void OnFetchFailure(String failure);
    }

    public void findFeedback(String announcementId, OnFetchFinish listener);
}
