package vnu.uet.tuan.uetsupporter.View.Profile.DetailLopMonHoc;

import vnu.uet.tuan.uetsupporter.Model.Course;
import vnu.uet.tuan.uetsupporter.Model.Response.CourseInformation;

/**
 * Created by vmtuan on 3/25/2017.
 */

public interface IViewDetailLopMonHoc {
    void onPreExecute();

    void onExecuteSuccess(CourseInformation courseInformation);

    void onExecuteFailure(String fail);

}
