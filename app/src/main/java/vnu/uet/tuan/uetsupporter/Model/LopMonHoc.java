package vnu.uet.tuan.uetsupporter.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class LopMonHoc implements Parcelable {
    /**
     * _id : INT1001
     * tenLopMonHoc : co so du lieu
     * __v : 0
     * idGiangVien : [{"_id":"tokhanh","tenGiangVien":"To Van Khanh","idKhoa":"cntt","__v":0,"idLopMonHoc":[]}]
     * thoiGian : 2017-02-04T14:17:05.370Z
     */

    private String _id;
    private String tenLopMonHoc;
    private String thoiGian;
    private List<GiangVien> idGiangVien;

    protected LopMonHoc(Parcel in) {
        _id = in.readString();
        tenLopMonHoc = in.readString();
        thoiGian = in.readString();
        idGiangVien = in.createTypedArrayList(GiangVien.CREATOR);
    }

    public static final Creator<LopMonHoc> CREATOR = new Creator<LopMonHoc>() {
        @Override
        public LopMonHoc createFromParcel(Parcel in) {
            return new LopMonHoc(in);
        }

        @Override
        public LopMonHoc[] newArray(int size) {
            return new LopMonHoc[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenLopMonHoc() {
        return tenLopMonHoc;
    }

    public void setTenLopMonHoc(String tenLopMonHoc) {
        this.tenLopMonHoc = tenLopMonHoc;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public List<GiangVien> getIdGiangVien() {
        return idGiangVien;
    }

    public void setIdGiangVien(List<GiangVien> idGiangVien) {
        this.idGiangVien = idGiangVien;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(tenLopMonHoc);
        dest.writeString(thoiGian);
        dest.writeTypedList(idGiangVien);
    }
}