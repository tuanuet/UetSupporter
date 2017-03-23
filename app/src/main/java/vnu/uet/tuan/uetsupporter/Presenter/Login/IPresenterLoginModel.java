package vnu.uet.tuan.uetsupporter.Presenter.Login;

/**
 * Created by vmtuan on 3/23/2017.
 */

public interface IPresenterLoginModel {
    interface OnLoginFinishedListener {
        void OnAutherticateSuccess();

        void OnAutherticateFailure();
    }

    void login(String user, String pass, OnLoginFinishedListener listener);
}
