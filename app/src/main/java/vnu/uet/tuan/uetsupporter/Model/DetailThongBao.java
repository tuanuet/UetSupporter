package vnu.uet.tuan.uetsupporter.Model;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Download.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.Download.MucDoThongBao;

/**
 * Created by vmtuan on 3/8/2017.
 */

public class DetailThongBao implements Parcelable {

    private String _id;
    private String tieuDe;
    private String noiDung;
    private LoaiThongBao idLoaiThongBao;
    private MucDoThongBao idMucDoThongBao;
    private String idSender;
    private String idReceiver;
    private String time;
    private List<File> idFile;
    private List<Comment> feedback;

    private final String TAG = this.getClass().getSimpleName();

    public DetailThongBao(String json) throws JSONException {
        JSONObject root = new JSONObject(json);

        this._id = root.getString("_id");
        this.tieuDe = root.getString("tieuDe");
        this.noiDung = root.getString("noiDung");
//        this.idSender = root.getString("idSender");
        this.idSender = "ahihi@123";
        this.idReceiver = root.getString("idReceiver");
        this.time = root.getString("time");
        Log.e(TAG, time);
        //==================================================================//
        JSONObject idMucDoThongBao = root.getJSONObject("idMucDoThongBao");
        int _id = idMucDoThongBao.getInt("_id");
        String tenMucDoThongBao = idMucDoThongBao.getString("tenMucDoThongBao");
        this.idMucDoThongBao = new MucDoThongBao(_id, tenMucDoThongBao);
        Log.e(TAG, tenMucDoThongBao);
        //==================================================================//

        JSONObject idLoaiThongBao = root.getJSONObject("idLoaiThongBao");
        _id = idLoaiThongBao.getInt("_id");
        String tenLoaiThongBao = idLoaiThongBao.getString("tenLoaiThongBao");
        this.idLoaiThongBao = new LoaiThongBao(_id, tenLoaiThongBao);
        Log.e(TAG, tenLoaiThongBao);

        //==================================================================//
        try {
            ArrayList<File> files = new ArrayList<>();
            JSONArray jsonFiles = root.getJSONArray("idFile");
            for (int i = 0; i < jsonFiles.length(); i++) {
                JSONObject temp = jsonFiles.getJSONObject(i);
                String id = temp.getString("_id");
                String title = temp.getString("tenFile");
                String link = temp.getString("link");
                File file = new File(id, title, link);
                files.add(file);
            }
            this.idFile = files;
        } catch (Exception e) {
            this.idFile = new ArrayList<>();
        }

        //==================================================================//
        try {
            JSONArray feedback = root.getJSONArray("feedback");
            ArrayList<Comment> listFeedback = new ArrayList<>();
            for (int i = 0; i < feedback.length(); i++) {
                JSONObject jsonComment = feedback.getJSONObject(i);
                listFeedback.add(new Comment(jsonComment));
            }
            this.feedback = listFeedback;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            this.feedback = new ArrayList<>();
        }
    }

    public DetailThongBao(String _id, String tieuDe, String noiDung, LoaiThongBao idLoaiThongBao,
                          MucDoThongBao idMucDoThongBao, String idSender, String idReceiver,
                          String time, List<File> idFile, List<Comment> feedback) {
        this._id = _id;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.idLoaiThongBao = idLoaiThongBao;
        this.idMucDoThongBao = idMucDoThongBao;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.time = time;
        this.idFile = idFile;
        this.feedback = feedback;
    }

    public DetailThongBao(String _id, String tieuDe, String noiDung, LoaiThongBao idLoaiThongBao,
                          MucDoThongBao idMucDoThongBao, String idSender, String idReceiver,
                          String time, List<File> idFile) {
        this._id = _id;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.idLoaiThongBao = idLoaiThongBao;
        this.idMucDoThongBao = idMucDoThongBao;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.time = time;
        this.idFile = idFile;
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
        idFile = in.createTypedArrayList(vnu.uet.tuan.uetsupporter.Model.File.CREATOR);
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

    public List<Comment> getFeedback() {
        return feedback;
    }

    public void setFeedback(List<Comment> feedback) {
        this.feedback = feedback;
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

    public LoaiThongBao getIdLoaiThongBao() {
        return idLoaiThongBao;
    }

    public void setIdLoaiThongBao(LoaiThongBao idLoaiThongBao) {
        this.idLoaiThongBao = idLoaiThongBao;
    }

    public MucDoThongBao getIdMucDoThongBao() {
        return idMucDoThongBao;
    }

    public void setIdMucDoThongBao(MucDoThongBao idMucDoThongBao) {
        this.idMucDoThongBao = idMucDoThongBao;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public List<File> getIdFile() {
        return idFile;
    }

    public void setIdFile(List<File> idFile) {
        this.idFile = idFile;
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
        dest.writeTypedList(idFile);
    }


}
