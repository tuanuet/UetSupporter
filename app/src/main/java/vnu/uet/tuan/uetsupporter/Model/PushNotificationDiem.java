package vnu.uet.tuan.uetsupporter.Model;

/**
 * Created by Vu Minh Tuan on 2/13/2017.
 */

public class PushNotificationDiem extends PushNotification {
    public static final String MSV = "MSV";
    public static final String TENLOPMONHOC = "tenLopMonHoc";
    public static final String TENHOCKI = "tenKiHoc";
    public static final String TENGIANVIEN = "tenGiangVien";
    public static final String TENMONHOC = "monHoc";
    public static final String DIEMTHANHPHAN = "diemThanhPhan";
    public static final String DIEMCUOIKI = "diemCuoiKi";


    private int maSinhVien;
    private String tenLopMonHoc;
    private String tenKiHoc;
    private String tenGiangVien;
    private String monHoc;
    private int diemThanhPhan;
    private int diemCuoiKi;

    public PushNotificationDiem() {
    }

    public PushNotificationDiem(String tieuDe, int kind, int MSV, String tenLopMonHoc, String tenKiHoc, String tenGiangVien, String monHoc, int diemThanhPhan, int diemCuoiKi) {
        super(tieuDe, kind);
        this.maSinhVien = MSV;
        this.tenLopMonHoc = tenLopMonHoc;
        this.tenKiHoc = tenKiHoc;
        this.tenGiangVien = tenGiangVien;
        this.monHoc = monHoc;
        this.diemThanhPhan = diemThanhPhan;
        this.diemCuoiKi = diemCuoiKi;
    }

    public PushNotificationDiem(int MSV, String tenLopMonHoc, String tenKiHoc, String tenGiangVien, String monHoc, int diemThanhPhan, int diemCuoiKi) {
        this.maSinhVien = MSV;
        this.tenLopMonHoc = tenLopMonHoc;
        this.tenKiHoc = tenKiHoc;
        this.tenGiangVien = tenGiangVien;
        this.monHoc = monHoc;
        this.diemThanhPhan = diemThanhPhan;
        this.diemCuoiKi = diemCuoiKi;
    }

    public int getMSV() {
        return maSinhVien;
    }

    public void setMSV(int MSV) {
        this.maSinhVien = MSV;
    }

    public String getTenLopMonHoc() {
        return tenLopMonHoc;
    }

    public void setTenLopMonHoc(String tenLopMonHoc) {
        this.tenLopMonHoc = tenLopMonHoc;
    }

    public String getTenKiHoc() {
        return tenKiHoc;
    }

    public void setTenKiHoc(String tenKiHoc) {
        this.tenKiHoc = tenKiHoc;
    }

    public String getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(String monHoc) {
        this.monHoc = monHoc;
    }

    public int getDiemThanhPhan() {
        return diemThanhPhan;
    }

    public void setDiemThanhPhan(int diemThanhPhan) {
        this.diemThanhPhan = diemThanhPhan;
    }

    public String getTenGiangVien() {
        return tenGiangVien;
    }

    public void setTenGiangVien(String tenGiangVien) {
        this.tenGiangVien = tenGiangVien;
    }

    public int getDiemCuoiKi() {
        return diemCuoiKi;
    }

    public void setDiemCuoiKi(int diemCuoiKi) {
        this.diemCuoiKi = diemCuoiKi;
    }
}
