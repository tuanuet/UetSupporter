package vnu.uet.tuan.uetsupporter.Model;

import java.io.Serializable;
import java.util.Date;

import vnu.uet.tuan.uetsupporter.Model.Base.IReactable;
import vnu.uet.tuan.uetsupporter.Model.interfaces.INotification;

/**
 * Created by Vu Minh Tuan on 2/13/2017.
 */

public class AnnouncementNotification implements Serializable,INotification {
    public static final String _ID = "_id";
    public static final String TIEUDE = "tieuDe";
    public static final String TYPENOTIFICATION = "typeNotification";
    public static final String NOIDUNG = "noiDung";
    public static final String LINK = "link";
    public static final String IDLOAITHONGBAO = "idLoaiThongBao";
    public static final String IDMUCDOTHONGBAO = "idMucDoThongBao";
    public static final String HASFILE = "hasfile";
    public static final String IDSENDER = "idSender";
    public static final String NAMESENDER = "nameSender";
    public static final String CODEKINDANNOUNCE = "codeMucDoThongBao";
    public static final String DESCRIPTION = "description";
    public static final String DESCRIPTION_IMAGES = "descriptionImages";


    private String _id;
    private String tieuDe;
    private String noiDung;
    private String link;
    private Boolean isRead;
    private String thoiGianNhan;
    private int typeNotification;
    private String idMucDoThongBao;
    private String idLoaiThongBao;
    private Boolean hasFile;
    private String idSender;
    private String nameSender;
    private String codeMucDoThongBao;
    private String description;
    private String[] descriptionImages;
    private IReactable angry;
    private IReactable cry;
    private IReactable love;
    private IReactable wow;
    private IReactable surprise;

    public IReactable getAngry() {
        return angry;
    }

    public void setAngry(IReactable angry) {
        this.angry = angry;
    }

    public IReactable getCry() {
        return cry;
    }

    public void setCry(IReactable cry) {
        this.cry = cry;
    }

    public IReactable getLove() {
        return love;
    }

    public void setLove(IReactable love) {
        this.love = love;
    }

    public IReactable getWow() {
        return wow;
    }

    public void setWow(IReactable wow) {
        this.wow = wow;
    }

    public IReactable getSurprise() {
        return surprise;
    }

    public void setSurprise(IReactable surprise) {
        this.surprise = surprise;
    }

    public String[] getDescriptionImages() {
        return descriptionImages;
    }

    public void setDescriptionImages(String[] descriptionImages) {
        this.descriptionImages = descriptionImages;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public AnnouncementNotification(String tieuDe, String noiDung, String link, Boolean isRead,
                                    String thoiGianNhan, int typeNotification, String idMucDoThongBao, String idLoaiThongBao,
                                    Boolean hasFile, String idSender, String nameSender,String codeMucDoThongBao) {
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.link = link;
        this.isRead = isRead;
        this.thoiGianNhan = thoiGianNhan;
        this.typeNotification = typeNotification;
        this.idMucDoThongBao = idMucDoThongBao;
        this.idLoaiThongBao = idLoaiThongBao;
        this.hasFile = hasFile;
        this.idSender = idSender;
        this.nameSender = nameSender;
        this.codeMucDoThongBao = codeMucDoThongBao;
    }

    public AnnouncementNotification() {
        this.tieuDe = "";
        this.noiDung = "";
        this.link = "";
        this.isRead = false;
        this.thoiGianNhan = "";
        this.typeNotification = 0;
        this.idMucDoThongBao = "";
        this.idLoaiThongBao = "";
        this.hasFile = false;
        this.idSender = "phongban2";
        this.nameSender = "Phòng Đào Tạo";
        this.codeMucDoThongBao ="binh_thuong";
    }

    public AnnouncementNotification(String tieuDe, String noiDung, String link,
                                    Boolean isRead, String thoiGianNhan, int typeNotification,
                                    String idMucDoThongBao, String idLoaiThongBao, Boolean hasFile) {
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.link = link;
        this.isRead = isRead;
        this.thoiGianNhan = thoiGianNhan;
        this.typeNotification = typeNotification;
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

    public String getCodeMucDoThongBao() {
        return codeMucDoThongBao;
    }

    public void setCodeMucDoThongBao(String codeMucDoThongBao) {
        this.codeMucDoThongBao = codeMucDoThongBao;
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

    public int getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(int typeNotification) {
        this.typeNotification = typeNotification;
    }

    @Override
    public String toString() {
        return this.angry.getIsReact() + "--" +this.cry.getIsReact() +"--"+ this.love.getIsReact() + "--" +this.wow.getIsReact()+ "--" +this.surprise.getIsReact();
    }
}
