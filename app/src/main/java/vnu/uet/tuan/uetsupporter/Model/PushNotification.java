package vnu.uet.tuan.uetsupporter.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Vu Minh Tuan on 2/13/2017.
 */

public class PushNotification implements Serializable {
    public static final String TIEUDE = "tieuDe";
    public static final String KIND = "kind";
    public static final String NOIDUNG = "noiDung";
    public static final String LINK = "link";

    private String tieuDe;
    private String noiDung;
    private String link;
    private Boolean isRead;
    private String thoiGianNhan;
    private int kind;

    public PushNotification() {
    }

    public PushNotification(String tieuDe, String noiDung, String link, Boolean isRead, String thoiGianNhan, int kind) {
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.link = link;
        this.isRead = isRead;
        this.thoiGianNhan = thoiGianNhan;
        this.kind = kind;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getThoiGianNhan() {
        return thoiGianNhan;
    }

    public void setThoiGianNhan(String thoiGianNhan) {
        this.thoiGianNhan = thoiGianNhan;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
