package vnu.uet.tuan.uetsupporter.Presenter.Profile.MainProfile;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Listener.OnCancelRequest;
import vnu.uet.tuan.uetsupporter.Model.SinhVien;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by vmtuan on 3/25/2017.
 */

public class PresenterMainProfileModel implements IPresenterMainProfileModel, Callback<SinhVien> {
    private Context context;
    private Call<SinhVien> call;
    private OnLoadStudentFinish listener;

    public PresenterMainProfileModel(Context context) {
        this.context = context;
    }

    @Override
    public void executeLoadStudent(String token, OnLoadStudentFinish listener) {
        this.listener = listener;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.getInformationSinhVien(Utils.getUserToken(context));
        call.enqueue(this);
    }

    @Override
    public void cancelLoadStudent(OnCancelRequest cancelRequest) {
        call.cancel();
        if (call.isCanceled()) {
            cancelRequest.OnCancelSuccess(context.getString(R.string.cancel_success));
        } else {
            cancelRequest.OnCancelFailure(context.getString(R.string.cancel_failure));
        }
    }

    @Override
    public void onResponse(Call<SinhVien> call, Response<SinhVien> response) {
        try {
            if (response.isSuccessful() && response.body() != null) {
                listener.OnLoadingSuccess(response.body());
            } else {
                listener.OnLoadingFailure(context.getString(R.string.fail_download));
            }
        } catch (Exception e) {
            listener.OnLoadingFailure(e.getMessage());
        }

    }

    @Override
    public void onFailure(Call<SinhVien> call, Throwable t) {
        listener.OnLoadingFailure(t.getMessage());
    }
}
