package vnu.uet.tuan.uetsupporter.Model.Response;

import vnu.uet.tuan.uetsupporter.Model.Base.IReactable;
import vnu.uet.tuan.uetsupporter.Model.Base.Reaction;

/**
 * Created by FRAMGIA\vu.minh.tuan on 16/03/2018.
 */

public class ReactionResponse {
    private String _id;
    private Reaction angry;
    private Reaction cry;
    private Reaction love;
    private Reaction wow;
    private Reaction surprise;
    private int feedbackCount;

    public ReactionResponse(String _id, Reaction angry, Reaction cry, Reaction love, Reaction wow, Reaction surprise) {
        this._id = _id;
        this.angry = angry;
        this.cry = cry;
        this.love = love;
        this.wow = wow;
        this.surprise = surprise;
    }

    public ReactionResponse() {
    }

    public int getFeedbackCount() {
        return feedbackCount;
    }

    public void setFeedbackCount(int feedbackCount) {
        this.feedbackCount = feedbackCount;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public IReactable getAngry() {
        return angry;
    }

    public void setAngry(Reaction angry) {
        this.angry = angry;
    }

    public IReactable getCry() {
        return cry;
    }

    public void setCry(Reaction cry) {
        this.cry = cry;
    }

    public IReactable getLove() {
        return love;
    }

    public void setLove(Reaction love) {
        this.love = love;
    }

    public IReactable getWow() {
        return wow;
    }

    public void setWow(Reaction wow) {
        this.wow = wow;
    }

    public IReactable getSurprise() {
        return surprise;
    }

    public void setSurprise(Reaction surprise) {
        this.surprise = surprise;
    }
}
