package vnu.uet.tuan.uetsupporter.config;

import okhttp3.MediaType;

/**
 * Created by Administrator on 12/01/2017.
 */


public class Config {
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String TENSINHVIEN = "name";
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
    public static final String POSITION_LOPMONHOC = "positionlopmonhoc";
    public static final String POSITION_EMAIL = "positionemail";
    public static final String FOLDER_EMAIL = "folderemail";
    public static final String SAVE = "save";
    public static final String ID_LOPMONHOC = "idlopmonhoc";
    public static final String POSITION_NOTIFICATION = "positionnotification";
    public static final String DETAILTHONGBAO = "detailthongbao";
    public static final int ACTION_EMAIL = 3;


    /**
     * ===================================================
     */
    public static String hostname = "http://192.168.18.99:3000";
//    public static String hostname = "http://ec2-18-218-196-243.us-east-2.compute.amazonaws.com";

    public static final String KEY_URL = "keyurl";
    public static final String GET_INFORMATION_USER = hostname + "/sinhvien/profile";
    public static final String POST_TOKEN = hostname + "/student/savetoken";
    public static final String API_HOSTNAME = hostname;
    public static final String LOGIN_URL = hostname + "/api/authenticate";
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

    /**
     * Mail Box
     */
    public enum MailBox {
        Inbox,
        Drafts,
        Sent,
        Trash
    }

    /**
     * SAVE PREFERENCE
     */

    public static final String REGISTER_NEWS = "register_new";
    public static final String REGISTER_ANNOUNCES = "register_announce";
    public enum ReactionCode   {
        ANGRY, CRY, LOVE, WOW, SURPRISE
    }

}
