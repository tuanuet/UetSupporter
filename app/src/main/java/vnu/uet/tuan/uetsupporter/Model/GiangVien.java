package vnu.uet.tuan.uetsupporter.Model;

/**
 * Created by Vu Minh Tuan on 2/9/2017.
 */

public class GiangVien {
    private String _id;
    private String tenGiangVien;

    public GiangVien() {
    }

    public GiangVien(String _id, String tenGiangVien) {
        this._id = _id;
        this.tenGiangVien = tenGiangVien;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenGiangVien() {
        return tenGiangVien;
    }

    public void setTenGiangVien(String tenGiangVien) {
        this.tenGiangVien = tenGiangVien;
    }
}
