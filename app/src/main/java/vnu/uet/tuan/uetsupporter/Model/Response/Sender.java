package vnu.uet.tuan.uetsupporter.Model.Response;

import java.io.Serializable;

/**
 * Created by FRAMGIA\vu.minh.tuan on 19/03/2018.
 */

public class Sender implements Serializable {
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
}