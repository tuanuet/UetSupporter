package vnu.uet.tuan.uetsupporter.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vnu.uet.tuan.uetsupporter.Model.Course;
import vnu.uet.tuan.uetsupporter.Model.Download.MucDoThongBao;
import vnu.uet.tuan.uetsupporter.Model.Download.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.Download.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.Model.Lecture;
import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.Contract;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.EmailSQLHelper;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.LoaiThongBaoSQLHelper;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.LoaiTinTucSQLHelper;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.MucDoThongBaoSQLHelper;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.PushNotificationSQLHelper;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.SQLFather;
import vnu.uet.tuan.uetsupporter.config.Config;

import static vnu.uet.tuan.uetsupporter.config.Config.JSON;

/**
 * Created by Administrator on 14/01/2017.
 */

public class Utils {
    private static final String TAG = "Utils";


    public static String getLastTimeAnnouncement(Context context){
        PushNotificationSQLHelper db = new PushNotificationSQLHelper(context);
        return db.lastestNotification();
    }
    public static String snakeCase(String str) {
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("")
                .replaceAll("Đ", "D")
                .replace("đ", "")
                .replaceAll(" ","_")
                .replaceAll(",","_")
                .replaceAll("__","_")
                .toLowerCase();
    }

    public static String tranformTags(String[] tags) {
        String joinner = "";
        for (String tag : tags){
            joinner += (tag + " | ");
        }
        return joinner.trim().substring(0,joinner.trim().length()-1).trim();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
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

    public static String getPassword(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String str = sharedPreferences.getString(Config.PASSWORD, null);
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
        return sharedPreferences.getString(Config.USER_TOKEN, null);
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

    public static String getJSONFromSever(String stringbody, String url) throws Exception {
        //post email and password
        OkHttpClient client = new OkHttpClient();
        String json = stringbody;

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String jsonObj = response.body().string();

        return jsonObj;
    }

    public static ArrayList<LoaiTinTuc> getAllLoaiTinTuc(Context context){
        LoaiTinTucSQLHelper db = new LoaiTinTucSQLHelper(context);
        Cursor cursor = db.getAll();
        ArrayList<LoaiTinTuc> list = new ArrayList<LoaiTinTuc>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiTinTuc loaiTinTuc = new LoaiTinTuc();
            loaiTinTuc.set_id(cursor.getString(Contract.LoaiTinTuc._id));
            loaiTinTuc.setKind(cursor.getString(Contract.LoaiTinTuc.kind));
            list.add(loaiTinTuc);
            cursor.moveToNext();
        }
        return list;
    }

    public static float getDiemTong(double diemThanhPhan, double diemCuoiKy) {
        return (float) (0.6 * diemCuoiKy + 0.4 * diemThanhPhan);
    }
    public static ArrayList<LoaiThongBao> getAllLoaiThongBao(Context context) {
        LoaiThongBaoSQLHelper db = new LoaiThongBaoSQLHelper(context);
        Cursor cursor = db.getAll();
        ArrayList<LoaiThongBao> list = new ArrayList<LoaiThongBao>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LoaiThongBao loaiThongBao = new LoaiThongBao();
            loaiThongBao.set_id(cursor.getString(Contract.LoaiThongBao._id));
            loaiThongBao.setTenLoaiThongBao(cursor.getString(Contract.LoaiThongBao.tenLoaiThongBao));
            list.add(loaiThongBao);
            cursor.moveToNext();
        }
        return list;
    }

