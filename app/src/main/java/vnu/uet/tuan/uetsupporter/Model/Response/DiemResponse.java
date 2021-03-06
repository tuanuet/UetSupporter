package vnu.uet.tuan.uetsupporter.Model.Response;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.LopMonHoc;
import vnu.uet.tuan.uetsupporter.Model.SinhVien;

/**
 * Created by Vu Minh Tuan on 2/24/2017.
 */

public class DiemResponse {

    /**
     * _id : 58b04dbd8b48950a64195ce6
     * idSinhVien : {"_id":"14020234","tenSinhVien":"duc khanh","idLopChinh":{"_id":"1",
     * "tenLopChinh":"K59CLC","idKhoa":{"_id":"cntt","tenKhoa":"cong nghe thong tin","__v":0},"__v":0}
     * ,"nhanLoaiThongBao":[],"__v":0,
     * "tokenFirebase":"*******","idLopMonHoc":["INT1001"]}
     * idLopMonHoc : {"_id":"INT2204 1","tenLopMonHoc":"Lap Trinh","idGiangVien":[{"_id":"tokhanh",
     * "tenGiangVien":"To Van Khanh","idKhoa":"cntt","__v":0,"idLopMonHoc":[]}],"thoiGian":null}
     * diemThanhPhan : 1.8
     * diemCuoiKy : 3
     * __v : 0
     */
    private String _id;
    private SinhVien idSinhVien;
    private LopMonHoc idLopMonHoc;
    private double diemThanhPhan;
    private double diemCuoiKy;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public SinhVien getIdSinhVien() {
        return idSinhVien;
    }

    public void setIdSinhVien(SinhVien idSinhVien) {
        this.idSinhVien = idSinhVien;
    }

    public LopMonHoc getIdLopMonHoc() {
        return idLopMonHoc;
    }

    public void setIdLopMonHoc(LopMonHoc idLopMonHoc) {
        this.idLopMonHoc = idLopMonHoc;
    }

    public double getDiemThanhPhan() {
        return diemThanhPhan;
    }

    public void setDiemThanhPhan(double diemThanhPhan) {
        this.diemThanhPhan = diemThanhPhan;
    }

    public double getDiemCuoiKy() {
        return diemCuoiKy;
    }

    public void setDiemCuoiKy(double diemCuoiKy) {
        this.diemCuoiKy = diemCuoiKy;
    }

}
