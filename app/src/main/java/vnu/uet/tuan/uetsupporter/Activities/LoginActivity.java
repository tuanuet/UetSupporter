package vnu.uet.tuan.uetsupporter.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import vnu.uet.tuan.uetsupporter.Presenter.Login.IPresenterLoginView;
import vnu.uet.tuan.uetsupporter.Presenter.Login.PresenterLoginLogic;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.View.Login.IViewLogin;
import vnu.uet.tuan.uetsupporter.config.Config;

import static vnu.uet.tuan.uetsupporter.config.Config.LOGIN_URL;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, IViewLogin {

    private ProgressDialog dialog;
    private TextView email, password;
    private Button btnSignIn;

    private IPresenterLoginView presenterLoginLogic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        checkFirstTime();

        initUI();

        presenterLoginLogic = new PresenterLoginLogic(getApplicationContext(), this);

        //neu da dang nhap tai khoan roi thi finish luon
        if (Utils.getUserToken(getApplicationContext()) != null) {
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void checkFirstTime() {
        if (Utils.isRunFirstTime(getApplicationContext())) {
            Log.e("runfirsttiem", Utils.isRunFirstTime(getApplicationContext()) + "");
            Thread runFirstTime = new Thread(new RunFirstTime());
            runFirstTime.start();
            finish();
        }
    }

    private void initUI() {
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage(getString(R.string.please_wait));

        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //xu ly asyn tai day
        String user = email.getText().toString();
        String pass = password.getText().toString();
        presenterLoginLogic.excuteLogin(user, pass);
    }

    @Override
    public void OnPreExecute() {
        dialog.show();
    }

    @Override
    public void OnAuthenticationSuccess() {
        dialog.dismiss();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        System.gc();
    }

    @Override
    public void OnAuthenticationFailure() {
        dialog.dismiss();
        password.setText("");
        Toast.makeText(getApplicationContext(),
                getString(R.string.fail_sign_in),
                Toast.LENGTH_LONG).show();

    }

    class RunFirstTime implements Runnable {
        @Override
        public void run() {
            Intent loading = new Intent(getApplicationContext(), LoadingActivity.class);
            startActivity(loading);
        }
    }


}
