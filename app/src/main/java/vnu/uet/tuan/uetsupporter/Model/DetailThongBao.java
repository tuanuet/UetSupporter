package vnu.uet.tuan.uetsupporter.Model;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.Download.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.Download.MucDoThongBao;
import vnu.uet.tuan.uetsupporter.Model.Response.Sender;

/**
 * Created by vmtuan on 3/8/2017.
 */

public class DetailThongBao implements Serializable {

    private String _id;
    private String tieuDe;
    private String noiDung;
    private LoaiThongBao idLoaiThongBao;
    private MucDoThongBao idMucDoThongBao;
    private Sender sender;
    private String idReceiver;
    private String description;
    private String time;
    private List<File> idFile;

    private final String TAG = this.getClass().getSimpleName();

    public DetailThongBao(String json) throws JSONException {
        JSONObject root = new JSONObject(json);

        this._id = root.getString("_id");
        this.tieuDe = root.getString("title");
        this.noiDung = root.getString("content");
        this.sender = new Sender();
        sender.set_id(root.getJSONObject("sender").getString("_id"));
        sender.setName(root.getJSONObject("sender").getString("name"));

        this.idReceiver = root.getString("receiver");
        this.description =  root.getString("description");
        this.time = root.getString("createdAt");
        //==================================================================//
        JSONObject idMucDoThongBao = root.getJSONObject("priorityNotify");
        String _id = idMucDoThongBao.getString("_id");
        String tenMucDoThongBao = idMucDoThongBao.getString("name");
        String code = idMucDoThongBao.getString("code");
        this.idMucDoThongBao = new MucDoThongBao(_id, tenMucDoThongBao,code);
        Log.e(TAG, tenMucDoThongBao);
        //==================================================================//

        JSONObject idLoaiThongBao = root.getJSONObject("kindOfAnnouncement");
        _id = idLoaiThongBao.getString("_id");
        String tenLoaiThongBao = idLoaiThongBao.getString("name");
        this.idLoaiThongBao = new LoaiThongBao(_id, tenLoaiThongBao);
        Log.e(TAG, tenLoaiThongBao);

        //==================================================================//
        /* try {
            ArrayList<File> files = new ArrayList<>();
            JSONArray jsonFiles = root.getJSONArray("file");
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
        */
        try {
            ArrayList<File> files = new ArrayList<>();
            JSONObject fileJson = root.getJSONObject("file");
            String id = fileJson.getString("_id");
            String title = fileJson.getString("name");
            String link = fileJson.getString("link");
            File file = new File(id, title, link);
            files.add(file);
            this.idFile = files;
        } catch (Exception e) {
            this.idFile = new ArrayList<>();
        }

    }

    public DetailThongBao() {
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

    public Sender getSender() {
        return sender;
    }

    public void setIdSender(Sender idSender) {
        this.sender = idSender;
    }

    public List<File> getIdFile() {
        return idFile;
    }

    public void setIdFile(List<File> idFile) {
        this.idFile = idFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
