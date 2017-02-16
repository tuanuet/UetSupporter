package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Adapter.PatternRecyclerAdapterTinTuc;
import vnu.uet.tuan.uetsupporter.Adapter.RecyclerAdapterHopThu;
import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class HopThuFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapterHopThu adapter;

    public HopThuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hopthu, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        ArrayList<PushNotification> list = Utils.getPushNotification(getActivity());
        if (list != null) {
            adapter = new RecyclerAdapterHopThu(getActivity(), list);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

}
