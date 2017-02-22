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

import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

import static vnu.uet.tuan.uetsupporter.config.Config.LOGIN_URL;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String SINHVIEN = "sinhvien";
    private static final String SUCCESS = "success";
    private ProgressDialog dialog;
    private static final String TOKEN = "token";
    private TextView email, password;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Utils.isRunFirstTime(getApplicationContext())) {
            Log.e("runfirsttiem", Utils.isRunFirstTime(getApplicationContext()) + "");
            Thread runFirstTime = new Thread(new RunFirstTime());
            runFirstTime.start();
            finish();
        }

        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage(getString(R.string.please_wait));

        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);

        //neu da dang nhap tai khoan roi thi finish luon
        if(Utils.getEmailUser(getApplicationContext())!=null){
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    class SignIn extends AsyncTask<Void,Void,String>{
        @Override
        protected void onPreExecute() {
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            return Utils.getJSONFromSever(getBodyRequest(),LOGIN_URL);
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            if (json != null) {
                try {
                    final JSONObject object = new JSONObject(json);
                    Boolean success = object.getBoolean(SUCCESS);
                    if (success) {

                        String token = object.getString(TOKEN);
                        JSONObject sinhvien = object.getJSONObject(SINHVIEN);
                        String tenSinhVien = sinhvien.getString(Config.TENSINHVIEN);
                        //luu vao shareprefer
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Config.EMAIL, email.getText().toString());
                        editor.putString(Config.TENSINHVIEN, tenSinhVien);
                        editor.putString(Config.PASSWORD, password.getText().toString());
                        editor.putString(Config.USER_TOKEN, token);
                        editor.commit();

                        //cap nhật lại token
                        Thread thread = new Thread(new RenderToken());

                        //cho den khi token đc cập nhận thì mới đăng nhập
                        Thread checkToken = new Thread(new CheckRenderToken());
                        thread.start();
                        checkToken.start();

                    } else {
                        //mat khau hoac tai khoan sai
                        dialog.dismiss();
                        password.setText("");
                        Toast.makeText(getApplicationContext(),
                                getString(R.string.fail_sign_in),
                                Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        //xu ly asyn tai day
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new SignIn().execute();
            }
        });
    }

    public String getBodyRequest() {
        String username = email.getText().toString();
        String pass = password.getText().toString();
        String json = "{ \"username\" : \"" + username + "\",\"password\" : \"" + pass + "\"}";
        return json;
    }

    private class CheckRenderToken implements Runnable {
        @Override
        public void run() {
            //start activity
            // progressDialog.dismiss();
            while (!Utils.canGetFirebaseToken(getApplicationContext())) {
                Log.e("Login", Utils.canGetFirebaseToken(getApplicationContext()) + "");
            }

            dialog.dismiss();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private class RenderToken implements Runnable {
        @Override
        public void run() {
            try {
                FirebaseInstanceId.getInstance().deleteInstanceId();
                FirebaseInstanceId.getInstance().getToken();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    class RunFirstTime implements Runnable {
        @Override
        public void run() {
            Intent loading = new Intent(getApplicationContext(), LoadingActivity.class);
            startActivity(loading);
        }
    }
}
