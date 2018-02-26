package vnu.uet.tuan.uetsupporter.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Vu Minh Tuan on 2/9/2017.
 */

public class Student extends Users implements Parcelable {

    private String _id;
    private String name;
    private Class myClass;
    private String tokenFirebase;
    private List<Course> courses;
    private String code;

    public Student() {

    }
    protected Student(Parcel in) {
        _id = in.readString();
        name = in.readString();
        myClass = in.readParcelable(Class.class.getClassLoader());
        tokenFirebase = in.readString();
        courses = in.createTypedArrayList(Course.CREATOR);
        code = in.readString();
    }


    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
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

    public Class getMyClass() {
        return myClass;
    }

    public void setMyClass(Class myClass) {
        this.myClass = myClass;
    }

    public String getTokenFirebase() {
        return tokenFirebase;
    }

    public void setTokenFirebase(String tokenFirebase) {
        this.tokenFirebase = tokenFirebase;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeParcelable(myClass, flags);
        dest.writeString(tokenFirebase);
        dest.writeTypedList(courses);
        dest.writeString(code);
    }
}
