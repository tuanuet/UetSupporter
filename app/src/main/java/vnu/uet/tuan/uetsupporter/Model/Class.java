package vnu.uet.tuan.uetsupporter.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class Class implements Parcelable {
    /**
     * _id : 1
     * name : K59CLC
     * idKhoa : {"_id":"cntt","tenKhoa":"cong nghe thong tin","__v":0}
     * __v : 0
     */

    private String _id;
    private String name;

    protected Class(Parcel in) {
        _id = in.readString();
        name = in.readString();
    }

    public static final Creator<Class> CREATOR = new Creator<Class>() {
        @Override
        public Class createFromParcel(Parcel in) {
            return new Class(in);
        }

        @Override
        public Class[] newArray(int size) {
            return new Class[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String tenLopChinh) {
        this.name = tenLopChinh;
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