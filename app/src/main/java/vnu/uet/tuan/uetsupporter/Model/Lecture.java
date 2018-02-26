package vnu.uet.tuan.uetsupporter.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class Lecture implements Parcelable {
    /**
     * _id : tokhanh
     * tenGiangVien : To Van Khanh
     * idKhoa : cntt
     * __v : 0
     * idLopMonHoc : []
     */

    private String _id;
    private String name;
    private String idMajor;

    protected Lecture(Parcel in) {
        _id = in.readString();
        name = in.readString();
        idMajor = in.readString();
    }

    public static final Creator<Lecture> CREATOR = new Creator<Lecture>() {
        @Override
        public Lecture createFromParcel(Parcel in) {
            return new Lecture(in);
        }

        @Override
        public Lecture[] newArray(int size) {
            return new Lecture[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdMajor() {
        return idMajor;
    }

    public void setIdMajor(String idKhoa) {
        this.idMajor = idKhoa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(idMajor);
    }
}