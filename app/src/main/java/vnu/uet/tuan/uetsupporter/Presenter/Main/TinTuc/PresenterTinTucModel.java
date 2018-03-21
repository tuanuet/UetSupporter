package vnu.uet.tuan.uetsupporter.Presenter.Main.TinTuc;

import android.content.Context;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.ClientConfig.ConfigCache;
import vnu.uet.tuan.uetsupporter.Listener.OnCancelRequest;
import vnu.uet.tuan.uetsupporter.Model.Response.TinTuc;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by vmtuan on 3/23/2017.
 */

public class PresenterTinTucModel implements IPresenterTinTucModel, Callback<ArrayList<TinTuc>> {

    private OnGetTinTucFinishListener listener;
    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private Call<ArrayList<TinTuc>> call;
    private ApiTinTuc api;

    public PresenterTinTucModel(Context context) {
        this.context = context;
        OkHttpClient okHttpClient = ConfigCache.createCachedClient(context);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                .client(okHttpClient)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(ApiTinTuc.class);
    }

    @Override
    public void sendRequest(String loaitintuc, int offset, OnGetTinTucFinishListener listener) {
        this.listener = listener;
        //retrofit

        call = api.getDataTinTuc(loaitintuc, offset * 20);
        call.enqueue(this);
    }

    @Override
    public void cancelSendRequest(OnCancelRequest listener) {
        call.cancel();
        if (call.isCanceled()) {
            listener.OnCancelSuccess(context.getString(R.string.cancel_success));
        } else {
            listener.OnCancelFailure(context.getString(R.string.cancel_failure));
        }
    }

    @Override
    public void onResponse(Call<ArrayList<TinTuc>> call, Response<ArrayList<TinTuc>> response) {
        try {
            if (response.isSuccessful() && response.body().size() >= 0)
                listener.OnSuccess(response.body());
            else
                listener.OnFailure(context.getString(R.string.fail_download));
        } catch (Exception e) {
            listener.OnFailure(e.getMessage());
        }

    }

    @Override
    public void onFailure(Call<ArrayList<TinTuc>> call, Throwable t) {
        listener.OnFailure(t.getMessage());
    }
}
