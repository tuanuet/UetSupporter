package vnu.uet.tuan.uetsupporter.Retrofit;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vnu.uet.tuan.uetsupporter.Model.Download.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.Download.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.Model.Download.MucDoThongBao;
import vnu.uet.tuan.uetsupporter.Model.Response.CourseInformation;
import vnu.uet.tuan.uetsupporter.Model.Response.DataSync;
import vnu.uet.tuan.uetsupporter.Model.Response.DiemResponse;
import vnu.uet.tuan.uetsupporter.Model.Response.Feedback;
import vnu.uet.tuan.uetsupporter.Model.Response.Message;
import vnu.uet.tuan.uetsupporter.Model.Response.ReactionResponse;
import vnu.uet.tuan.uetsupporter.Model.Student;
import vnu.uet.tuan.uetsupporter.Model.Response.TinTuc;

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

    @GET("/api/student/profile")
    Call<Student> getInformationSinhVien(@Header("Authorization") String authorization);

    @GET("/api/mark/{idCourse}")
    Call<DiemResponse> getDiemSinhVienByIdLop(@Path("idCourse") String link, @Header("Authorization") String authorization);

    @GET("/api/thongbao/{id}")
    Call<ResponseBody> getDetailThongBao(@Path("id") String id, @Header("Authorization") String authorization);

    @POST("/api/logout")
    Call<Message> postLogout(@Header("Authorization") String authorization);

    @GET("/api/info/course/{id}")
    Call<CourseInformation> getInfoCourse(@Path("id") String id, @Header("Authorization") String authorization);

    @GET("/test/feedback")
    Call<List<Feedback>> getFeedBackByAnnouncementId(@Query("announcementId") String announcementId, @Header("Authorization") String authorization);

    @POST("/api/fetching/news-announcements")
    Call<DataSync> syncServer(@Body RequestBody params);

    @POST("/api/reaction")
    Call<Message> postReaction(@Body RequestBody params,@Header("Authorization") String authorization);

    @POST("/api/fetch/reaction")
    Call<ArrayList<ReactionResponse>> fetchReaction(@Body RequestBody params, @Header("Authorization") String authorization);
}
