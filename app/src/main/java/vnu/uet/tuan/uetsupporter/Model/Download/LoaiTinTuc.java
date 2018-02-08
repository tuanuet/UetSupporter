package vnu.uet.tuan.uetsupporter.Model.Download;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 19/01/2017.
 */
public class LoaiTinTuc implements Parcelable, Serializable {
    private String _id;
    private String name;


    protected LoaiTinTuc(Parcel in) {
        _id = in.readString();
        name = in.readString();
    }

    public static final Creator<LoaiTinTuc> CREATOR = new Creator<LoaiTinTuc>() {
        @Override
        public LoaiTinTuc createFromParcel(Parcel in) {
            return new LoaiTinTuc(in);
        }

        @Override
        public LoaiTinTuc[] newArray(int size) {
            return new LoaiTinTuc[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
    }

    public LoaiTinTuc(String _id, String kind) {
        this._id = _id;
        this.name = kind;
    }


    public LoaiTinTuc() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getKind() {
        return name;
    }

    public void setKind(String kind) {
        this.name = kind;
    }
}
