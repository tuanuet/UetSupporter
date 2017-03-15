package vnu.uet.tuan.uetsupporter.Model.Download;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Vu Minh Tuan on 2/20/2017.
 */

public class MucDoThongBao implements Parcelable, Serializable {


    /**
     * _id : 1
     * tenMucDoThongBao : Khan cap
     */

    private int _id;
    private String tenMucDoThongBao;

    public MucDoThongBao(int _id, String tenMucDoThongBao) {
        this._id = _id;
        this.tenMucDoThongBao = tenMucDoThongBao;
    }

    public MucDoThongBao() {
    }

    protected MucDoThongBao(Parcel in) {
        _id = in.readInt();
        tenMucDoThongBao = in.readString();
    }

    public static final Creator<MucDoThongBao> CREATOR = new Creator<MucDoThongBao>() {
        @Override
        public MucDoThongBao createFromParcel(Parcel in) {
            return new MucDoThongBao(in);
        }

        @Override
        public MucDoThongBao[] newArray(int size) {
            return new MucDoThongBao[size];
        }
    };

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTenMucDoThongBao() {
        return tenMucDoThongBao;
    }

    public void setTenMucDoThongBao(String tenMucDoThongBao) {
        this.tenMucDoThongBao = tenMucDoThongBao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(tenMucDoThongBao);
    }
}
