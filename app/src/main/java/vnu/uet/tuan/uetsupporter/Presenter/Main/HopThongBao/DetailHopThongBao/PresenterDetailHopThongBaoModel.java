package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.DetailHopThongBao;

import android.content.Context;
import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vnu.uet.tuan.uetsupporter.Model.DetailThongBao;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by vmtuan on 3/24/2017.
 */

public class PresenterDetailHopThongBaoModel implements IPresenterDetailHopThongBaoModel, Callback<ResponseBody> {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private Call<ResponseBody> call;
    private OnGetThongBaoFinish onGetThongBaoFinish;
    private OnCancelGetThongBaoFinish onCancelGetThongBaoFinish;

    public PresenterDetailHopThongBaoModel(Context context) {
        this.context = context;
    }


    @Override
    public void getDetailHopThongBao(String link, OnGetThongBaoFinish listener) {
        this.onGetThongBaoFinish = listener;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.getDetailThongBao(link, Utils.getUserToken(context));
        call.enqueue(this);
    }


    @Override
    public void cancelGetDetailHopThongBao(OnCancelGetThongBaoFinish listener) {
        this.onCancelGetThongBaoFinish = listener;
        call.cancel();
        if (call.isCanceled()) {
            listener.OnCancelSuccess();
        } else {
            listener.OnCancelFailure();
        }
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful() && response.body() != null) {
            try {
                String json = response.body().string();
                Log.e(TAG,json);
                onGetThongBaoFinish.OnSuccess(new DetailThongBao(json));
            } catch (Exception e) {
                onGetThongBaoFinish.OnFailure(e.getMessage());
            }
        } else {
            onGetThongBaoFinish.OnFailure(context.getString(R.string.fail_download));
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        onGetThongBaoFinish.OnFailure(t.getMessage());
    }
}
