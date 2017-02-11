package vnu.uet.tuan.uetsupporter.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class GiangVien implements Parcelable {
    /**
     * _id : tokhanh
     * tenGiangVien : To Van Khanh
     * idKhoa : cntt
     * __v : 0
     * idLopMonHoc : []
     */

    private String _id;
    private String tenGiangVien;
    private String idKhoa;

    protected GiangVien(Parcel in) {
        _id = in.readString();
        tenGiangVien = in.readString();
        idKhoa = in.readString();
    }

    public static final Creator<GiangVien> CREATOR = new Creator<GiangVien>() {
        @Override
        public GiangVien createFromParcel(Parcel in) {
            return new GiangVien(in);
        }

        @Override
        public GiangVien[] newArray(int size) {
            return new GiangVien[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenGiangVien() {
        return tenGiangVien;
    }

    public void setTenGiangVien(String tenGiangVien) {
        this.tenGiangVien = tenGiangVien;
    }

    public String getIdKhoa() {
        return idKhoa;
    }

    public void setIdKhoa(String idKhoa) {
        this.idKhoa = idKhoa;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(tenGiangVien);
        dest.writeString(idKhoa);
    }
}