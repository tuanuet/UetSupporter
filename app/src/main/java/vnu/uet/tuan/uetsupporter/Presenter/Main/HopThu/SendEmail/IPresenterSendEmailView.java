package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.SendEmail;

/**
 * Created by FRAMGIA\vu.minh.tuan on 22/02/2018.
 */

public interface IPresenterSendEmailView {
    void excuteSendEmail(String to,String from, String cc, String bcc, String subject, String body, String path);
    void excuteSendEmail(String to,String from, String cc, String bcc, String subject, String body);
}
