package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.Model.Response.DiemResponse;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailHopThongBaoDiemFragment extends Fragment {
    PushNotification notification;
    List<DiemResponse> listDiem;
    Call<List<DiemResponse>> call;
    Button btn_xemthem;
    LinearLayout table;
    TextView txt_msv, txt_ten, txt_giuaky, txt_cuoiky, txt_tong;
    private boolean isShow = false;


    public DetailHopThongBaoDiemFragment() {
        // Required empty public constructor
    }

    private void initUI(View view) {
        btn_xemthem = (Button) view.findViewById(R.id.btn_xemthem);
        table = (LinearLayout) view.findViewById(R.id.table);
        txt_msv = (TextView) view.findViewById(R.id.msv);
        txt_ten = (TextView) view.findViewById(R.id.ten);
        txt_giuaky = (TextView) view.findViewById(R.id.giuaky);
        txt_cuoiky = (TextView) view.findViewById(R.id.cuoiky);
        txt_tong = (TextView) view.findViewById(R.id.tong);
    }

    /**
     * UPDATE CHINH USER NAY THOI
     * tim id sinh vien
     */
    private void updateUI() {
        int postion = 0;
        for (int i = 0; i < listDiem.size(); i++) {
            if (listDiem.get(i).getIdSinhVien().get_id().equals(Utils.getEmailUser(getActivity()))) {
                postion = i;
                break;
            }
        }

        DiemResponse userDiem = listDiem.get(postion);
        //xoa di item user trong list
        listDiem.remove(postion);

        txt_msv.setText(userDiem.getIdSinhVien().get_id());
        txt_ten.setText(userDiem.getIdSinhVien().getTenSinhVien());
        txt_giuaky.setText(String.valueOf(userDiem.getDiemThanhPhan()));
        txt_cuoiky.setText(String.valueOf(userDiem.getDiemCuoiKy()));
        txt_tong.setText(String.valueOf(getDiemTong(userDiem.getDiemThanhPhan(), userDiem.getDiemCuoiKy())));
    }

    private float getDiemTong(double diemThanhPhan, double diemCuoiKy) {
        return (float) (0.6 * diemCuoiKy + 0.4 * diemThanhPhan);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_hop_thong_bao_diem, container, false);

//        getDataFromIntent();

        initUI(view);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_xemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listDiem != null) {

                    if (!isShow) {
                        btn_xemthem.setText(getString(R.string.xemthem));
                        for (int pos = 0; pos < listDiem.size(); pos++) {
                            createRow(listDiem.get(pos));
                        }
                    } else {
                        btn_xemthem.setText(getString(R.string.thugon));
                    }

                }

            }
        });

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AsynGetDiem().execute();
            }
        });
    }


    protected class AsynGetDiem extends AsyncTask<Void, Void, List<DiemResponse>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            call = getDiem();
        }

        @Override
        protected List<DiemResponse> doInBackground(Void... params) {

            try {
                Response<List<DiemResponse>> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    return response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<DiemResponse> diemResponse) {
            super.onPostExecute(diemResponse);
            if (diemResponse != null) {
                listDiem = diemResponse;
                updateUI();
            } else {
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private Call<List<DiemResponse>> getDiem() {
        Call<List<DiemResponse>> call;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
//        String idLop = getidLop(notification.getLink());
        String idLop = "/diemmonhoc/lop/INT2204 1";
        call = apiTinTuc.getDiemOneSinhVien(getidLop(idLop), Utils.getUserToken(getActivity()));
        return call;
    }

    //link dang /avc/avc/idLop;
    //đưa về lay idlop
    private String getidLop(String link) {
        String[] arr = link.split("/");
        if (arr.length == 4) {
            return arr[3].trim();
        } else return null;
    }

    private void getDataFromIntent() {
        notification = (PushNotification) getArguments().getSerializable(Config.KEY_PUSHNOTIFICATION);
    }

    private void createRow(DiemResponse item) {
        LinearLayout row = new LinearLayout(getActivity());
        row.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        10)
        );
        row.setOrientation(LinearLayout.HORIZONTAL);
        //================================================================
        row.addView(createCol(R.drawable.border_linerlayout_left_bottom,
                2, item.getIdSinhVien().get_id()));
        row.addView(createCol(R.drawable.border_linerlayout_left_bottom,
                3.5f, item.getIdSinhVien().getTenSinhVien()));
        row.addView(createCol(R.drawable.border_linerlayout_left_bottom,
                1.5f, String.valueOf(item.getDiemThanhPhan())));
        row.addView(createCol(R.drawable.border_linerlayout_left_bottom,
                1.5f, String.valueOf(item.getDiemCuoiKy())));
        row.addView(createCol(R.drawable.border_linerlayout_left_right_bottom,
                1.5f, String.valueOf(getDiemTong(item.getDiemThanhPhan(), item.getDiemCuoiKy()))));

        //==================================================================
        table.addView(row);

    }

    private LinearLayout createCol(int idBackground, float weight, String text) {
        LinearLayout col = new LinearLayout(getActivity());
        col.setLayoutParams(
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        weight
                )
        );
        col.setBackground(getResources().getDrawable(idBackground));

        TextView txt = new TextView(getActivity());
        txt.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        txt.setGravity(Gravity.CENTER);
        txt.setText(text);
        col.addView(txt);

        return col;
    }

}
