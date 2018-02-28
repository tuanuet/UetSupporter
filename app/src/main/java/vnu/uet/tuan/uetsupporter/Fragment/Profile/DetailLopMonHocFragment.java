package vnu.uet.tuan.uetsupporter.Fragment.Profile;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import vnu.uet.tuan.uetsupporter.Model.Response.CourseInformation;
import vnu.uet.tuan.uetsupporter.Model.Student;
import vnu.uet.tuan.uetsupporter.Presenter.Profile.DetailLopMonHoc.IPresenterDetailLopMonHocView;
import vnu.uet.tuan.uetsupporter.Presenter.Profile.DetailLopMonHoc.PresenterDetailLopMonHocLogic;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.View.Profile.DetailLopMonHoc.IViewDetailLopMonHoc;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailLopMonHocFragment extends Fragment implements IViewDetailLopMonHoc {

    private final String TAG = this.getClass().getSimpleName();
    private String courseId;
    private IPresenterDetailLopMonHocView presenter;
    private LinearLayout table;
    private TextView titleName,titleCodeCourse,titleSizeClass,titleTerm,titleLecturer;
    public DetailLopMonHocFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_lop_mon_hoc, container, false);

        //getData từ activity
        this.courseId = getCourseId();

        initUI(view);

        presenter = new PresenterDetailLopMonHocLogic(getActivity(),this);
        presenter.executeLoadInformationCourse(this.courseId);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private LinearLayout createCol(int idBackground, float weight, String text) {
        LinearLayout col = new LinearLayout(getActivity());
        col.setLayoutParams(
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        weight
                )
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            col.setBackground(getResources().getDrawable(idBackground));
        }

        TextView txt = new TextView(getActivity());
        txt.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        txt.setGravity(Gravity.CENTER);
        txt.setText(text);
        col.addView(txt);

        return col;
    }
    private void createRow(Student student) {
        LinearLayout row = new LinearLayout(getActivity());
        row.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        10)
        );
        row.setOrientation(LinearLayout.HORIZONTAL);
        //================================================================
        row.addView(createCol(R.drawable.border_linerlayout_left_bottom,
                2, student.getCode()));
        row.addView(createCol(R.drawable.border_linerlayout_left_bottom,
                4, student.getName()));
        row.addView(createCol(R.drawable.border_linerlayout_left_right_bottom,
                4, student.getMyClass().getName()));

        //==================================================================
        table.addView(row);

    }

    private void initUI(View view) {
//        txt_test = (TextView) view.findViewById(R.id.test);
        table = (LinearLayout) view.findViewById(R.id.table);
        titleName = (TextView) view.findViewById(R.id.title_mon_hoc);
        titleCodeCourse = (TextView) view.findViewById(R.id.title_ma_mon_hoc);
        titleLecturer = (TextView) view.findViewById(R.id.title_giang_viens);
        titleSizeClass = (TextView) view.findViewById(R.id.title_size_class);
        titleTerm = (TextView) view.findViewById(R.id.title_term);
    }

    public String getCourseId() {
        return getArguments().getString(Config.ID_LOPMONHOC);
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onExecuteSuccess(CourseInformation courseInformation) {

        titleTerm.setText(String.format(getString(R.string.title_term),"II năm học 2017-2018"));
        titleName.setText(String.format(getString(R.string.title_mon_hoc),courseInformation.getCourse().getName()));
        titleCodeCourse.setText(String.format(getString(R.string.title_ma_mon_hoc),courseInformation.getCourse().get_id()));
        titleLecturer.setText(String.format(getString(R.string.title_giang_viens),Utils.getTenGiangVien(courseInformation.getCourse().getLectures())));
        titleSizeClass.setText(String.format(getString(R.string.title_size_class),100));

        for (Student student : courseInformation.getStudents()){
            createRow(student);
        }
    }

    @Override
    public void onExecuteFailure(String fail) {
        Log.e(TAG,fail);
        Toast.makeText(getContext(), fail, Toast.LENGTH_SHORT).show();
    }
}
