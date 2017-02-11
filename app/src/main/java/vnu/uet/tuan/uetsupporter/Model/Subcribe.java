package vnu.uet.tuan.uetsupporter.Model;

import java.util.List;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class Subcribe {

    /**
     * _id : 14020234
     * __v : 0
     * idLoaiThongBao : [0,1,3,2]
     * idLoaiTinTuc : [1,2,3]
     */

    private String _id;
    private List<Integer> idLoaiThongBao;
    private List<Integer> idLoaiTinTuc;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Integer> getIdLoaiThongBao() {
        return idLoaiThongBao;
    }

    public void setIdLoaiThongBao(List<Integer> idLoaiThongBao) {
        this.idLoaiThongBao = idLoaiThongBao;
    }

    public List<Integer> getIdLoaiTinTuc() {
        return idLoaiTinTuc;
    }

    public void setIdLoaiTinTuc(List<Integer> idLoaiTinTuc) {
        this.idLoaiTinTuc = idLoaiTinTuc;
    }
}
