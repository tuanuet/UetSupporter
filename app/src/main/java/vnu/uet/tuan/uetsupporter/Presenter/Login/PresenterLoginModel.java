package vnu.uet.tuan.uetsupporter.Presenter.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;


import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

import static vnu.uet.tuan.uetsupporter.config.Config.LOGIN_URL;


/**
 * Created by vmtuan on 3/23/2017.
 */

public class PresenterLoginModel implements IPresenterLoginModel {
    private final String TAG = this.getClass().getSimpleName();
    private OnLoginFinishedListener listener;
    private Context context;


    PresenterLoginModel(Context context) {
        this.context = context;
    }


    @Override
    public void login(final String user, final String pass, OnLoginFinishedListener listener) {
        //retrofit
        Handler handler = new Handler();
        this.listener = listener;


        handler.post(new Runnable() {
            @Override
            public void run() {
                new SignIn().execute(user, pass);
            }
        });

    }

    private class SignIn extends AsyncTask<String, Void, String> {
        private static final String SINHVIEN = "sinhvien";
        private static final String SUCCESS = "success";
        private static final String TOKEN = "token";
        private String email, pass;

        @Override
        protected String doInBackground(String... params) {
            this.email = params[0];
            this.pass = params[1];

            try {
                return Utils.getJSONFromSever(getBodyRequest(email, pass), LOGIN_URL);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            if (json == null) {
                listener.OnAutherticateFailure();
            } else {
                try {
                    Log.e(TAG, "onPostExecute");
                    final JSONObject object = new JSONObject(json);
                    Boolean success = object.getBoolean(SUCCESS);
                    if (success) {
                        String token = object.getString(TOKEN);
                        JSONObject sinhvien = object.getJSONObject(SINHVIEN);
                        String tenSinhVien = sinhvien.getString(Config.TENSINHVIEN);
                        //luu vao shareprefer
                        savePreferanece(context, email, tenSinhVien, token);
                        //cap nhật lại token
                        Thread thread = new Thread(new RenderToken());

                        //cho den khi token đc cập nhận thì mới đăng nhập
                        Thread checkToken = new Thread(new CheckRenderToken());
                        thread.start();
                        checkToken.start();

                    } else {
                        Log.e(TAG, "OnAutherticateFailure");
                        listener.OnAutherticateFailure();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }


        public String getBodyRequest(String username, String pass) {
            String json = "{ \"username\" : \"" + username + "\",\"password\" : \"" + pass + "\"}";
            return json;
        }
    }

    private class CheckRenderToken implements Runnable {
        @Override
        public void run() {
            //start activity
            // progressDialog.dismiss();
            while (!Utils.canGetFirebaseToken(context)) {
                Log.e("Login", Utils.canGetFirebaseToken(context) + "");
            }

            listener.OnAutherticateSuccess();
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
                listener.OnAutherticateFailure();
            }
        }

    }

    private void savePreferanece(Context context, String email, String tenSinhVien, String token) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.EMAIL, email);
        editor.putString(Config.TENSINHVIEN, tenSinhVien);
        editor.putString(Config.USER_TOKEN, token);
        editor.commit();
    }

}
