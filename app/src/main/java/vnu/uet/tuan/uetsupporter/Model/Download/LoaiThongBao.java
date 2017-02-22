package vnu.uet.tuan.uetsupporter.Model.Download;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class LoaiThongBao implements Parcelable {

    /**
     * _id : 0
     * tenLoaiThongBao : DiemThi
     */

    private int _id;
    private String tenLoaiThongBao;

    public LoaiThongBao(Parcel in) {
        _id = in.readInt();
        tenLoaiThongBao = in.readString();
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

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTenLoaiThongBao() {
        return tenLoaiThongBao;
    }

    public void setTenLoaiThongBao(String tenLoaiThongBao) {
        this.tenLoaiThongBao = tenLoaiThongBao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(tenLoaiThongBao);
    }
}
