package vnu.uet.tuan.uetsupporter.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Adapter.PatternRecyclerAdapterNotification;
import vnu.uet.tuan.uetsupporter.Adapter.PatternRecyclerAdapterTinTuc;
import vnu.uet.tuan.uetsupporter.Model.TinTuc;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatternFactoryFragmentTinTuc#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatternFactoryFragmentTinTuc extends Fragment implements Callback<TinTuc[]> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private TinTuc[] listTinTuc = new TinTuc[500];
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
            Log.e("pattern", loaiTinTuc + "");
            getTinTucByLoaiTinTuc(loaiTinTuc);
        }

    }

    public void getTinTucByLoaiTinTuc(int loaitintuc) {
        Call<TinTuc[]> call;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.3:3000")
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.getDataTinTuc(loaitintuc);
        call.enqueue(this);

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

        Log.e("TAG", "onCreateView");
        return view;
    }

    @Override
    public void onResponse(Call<TinTuc[]> call, Response<TinTuc[]> response) {
        listTinTuc = response.body();

        adapter = new PatternRecyclerAdapterTinTuc(getActivity(), listTinTuc);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFailure(Call<TinTuc[]> call, Throwable t) {
        Toast.makeText(getActivity(), "Đường truyền gặp lỗi!", Toast.LENGTH_SHORT).show();
    }
}