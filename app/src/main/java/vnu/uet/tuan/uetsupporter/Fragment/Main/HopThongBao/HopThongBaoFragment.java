package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import vnu.uet.tuan.uetsupporter.Model.Base.IReactable;
import vnu.uet.tuan.uetsupporter.Model.Base.Reaction;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.MainHopThongBao.IPresenterHopThongBaoView;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.MainHopThongBao.PresenterHopThongBaoLogic;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.MainHopThongBao.IViewHopThongBao;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class HopThongBaoFragment extends Fragment implements RecyclerAdapterHopThongBao.ClickListener,
        IViewHopThongBao, SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapterHopThongBao adapter;
    private ArrayList<AnnouncementNotification> list;
    private IPresenterHopThongBaoView presenterHopThongBaoLogic;
    private int reactionPostion = 0;
    private SwipeRefreshLayout refreshLayout;

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
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        list = new ArrayList<>();
        adapter = new RecyclerAdapterHopThongBao(getActivity(), list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void onReactionClick(int position, String id, int code) {
        Log.e(TAG, "POSITION: " + position);
        presenterHopThongBaoLogic.react(id, code);
        this.reactionPostion = position;
    }


    @Override
    public void OnPreExcute() {

    }

    @Override
    public void OnGetHopThongBaoSuccess(List<AnnouncementNotification> notifications) {
        list.addAll(notifications);
        adapter.notifyItemInserted(list.size() - notifications.size());
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void OnGetHopThongBaoFailure(String fail) {
        Toast.makeText(getActivity(), fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnReactionSuccess(int code) {
        AnnouncementNotification notification = list.get(reactionPostion);
        updateNotification(code, notification);
        adapter.notifyItemChanged(reactionPostion, notification);
    }

    private void updateNotification(int code, AnnouncementNotification notification) {

        list.get(reactionPostion).setSurprise(new Reaction());
        list.get(reactionPostion).setAngry(new Reaction());
        list.get(reactionPostion).setCry(new Reaction());
        list.get(reactionPostion).setLove(new Reaction());
        list.get(reactionPostion).setWow(new Reaction());

        if (code == Config.ReactionCode.ANGRY.ordinal()) {
            IReactable angry = new Reaction(
                    notification.getAngry().getLength() + 1,
                    true
            );
            list.get(reactionPostion).setAngry(angry);
        } else if (code == Config.ReactionCode.CRY.ordinal()) {
            IReactable cry = new Reaction(
                    notification.getCry().getLength() + 1,
                    true
            );
            list.get(reactionPostion).setCry(cry);
        } else if (code == Config.ReactionCode.LOVE.ordinal()) {
            IReactable love = new Reaction(
                    notification.getLove().getLength() + 1,
                    true
            );
            list.get(reactionPostion).setLove(love);
        } else if (code == Config.ReactionCode.WOW.ordinal()) {
            IReactable wow = new Reaction(
                    notification.getWow().getLength() + 1,
                    true
            );
            list.get(reactionPostion).setWow(wow);
        } else if (code == Config.ReactionCode.SURPRISE.ordinal()) {
            IReactable surprise = new Reaction(
                    notification.getSurprise().getLength() + 1,
                    true
            );
            list.get(reactionPostion).setSurprise(surprise);
        }

    }

    @Override
    public void OnReactionFailure(String fail) {
        Toast.makeText(getActivity(), fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        list.clear();
        adapter.notifyDataSetChanged();
        presenterHopThongBaoLogic.executeRetrigerPushNotification();
    }
}
