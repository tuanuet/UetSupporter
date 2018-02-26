package vnu.uet.tuan.uetsupporter.Fragment.Profile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailLopMonHocFragment extends Fragment {

    private String mIdLopMonHoc = "";
    private TextView txt_test;

    public DetailLopMonHocFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_lop_mon_hoc, container, false);

        //getData từ activity
        getData();

        initUI(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txt_test.setText(mIdLopMonHoc);

    }

    private void initUI(View view) {
        txt_test = (TextView) view.findViewById(R.id.test);
    }

    public void getData() {
        mIdLopMonHoc = getArguments().getString(Config.ID_LOPMONHOC);
    }
}
