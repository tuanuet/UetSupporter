package vnu.uet.tuan.uetsupporter.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by vmtuan on 3/8/2017.
 */

public class File implements Parcelable, Serializable {
    /**
     * _id : 58be855acf300013a847177f
     * tenFile : BangDiem_int2204_1 (2).pdf
     * link : D:\Tai Lieu\NCKH\UETSupportAPI\Utils/../files/BangDiem_int2204_1 (2).pdf
     * __v : 0
     */

    private String _id;
    private String tenFile;
    private String link;

    public File(String _id, String tenFile, String link) {
        this._id = _id;
        this.tenFile = tenFile;
        this.link = link;
    }

    public File() {
    }

    protected File(Parcel in) {
        _id = in.readString();
        tenFile = in.readString();
        link = in.readString();
    }

    public static final Creator<File> CREATOR = new Creator<File>() {
        @Override
        public File createFromParcel(Parcel in) {
            return new File(in);
        }

        @Override
        public File[] newArray(int size) {
            return new File[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenFile() {
        return tenFile;
    }

    public void setTenFile(String tenFile) {
        this.tenFile = tenFile;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(tenFile);
        dest.writeString(link);
    }
}