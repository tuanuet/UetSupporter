package vnu.uet.tuan.uetsupporter.Model;

import java.util.ArrayList;

/**
 * Created by Vu Minh Tuan on 2/9/2017.
 */

public class LopMonHoc {
    private String _id;
    private String tenLopMonHoc;
    private ArrayList<GiangVien> giangVienArrayList;
    private String thoiGian;
    private int diemGiuaKi;
    private int diemCuoiKi;

    public LopMonHoc(String _id, String tenLopMonHoc, ArrayList<GiangVien> giangVienArrayList, String thoiGian) {
        this._id = _id;
        this.tenLopMonHoc = tenLopMonHoc;
        this.giangVienArrayList = giangVienArrayList;
        this.thoiGian = thoiGian;
    }

    public int getDiemGiuaKi() {
        return diemGiuaKi;
    }

    public void setDiemGiuaKi(int diemGiuaKi) {
        this.diemGiuaKi = diemGiuaKi;
    }

    public int getDiemCuoiKi() {
        return diemCuoiKi;
    }

    public void setDiemCuoiKi(int diemCuoiKi) {
        this.diemCuoiKi = diemCuoiKi;
    }

    public ArrayList<GiangVien> getGiangVienArrayList() {
        return giangVienArrayList;
    }

    public void setGiangVienArrayList(ArrayList<GiangVien> giangVienArrayList) {
        this.giangVienArrayList = giangVienArrayList;
    }

    public String getThoiGian() {

        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public LopMonHoc(String _id, String tenLopMonHoc, String thoiGian) {
        this._id = _id;
        this.tenLopMonHoc = tenLopMonHoc;
        this.thoiGian = thoiGian;

    }

    public LopMonHoc(String _id, String tenLopMonHoc) {
        this._id = _id;
        this.tenLopMonHoc = tenLopMonHoc;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenLopMonHoc() {
        return tenLopMonHoc;
    }

    public void setTenLopMonHoc(String tenLopMonHoc) {
        this.tenLopMonHoc = tenLopMonHoc;
    }
}