    public static ArrayList<AnnouncementNotification> getPushNotification(Context context) {
        PushNotificationSQLHelper db = new PushNotificationSQLHelper(context);
        Cursor cursor = db.getArrayPushNotification();

        ArrayList<AnnouncementNotification> list = new ArrayList<AnnouncementNotification>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AnnouncementNotification announcementNotification = new AnnouncementNotification();
            announcementNotification.setTieuDe(cursor.getString(Contract.PushNotification.tieu_de));
            announcementNotification.setNoiDung(cursor.getString(Contract.PushNotification.noi_dung));
            announcementNotification.setLink(cursor.getString(Contract.PushNotification.link_page));
            announcementNotification.setThoiGianNhan(cursor.getString(Contract.PushNotification.thoi_gian_nhan));
            announcementNotification.setIdLoaiThongBao(cursor.getString(Contract.PushNotification.id_loai_thong_bao));
            announcementNotification.setIdMucDoThongBao(cursor.getString(Contract.PushNotification.id_muc_do_thong_bao));
            announcementNotification.setCodeMucDoThongBao(cursor.getString(Contract.PushNotification.code_muc_do_thong_bao));
            announcementNotification.setHasFile(cursor.getInt(Contract.PushNotification.has_file) == 1);
            announcementNotification.setIdSender(cursor.getString(Contract.PushNotification.id_sender));
            announcementNotification.setNameSender(cursor.getString(Contract.PushNotification.name_sender));
            announcementNotification.setRead(cursor.getInt(Contract.PushNotification.is_read) == 1);
            announcementNotification.setTypeNotification(cursor.getInt(Contract.PushNotification.type_notification));
            announcementNotification.setDescription(cursor.getString(Contract.PushNotification.description));
            announcementNotification.set_id(cursor.getString(Contract.PushNotification.server_id));
            announcementNotification.setDescriptionImages(convertStringToArray(cursor.getString(Contract.PushNotification.description_images)));
            list.add(announcementNotification);
            cursor.moveToNext();
        }
        return list;
    }

    public static String[] convertStringToArray(String stringArray){
        if(stringArray.substring(1,stringArray.length()-1).equals("")){
            return new String[0];
        }else {
            String[] newS = stringArray.substring(1,stringArray.length()-1).split(",");
            String[] result = new String[newS.length];
            for (int i = 0; i < newS.length; i++) {
                result[i] = newS[i].trim();
            }
            return result;
        }
    }

    public static String[] convertStringToArrayFromServer(String stringArray){
        if(stringArray.equals("")){
            return new String[0];
        }else {
            String[] newS = stringArray.split(",");
            String[] result = new String[newS.length];
            for (int i = 0; i < newS.length; i++) {
                result[i] = newS[i].trim();
            }
            return result;
        }
    }
    public static ArrayList<Integer> getArrayFromString(String s){
        ArrayList<Integer> arr = new ArrayList<Integer>();
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

    public static String getTenGiangVien(List<Lecture> list) {
        if(list.size() != 0) {
            String str = "Giảng viên: ";
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    str += list.get(i).getName();
                } else {
                    str += list.get(i).getName() + ", ";
                }
            }
            return str;
        } else {
            return "";
        }

    }

    public static String getTenLopMonHoc(Course lopMonHoc) {
        String str = "Lớp: ";

        return str + "(" + lopMonHoc.get_id() + ") " + lopMonHoc.getName();

    }
    public static String getThoiGian(Context context, String strDate) {
        DateTime date = new DateTime();

        if (strDate != null) {
            date = new DateTime(strDate);
        }
        int diffInDays = (int)( (new Date().getTime() - date.getMillis())
                / (1000 * 60 * 60 * 24) );
        if(diffInDays == 1){
            return context.getString(R.string.string_yesterday);
        } else if(diffInDays == 0) {
            if(new DateTime().getDayOfMonth() != date.getDayOfMonth()){
                return context.getString(R.string.string_yesterday);
            }else {
                return date.toString("hh:mm a");
            }
        } else {
            return date.toString("dd 'Thg' M YYYY");
        }

    }

    public static String getTimeEmail(Context context, Date date) {

        int diffInDays = (int)( (new Date().getTime() - date.getTime())
                / (1000 * 60 * 60 * 24) );
        if(diffInDays == 1){
            return context.getString(R.string.string_yesterday);
        } else if(diffInDays == 0) {
            SimpleDateFormat ft =
                    new SimpleDateFormat ("hh:mm a");
            return ft.format(date);
        } else {
            SimpleDateFormat ft =
                    new SimpleDateFormat ("dd 'Thg' M");
            return ft.format(date);
        }

    }

    public static String getThoiGian(long i) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

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


    public static ArrayList<MucDoThongBao> getAllMucDoThongBao(Context context) {
        SQLFather sql = new MucDoThongBaoSQLHelper(context);
        Cursor cursor = sql.getAll();
        ArrayList<MucDoThongBao> list = new ArrayList<MucDoThongBao>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MucDoThongBao mucDoThongBao = new MucDoThongBao();
            mucDoThongBao.set_id(cursor.getString(Contract.MucDoThongBao._id));
            mucDoThongBao.setTenMucDoThongBao(cursor.getString(Contract.MucDoThongBao.tenMucDoThongBao));
            list.add(mucDoThongBao);
            cursor.moveToNext();
        }
        return list;
    }

