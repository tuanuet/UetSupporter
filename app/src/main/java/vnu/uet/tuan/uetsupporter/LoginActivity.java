package vnu.uet.tuan.uetsupporter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import vnu.uet.tuan.uetsupporter.config.Config;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    static  TextView email,password;
    Button btnSignIn;
    SharedPreferences sharedpreferences;

    ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        sharedpreferences = getSharedPreferences(Config.MyPREFERENCES, Context.MODE_PRIVATE);
        btnSignIn.setOnClickListener(this);

        //neu da dang nhap tai khoan roi thi finish luon
        if(!sharedpreferences.getString(Config.EMAIL,"").equals("")){
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }


    }
    class SignIn extends AsyncTask<Void,Void,String>{
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage(getString(R.string.please_wait));
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //post email and password

            if(true){
                //luu vao shareprefer
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Config.EMAIL,email.getText().toString());
                editor.putString(Config.PASSWORD,password.getText().toString());
                editor.commit();

                //cap nhật lại token
                try {

                    FirebaseInstanceId.getInstance().deleteInstanceId();
                    FirebaseInstanceId.getInstance().getToken();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //start activity
                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                //mat khau hoac tai khoan sai
                password.setText("");
                Toast.makeText(getApplicationContext(),getString(R.string.fail_sign_in),Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            super.onPostExecute(s);
        }
    }
    @Override
    public void onClick(View v) {


        //fake du lieu
        String str_email = email.getText().toString();
        String str_pass = password.getText().toString();

        //xu ly asyn tai day
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new SignIn().execute();
            }
        });
    }
}
