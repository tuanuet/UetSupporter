package vnu.uet.tuan.uetsupporter.Presenter.Profile.DetailLopMonHoc;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Model.Response.CourseInformation;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by vmtuan on 3/25/2017.
 */

public class PresenterDetailLopMonHocModel implements IPresenterDetailLopMonHocModel, Callback<CourseInformation> {
    private Context context;
    private OnLoadCourseinformationFinish listener;
    private Call<CourseInformation> call;
    private String TAG = this.getClass().getSimpleName();

    public PresenterDetailLopMonHocModel(Context context) {
        this.context = context;
    }

    @Override
    public void getDataFromModel(String courseId, OnLoadCourseinformationFinish listener) {
        this.listener = listener;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.getInfoCourse(courseId,Utils.getUserToken(context));
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<CourseInformation> call, Response<CourseInformation> response) {
        if(response.isSuccessful()){
            Log.e(TAG,response.message());
            listener.OnLoadingSuccess(response.body());
        }else {
            listener.OnLoadingFailure(context.getString(R.string.fail_download));
        }
    }

    @Override
    public void onFailure(Call<CourseInformation> call, Throwable t) {
        listener.OnLoadingFailure(t.getMessage());
    }
}
