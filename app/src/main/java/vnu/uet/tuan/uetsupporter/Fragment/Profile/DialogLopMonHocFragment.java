package vnu.uet.tuan.uetsupporter.Fragment.Profile;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import vnu.uet.tuan.uetsupporter.Adapter.ProfileRecyclerLopMonHoc;
import vnu.uet.tuan.uetsupporter.Model.SinhVien;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class DialogLopMonHocFragment extends DialogFragment {
    RecyclerView recycler;
    SinhVien mSinhVien;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSinhVien = getArguments().getParcelable(Config.SINHVIEN);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_lopmonhoc, container, false);

        initUI(view);

        return view;

    }

    private void initUI(View view) {

        recycler = (RecyclerView) view.findViewById(R.id.recycle_profile);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(mLayoutManager);

        ProfileRecyclerLopMonHoc adapter = new ProfileRecyclerLopMonHoc(getActivity(), mSinhVien.getIdLopMonHoc());
        recycler.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
    }

}
