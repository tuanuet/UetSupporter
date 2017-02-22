package vnu.uet.tuan.uetsupporter.Model.Response;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class Message {
    private Boolean success;
    private String message;

    public Message(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
