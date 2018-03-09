package vnu.uet.tuan.uetsupporter.Presenter.Fetching;

import vnu.uet.tuan.uetsupporter.Model.Response.DataSync;

/**
 * Created by FRAMGIA\vu.minh.tuan on 09/03/2018.
 */

public interface IPresenterFetchingModel {
    interface OnFinishedListener {
        void OnSuccess(DataSync dataSync);

        void OnFailure(String failure);
    }

    public void fetching(String lastTimeAnnoun, String[] kindAnnouncements, String lastTimeNew, String[] kindNews, OnFinishedListener listener);
}
