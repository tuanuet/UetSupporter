package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Activities.Result2Activity;
import vnu.uet.tuan.uetsupporter.Activities.ResultActivity;
import vnu.uet.tuan.uetsupporter.Adapter.RecyclerAdapterHopThongBao;
import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class HopThongBaoFragment extends Fragment implements RecyclerAdapterHopThongBao.ClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapterHopThongBao adapter;
    ArrayList<PushNotification> list;

    public HopThongBaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hopthongbao, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = Utils.getPushNotification(getActivity());
        if (list != null) {
            adapter = new RecyclerAdapterHopThongBao(getActivity(), list);
            recyclerView.setAdapter(adapter);
        }
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position, View v) {
        PushNotification notification = list.get(position);
        Intent intent = new Intent(getActivity(), Result2Activity.class);
        intent.putExtra(Config.KEY_PUSHNOTIFICATION, notification);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position, View v) {

    }
}
