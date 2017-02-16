package vnu.uet.tuan.uetsupporter.SQLiteHelper;

import android.provider.BaseColumns;

/**
 * Created by Vu Minh Tuan on 2/4/2017.
 */

public class Contract {
    public class LoaiTinTuc implements BaseColumns{
        public static final int _id = 0;
        public static final int kind = 2;
        public static final int linkpage = 1;
        public static final String NAME_TABLE = "loaitintuc";
        public static final String _ID = "_id";
        public static final String KIND = "kind";
        public static final String LINKPAGE = "linkPage";
        public static final int VERSION = 1;
    }

    public class LoaiThongBao implements BaseColumns {
        public static final int _id = 0;
        public static final int tenLoaiThongBao = 1;
        public static final String NAME_TABLE = "loaithongbao";
        public static final String _ID = "_id";
        public static final String TENLOAITHONGBAO = "tenloaithongbao";
        public static final int VERSION = 1;
    }

    public class PushNotification implements BaseColumns {
        public static final int _id = 0;
        public static final int tieu_de = 1;
        public static final int noi_dung = 2;
        public static final int kind = 3;
        public static final int link_page = 4;
        public static final int is_read = 5;
        public static final int thoi_gian_nhan = 6;
        public static final int VERSION = 1;

        public static final String NAME_TABLE = "pushnotification";
        public static final String _ID = "id";
        public static final String TIEU_DE = "tieude";
        public static final String NOI_DUNG = "noidung";
        public static final String KIND = "loai";
        public static final String LINK = "link";
        public static final String ISREAD = "isread";
        public static final String THOIGIANNHAN = "thoigiannhan";

    }
}
