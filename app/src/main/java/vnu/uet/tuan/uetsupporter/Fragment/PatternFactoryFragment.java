package vnu.uet.tuan.uetsupporter.Fragment;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import vnu.uet.tuan.uetsupporter.Adapter.PatternRecyclerAdapter;
import vnu.uet.tuan.uetsupporter.Model.MyParcelable;
import vnu.uet.tuan.uetsupporter.Model.Notification;
import vnu.uet.tuan.uetsupporter.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatternFactoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatternFactoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Notification[] thongBaos;

    public PatternFactoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param thongBaos Parameter 1.
     * @return A new instance of fragment PatternFactoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatternFactoryFragment newInstance(Notification[] thongBaos) {
        PatternFactoryFragment fragment = new PatternFactoryFragment();
        Bundle args = new Bundle();
        MyParcelable parcelable = new MyParcelable(thongBaos);
        args.putParcelable(ARG_PARAM1,parcelable);
        fragment.setArguments(args);
        Log.e("TAG",thongBaos.length + "");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG","onCreate");
        if (getArguments() != null) {
            MyParcelable parcelable = getArguments().getParcelable(ARG_PARAM1);
            thongBaos = parcelable.getList();

        }
    }

    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    PatternRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pattern_factory, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_pattern);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);
        adapter =new PatternRecyclerAdapter(getActivity(),thongBaos);
        recyclerView.setAdapter(adapter);
        Log.e("TAG","onCreateView");
        return view;
    }

}
