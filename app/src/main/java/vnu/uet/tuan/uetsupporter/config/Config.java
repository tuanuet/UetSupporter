package vnu.uet.tuan.uetsupporter.config;

import okhttp3.MediaType;

/**
 * Created by Administrator on 12/01/2017.
 */

public class Config {
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String TENSINHVIEN = "tenSinhVien";
    public static final String SINHVIEN = "sinhvien";
    public static final String KEY_PUSHNOTIFICATION = "key_pushnotification";


    public static final String FIREBASE_TOKEN = "firebase_token";

    public static final int IDNOTICATION = 9011;
    public static final String USER_TOKEN = "user_token";
    public static final String CAN_BE_FIREBASE_TOKEN = "can be firebase token";
    public static final String IS_RUN_FIRST_TIME = "run first time";
    /**í
     * ===================================================
     */
    public static final int daotao = 0;
    public static final int tuyendung = 7;


    /**
     * ===================================================
     */
    public static String hostname = "http://192.168.0.2:3000";

    public static final String KEY_URL = "keyurl";
    public static final String GET_INFORMATION_USER = hostname + "/sinhvien/profile";
    public static final String POST_TOKEN = hostname + "/sinhvien/guitokenfirebase";
    public static final String API_HOSTNAME = hostname;
    public static final String LOGIN_URL = hostname + "/users/authenticate";
    public static final String GET_LOAITINTUC = hostname+"/loaitintuc";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    /**
     * ===================================================
     */
    public static final String ACTION_CHITIET = "vnu.uet.tuan.uetsupporter.chitiet";
    public static final String ACTION_XEMSAU = "vnu.uet.tuan.uetsupporter.xemsau";
    public static final String ACTION_DAXEM = "vnu.uet.tuan.uetsupporter.daxem";
    public static final String ACTION_CLOSE = "vnu.uet.tuan.uetsupporter.close";

}
