package vnu.uet.tuan.uetsupporter.Model;

/**
 * Created by Vu Minh Tuan on 2/9/2017.
 */

public class Khoa {
    private String _id;
    private String tenKhoa;

    public Khoa(String _id, String tenKhoa) {
        this._id = _id;
        this.tenKhoa = tenKhoa;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }
}
