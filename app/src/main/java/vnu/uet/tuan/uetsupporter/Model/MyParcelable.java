package vnu.uet.tuan.uetsupporter.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 13/01/2017.
 */

public class MyParcelable implements Parcelable {

    Notification[] list;
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeTypedArray(list, 0);
    }

    public MyParcelable(Notification[] list) {
        this.list = list;
    }

    public Notification[] getList() {
        return list;
    }

    public static final Parcelable.Creator<MyParcelable> CREATOR
            = new Parcelable.Creator<MyParcelable>() {
        public MyParcelable createFromParcel(Parcel in) {
            return new MyParcelable(in);
        }

        public MyParcelable[] newArray(int size) {
            return new MyParcelable[size];
        }
    };

    private MyParcelable(Parcel in) {
        readFromParcel(in);
    }
    private void readFromParcel(Parcel in) {
        list = in.createTypedArray(Notification.CREATOR);
    }
}
