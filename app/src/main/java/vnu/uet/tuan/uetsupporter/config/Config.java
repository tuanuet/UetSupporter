package vnu.uet.tuan.uetsupporter.config;

import okhttp3.MediaType;

/**
 * Created by Administrator on 12/01/2017.
 */

public class Config {
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String FIREBASE_TOKEN = "firebase_token";

    public static final int IDNOTICATION = 9011;
    public static final String USER_TOKEN = "user_token";
    /**
     * ===================================================
     */
    public static final int daotao = 0;
    public static final int tuyendung = 7;
    /**
     * ===================================================
     */

    public static final String KEY_URL = "keyurl";

    public static final String POST_TOKEN = "http://192.168.1.3:3000/users/tokenfirebase";
    public static final String API_HOSTNAME = "http://192.168.1.3:3000";
    public static final String LOGIN_URL = "http://192.168.1.3:3000/users/authenticate";


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public static final String CAN_BE_FIREBASE_TOKEN = "can be firebase token";
}
