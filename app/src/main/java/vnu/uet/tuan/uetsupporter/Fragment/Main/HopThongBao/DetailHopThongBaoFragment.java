package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Model.DetailThongBao;
import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.Model.Response.DiemResponse;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailHopThongBaoFragment extends Fragment {

    PushNotification notification;
    TextView txt_test;
    Call<DetailThongBao> call;
    DetailThongBao mDetailThongBao;

    private final String TAG = this.getClass().getSimpleName();
    public DetailHopThongBaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_hop_thong_bao, container, false);

        getData();

        initUI(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AsynDetailThongBao().execute();
            }
        });

    }

    private void getData() {
        notification = (PushNotification) getArguments().getSerializable(Config.KEY_PUSHNOTIFICATION);
    }

    private void initUI(View view) {
        txt_test = (TextView) view.findViewById(R.id.test);
    }

    private Call<DetailThongBao> getDiem() {
        Call<DetailThongBao> call;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        Log.e(TAG, notification.getLink());

        String idLop = getId(notification.getLink());

        call = apiTinTuc.getDetailThongBao(idLop, Utils.getUserToken(getActivity()));
        return call;
    }

    //link dang /avc/idThongbao;
    //đưa về lay idThongbao
    private String getId(String link) {
        String[] arr = link.split("/");
        if (arr.length == 3) {
            return arr[2].trim();
        } else return null;
    }

    protected class AsynDetailThongBao extends AsyncTask<Void, Void, DetailThongBao> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            call = getDiem();
        }

        @Override
        protected DetailThongBao doInBackground(Void... params) {

            try {
                Response<DetailThongBao> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    return response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(DetailThongBao detailThongBao) {
            super.onPostExecute(detailThongBao);
            if (detailThongBao != null) {
                updateUI(detailThongBao);
            } else {
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void updateUI(DetailThongBao detailThongBao) {
        txt_test.setText(detailThongBao.getTieuDe());
    }
}
