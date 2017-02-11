package vnu.uet.tuan.uetsupporter.SQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.LoaiTinTuc;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class LoaiThongBaoSQLHelper extends SQLiteOpenHelper {
    Context context;

    public LoaiThongBaoSQLHelper(Context context) {
        super(context, Contract.LoaiThongBao.NAME_TABLE, null, Contract.LoaiThongBao.VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + Contract.LoaiThongBao.NAME_TABLE + " ( " +
                Contract.LoaiThongBao._ID + " integer primary key, " +
                Contract.LoaiThongBao.TENLOAITHONGBAO + " text not null, " +

                " UNIQUE ( " + Contract.LoaiThongBao.TENLOAITHONGBAO + ") ON CONFLICT REPLACE);";

        db.execSQL(sql);
    }

    public int insertBulkLoaiThongBao(List<LoaiThongBao> arr) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        int countNumber = 0;
        try {

            for (int i = 0; i < arr.size(); i++) {
                ContentValues values = new ContentValues();
                LoaiThongBao loaiThongBao = arr.get(i);

                values.put(Contract.LoaiThongBao._ID, loaiThongBao.get_id());
                values.put(Contract.LoaiThongBao.TENLOAITHONGBAO, loaiThongBao.getTenLoaiThongBao());

                long _id = db.insert(Contract.LoaiThongBao.NAME_TABLE, null, values);
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

    public Cursor getArrayLoaiThongBao() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + Contract.LoaiThongBao.NAME_TABLE + " order by " + Contract.LoaiThongBao._ID + " asc";
        Log.e("sql", query);

        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.LoaiThongBao.NAME_TABLE);
    }
}