//    public static int renderColorMucDo(String idMucDoThongBao, ArrayList<MucDoThongBao> mucDoThongBaoList) {
//        for (MucDoThongBao item : mucDoThongBaoList) {
//            if (idMucDoThongBao == item.get_id()) {
//                if (item.get_id() == 1)
//                    return Color.RED;           //QuAN TROGG
//                else if (item.get_id() == 2)
//                    return Color.YELLOW;          //KhanCap
//                else if (item.get_id() == 3)
//                    return Color.BLUE;        // binh thuong
//            }
//        }
//        return 0;
//    }

    public static String getFirstChar(String title) {
        if (title.contains("@")) {
            String before = title.split("@")[0];
            if (!before.contains("1")) {
                return String.valueOf(before.trim().charAt(0));
            } else {
                Random r = new Random();
                return String.valueOf((char) (r.nextInt(26) + 'a'));
            }
        } else {
            return String.valueOf(title.trim().charAt(0));
        }

    }

    public static int getRandomColor(String username) {
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color2 = generator.getColor(username);
        return color2;
    }

    public static int getRandomColor(Context context, String typeColor) {

        int returnColor = Color.BLACK;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + typeColor, "array", context.getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }

    public static ArrayList<Email> getAllEmail(Cursor cs) {
        ArrayList<Email> list = new ArrayList<Email>();
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            Email email = new Email();
            email.setPosition(cs.getInt(Contract.Email.id));
            email.setFrom(cs.getString(Contract.Email.from));
            email.setContent(cs.getString(Contract.Email.content));
            email.setHasFile(cs.getInt(Contract.Email.hasfile) == 1);
            email.setTitle(cs.getString(Contract.Email.title));
            email.setRead(cs.getInt(Contract.Email.isread) == 1);
            email.setSendDate(cs.getString(Contract.Email.sendDate));
            email.setReceiveDate(new Date(cs.getString(Contract.Email.receivedDate)));
            email.setFolder(cs.getString(Contract.Email.folder));
            email.setImportance(cs.getString(Contract.Email.importance));
            list.add(email);
            cs.moveToNext();
        }
        return list;
    }

    public static int getPixelFromDP(Context context, int dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = (int) (dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static boolean clearNotification(Activity activity) {
        PushNotificationSQLHelper helper = new PushNotificationSQLHelper(activity);
        try {
            helper.clearDb();
            return true;
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
            return false;
        }
    }

    public static boolean clearEmail(Activity activity) {
        EmailSQLHelper helper = new EmailSQLHelper(activity);
        try {
            helper.clearDb();
            return true;
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
            return false;
        }
    }

    public static String[] getKindAnnouncements(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String registerAnnouncementString = sharedPreferences.getString(Config.REGISTER_ANNOUNCES,"[]");
        return convertStringToArray(registerAnnouncementString);
    }

    public static String[] getKindNews(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String registerNewString = sharedPreferences.getString(Config.REGISTER_NEWS,"[]");
        return convertStringToArray(registerNewString);
    }

    public static String getLastTimeNew(Context context) {
        return new DateTime().toString();
    }

    public static String[] getIdAnnouncements(ArrayList<AnnouncementNotification> list) {
        String[] ids = new String[list.size()];
        for (int i= 0; i< list.size();i++){
            ids[i]= list.get(i).get_id();
        }
        return  ids;
    }

    public static int getTotalReaction(AnnouncementNotification notification) {
        return (notification.getAngry() != null ? notification.getAngry().getLength() : 0)
                + (notification.getCry() != null ? notification.getCry().getLength() : 0)
                + (notification.getLove() != null ? notification.getLove().getLength() : 0)
                + (notification.getWow() != null ? notification.getWow().getLength() : 0)
                + (notification.getSurprise() != null ? notification.getSurprise().getLength() : 0);
    }
}
