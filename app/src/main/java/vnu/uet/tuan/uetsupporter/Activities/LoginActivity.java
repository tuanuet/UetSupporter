package vnu.uet.tuan.uetsupporter.Activities;

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


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    static  TextView email,password;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        //        ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
        @Override
        protected void onPreExecute() {
//            progressDialog.setMessage(getString(R.string.please_wait));
//            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            return Utils.getJSONFromSever(getBodyRequest());
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            if (json != null) {
                try {
                    final JSONObject object = new JSONObject(json);
                    Boolean success = object.getBoolean("success");
                    if (success) {

                        String token = object.getString("token");


                        //luu vao shareprefer
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Config.EMAIL, email.getText().toString());
                        editor.putString(Config.PASSWORD, password.getText().toString());
                        editor.putString(Config.USER_TOKEN, token);
                        editor.commit();

                        //cap nhật lại token
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    FirebaseInstanceId.getInstance().deleteInstanceId();
                                    FirebaseInstanceId.getInstance().getToken();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        //cho den khi token đc cập nhận thì mới đăng nhập
                        Thread checkToken = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //start activity
                                // progressDialog.dismiss();
                                while (!Utils.canGetFirebaseToken(getApplicationContext())) {
                                    Log.e("Login", Utils.canGetFirebaseToken(getApplicationContext()) + "");
                                    try {
                                        Thread.sleep(200);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }


                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        thread.start();
                        checkToken.start();

                    } else {
                        //mat khau hoac tai khoan sai
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
}
