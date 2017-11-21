package vnu.uet.tuan.uetsupporter.SQLiteHelper;

import android.provider.BaseColumns;

/**
 * Created by Vu Minh Tuan on 2/4/2017.
 */

public class Contract {
    public class Email implements BaseColumns {
        public static final int id = 0;
        public static final int from = 1;
        public static final int sendDate = 2;
        public static final int receivedDate = 3;
        public static final int importance = 4;
        public static final int title = 5;
        public static final int content = 6;
        public static final int hasfile = 7;
        public static final int isread = 8;
        public static final int folder = 9;

        public static final String NAME_TABLE = "email";
        public static final String _ID = "_id";
        public static final String FROM = "fromemail";
        public static final String SEND_DATE = "sendate";
        public static final String RECEIVEDDATE = "receiveddate";
        public static final String IMPORTANCE = "importance";
        public static final String TITLE = "title";
        public static final String CONTENT = "content";
        public static final String HASFILE = "hasfile";
        public static final String ISREAD = "isread";
        public static final String FOLDER = "folder";
        public static final int VERSION = 1;
    }

    public class LoaiTinTuc implements BaseColumns{
        public static final int _id = 0;
        public static final int kind = 2;
        public static final int linkpage = 1;
        public static final String NAME_TABLE = "loaitintuc";
        public static final String _ID = "_id";
        public static final String KIND = "name";
        public static final String LINKPAGE = "linkPage";
        public static final int VERSION = 1;
    }

    public class LoaiThongBao implements BaseColumns {
        public static final int _id = 0;
        public static final int tenLoaiThongBao = 1;
        public static final String NAME_TABLE = "loaithongbao";
        public static final String _ID = "_id";
        public static final String TENLOAITHONGBAO = "name";
        public static final int VERSION = 1;
    }

    public class MucDoThongBao implements BaseColumns {
        public static final int _id = 0;
        public static final int tenMucDoThongBao = 1;
        public static final String NAME_TABLE = "mucdothongbao";
        public static final String _ID = "_id";
        public static final String TENMUCDOTHONGBAO = "name";
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
        public static final int has_file = 7;
        public static final int id_loai_thong_bao = 8;
        public static final int id_muc_mo_thong_bao = 9;
        public static final int id_sender = 10;
        public static final int name_sender = 11;
        public static final int VERSION = 1;

        public static final String NAME_TABLE = "pushnotification";
        public static final String _ID = "id";
        public static final String TIEU_DE = "tieude";
        public static final String NOI_DUNG = "noidung";
        public static final String KIND = "loai";
        public static final String LINK = "link";
        public static final String ISREAD = "isread";
        public static final String THOIGIANNHAN = "thoigiannhan";
        public static final String HASFILE = "hasfile";
        public static final String IDLOAITHONGBAO = "idloaithongbao";
        public static final String IDMUCHOTHONGBAO = "idmucdothongbao";
        public static final String IDSENDER = "idsender";
        public static final String NAMESENDER = "namesender";

    }
}
