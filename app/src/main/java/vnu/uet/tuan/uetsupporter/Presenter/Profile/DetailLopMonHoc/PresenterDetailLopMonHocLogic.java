package vnu.uet.tuan.uetsupporter.Presenter.Profile.DetailLopMonHoc;

import android.content.Context;

import vnu.uet.tuan.uetsupporter.Model.Response.CourseInformation;
import vnu.uet.tuan.uetsupporter.View.Profile.DetailLopMonHoc.IViewDetailLopMonHoc;

/**
 * Created by vmtuan on 3/25/2017.
 */

public class PresenterDetailLopMonHocLogic implements IPresenterDetailLopMonHocView, IPresenterDetailLopMonHocModel.OnLoadCourseinformationFinish {
    private PresenterDetailLopMonHocModel presenter;
    private IViewDetailLopMonHoc iView;

    public PresenterDetailLopMonHocLogic(Context context, IViewDetailLopMonHoc iView) {
        this.iView = iView;
        presenter = new PresenterDetailLopMonHocModel(context);

    }

    @Override
    public void executeLoadInformationCourse(String courseId) {
        iView.onPreExecute();
        presenter.getDataFromModel(courseId,this);
    }

    @Override
    public void OnLoadingSuccess(CourseInformation courseInformation) {
        iView.onExecuteSuccess(courseInformation);
    }

    @Override
    public void OnLoadingFailure(String fail) {
        iView.onExecuteFailure(fail);
    }
}
