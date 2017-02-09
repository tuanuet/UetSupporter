package vnu.uet.tuan.uetsupporter.Model;

/**
 * Created by Vu Minh Tuan on 2/9/2017.
 */

public class LopChinh {
    private String tenLopChinh;
    private Khoa khoa;

    public LopChinh(String tenLopChinh, Khoa khoa) {
        this.tenLopChinh = tenLopChinh;
        this.khoa = khoa;
    }

    public String getTenLopChinh() {
        return tenLopChinh;
    }

    public void setTenLopChinh(String tenLopChinh) {
        this.tenLopChinh = tenLopChinh;
    }

    public Khoa getKhoa() {
        return khoa;
    }

    public void setKhoa(Khoa khoa) {
        this.khoa = khoa;
    }
}
