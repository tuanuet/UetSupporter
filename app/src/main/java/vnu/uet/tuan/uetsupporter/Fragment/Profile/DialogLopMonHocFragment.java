package vnu.uet.tuan.uetsupporter.Fragment.Profile;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import vnu.uet.tuan.uetsupporter.Activities.Result2Activity;
import vnu.uet.tuan.uetsupporter.Activities.ResultActivity;
import vnu.uet.tuan.uetsupporter.Adapter.ProfileRecyclerLopMonHoc;
import vnu.uet.tuan.uetsupporter.Model.Student;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by Vu Minh Tuan on 2/11/2017.
 */

public class DialogLopMonHocFragment extends DialogFragment implements ProfileRecyclerLopMonHoc.ClickListener {
    RecyclerView recycler;
    Student mSinhVien;
    ProfileRecyclerLopMonHoc adapter;
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter.setOnItemClickListener(this);
    }

    private void initUI(View view) {

        recycler = (RecyclerView) view.findViewById(R.id.recycle_profile);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(mLayoutManager);

        adapter = new ProfileRecyclerLopMonHoc(getActivity(), mSinhVien.getCourses());
        recycler.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(int position, View v) {
        Intent intent = new Intent(getActivity(), Result2Activity.class);
        intent.putExtra(Config.ID_LOPMONHOC, mSinhVien.getCourses().get(position).get_id());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position, View v) {

    }
}
