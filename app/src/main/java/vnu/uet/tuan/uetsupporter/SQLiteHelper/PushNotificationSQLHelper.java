package vnu.uet.tuan.uetsupporter.SQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;
import vnu.uet.tuan.uetsupporter.Model.Mail.Email;

import static vnu.uet.tuan.uetsupporter.Utils.Utils.convertStringToArray;

/**
 * Created by Vu Minh Tuan on 2/16/2017.
 */

public class PushNotificationSQLHelper extends SQLiteOpenHelper {

    private final String TAG = this.getClass().getSimpleName();
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
                Contract.PushNotification.DESCRIPTION_IMAGES + " text not null, " +
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

    public String[] getServerId(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select "+ Contract.PushNotification.SERVER_ID +" from " + Contract.PushNotification.NAME_TABLE + " order by id limit 30";
        Cursor cs = db.rawQuery(query,null);

        ArrayList<String> list = new ArrayList<String>();
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            list.add(cs.getString(0));
            cs.moveToNext();
        }
        return list.toArray(new String[0]);
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
        values.put(Contract.PushNotification.DESCRIPTION_IMAGES, Arrays.toString(notification.getDescriptionImages()));

        return (int) db.insert(Contract.PushNotification.NAME_TABLE, null, values);
    }

    public void clearDb() throws Exception{
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(Contract.PushNotification.NAME_TABLE, null, null);
        db.close();
    }

    public boolean removeByServerId(String _id) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String sqlDetele = "DELETE FROM " + Contract.PushNotification.NAME_TABLE +
                    "  WHERE " + Contract.Email._ID + " = \""+ _id + "\" ;";
            Log.e(TAG,sqlDetele);
            db.execSQL(sqlDetele);
            return true;
        }catch (Exception err){
            return false;
        }
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

    public String lastestNotification() {
        String result = new DateTime().toString();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select "+ Contract.PushNotification.THOIGIANNHAN +" from " + Contract.PushNotification.NAME_TABLE + " order by id desc limit 1";
        Cursor cs = db.rawQuery(query,null);

        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            result = cs.getString(0);
            cs.moveToNext();
        }
        return result;
    }

    public AnnouncementNotification getAnnouncementById(String _id) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "select * from " + Contract.PushNotification.NAME_TABLE
                    + " where " + Contract.PushNotification.SERVER_ID + " = \"" + _id+"\"";

            Cursor cursor = db.rawQuery(query, null);
            AnnouncementNotification announcementNotification;
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                announcementNotification = new AnnouncementNotification();
                announcementNotification.setTieuDe(cursor.getString(Contract.PushNotification.tieu_de));
                announcementNotification.setNoiDung(cursor.getString(Contract.PushNotification.noi_dung));
                announcementNotification.setLink(cursor.getString(Contract.PushNotification.link_page));
                announcementNotification.setThoiGianNhan(cursor.getString(Contract.PushNotification.thoi_gian_nhan));
                announcementNotification.setIdLoaiThongBao(cursor.getString(Contract.PushNotification.id_loai_thong_bao));
                announcementNotification.setIdMucDoThongBao(cursor.getString(Contract.PushNotification.id_muc_do_thong_bao));
                announcementNotification.setCodeMucDoThongBao(cursor.getString(Contract.PushNotification.code_muc_do_thong_bao));
                announcementNotification.setHasFile(cursor.getInt(Contract.PushNotification.has_file) == 1);
                announcementNotification.setIdSender(cursor.getString(Contract.PushNotification.id_sender));
                announcementNotification.setNameSender(cursor.getString(Contract.PushNotification.name_sender));
                announcementNotification.setRead(cursor.getInt(Contract.PushNotification.is_read) == 1);
                announcementNotification.setTypeNotification(cursor.getInt(Contract.PushNotification.type_notification));
                announcementNotification.setDescription(cursor.getString(Contract.PushNotification.description));
                announcementNotification.set_id(cursor.getString(Contract.PushNotification.server_id));
                announcementNotification.setDescriptionImages(convertStringToArray(cursor.getString(Contract.PushNotification.description_images)));
                return announcementNotification;
            } else {
                return null;
            }
        }catch (Exception ex) {
            return null;
        }
    }
}
