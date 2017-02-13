package vnu.uet.tuan.uetsupporter.Model;

/**
 * Created by Vu Minh Tuan on 2/13/2017.
 */

public class PushNotification {
    public static final String TIEUDE = "tieuDe";
    public static final String KIND = "kind";

    private String tieuDe;
    private int kind;

    public PushNotification(String tieuDe, int kind) {
        this.tieuDe = tieuDe;
        this.kind = kind;
    }

    public PushNotification() {
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
