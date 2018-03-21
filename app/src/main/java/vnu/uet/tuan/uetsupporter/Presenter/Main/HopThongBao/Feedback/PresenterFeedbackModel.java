package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.Feedback;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.ClientConfig.UserAgentInterceptor;
import vnu.uet.tuan.uetsupporter.Model.Response.Feedback;
import vnu.uet.tuan.uetsupporter.Model.Response.Feedback;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by FRAMGIA\vu.minh.tuan on 19/03/2018.
 */

public class PresenterFeedbackModel implements IPresenterFeedBackModel{
    private Context context;
    private Retrofit retrofit;

    public PresenterFeedbackModel(Context context) {
        this.context = context;

        String UA = System.getProperty("http.agent");  // Get android user agent.
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(new UserAgentInterceptor(UA))
                .build();


        retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                .client(client)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    @Override
    public void findFeedback(String announcementId, OnFetchFinish listener) {
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        Call<List<Feedback>> call = apiTinTuc.getFeedBackByAnnouncementId(announcementId, Utils.getUserToken(context));
        call.enqueue(new Callback<List<Feedback>>() {
            @Override
            public void onResponse(Call<List<Feedback>> call, Response<List<Feedback>> response) {
                if(response.isSuccessful() && response.code() < 400) {
                    listener.OnFetchSuccess(response.body());
                }else {
                    listener.OnFetchFailure(context.getResources().getString(R.string.fail_download));
                }
            }

            @Override
            public void onFailure(Call<List<Feedback>> call, Throwable t) {
                if(t!=null){
                    listener.OnFetchFailure(t.getMessage());
                }else {
                    listener.OnFetchFailure(context.getResources().getString(R.string.fail_download));
                }
            }
        });
    }

    private RequestBody createBodySub(String announcementId, String feedback, String subFeedback){
        Map<String, Object> map = new ArrayMap<>();
        map.put("announcementId",announcementId);
        map.put("content",feedback);
        map.put("isSub",true);
        map.put("rootId",subFeedback);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(map)).toString());
    }

    private RequestBody createBodyNoSub(String announcementId, String feedback){
        Map<String, Object> map = new ArrayMap<>();
        map.put("announcementId",announcementId);
        map.put("content",feedback);
        map.put("isSub",false);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(map)).toString());
    }

    @Override
    public void postSubFeedBack(String announcementId, String feedback, String subFeedback, OnPostFinish listener) {
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        Call<Feedback> call = apiTinTuc.postFeedBack(createBodySub(announcementId,feedback,subFeedback), Utils.getUserToken(context));
        call.enqueue(handleResponse(listener));
    }

    @Override
    public void postSubFeedBack(String announcementId, String feedback, OnPostFinish listener) {
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        Call<Feedback> call = apiTinTuc.postFeedBack(createBodyNoSub(announcementId,feedback), Utils.getUserToken(context));
        call.enqueue(handleResponse(listener));
    }

    private Callback<Feedback> handleResponse(OnPostFinish listener){
        return new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                if(response.isSuccessful() && response.code() < 400){
                    listener.OnPostSuccess(response.body());
                }else {
                    listener.OnPostFailure(context.getResources().getString(R.string.fail_download));
                }

            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                if(t != null) {
                    listener.OnPostFailure(t.getMessage());
                }else{
                    listener.OnPostFailure(context.getResources().getString(R.string.fail_download));
                }
            }
        };
    }

}
