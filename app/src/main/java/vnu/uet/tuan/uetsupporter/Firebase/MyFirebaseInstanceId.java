package vnu.uet.tuan.uetsupporter.Firebase;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by Administrator on 12/01/2017.
 */

public class MyFirebaseInstanceId extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        final String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token1", token);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.MyPREFERENCES, Context.MODE_PRIVATE);
        //neu nhu token mới thì ko gửi lên server, khi đã ren một token r, sau khi đang nhập sẽ ren ra token khác

        if (!sharedPreferences.getString(Config.FIREBASE_TOKEN, "").equals("")) {
            //gửi lên Server lưu lại token
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    new SendToken().execute(token);
                }
            });
            thread.start();
        }

    }

    class SendToken extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            //gửi token lên server
            String token = params[0];
            SharedPreferences sharedPreferences = getSharedPreferences(Config.MyPREFERENCES, Context.MODE_PRIVATE);

//            OkHttpClient client = new OkHttpClient();
//            //tao string json
//            String json =new String();
//
//            RequestBody body = RequestBody.create(Config.JSON, json);
//            Request request = new Request.Builder()
//                    .url(Config.POST_TOKEN)
//                    .post(body)
//                    .build();
//            Response response = null;
            //                response = client.newCall(request).execute();
//                response.body().string();
            //xử lý string response

            //nếu ok thì lưu vào share
            //sau khi gửi xong lên server thì lưu vào share
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Config.FIREBASE_TOKEN, token);
            editor.commit();
            return null;
        }
    }
}
