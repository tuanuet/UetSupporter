package vnu.uet.tuan.uetsupporter.Fragment.Loading;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Model.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.Contract;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.LoaiThongBaoSQLHelper;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.LoaiTinTucSQLHelper;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingFragment extends Fragment {

    private LoaiTinTucSQLHelper loaiTinTucSQLHelper;
    private LoaiThongBaoSQLHelper loaiThongBaoSQLHelper;

    public LoadingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loading, container, false);

        loaiTinTucSQLHelper = new LoaiTinTucSQLHelper(getActivity());
        loaiThongBaoSQLHelper = new LoaiThongBaoSQLHelper(getActivity());

        insertDBLoaiTinTuc();
        insertDBLoaiThongBao();

        return view;
    }

    private void insertDBLoaiThongBao() {
        Call<List<LoaiThongBao>> call;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.getAllLoaiThongBao();
        call.enqueue(new Callback<List<LoaiThongBao>>() {
            @Override
            public void onResponse(Call<List<LoaiThongBao>> call, Response<List<LoaiThongBao>> response) {
                List<LoaiThongBao> arrLoaiThongBao = response.body();
                if (arrLoaiThongBao != null) {
                    int count = loaiThongBaoSQLHelper.insertBulkLoaiThongBao(arrLoaiThongBao);
                    Log.e("Loading", count + "");
                } else {
                    Toast.makeText(getActivity(), "Đường truyền lỗi kiểm tra lại", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LoaiThongBao>> call, Throwable t) {

            }
        });

    }

    private void insertDBLoaiTinTuc() {
        Call<ArrayList<LoaiTinTuc>> call;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.getAllLoaiTinTuc();
        call.enqueue(new Callback<ArrayList<LoaiTinTuc>>() {
            @Override
            public void onResponse(Call<ArrayList<LoaiTinTuc>> call, Response<ArrayList<LoaiTinTuc>> response) {
                ArrayList<LoaiTinTuc> arrLoaiTinTuc = response.body();
                if (arrLoaiTinTuc != null) {
                    int count = loaiTinTucSQLHelper.insertBulkLoaiTinTuc(arrLoaiTinTuc);
                    Log.e("Loading", count + "");
                } else {
                    Toast.makeText(getActivity(), "Đường truyền lỗi kiểm tra lại", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LoaiTinTuc>> call, Throwable t) {

            }
        });
    }


}
