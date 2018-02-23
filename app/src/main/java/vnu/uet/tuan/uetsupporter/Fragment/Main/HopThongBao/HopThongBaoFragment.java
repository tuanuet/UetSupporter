package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vnu.uet.tuan.uetsupporter.Activities.Result2Activity;
import vnu.uet.tuan.uetsupporter.Adapter.RecyclerAdapterHopThongBao;
import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.MainHopThongBao.IPresenterHopThongBaoView;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.MainHopThongBao.PresenterHopThongBaoLogic;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.MainHopThongBao.IViewHopThongBao;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class HopThongBaoFragment extends Fragment implements RecyclerAdapterHopThongBao.ClickListener,
        IViewHopThongBao {

    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapterHopThongBao adapter;
    private ArrayList<AnnouncementNotification> list;
    private IPresenterHopThongBaoView presenterHopThongBaoLogic;


    public HopThongBaoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hopthongbao, container, false);

        initUI(view);

        return view;
    }

    private void initUI(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        list = new ArrayList<>();
        adapter = new RecyclerAdapterHopThongBao(getActivity(), list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG,"onViewCreated");
        presenterHopThongBaoLogic = new PresenterHopThongBaoLogic(getActivity(), this);
        presenterHopThongBaoLogic.executeRetrigerPushNotification();
    }

    @Override
    public void onItemClick(int position, View v) {
        AnnouncementNotification notification = list.get(position);
        Intent intent = new Intent(getActivity(), Result2Activity.class);
        intent.putExtra(Config.KEY_PUSHNOTIFICATION, notification);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position, View v) {

    }


    @Override
    public void OnPreExcute() {

    }

    @Override
    public void OnGetHopThongBaoSuccess(List<AnnouncementNotification> notifications) {
        list.addAll(notifications);
        adapter.notifyItemInserted(list.size() - notifications.size());
    }

    @Override
    public void OnGetHopThongBaoFailure(String fail) {
        Toast.makeText(getActivity(), fail, Toast.LENGTH_SHORT).show();
    }
}
