package vnu.uet.tuan.uetsupporter.Model;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class LoaiThongBao {

    /**
     * _id : 0
     * tenLoaiThongBao : DiemThi
     */

    private int _id;
    private String tenLoaiThongBao;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTenLoaiThongBao() {
        return tenLoaiThongBao;
    }

    public void setTenLoaiThongBao(String tenLoaiThongBao) {
        this.tenLoaiThongBao = tenLoaiThongBao;
    }
}
