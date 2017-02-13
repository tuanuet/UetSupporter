package vnu.uet.tuan.uetsupporter.Model;

/**
 * Created by Vu Minh Tuan on 2/13/2017.
 */

public class PushNotificationThongBao extends PushNotification {
    public static final String NOIDUNG = "noiDung";
    public static final String TENFILE = "tenFile";
    public static final String LINKFILE = "linkFile";
    public static final String MUCDOTHONGBAO = "mucDoThongBao";
    public static final String IDLOAITHONGBAO = "idLoaiThongBao";


    private String noiDung;
    private String tenFile;
    private String linkFile;
    private String mucDoThongBao;
    private int idloaiThongBao;

    public PushNotificationThongBao(String tieuDe, int kind, String noiDung, String tenFile, String linkFile, String mucDoThongBao, int idloaiThongBaos) {
        super(tieuDe, kind);
        this.noiDung = noiDung;
        this.tenFile = tenFile;
        this.linkFile = linkFile;
        this.mucDoThongBao = mucDoThongBao;
        this.idloaiThongBao = idloaiThongBaos;
    }

    public PushNotificationThongBao(String noiDung, String tenFile, String linkFile, String mucDoThongBao, int idloaiThongBaos) {
        this.noiDung = noiDung;
        this.tenFile = tenFile;
        this.linkFile = linkFile;
        this.mucDoThongBao = mucDoThongBao;
        this.idloaiThongBao = idloaiThongBaos;
    }

    public PushNotificationThongBao() {
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getTenFile() {
        return tenFile;
    }

    public void setTenFile(String tenFile) {
        this.tenFile = tenFile;
    }

    public String getLinkFile() {
        return linkFile;
    }

    public void setLinkFile(String linkFile) {
        this.linkFile = linkFile;
    }

    public String getMucDoThongBao() {
        return mucDoThongBao;
    }

    public void setMucDoThongBao(String mucDoThongBao) {
        this.mucDoThongBao = mucDoThongBao;
    }

    public int getIdloaiThongBaos() {
        return idloaiThongBao;
    }

    public void setIdloaiThongBaos(int idloaiThongBaos) {
        this.idloaiThongBao = idloaiThongBaos;
    }
}
