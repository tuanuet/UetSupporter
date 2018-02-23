package vnu.uet.tuan.uetsupporter.Retrofit;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vnu.uet.tuan.uetsupporter.Model.Download.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.Download.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.Model.Download.MucDoThongBao;
import vnu.uet.tuan.uetsupporter.Model.Response.DiemResponse;
import vnu.uet.tuan.uetsupporter.Model.Response.Message;
import vnu.uet.tuan.uetsupporter.Model.SinhVien;
import vnu.uet.tuan.uetsupporter.Model.Subcribe;
import vnu.uet.tuan.uetsupporter.Model.Response.TinTuc;
import vnu.uet.tuan.uetsupporter.Model.DetailThongBao;

/**
 * Created by Administrator on 19/01/2017.
 */

public interface ApiTinTuc {
    @GET("/api/courses")
    Call<String[]> getCourseIds(@Header("Authorization") String authorization);

    @GET("/api/news")
    Call<ArrayList<TinTuc>> getDataTinTuc(@Query("loaitintuc") String loaitintuc, @Query("offset") int offset);

    @GET("/api/loaitintuc")
    Call<ArrayList<LoaiTinTuc>> getAllLoaiTinTuc();

    @GET("/api/loaithongbao")
    Call<List<LoaiThongBao>> getAllLoaiThongBao();

    @GET("/api/mucdothongbao")
    Call<List<MucDoThongBao>> getAllMucDoThongBao();

    @GET("/sinhvien/profile")
    Call<SinhVien> getInformationSinhVien(@Header("Authorization") String authorization);

    @GET("/api/mark/{idCourse}")
    Call<List<DiemResponse>> getDiemSinhVienByIdLop(@Path("idCourse") String link, @Header("Authorization") String authorization);

    @GET("/api/thongbao/{id}")
    Call<ResponseBody> getDetailThongBao(@Path("id") String id, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("/sinhvien/deletefirebasetoken")
    Call<Message> postToDeleteTokenFirebase(@Field("firebaseToken") String firebaseToken, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("/sinhvien/guifeedback/{id}")
    Call<Message> postComment(@Field("noiDung") String noiDung,
                              @Path("id") String thongbaoid, @Header("Authorization") String authorization);

}
