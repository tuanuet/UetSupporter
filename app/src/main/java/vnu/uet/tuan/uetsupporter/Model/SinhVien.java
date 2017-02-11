package vnu.uet.tuan.uetsupporter.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Vu Minh Tuan on 2/9/2017.
 */

public class SinhVien extends Users implements Parcelable {

    /**
     * _id : 14020234
     * tenSinhVien : duc khanh
     * idLopChinh : {"_id":"1","tenLopChinh":"K59CLC","idKhoa":{"_id":"cntt","tenKhoa":"cong nghe thong tin","__v":0},"__v":0}
     * nhanLoaiThongBao : []
     * __v : 0
     * tokenFirebase : d_7uLvy7uqA:APA91bGBboYN306HmVuhu30v4UOelLbvxLg9npXLrCRneUeT9_bZ4lKcFCKnjGCPvx9wh_6rOPT0rrlaNJ6ncuGNAx2CnRVXa9jJ7aFqdz2DNrcL9nfONVqv0Xr15rLn11bi0whJujq9
     * idLopMonHoc : [{"_id":"INT1001","tenLopMonHoc":"co so du lieu","__v":0,"idGiangVien":[{"_id":"tokhanh","tenGiangVien":"To Van Khanh","idKhoa":"cntt","__v":0,"idLopMonHoc":[]}],"thoiGian":"2017-02-04T14:17:05.370Z"}]
     */

    private String _id;
    private String tenSinhVien;
    private LopChinh idLopChinh;
    private String tokenFirebase;
    private List<LopMonHoc> idLopMonHoc;

    protected SinhVien(Parcel in) {
        _id = in.readString();
        tenSinhVien = in.readString();
        idLopChinh = in.readParcelable(LopChinh.class.getClassLoader());
        tokenFirebase = in.readString();
        idLopMonHoc = in.createTypedArrayList(LopMonHoc.CREATOR);
    }

    public static final Creator<SinhVien> CREATOR = new Creator<SinhVien>() {
        @Override
        public SinhVien createFromParcel(Parcel in) {
            return new SinhVien(in);
        }

        @Override
        public SinhVien[] newArray(int size) {
            return new SinhVien[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenSinhVien() {
        return tenSinhVien;
    }

    public void setTenSinhVien(String tenSinhVien) {
        this.tenSinhVien = tenSinhVien;
    }

    public LopChinh getIdLopChinh() {
        return idLopChinh;
    }

    public void setIdLopChinh(LopChinh idLopChinh) {
        this.idLopChinh = idLopChinh;
    }

    public String getTokenFirebase() {
        return tokenFirebase;
    }

    public void setTokenFirebase(String tokenFirebase) {
        this.tokenFirebase = tokenFirebase;
    }

    public List<LopMonHoc> getIdLopMonHoc() {
        return idLopMonHoc;
    }

    public void setIdLopMonHoc(List<LopMonHoc> idLopMonHoc) {
        this.idLopMonHoc = idLopMonHoc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(tenSinhVien);
        dest.writeParcelable(idLopChinh, flags);
        dest.writeString(tokenFirebase);
        dest.writeTypedList(idLopMonHoc);
    }
}
