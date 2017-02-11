package vnu.uet.tuan.uetsupporter.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class Khoa implements Parcelable {
    /**
     * _id : cntt
     * tenKhoa : cong nghe thong tin
     * __v : 0
     */

    private String _id;
    private String tenKhoa;
    private int __v;

    protected Khoa(Parcel in) {
        _id = in.readString();
        tenKhoa = in.readString();
        __v = in.readInt();
    }

    public static final Creator<Khoa> CREATOR = new Creator<Khoa>() {
        @Override
        public Khoa createFromParcel(Parcel in) {
            return new Khoa(in);
        }

        @Override
        public Khoa[] newArray(int size) {
            return new Khoa[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(tenKhoa);
        dest.writeInt(__v);
    }
}