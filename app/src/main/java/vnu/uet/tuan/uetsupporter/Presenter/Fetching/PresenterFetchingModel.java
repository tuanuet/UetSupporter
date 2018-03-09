package vnu.uet.tuan.uetsupporter.Presenter.Fetching;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Model.Response.DataSync;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by FRAMGIA\vu.minh.tuan on 09/03/2018.
 */

public class PresenterFetchingModel implements IPresenterFetchingModel, Callback<DataSync> {
    private final String TAG = this.getClass().getSimpleName();
    private Call<DataSync> call;
    private OnFinishedListener listener;
    private ApiTinTuc apiTinTuc;
    private Context context;

    public PresenterFetchingModel(Context context){
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiTinTuc = retrofit.create(ApiTinTuc.class);

    }
    @Override
    public void fetching(String lastTimeAnnouncement, String[] kindAnnouncements,String lastTimeNew, String[] kindNews, OnFinishedListener listener) {
        this.listener = listener;

        Map<String, Object> map = new ArrayMap<>();
        map.put("kindAnnouncements",kindAnnouncements);
        map.put("kindNews",kindNews);
        map.put("lastTimeAnnouncement",lastTimeAnnouncement);
        map.put("lastTimeNew",lastTimeNew);

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(map)).toString());
        call = apiTinTuc.syncServer(body);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<DataSync> call, Response<DataSync> response) {
        if(response.code() < 400 && response.isSuccessful()){
            listener.OnSuccess(response.body());
        }else {
            listener.OnFailure(context.getResources().getString(R.string.fail_download));
        }

    }

    @Override
    public void onFailure(Call<DataSync> call, Throwable t) {
        Log.e(TAG,t.toString());
        listener.OnFailure(context.getResources().getString(R.string.fail_download));
    }
}
