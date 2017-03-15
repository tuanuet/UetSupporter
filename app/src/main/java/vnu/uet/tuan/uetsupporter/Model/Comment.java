package vnu.uet.tuan.uetsupporter.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by vmtuan on 3/11/2017.
 */

public class Comment implements Parcelable, Serializable {

    /**
     * noiDung : good morning
     * kind : SinhVien
     * _id : 58c3a6fd3f3efb0cb4470eb6
     * time : 2017-03-11T07:27:57.260Z
     */
    private final String TAG = this.getClass().getSimpleName();
    private String noiDung;
    private String kind;
    private String _id;
    private String time;
    private UserComment comment;

    protected Comment(Parcel in) {
        noiDung = in.readString();
        kind = in.readString();
        _id = in.readString();
        time = in.readString();
        comment = in.readParcelable(UserComment.class.getClassLoader());
    }

    public Comment() {
    }

    public Comment(JSONObject root) throws JSONException {
        this.noiDung = root.getString("noiDung");
        this.kind = root.getString("kind");
        this._id = root.getString("_id");
        this.time = root.getString("time");
        Log.e(TAG, kind);
        //==================================================================//

        JSONObject idComment = root.getJSONObject("idComment");
        switch (kind) {
            case "SinhVien": {
                String id = idComment.getString("_id");
                String tensinhVien = idComment.getString("tenSinhVien");
                Log.e(TAG, tensinhVien);
                this.comment = new SinhVienComment(id, tensinhVien);
                break;
            }
            case "PhongBan": {
                String id = idComment.getString("_id");
                String tenPhongBan = idComment.getString("tenPhongBan");
                Log.e(TAG, tenPhongBan);
                this.comment = new PhongBanComment(id, tenPhongBan);
                break;
            }
        }

    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public String getNoiDung() {
        return noiDung;
    }

    public Comment(String noiDung, String kind, String _id, String time, UserComment comment) {
        this.noiDung = noiDung;
        this.kind = kind;
        this._id = _id;
        this.time = time;
        this.comment = comment;
    }

    public UserComment getUserComment() {
        return comment;
    }

    public void setUserComment(UserComment comment) {
        this.comment = comment;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noiDung);
        dest.writeString(kind);
        dest.writeString(_id);
        dest.writeString(time);
        dest.writeParcelable(comment, flags);
    }


    public static class UserComment implements Parcelable, Serializable {
        public UserComment() {
        }

        protected UserComment(Parcel in) {
        }

        public static final Creator<UserComment> CREATOR = new Creator<UserComment>() {
            @Override
            public UserComment createFromParcel(Parcel in) {
                return new UserComment(in);
            }

            @Override
            public UserComment[] newArray(int size) {
                return new UserComment[size];
            }
        };

        public String getName() {
            return "";
        }

        public String getId() {
            return "";
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }
    }

    public static class SinhVienComment extends UserComment implements Parcelable {
        /**
         * _id : 14020234
         * tenSinhVien : duc khanh
         * idLopChinh : 1
         * tokenFirebase : fsfsadfasdf
         * __v : 0
         * idLopMonHoc : ["INT1001"]
         */

        private String _id;
        private String tenSinhVien;

        public SinhVienComment(String _id, String tenSinhVien) {
            this._id = _id;
            this.tenSinhVien = tenSinhVien;
        }

        public SinhVienComment() {
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setTenSinhVien(String tenSinhVien) {
            this.tenSinhVien = tenSinhVien;
        }


        @Override
        public String getName() {
            return this.tenSinhVien;
        }

        @Override
        public String getId() {
            return this._id;
        }
    }

    public static class PhongBanComment extends UserComment implements Parcelable {
        /**
         * _id : phongban1
         * tenPhongBan : Phong tai chinh
         * __v : 0
         * idThongBao : []
         */
        private String _id;
        private String tenPhongBan;

        public PhongBanComment(String _id, String tenPhongBan) {
            this._id = _id;
            this.tenPhongBan = tenPhongBan;
        }

        public PhongBanComment() {
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setTenPhongBan(String tenPhongBan) {
            this.tenPhongBan = tenPhongBan;
        }

        @Override
        public String getName() {
            return this.tenPhongBan;
        }

        @Override
        public String getId() {
            return this._id;
        }
    }
}
