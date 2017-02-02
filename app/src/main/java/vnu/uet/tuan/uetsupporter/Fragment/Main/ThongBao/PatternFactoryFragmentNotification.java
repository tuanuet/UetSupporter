package vnu.uet.tuan.uetsupporter.Fragment.Main.ThongBao;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vnu.uet.tuan.uetsupporter.Adapter.PatternRecyclerAdapterNotification;
import vnu.uet.tuan.uetsupporter.Model.MyParcelable;
import vnu.uet.tuan.uetsupporter.Model.Notification;
import vnu.uet.tuan.uetsupporter.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatternFactoryFragmentNotification#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatternFactoryFragmentNotification extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Notification[] thongBaos;

    public PatternFactoryFragmentNotification() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param thongBaos Parameter 1.
     * @return A new instance of fragment PatternFactoryFragmentNotification.
     */
    // TODO: Rename and change types and number of parameters
    public static PatternFactoryFragmentNotification newInstance(Notification[] thongBaos) {
        PatternFactoryFragmentNotification fragment = new PatternFactoryFragmentNotification();
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
    PatternRecyclerAdapterNotification adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pattern_factory_notification, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_pattern);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new PatternRecyclerAdapterNotification(getActivity(), thongBaos);
        recyclerView.setAdapter(adapter);
        Log.e("TAG","onCreateView");
        return view;
    }

}
