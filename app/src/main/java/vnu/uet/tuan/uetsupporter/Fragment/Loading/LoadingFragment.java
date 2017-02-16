package vnu.uet.tuan.uetsupporter.Fragment.Loading;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Activities.LoadingActivity;
import vnu.uet.tuan.uetsupporter.Activities.MainActivity;
import vnu.uet.tuan.uetsupporter.Model.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.LoaiThongBaoSQLHelper;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.LoaiTinTucSQLHelper;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingFragment extends Fragment {

    private int currentTask = 0;
    private int maxTask = 2;
    private LoaiTinTucSQLHelper loaiTinTucSQLHelper;
    private LoaiThongBaoSQLHelper loaiThongBaoSQLHelper;
    private ProgressBar progressBar;
    private TextView txt_percent;

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
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        txt_percent = (TextView) view.findViewById(R.id.txt_percent);
        progressBar.setMax(maxTask);

        Thread runCheck = new Thread(new RunCheck());
        runCheck.start();


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
                    increasingTask();
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
                    increasingTask();
                } else {
                    Toast.makeText(getActivity(), "Đường truyền lỗi kiểm tra lại", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LoaiTinTuc>> call, Throwable t) {

            }
        });
    }

    class RunCheck implements Runnable {

        @Override
        public void run() {
            while (currentTask != maxTask) {
                //wait
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                progressBar.setProgress(currentTask);

//                try{
//                    txt_percent.setText(getPercen());
//                }catch (Exception e){
//                    e.printStackTrace();
//                }

                //update progress
            }
            //cai dat lai bien IsRunFirstTime thanh false
            setIsRunFirstTime(false);
            getActivity().finish();
            Intent login = new Intent(getActivity(), LoadingActivity.class);
            startActivity(login);
        }
    }

    private void setIsRunFirstTime(Boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Config.IS_RUN_FIRST_TIME, value);
        editor.commit();
    }

    private synchronized void increasingTask() {
        currentTask++;
    }

    private String getPercen() {
        double doubl = currentTask / maxTask;
        return doubl + " %";
    }

}
