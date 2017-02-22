package vnu.uet.tuan.uetsupporter.Model.Download;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 19/01/2017.
 */
public class LoaiTinTuc implements Parcelable{
    private int _id;
    private String kind;
    private String linkPage;

    protected LoaiTinTuc(Parcel in) {
        _id = in.readInt();
        kind = in.readString();
        linkPage = in.readString();
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
        dest.writeInt(_id);
        dest.writeString(kind);
        dest.writeString(linkPage);
    }

    public LoaiTinTuc(int _id, String kind, String linkPage) {
        this._id = _id;
        this.kind = kind;
        this.linkPage = linkPage;
    }


    public LoaiTinTuc() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getLinkPage() {
        return linkPage;
    }

    public void setLinkPage(String linkPage) {
        this.linkPage = linkPage;
    }
}
