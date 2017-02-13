package vnu.uet.tuan.uetsupporter.Retrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import vnu.uet.tuan.uetsupporter.Model.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.Model.Message;
import vnu.uet.tuan.uetsupporter.Model.SinhVien;
import vnu.uet.tuan.uetsupporter.Model.Subcribe;
import vnu.uet.tuan.uetsupporter.Model.TinTuc;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by Administrator on 19/01/2017.
 */

public interface ApiTinTuc {
    @GET("/tintuc/test")
    Call<ArrayList<TinTuc>> getDataTinTuc(@Query("loaitintuc") int loaitintuc, @Query("offset") int offset);

    @GET("/loaitintuc")
    Call<ArrayList<LoaiTinTuc>> getAllLoaiTinTuc();

    @GET("/loaithongbao")
    Call<List<LoaiThongBao>> getAllLoaiThongBao();

    @GET("/subscribe")
    Call<Subcribe> getSubcribeLoaiTinTuc(@Header("Authorization") String authorization);

    @GET("/sinhvien/profile")
    Call<SinhVien> getInformationSinhVien(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("/sinhvien/guiloaitintuc")
    Call<Message> postLoaiTinTuc(@Field("srrayObj") String arr, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("/sinhvien/guiloaithongbao")
    Call<Message> postLoaiThongBao(@Field("srrayObj") String arr, @Header("Authorization") String authorization);
}
