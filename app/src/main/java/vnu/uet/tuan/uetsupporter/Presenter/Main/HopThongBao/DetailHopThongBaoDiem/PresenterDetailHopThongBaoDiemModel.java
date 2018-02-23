package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.DetailHopThongBaoDiem;

import android.content.Context;
import android.util.Log;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Model.DetailThongBao;
import vnu.uet.tuan.uetsupporter.Model.Response.DiemResponse;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by vmtuan on 3/24/2017.
 */

public class PresenterDetailHopThongBaoDiemModel implements IPresenterDetailHopThongBaoDiemModel, Callback<List<DiemResponse>> {

    private Context context;
    private Call<List<DiemResponse>> call;
    private OnGetThongBaoDiemFinish onGetThongBaoDiemFinish;

    private final String TAG = this.getClass().getSimpleName();

    public PresenterDetailHopThongBaoDiemModel(Context context) {
        this.context = context;
    }


    @Override
    public void getDetailHopThongBaoDiem(String link, OnGetThongBaoDiemFinish listener) {
        this.onGetThongBaoDiemFinish = listener;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.getDiemSinhVienByIdLop(link, Utils.getUserToken(context));
        call.enqueue(this);
    }


    @Override
    public void cancelGetDetailHopThongBaoDiem(OnCancelGetThongBaoDiemFinish listener) {
        call.cancel();
        if (call.isCanceled()) {
            listener.OnCancelSuccess();
        } else {
            listener.OnCancelFailure();
        }
    }


    @Override
    public void onResponse(Call<List<DiemResponse>> call, Response<List<DiemResponse>> response) {
        if (response.isSuccessful() && response.body() != null) {
            try {
                onGetThongBaoDiemFinish.OnSuccess(response.body());
            } catch (Exception e) {
                onGetThongBaoDiemFinish.OnFailure(e.getMessage());
            }
        } else {
            onGetThongBaoDiemFinish.OnFailure(context.getString(R.string.fail_download));
        }
    }

    @Override
    public void onFailure(Call<List<DiemResponse>> call, Throwable t) {
        onGetThongBaoDiemFinish.OnFailure(t.getMessage());
    }
}
