package vnu.uet.tuan.uetsupporter.Model.Download;

/**
 * Created by Vu Minh Tuan on 2/20/2017.
 */

public class MucDoThongBao {


    /**
     * _id : 1
     * tenMucDoThongBao : Khan cap
     */

    private int _id;
    private String tenMucDoThongBao;

    public MucDoThongBao(int _id, String tenMucDoThongBao) {
        this._id = _id;
        this.tenMucDoThongBao = tenMucDoThongBao;
    }

    public MucDoThongBao() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTenMucDoThongBao() {
        return tenMucDoThongBao;
    }

    public void setTenMucDoThongBao(String tenMucDoThongBao) {
        this.tenMucDoThongBao = tenMucDoThongBao;
    }
}
