package vnu.uet.tuan.uetsupporter.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class LopChinh implements Parcelable {
    /**
     * _id : 1
     * tenLopChinh : K59CLC
     * idKhoa : {"_id":"cntt","tenKhoa":"cong nghe thong tin","__v":0}
     * __v : 0
     */

    private String _id;
    private String tenLopChinh;
    private Khoa idKhoa;

    protected LopChinh(Parcel in) {
        _id = in.readString();
        tenLopChinh = in.readString();
        idKhoa = in.readParcelable(Khoa.class.getClassLoader());
    }

    public static final Creator<LopChinh> CREATOR = new Creator<LopChinh>() {
        @Override
        public LopChinh createFromParcel(Parcel in) {
            return new LopChinh(in);
        }

        @Override
        public LopChinh[] newArray(int size) {
            return new LopChinh[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenLopChinh() {
        return tenLopChinh;
    }

    public void setTenLopChinh(String tenLopChinh) {
        this.tenLopChinh = tenLopChinh;
    }

    public Khoa getIdKhoa() {
        return idKhoa;
    }

    public void setIdKhoa(Khoa idKhoa) {
        this.idKhoa = idKhoa;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(tenLopChinh);
        dest.writeParcelable(idKhoa, flags);
    }
}