package vnu.uet.tuan.uetsupporter.Retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vnu.uet.tuan.uetsupporter.Model.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.Model.TinTuc;

/**
 * Created by Administrator on 19/01/2017.
 */

public interface ApiTinTuc {
    @GET("/tintuc/test")
    Call<ArrayList<TinTuc>> getDataTinTuc(@Query("loaitintuc") int loaitintuc);

    @GET("/loaitintuc")
    Call<LoaiTinTuc[]> getAllLoaiTinTuc();
}
