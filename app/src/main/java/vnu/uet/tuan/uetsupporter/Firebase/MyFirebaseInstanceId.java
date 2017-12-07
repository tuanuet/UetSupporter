package vnu.uet.tuan.uetsupporter.Firebase;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by Administrator on 12/01/2017.
 */

public class MyFirebaseInstanceId extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        final String tokenFirebase = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token1", tokenFirebase);
        // neu nhu token mới lần đầu thì ko gửi lên server,
        // khi đã ren một token r, sau khi đang nhập sẽ ren ra token khác

        if (Utils.getFirebaseToken(getApplicationContext())!=null) {
            //gửi lên Server lưu lại token
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    new SendToken().execute(tokenFirebase);
                }
            });
            thread.start();
        }

        if (Utils.getFirebaseToken(getApplicationContext()) == null) {
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Config.CAN_BE_FIREBASE_TOKEN, false);
            editor.putString(Config.FIREBASE_TOKEN, tokenFirebase);
            editor.commit();
        }

    }

    class SendToken extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            //gửi token lên server
            String tokenFirebase = params[0];
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            OkHttpClient client = new OkHttpClient();
            //tao string json
            String json = getJSONString(tokenFirebase);
            Log.e("POST_TOKEN", json);
            RequestBody body = RequestBody.create(Config.JSON, json);
            Request.Builder builder = new Request.Builder()
                    .url(Config.POST_TOKEN)
                    .post(body)
                    .addHeader("Authorization",Utils.getUserToken(getApplicationContext()));
            Request request = builder.build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                //xử lý string response
                JSONObject object = new JSONObject(response.body().string());
                Boolean success = object.getBoolean("success");
                if (success) {
                    //nếu ok thì lưu vào share
                    //sau khi gửi xong lên server thì lưu vào share
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(Config.CAN_BE_FIREBASE_TOKEN, true);
                    editor.putString(Config.FIREBASE_TOKEN, tokenFirebase);
                    editor.commit();
                } else {
                    // Chua biet lam cai gi
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public String getJSONString(String tokenFirebase) {
        return "{ \"token\" : \"" + Utils.getUserToken(getApplicationContext())
                + "\", \"tokenFirebase\" : \"" + tokenFirebase + "\"}";
    }
}
