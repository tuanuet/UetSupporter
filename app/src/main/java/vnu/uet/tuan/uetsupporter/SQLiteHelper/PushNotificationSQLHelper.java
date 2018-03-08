package vnu.uet.tuan.uetsupporter.SQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;

/**
 * Created by Vu Minh Tuan on 2/16/2017.
 */

public class PushNotificationSQLHelper extends SQLiteOpenHelper {

    Context context;

    public PushNotificationSQLHelper(Context context) {
        super(context, Contract.PushNotification.NAME_TABLE, null, Contract.PushNotification.VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + Contract.PushNotification.NAME_TABLE + " ( " +
                Contract.PushNotification._ID + " integer primary key AUTOINCREMENT , " +
                Contract.PushNotification.TIEU_DE + " text not null, " +
                Contract.PushNotification.NOI_DUNG + " text not null, " +
                Contract.PushNotification.LINK + " text not null, " +
                Contract.PushNotification.ISREAD + " integer not null, " +
                Contract.PushNotification.THOIGIANNHAN + " text not null, " +
                Contract.PushNotification.HASFILE + " integer not null, " +
                Contract.PushNotification.IDLOAITHONGBAO + " integer not null, " +
                Contract.PushNotification.IDMUCHOTHONGBAO + " integer not null, " +
                Contract.PushNotification.IDSENDER + " text not null, " +
                Contract.PushNotification.NAMESENDER + " text not null, " +
                Contract.PushNotification.CODEMUCDOTHONGBAO + " text not null, " +
                Contract.PushNotification.TYPENOTIFICATION + " integer not null, " +
                Contract.PushNotification.DESCRIPTION + " text not null, " +
                Contract.PushNotification.SERVER_ID + " text not null, " +
                " UNIQUE (" + Contract.PushNotification._ID + ") ON CONFLICT REPLACE);";

        db.execSQL(sql);
    }

    public boolean updateReadByPosition(int position, boolean value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Contract.PushNotification.ISREAD, value ? 1 : 0);
        int id = db.update(Contract.PushNotification.NAME_TABLE, cv, Contract.PushNotification._ID + "=" + position, null);
        return id == position;
    }

    public int insertOne(AnnouncementNotification notification) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.PushNotification.LINK, notification.getLink());
        values.put(Contract.PushNotification.TIEU_DE, notification.getTieuDe());
        values.put(Contract.PushNotification.NOI_DUNG, notification.getNoiDung());
        values.put(Contract.PushNotification.THOIGIANNHAN, notification.getThoiGianNhan());
        values.put(Contract.PushNotification.IDLOAITHONGBAO, notification.getIdLoaiThongBao());
        values.put(Contract.PushNotification.IDMUCHOTHONGBAO, notification.getIdMucDoThongBao());
        values.put(Contract.PushNotification.ISREAD, notification.getRead() ? 1 : 0);
        values.put(Contract.PushNotification.HASFILE, notification.getHasFile() ? 1 : 0);
        values.put(Contract.PushNotification.IDSENDER, notification.getIdSender());
        values.put(Contract.PushNotification.NAMESENDER, notification.getNameSender());
        values.put(Contract.PushNotification.CODEMUCDOTHONGBAO,notification.getCodeMucDoThongBao());
        values.put(Contract.PushNotification.TYPENOTIFICATION,notification.getTypeNotification());
        values.put(Contract.PushNotification.DESCRIPTION,notification.getDescription());
        values.put(Contract.PushNotification.SERVER_ID,notification.get_id());
        return (int) db.insert(Contract.PushNotification.NAME_TABLE, null, values);
    }

    public void clearDb() throws Exception{
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(Contract.PushNotification.NAME_TABLE, null, null);
        db.close();

    }
    public int getCountNotificationNotRead() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + Contract.PushNotification.NAME_TABLE + " where "
                + Contract.PushNotification.ISREAD + " = 0";
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }

    public Cursor getArrayPushNotification() {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + Contract.PushNotification.NAME_TABLE
                + " order by " + Contract.PushNotification._ID + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.PushNotification.NAME_TABLE);
    }
}
