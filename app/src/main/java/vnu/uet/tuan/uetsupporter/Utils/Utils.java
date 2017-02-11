package vnu.uet.tuan.uetsupporter.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vnu.uet.tuan.uetsupporter.Model.GiangVien;
import vnu.uet.tuan.uetsupporter.Model.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.Model.LopMonHoc;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.Contract;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.LoaiThongBaoSQLHelper;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.LoaiTinTucSQLHelper;
import vnu.uet.tuan.uetsupporter.config.Config;

import static vnu.uet.tuan.uetsupporter.config.Config.JSON;
import static vnu.uet.tuan.uetsupporter.config.Config.LOGIN_URL;

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
        Cursor cursor = db.getArrayLoaiTinTuc();
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
        Cursor cursor = db.getArrayLoaiThongBao();
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

        return s;
    }
}
