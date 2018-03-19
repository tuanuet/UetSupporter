package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.Feedback;

import android.content.Context;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Response.Feedback;
import vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.Feedback.IViewFeedBack;

/**
 * Created by FRAMGIA\vu.minh.tuan on 19/03/2018.
 */

public class PresenterFeedbackLogic implements IPresenterFeedbackLogic, IPresenterFeedBackModel.OnFetchFinish {
    IPresenterFeedBackModel presenter;
    private IViewFeedBack iView;
    public PresenterFeedbackLogic(Context context, IViewFeedBack iView){
        this.presenter = new PresenterFeedbackModel(context);
        this.iView = iView;
    }

    @Override
    public void execute(String announcementId) {
        iView.onPreExecute();
        presenter.findFeedback(announcementId,this);
    }

    @Override
    public void OnFetchSuccess(List<Feedback> feedbacks) {
        iView.onExecuteSuccess(feedbacks);
    }

    @Override
    public void OnFetchFailure(String failure) {
        iView.onExecuteFailure(failure);
    }
}
