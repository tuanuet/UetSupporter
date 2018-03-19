package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.Feedback;

/**
 * Created by FRAMGIA\vu.minh.tuan on 19/03/2018.
 */

public interface IPresenterFeedbackLogic {
    void execute(String announcementId);
    void postFeedback(String announcementId,String feedback, String subFeedBack);
    void postFeedback(String announcementId,String feedback);
}
