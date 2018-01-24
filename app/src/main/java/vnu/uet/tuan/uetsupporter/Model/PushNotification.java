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
    public static final String IDLOAITHONGBAO = "idLoaiThongBao";
    public static final String IDMUCDOTHONGBAO = "idMucDoThongBao";
    public static final String HASFILE = "hasfile";
    public static final String IDSENDER = "idSender";
    public static final String NAMESENDER = "nameSender";

    private String tieuDe;
    private String noiDung;
    private String link;
    private Boolean isRead;
    private String thoiGianNhan;
    private int kind;
    private String idMucDoThongBao;
    private String idLoaiThongBao;
    private Boolean hasFile;
    private String idSender;
    private String nameSender;

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getNameSender() {
        return nameSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }

    public PushNotification(String tieuDe, String noiDung, String link, Boolean isRead,
                            String thoiGianNhan, int kind, String idMucDoThongBao, String idLoaiThongBao,
                            Boolean hasFile, String idSender, String nameSender) {
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.link = link;
        this.isRead = isRead;
        this.thoiGianNhan = thoiGianNhan;
        this.kind = kind;
        this.idMucDoThongBao = idMucDoThongBao;
        this.idLoaiThongBao = idLoaiThongBao;
        this.hasFile = hasFile;
        this.idSender = idSender;
        this.nameSender = nameSender;
    }

    public PushNotification() {
        this.tieuDe = "";
        this.noiDung = "";
        this.link = "";
        this.isRead = false;
        this.thoiGianNhan = "";
        this.kind = 0;
        this.idMucDoThongBao = "";
        this.idLoaiThongBao = "";
        this.hasFile = false;
        this.idSender = "phongban2";
        this.nameSender = "Phòng Đào Tạo";
    }

    public PushNotification(String tieuDe, String noiDung, String link,
                            Boolean isRead, String thoiGianNhan, int kind,
                            String idMucDoThongBao, String idLoaiThongBao, Boolean hasFile) {
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.link = link;
        this.isRead = isRead;
        this.thoiGianNhan = thoiGianNhan;
        this.kind = kind;
        this.idMucDoThongBao = idMucDoThongBao;
        this.idLoaiThongBao = idLoaiThongBao;
        this.hasFile = hasFile;
    }

    public String getIdMucDoThongBao() {
        return idMucDoThongBao;
    }

    public void setIdMucDoThongBao(String idMucDoThongBao) {
        this.idMucDoThongBao = idMucDoThongBao;
    }

    public String getIdLoaiThongBao() {
        return idLoaiThongBao;
    }

    public void setIdLoaiThongBao(String idLoaiThongBao) {
        this.idLoaiThongBao = idLoaiThongBao;
    }

    public Boolean getHasFile() {
        return hasFile;
    }

    public void setHasFile(Boolean hasFile) {
        this.hasFile = hasFile;
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

    @Override
    public String toString() {
        return this.link + "--" +this.tieuDe;
    }
}
