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
}
