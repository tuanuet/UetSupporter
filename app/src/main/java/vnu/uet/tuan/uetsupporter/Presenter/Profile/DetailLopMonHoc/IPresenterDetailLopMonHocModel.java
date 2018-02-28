package vnu.uet.tuan.uetsupporter.Presenter.Profile.DetailLopMonHoc;

import vnu.uet.tuan.uetsupporter.Model.Response.CourseInformation;

/**
 * Created by vmtuan on 3/25/2017.
 */

public interface IPresenterDetailLopMonHocModel {
    interface OnLoadCourseinformationFinish {
        void OnLoadingSuccess(CourseInformation courseInformation);

        void OnLoadingFailure(String fail);
    }

    void getDataFromModel(String courseId, OnLoadCourseinformationFinish listener);
}
