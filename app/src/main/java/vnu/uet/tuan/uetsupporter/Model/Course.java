package vnu.uet.tuan.uetsupporter.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class Course implements Parcelable {
    /**
     * _id : INT1001
     * tenLopMonHoc : co so du lieu
     * __v : 0
     * idGiangVien : [{"_id":"tokhanh","tenGiangVien":"To Van Khanh","idKhoa":"cntt","__v":0,"idLopMonHoc":[]}]
     * thoiGian : 2017-02-04T14:17:05.370Z
     */

    private String _id;
    private String name;
    private String createAt;
    private List<Lecture> lecturers;
    private int sizeClass;

    protected Course(Parcel in) {
        _id = in.readString();
        name = in.readString();
        createAt = in.readString();
        lecturers = in.createTypedArrayList(Lecture.CREATOR);
        sizeClass = in.readInt();
    }

    public Course() {
    }


    public int getSizeClass() {
        return sizeClass;
    }

    public void setSizeClass(int sizeClass) {
        this.sizeClass = sizeClass;
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
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

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public List<Lecture> getLectures() {
        return lecturers;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lecturers = lectures;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(createAt);
        dest.writeTypedList(lecturers);
        dest.writeInt(sizeClass);
    }
}