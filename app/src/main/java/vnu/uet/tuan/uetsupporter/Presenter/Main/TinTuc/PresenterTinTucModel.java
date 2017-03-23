package vnu.uet.tuan.uetsupporter.Presenter.Main.TinTuc;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Cache.ConfigCache;
import vnu.uet.tuan.uetsupporter.Model.Response.TinTuc;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by vmtuan on 3/23/2017.
 */

public class PresenterTinTucModel implements IPresenterTinTucModel, Callback<ArrayList<TinTuc>> {

    private OnGetTinTucFinishListener listener;
    private final String TAG = this.getClass().getSimpleName();
    private Context context;

    public PresenterTinTucModel(Context context) {
        this.context = context;
    }

    @Override
    public void sendRequest(int loaitintuc, int offset, OnGetTinTucFinishListener listener) {
        this.listener = listener;
        //retrofit
        getTinTucByLoaiTinTuc(loaitintuc, offset);

    }

    private void getTinTucByLoaiTinTuc(int loaitintuc, int offsetPage) {
        Call<ArrayList<TinTuc>> call;
        OkHttpClient okHttpClient = ConfigCache.createCachedClient(context);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                .client(okHttpClient)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.getDataTinTuc(loaitintuc, offsetPage * 10);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<ArrayList<TinTuc>> call, Response<ArrayList<TinTuc>> response) {
        try {
            if (response.isSuccessful() && response.body().size() >= 0)
                listener.OnSuccess(response.body());
            else
                listener.OnFailure();
        } catch (Exception e) {
            listener.OnFailure();
        }

    }

    @Override
    public void onFailure(Call<ArrayList<TinTuc>> call, Throwable t) {
        listener.OnFailure();
    }
}
