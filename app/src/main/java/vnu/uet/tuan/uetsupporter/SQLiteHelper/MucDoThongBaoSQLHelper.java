package vnu.uet.tuan.uetsupporter.SQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Download.MucDoThongBao;

/**
 * Created by Vu Minh Tuan on 2/22/2017.
 */

public class MucDoThongBaoSQLHelper extends SQLFather {
    Context context;

    public MucDoThongBaoSQLHelper(Context context) {
        super(context, Contract.MucDoThongBao.NAME_TABLE, null, Contract.MucDoThongBao.VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + Contract.LoaiThongBao.NAME_TABLE + " ( " +
                Contract.MucDoThongBao._ID + " integer primary key, " +
                Contract.MucDoThongBao.TENMUCDOTHONGBAO + " text not null, " +

                " UNIQUE ( " + Contract.MucDoThongBao._ID + ") ON CONFLICT REPLACE);";

        db.execSQL(sql);
    }

    @Override
    public int insertBulk(List list) {
        List<MucDoThongBao> arr = list;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        int countNumber = 0;
        try {

            for (int i = 0; i < arr.size(); i++) {
                ContentValues values = new ContentValues();
                MucDoThongBao mucDoThongBao = arr.get(i);

                values.put(Contract.MucDoThongBao._ID, mucDoThongBao.get_id());
                values.put(Contract.MucDoThongBao.TENMUCDOTHONGBAO, mucDoThongBao.getTenMucDoThongBao());

                long _id = db.insert(Contract.MucDoThongBao.NAME_TABLE, null, values);
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

    @Override
    public Cursor getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + Contract.MucDoThongBao.NAME_TABLE + " order by " + Contract.MucDoThongBao._ID + " asc";
        Log.e("sql", query);

        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.MucDoThongBao.NAME_TABLE);
    }
}
