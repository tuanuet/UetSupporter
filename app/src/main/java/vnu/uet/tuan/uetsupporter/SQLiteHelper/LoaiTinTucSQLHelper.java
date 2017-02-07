package vnu.uet.tuan.uetsupporter.SQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Model.LoaiTinTuc;

/**
 * Created by Vu Minh Tuan on 2/7/2017.
 */

public class LoaiTinTucSQLHelper extends SQLiteOpenHelper {

    Context context;
    public LoaiTinTucSQLHelper(Context context) {
        super(context, Contract.LoaiTinTuc.NAME_TABLE, null, Contract.LoaiTinTuc.VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + Contract.LoaiTinTuc.NAME_TABLE + " ( " +
                Contract.LoaiTinTuc._ID + " integer primary key, "+
                Contract.LoaiTinTuc.LINKPAGE + " text not null, "+
                Contract.LoaiTinTuc.KIND + " text not null, " +

                " UNIQUE (" + Contract.LoaiTinTuc._ID + ", " +
                Contract.LoaiTinTuc.LINKPAGE + ", " +
                Contract.LoaiTinTuc.KIND +") ON CONFLICT REPLACE);";

        db.execSQL(sql);
    }

    public int insertBulkLoaiTinTuc(ArrayList<LoaiTinTuc> arr){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        int countNumber= 0;
        try {

            for (int i = 0; i < arr.size(); i++) {
                ContentValues values = new ContentValues();
                LoaiTinTuc loaiTinTuc = arr.get(i);

                values.put(Contract.LoaiTinTuc._ID,loaiTinTuc.get_id());
                values.put(Contract.LoaiTinTuc.LINKPAGE,loaiTinTuc.getLinkPage());
                values.put(Contract.LoaiTinTuc.KIND,loaiTinTuc.getKind());

                long _id = db.insert(Contract.LoaiTinTuc.NAME_TABLE, null, values);
                if (_id != -1) {
                    countNumber++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return countNumber;

    }

    public Cursor getArrayLoaiTinTuc(){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from "+ Contract.LoaiTinTuc.NAME_TABLE + " order by " + Contract.LoaiTinTuc._ID + " asc";
        Log.e("sql",query);

        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.LoaiTinTuc.NAME_TABLE);
    }
}
