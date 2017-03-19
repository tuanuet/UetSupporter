package vnu.uet.tuan.uetsupporter.Fragment.Main.TinTuc;


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
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Activities.ResultActivity;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatternFactoryFragmentTinTuc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatternFactoryFragmentTinTuc extends Fragment implements
        Callback<ArrayList<TinTuc>> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private final String TAG = this.getClass().getSimpleName();

    // TODO: Rename and change types of parameters
    private ArrayList<TinTuc> listTinTuc;
    private int loaiTinTuc;


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
    public static PatternFactoryFragmentTinTuc newInstance(int loaitintuc) {
        PatternFactoryFragmentTinTuc fragment = new PatternFactoryFragmentTinTuc();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, loaitintuc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "onCreate");
        if (getArguments() != null) {
            loaiTinTuc = getArguments().getInt(ARG_PARAM1);
            listTinTuc = new ArrayList<TinTuc>();
            Log.e("pattern", loaiTinTuc + "");
            getTinTucByLoaiTinTuc(loaiTinTuc, 0);
        }

    }



    RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    PatternRecyclerAdapterTinTuc adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * lam lai phan nay
         */

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pattern_factory_notification, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_pattern);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new PatternRecyclerAdapterTinTuc(getActivity(), listTinTuc);
        recyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter.setOnItemClickListener(new PatternRecyclerAdapterTinTuc.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // do whatever
                Toast.makeText(
                        getActivity(),
                        listTinTuc.get(position).getTitle(),
                        Toast.LENGTH_SHORT).show();

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
                getTinTucByLoaiTinTuc(loaiTinTuc, current_page);
            }
        });


    }

    public void getTinTucByLoaiTinTuc(int loaitintuc, int offsetPage) {
        Call<ArrayList<TinTuc>> call;
        OkHttpClient okHttpClient = ConfigCache.createCachedClient(getActivity());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                .client(okHttpClient)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.getDataTinTuc(loaitintuc, offsetPage * 10);
        call.enqueue(this);

    }
    @Override
    public void onResponse(Call<ArrayList<TinTuc>> call, Response<ArrayList<TinTuc>> response) {
        int lastPosition = 0;
        if (listTinTuc == null) {
            lastPosition = 0;
            try {
                listTinTuc = response.body();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

        } else {
            try {
                lastPosition = listTinTuc.size();
                listTinTuc.addAll(response.body());
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        adapter.notifyItemInserted(lastPosition);
    }

    @Override
    public void onFailure(Call<ArrayList<TinTuc>> call, Throwable t) {
//        Toast.makeText(getActivity(), "Đường truyền gặp lỗi!", Toast.LENGTH_SHORT).show();
    }


}
