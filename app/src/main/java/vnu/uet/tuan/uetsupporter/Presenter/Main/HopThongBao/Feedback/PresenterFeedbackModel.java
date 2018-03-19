package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.Feedback;

import android.content.Context;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Model.Response.Feedback;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by FRAMGIA\vu.minh.tuan on 19/03/2018.
 */

public class PresenterFeedbackModel implements IPresenterFeedBackModel, Callback<List<Feedback>> {
    private OnFetchFinish listener;
    private Context context;
    private Call<List<Feedback>> call;
    private Retrofit retrofit;

    public PresenterFeedbackModel(Context context) {
        this.context = context;
        retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    @Override
    public void findFeedback(String announcementId, OnFetchFinish listener) {
        this.listener = listener;
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.getFeedBackByAnnouncementId(announcementId, Utils.getUserToken(context));
        call.enqueue(this);
    }

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
}
