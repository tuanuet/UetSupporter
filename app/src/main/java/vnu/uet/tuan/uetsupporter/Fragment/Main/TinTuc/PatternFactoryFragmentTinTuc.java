package vnu.uet.tuan.uetsupporter.Fragment.Main.TinTuc;


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

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Adapter.PatternRecyclerAdapterTinTuc;
import vnu.uet.tuan.uetsupporter.Cache.ConfigCache;
import vnu.uet.tuan.uetsupporter.Listener.EndlessRecyclerOnScrollListener;

import vnu.uet.tuan.uetsupporter.Model.Response.TinTuc;
import vnu.uet.tuan.uetsupporter.Presenter.Main.TinTuc.IPresenterTinTucView;
import vnu.uet.tuan.uetsupporter.Presenter.Main.TinTuc.PresenterTinTucLogic;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Activities.ResultActivity;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.View.Main.TinTuc.IViewTinTuc;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatternFactoryFragmentTinTuc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatternFactoryFragmentTinTuc extends Fragment implements IViewTinTuc,SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_PARAM1 = "param1";

    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<TinTuc> listTinTuc;
    private String loaiTinTuc;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private PatternRecyclerAdapterTinTuc adapter;
    private IPresenterTinTucView presenterTinTucLogic;
    private SwipeRefreshLayout refreshLayout;

    public PatternFactoryFragmentTinTuc() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param loaitintuc Parameter 1.
     * @return A new instance of fragment PatternFactoryFragmentNotification.
     */
    // TODO: Rename and change types and number of parameters
    public static PatternFactoryFragmentTinTuc newInstance(String loaitintuc) {
        PatternFactoryFragmentTinTuc fragment = new PatternFactoryFragmentTinTuc();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, loaitintuc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "onCreate");
        if (getArguments() != null) {
            loaiTinTuc = getArguments().getString(ARG_PARAM1);
            listTinTuc = new ArrayList<>();
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * lam lai phan nay
         */

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pattern_factory_notification, container, false);

        initUI(view);

        presenterTinTucLogic = new PresenterTinTucLogic(getActivity(), this);
        presenterTinTucLogic.executeTinTuc(loaiTinTuc, 0);

        return view;
    }

    private void initUI(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_pattern);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new PatternRecyclerAdapterTinTuc(getActivity(), listTinTuc);
        recyclerView.setAdapter(adapter);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(this);

        adapter.setOnItemClickListener(new PatternRecyclerAdapterTinTuc.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putExtra(Config.KEY_URL, listTinTuc.get(position).getLink());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                presenterTinTucLogic.executeTinTuc(loaiTinTuc, current_page);
            }
        });
    }


    @Override
    public void OnPreSendRequest() {

    }

    @Override
    public void OnGetReponseSuccess(List<TinTuc> tinTucs) {
        int lastPosition = listTinTuc.size();
        listTinTuc.addAll(tinTucs);
        adapter.notifyItemInserted(lastPosition);
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void OnGetReponseFailure(String fail) {
        Toast.makeText(getActivity(), fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelSuccess(String success) {
    }

    @Override
    public void onCancelFailure(String fail) {
        Log.e(TAG, "onCancelFailure: " + fail);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenterTinTucLogic.cancelExcute();
    }


    @Override
    public void onRefresh() {
        listTinTuc.clear();
        adapter.notifyDataSetChanged();
        presenterTinTucLogic.cancelExcute();
        presenterTinTucLogic.executeTinTuc(loaiTinTuc, 0);
    }
}
