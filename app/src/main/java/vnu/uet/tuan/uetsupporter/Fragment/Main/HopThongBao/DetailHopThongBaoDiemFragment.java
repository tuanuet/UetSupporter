package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.io.IOException;
import java.util.ArrayList;
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
public class DetailHopThongBaoDiemFragment extends Fragment implements OnChartValueSelectedListener {
    PushNotification notification;
    List<DiemResponse> listDiem;
    Call<List<DiemResponse>> call;
    Button btn_xemthem;
    LinearLayout table;
    TextView txt_msv, txt_ten, txt_giuaky, txt_cuoiky, txt_tong;
    PieChart pieChart1;

    protected String[] mParties = new String[]{
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

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
        pieChart1 = (PieChart) view.findViewById(R.id.chart);

        initPieChart(pieChart1);
    }

    private void initPieChart(PieChart mChart) {
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        setData(pieChart1, 4, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);
    }

    private void setData(PieChart mChart, int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                    mParties[i % mParties.length],
                    getResources().getDrawable(R.drawable.ic_action_stat_reply)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");


        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
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


        //==============================

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

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

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
            if (diemResponse != null && diemResponse.size() != 0) {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            col.setBackground(getResources().getDrawable(idBackground));
        }

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
