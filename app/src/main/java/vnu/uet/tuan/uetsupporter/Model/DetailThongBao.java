package vnu.uet.tuan.uetsupporter.Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Download.MucDoThongBao;

/**
 * Created by vmtuan on 3/8/2017.
 */

public class DetailThongBao implements Parcelable {
    /**
     * _id : 58be855bcf300013a8471780
     * tieuDe : Test lân 20
     * noiDung : Nôi dung
     * idLoaiThongBao : {"_id":5,"__v":0,"tenLoaiThongBao":"ThongBaoKhac"}
     * idMucDoThongBao : {"_id":1,"tenMucDoThongBao":"quan trong","__v":0}
     * idSender : phongban1
     * idReceiver : INT1001
     * __v : 0
     * idFeedback : []
     * idFile : [{"_id":"58be855acf300013a847177f","tenFile":"BangDiem_int2204_1 (2).pdf","link":"D:\\Tai Lieu\\NCKH\\UETSupportAPI\\Utils/../files/BangDiem_int2204_1 (2).pdf","__v":0}]
     * time : 2017-03-07T10:03:07.254Z
     */

    private String _id;
    private String tieuDe;
    private String noiDung;
    private LoaiThongBao loaiThongBao;
    private MucDoThongBao mucDoThongBao;
    private String idSender;
    private String idReceiver;
    private String time;
    private List<File> File;

    public DetailThongBao(String _id, String tieuDe, String noiDung, LoaiThongBao loaiThongBao,
                          MucDoThongBao mucDoThongBao, String idSender, String idReceiver,
                          String time, List<vnu.uet.tuan.uetsupporter.Model.File> file) {
        this._id = _id;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.loaiThongBao = loaiThongBao;
        this.mucDoThongBao = mucDoThongBao;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.time = time;
        File = file;
    }

    public DetailThongBao() {
    }

    protected DetailThongBao(Parcel in) {
        _id = in.readString();
        tieuDe = in.readString();
        noiDung = in.readString();
        idSender = in.readString();
        idReceiver = in.readString();
        time = in.readString();
        File = in.createTypedArrayList(vnu.uet.tuan.uetsupporter.Model.File.CREATOR);
    }

    public static final Creator<DetailThongBao> CREATOR = new Creator<DetailThongBao>() {
        @Override
        public DetailThongBao createFromParcel(Parcel in) {
            return new DetailThongBao(in);
        }

        @Override
        public DetailThongBao[] newArray(int size) {
            return new DetailThongBao[size];
        }
    };

    public LoaiThongBao getLoaiThongBao() {
        return loaiThongBao;
    }

    public void setLoaiThongBao(LoaiThongBao loaiThongBao) {
        this.loaiThongBao = loaiThongBao;
    }

    public MucDoThongBao getMucDoThongBao() {
        return mucDoThongBao;
    }

    public void setMucDoThongBao(MucDoThongBao mucDoThongBao) {
        this.mucDoThongBao = mucDoThongBao;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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


    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<File> getFile() {
        return File;
    }

    public void setFile(List<File> File) {
        this.File = File;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(tieuDe);
        dest.writeString(noiDung);
        dest.writeString(idSender);
        dest.writeString(idReceiver);
        dest.writeString(time);
        dest.writeTypedList(File);
    }

    public static class LoaiThongBao {
    }

    public static class IdMucDoThongBaoBean {
    }


}
