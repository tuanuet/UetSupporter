package vnu.uet.tuan.uetsupporter.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vnu.uet.tuan.uetsupporter.Model.GiangVien;
import vnu.uet.tuan.uetsupporter.Model.Download.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.Download.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.Model.LopMonHoc;
import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.Contract;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.LoaiThongBaoSQLHelper;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.LoaiTinTucSQLHelper;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.PushNotificationSQLHelper;
import vnu.uet.tuan.uetsupporter.config.Config;

import static vnu.uet.tuan.uetsupporter.config.Config.JSON;

/**
 * Created by Administrator on 14/01/2017.
 */

public class Utils {
    public static ArrayList<String> getMultipleListSetting(Context context,String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Set set = sharedPreferences.getStringSet(key,null);
        ArrayList listString = new ArrayList();
        listString.addAll(set);

        return listString;
    }

    public static String getUsername(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String str = sharedPreferences.getString(Config.TENSINHVIEN, null);
        return str;
    }

    public static Boolean getBoolenSetting(Context context,String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean aBoolean = sharedPreferences.getBoolean(key,false);
        return aBoolean;
    }
    public static String getStringSetting(Context context,String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String str = sharedPreferences.getString(key,null);
        return str;
    }
    public static int getIntegerSetting(Context context,String key){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int str = sharedPreferences.getInt(key,-1);
        return str;
    }
    public static String getUserToken(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Config.USER_TOKEN,null);
    }

    public static Uri getSoundNotification(Context context) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        String strRingtonePreference = preference.getString(context.getString(R.string.sound_notification),
                context.getString(R.string.sound_notification_default));
        return Uri.parse(strRingtonePreference);
    }
    public static String getFirebaseToken(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Config.FIREBASE_TOKEN,null);
    }

    public static String getEmailUser(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Config.EMAIL,null);
    }

    public static Boolean canGetFirebaseToken(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(Config.CAN_BE_FIREBASE_TOKEN, false);
    }

    public static String getJSONFromSever(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getJSONFromSever(String stringbody,String url) {
        //post email and password
        OkHttpClient client = new OkHttpClient();
        String json = stringbody;

        Log.e("json", json);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = null;

        try {
            response = client.newCall(request).execute();
            String jsonObj = response.body().string();
            Log.e("response", jsonObj);

            return jsonObj;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<LoaiTinTuc> getAllLoaiTinTuc(Context context){
        LoaiTinTucSQLHelper db = new LoaiTinTucSQLHelper(context);
        Cursor cursor = db.getAll();
        ArrayList<LoaiTinTuc> list = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiTinTuc loaiTinTuc = new LoaiTinTuc();
            loaiTinTuc.set_id(cursor.getInt(Contract.LoaiTinTuc._id));
            loaiTinTuc.setLinkPage(cursor.getString(Contract.LoaiTinTuc.linkpage));
            loaiTinTuc.setKind(cursor.getString(Contract.LoaiTinTuc.kind));
            list.add(loaiTinTuc);
            cursor.moveToNext();
        }
        return list;
    }

    public static ArrayList<LoaiThongBao> getAllLoaiThongBao(Context context) {
        LoaiThongBaoSQLHelper db = new LoaiThongBaoSQLHelper(context);
        Cursor cursor = db.getAll();
        ArrayList<LoaiThongBao> list = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LoaiThongBao loaiThongBao = new LoaiThongBao();
            loaiThongBao.set_id(cursor.getInt(Contract.LoaiThongBao._id));
            loaiThongBao.setTenLoaiThongBao(cursor.getString(Contract.LoaiThongBao.tenLoaiThongBao));
            list.add(loaiThongBao);
            cursor.moveToNext();
        }
        return list;
    }

    public static ArrayList<PushNotification> getPushNotification(Context context) {
        PushNotificationSQLHelper db = new PushNotificationSQLHelper(context);
        Cursor cursor = db.getArrayPushNotification();

        ArrayList<PushNotification> list = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PushNotification pushNotification = new PushNotification();
            pushNotification.setTieuDe(cursor.getString(Contract.PushNotification.tieu_de));
            pushNotification.setNoiDung(cursor.getString(Contract.PushNotification.noi_dung));
            pushNotification.setKind(cursor.getInt(Contract.PushNotification.kind));
            pushNotification.setLink(cursor.getString(Contract.PushNotification.link_page));
            pushNotification.setThoiGianNhan(cursor.getString(Contract.PushNotification.thoi_gian_nhan));
            int isRead = cursor.getInt(Contract.PushNotification.is_read);
            if (isRead == 1) {
                pushNotification.setRead(true);
            } else {
                pushNotification.setRead(false);
            }
            list.add(pushNotification);
            cursor.moveToNext();
        }
        return list;
    }

    public static ArrayList<Integer> getArrayFromString(String s){
        ArrayList<Integer> arr = new ArrayList<>();
        s = s.trim().substring(1,s.length()-1); //bo di dau []
        Log.e("utils",s);
        String[] list = s.split(",");
        for (int i = 0; i < list.length; i++) {
            arr.add(Integer.valueOf(list[i].trim()));
            Log.e("utils",arr.get(i)+"");
        }
        return arr;

    }

    public static String getUrlWithToken(String url,Context context){
        String s = url + "?token="+ getUserToken(context);
        return s;
    }

    public static String getTenGiangVien(List<GiangVien> list) {
        String str = "Giảng viên: ";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                str += list.get(i).getTenGiangVien();
            } else {
                str += list.get(i).getTenGiangVien() + ", ";
            }
        }
        return str;
    }

    public static String getTenLopMonHoc(LopMonHoc lopMonHoc) {
        String str = "Lớp: ";

        return str + "(" + lopMonHoc.get_id() + ") " + lopMonHoc.getTenLopMonHoc();

    }

    public static String getThoiGian(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date date = new Date(s);
        return sdf.format(date);
    }

    public static String getThoiGian(long i) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(i);
        return sdf.format(resultdate);
    }

    public static boolean isRunFirstTime(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(Config.IS_RUN_FIRST_TIME, true);
    }

    public static int getNumberOnNav(Context context) {
        PushNotificationSQLHelper db = new PushNotificationSQLHelper(context);
        return db.getCountNotificationNotRead();
    }
}
