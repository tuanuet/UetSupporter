package vnu.uet.tuan.uetsupporter.Model.Download;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class LoaiThongBao implements Parcelable, Serializable {

    private String _id;
    private String name;

    public LoaiThongBao(Parcel in) {
        _id = in.readString();
        name = in.readString();
    }

    public LoaiThongBao(String _id, String tenLoaiThongBao) {
        this._id = _id;
        this.name = tenLoaiThongBao;
    }

    public LoaiThongBao() {
    }

    public static final Creator<LoaiThongBao> CREATOR = new Creator<LoaiThongBao>() {
        @Override
        public LoaiThongBao createFromParcel(Parcel in) {
            return new LoaiThongBao(in);
        }

        @Override
        public LoaiThongBao[] newArray(int size) {
            return new LoaiThongBao[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenLoaiThongBao() {
        return name;
    }

    public void setTenLoaiThongBao(String tenLoaiThongBao) {
        this.name = tenLoaiThongBao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
    }
}
