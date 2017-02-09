package vnu.uet.tuan.uetsupporter.Model;

import java.util.ArrayList;

/**
 * Created by Vu Minh Tuan on 2/9/2017.
 */

public class SinhVien extends Users {
    private String _id;
    private String tenSinhVien;
    private LopChinh lopChinh;
    private ArrayList<LopMonHoc> lopMonHocArrayList;

    public SinhVien(String username,String pass
            ,String _id
            ,String tenSinhVien,LopChinh lopChinh
            ,ArrayList<LopMonHoc> lopMonHocArrayList)
    {
        super(username,pass);
        this._id = _id;
        this.lopChinh = lopChinh;
        this.tenSinhVien = tenSinhVien;
        this.lopMonHocArrayList = lopMonHocArrayList;
    }
    public SinhVien(String username,String password) {
        super(username,password);
    }

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

    public LopChinh getLopChinh() {
        return lopChinh;
    }

    public void setLopChinh(LopChinh lopChinh) {
        this.lopChinh = lopChinh;
    }

    public ArrayList<LopMonHoc> getLopMonHocArrayList() {
        return lopMonHocArrayList;
    }

    public void setLopMonHocArrayList(ArrayList<LopMonHoc> lopMonHocArrayList) {
        this.lopMonHocArrayList = lopMonHocArrayList;
    }
}
