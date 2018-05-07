package vnu.uet.tuan.uetsupporter.Model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by FRAMGIA\vu.minh.tuan on 19/03/2018.
 */

public class Sender implements Serializable {
    public static final String NAME = "name";
    public static final String _ID = "_id";

    private String name;
    private String _id;

    public Sender() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public static Sender fromJSONString(String data) {
        try {
            JSONObject json = new JSONObject(data);
            Sender sender = new Sender();

            sender.set_id(json.getString(Sender._ID));
            sender.setName(json.getString(Sender.NAME));
            return sender;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return this.name+ "===" +this._id;
    }
}