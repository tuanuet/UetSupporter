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

    private String _id;
    private String name;
    private String code;

    public MucDoThongBao(String _id, String tenMucDoThongBao) {
        this._id = _id;
        this.name = tenMucDoThongBao;
    }

    public MucDoThongBao(String _id, String name, String code) {
        this._id = _id;
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MucDoThongBao() {
    }

    protected MucDoThongBao(Parcel in) {
        _id = in.readString();
        name = in.readString();
        code = in.readString();
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenMucDoThongBao() {
        return name;
    }

    public void setTenMucDoThongBao(String tenMucDoThongBao) {
        this.name = tenMucDoThongBao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(code);
    }
}
