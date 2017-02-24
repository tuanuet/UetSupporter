package vnu.uet.tuan.uetsupporter.Fragment.Loading;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Activities.LoadingActivity;
import vnu.uet.tuan.uetsupporter.Model.Download.LoaiThongBao;
import vnu.uet.tuan.uetsupporter.Model.Download.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.Model.Download.MucDoThongBao;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.LoaiThongBaoSQLHelper;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.LoaiTinTucSQLHelper;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.MucDoThongBaoSQLHelper;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingFragment extends Fragment {

    private int currentTask = 0;
    private int maxTask = 3;
    private LoaiTinTucSQLHelper loaiTinTucSQLHelper;
    private LoaiThongBaoSQLHelper loaiThongBaoSQLHelper;
    private MucDoThongBaoSQLHelper mucDoThongBaoSQLHelper;
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
        mucDoThongBaoSQLHelper = new MucDoThongBaoSQLHelper(getActivity());
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        txt_percent = (TextView) view.findViewById(R.id.txt_percent);
        progressBar.setMax(maxTask);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new RunLoading().execute();
            }
        });
    }

    class RunLoading extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txt_percent.setText(getPercen());
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                //insert LoaiThongBao
                Call<List<LoaiThongBao>> call = getLoaiThongBao();
                Response<List<LoaiThongBao>> responseThongBao = call.execute();
                if (responseThongBao.isSuccessful() && responseThongBao.body() != null) {
                    List<LoaiThongBao> list = responseThongBao.body();
                    int count = loaiThongBaoSQLHelper.insertBulk(list);
                    currentTask++;
                    publishProgress(currentTask);
                } else {
                    return false;
                }

                //insert LoaiTinTuc
                Call<ArrayList<LoaiTinTuc>> callTinTuc = getLoaiTinTuc();
                Response<ArrayList<LoaiTinTuc>> responseLoaiTinTuc = callTinTuc.execute();
                if (responseLoaiTinTuc.isSuccessful() && responseLoaiTinTuc.body() != null) {
                    ArrayList<LoaiTinTuc> list = responseLoaiTinTuc.body();
                    int count = loaiTinTucSQLHelper.insertBulk(list);
                    currentTask++;
                    publishProgress(currentTask);
                } else {
                    return false;
                }

                //insert MucDoThongBao
                Call<List<MucDoThongBao>> callMucDoThongBao = getMucDoThongBao();
                Response<List<MucDoThongBao>> responseMucDoThongBao = callMucDoThongBao.execute();
                if (responseMucDoThongBao.isSuccessful() && responseMucDoThongBao.body() != null) {
                    List<MucDoThongBao> list = responseMucDoThongBao.body();
                    Log.e("Loading", "Size muc do: " + list.size());
                    int count = mucDoThongBaoSQLHelper.insertBulk(list);
                    currentTask++;
                    publishProgress(currentTask);
                } else {
                    return false;
                }
                //.......

            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            txt_percent.setText(getPercen());
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            //cai dat lai bien IsRunFirstTime thanh false
            if (isSuccess) {
                if (currentTask == maxTask) {
                    setIsRunFirstTime(false);
                    getPercen();
                    Toast.makeText(getActivity(), getString(R.string.please_exit), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), getString(R.string.fail_download), Toast.LENGTH_LONG).show();
            }

        }
    }

    private Call<List<LoaiThongBao>> getLoaiThongBao() {
        Call<List<LoaiThongBao>> call;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.getAllLoaiThongBao();
        return call;
    }

    private Call<List<MucDoThongBao>> getMucDoThongBao() {
        Call<List<MucDoThongBao>> call;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.getAllMucDoThongBao();
        return call;
    }

    private Call<ArrayList<LoaiTinTuc>> getLoaiTinTuc() {
        Call<ArrayList<LoaiTinTuc>> call;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.getAllLoaiTinTuc();
        return call;
    }

    private String getPercen() {
        float doubl = (float) currentTask / maxTask;
        return doubl + " %";
    }
    private void setIsRunFirstTime(Boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Config.IS_RUN_FIRST_TIME, value);
        editor.commit();
    }
}
