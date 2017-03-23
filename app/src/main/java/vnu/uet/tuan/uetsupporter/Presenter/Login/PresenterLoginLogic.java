package vnu.uet.tuan.uetsupporter.Presenter.Login;


import android.content.Context;

import vnu.uet.tuan.uetsupporter.View.Login.IViewLogin;


/**
 * Created by vmtuan on 3/23/2017.
 */

public class PresenterLoginLogic implements IPresenterLoginView, IPresenterLoginModel.OnLoginFinishedListener {

    private IViewLogin iViewLogin;
    private PresenterLoginModel presenterLoginModel;

    public PresenterLoginLogic(Context context, IViewLogin iViewLogin) {
        this.iViewLogin = iViewLogin;
        this.presenterLoginModel = new PresenterLoginModel(context);
    }

    @Override
    public void excuteLogin(String user, String pass) {
        iViewLogin.OnPreExecute();
        presenterLoginModel.login(user, pass, this);
    }

    @Override
    public void OnAutherticateSuccess() {
        iViewLogin.OnAuthenticationSuccess();
    }

    @Override
    public void OnAutherticateFailure() {
        iViewLogin.OnAuthenticationFailure();
    }


}
