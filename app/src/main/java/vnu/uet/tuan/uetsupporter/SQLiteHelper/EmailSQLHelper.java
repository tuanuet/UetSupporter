package vnu.uet.tuan.uetsupporter.SQLiteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Mail.Email;

/**
 * Created by vmtuan on 3/4/2017.
 */

public class EmailSQLHelper extends SQLFather {
    private final String TAG = this.getClass().getSimpleName();
    Context context;

    public EmailSQLHelper(Context context) {
        super(context, Contract.Email.NAME_TABLE, null, Contract.Email.VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + Contract.Email.NAME_TABLE + " ( " +
                Contract.Email._ID + " integer primary key, " +
                Contract.Email.FROM + " text not null, " +
                Contract.Email.SEND_DATE + " text not null, " +
                Contract.Email.RECEIVEDDATE + " text not null, " +
                Contract.Email.IMPORTANCE + " text not null, " +
                Contract.Email.TITLE + " text not null, " +
                Contract.Email.CONTENT + " text not null, " +
                Contract.Email.HASFILE + " integer not null, " +
                Contract.Email.ISREAD + " integer not null, " +
                Contract.Email.FOLDER + " text not null, " +

                " UNIQUE ( " + Contract.Email._ID + ") ON CONFLICT REPLACE);";

        db.execSQL(sql);
    }

    public ArrayList<Email> insertBulkGetArrayId(List list) {
        List<Email> arr = list;
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ArrayList<Email> ids = new ArrayList<>();
        try {
            for (int i = 0; i < arr.size(); i++) {
                ContentValues values = new ContentValues();
                Email email = arr.get(i);

                values.put(Contract.Email._ID, email.getPosition());
                values.put(Contract.Email.FROM, email.getFrom());
                values.put(Contract.Email.CONTENT, email.getContent());
                values.put(Contract.Email.HASFILE, (email.isHasFile()) ? 1 : 0);
                values.put(Contract.Email.IMPORTANCE, email.getImportance().trim().toLowerCase());
                values.put(Contract.Email.ISREAD, (email.isRead()) ? 1 : 0);
                values.put(Contract.Email.RECEIVEDDATE, email.getReceiveDate());
                values.put(Contract.Email.TITLE, email.getTitle());
                values.put(Contract.Email.SEND_DATE, email.getSendDate());
                values.put(Contract.Email.FOLDER, email.getFolder());

                long _id = db.insert(Contract.Email.NAME_TABLE, null, values);
                if (_id != -1) {
                    ids.add(email);
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return ids;
    }
    @Override
    public int insertBulk(List list) {
        List<Email> arr = list;
        Log.e(TAG, "size email: " + list.size());
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        int countNumber = 0;
        try {

            for (int i = 0; i < arr.size(); i++) {
                ContentValues values = new ContentValues();
                Email email = arr.get(i);

                values.put(Contract.Email._ID, email.getPosition());
                values.put(Contract.Email.FROM, email.getFrom());
                values.put(Contract.Email.CONTENT, email.getContent());
                values.put(Contract.Email.HASFILE, (email.isHasFile()) ? 1 : 0);
                values.put(Contract.Email.IMPORTANCE, email.getImportance().trim().toLowerCase());
                values.put(Contract.Email.ISREAD, (email.isRead()) ? 1 : 0);
                values.put(Contract.Email.RECEIVEDDATE, email.getReceiveDate());
                values.put(Contract.Email.TITLE, email.getTitle());
                values.put(Contract.Email.SEND_DATE, email.getSendDate());
                values.put(Contract.Email.FOLDER, email.getFolder());

                long _id = db.insert(Contract.Email.NAME_TABLE, null, values);
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
        String query = "select * from " + Contract.Email.NAME_TABLE + " order by " + Contract.LoaiThongBao._ID + " desc";
        Log.e("sql", query);

        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Email.NAME_TABLE);
    }

    public boolean deleteEmailOffset(int i) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDetele = "DELETE FROM " + Contract.Email.NAME_TABLE +
                "  WHERE " + Contract.Email.FOLDER + " = \"Inbox\" and " + Contract.Email._ID + " <= (" +
                "      SELECT " + Contract.Email._ID +
                "      FROM " + Contract.Email.NAME_TABLE +
                "      ORDER BY " + Contract.Email._ID + " DESC " +
                "      LIMIT 1 OFFSET " + i +
                "    ) ";
        db.execSQL(sqlDetele);
        return true;
    }
}
