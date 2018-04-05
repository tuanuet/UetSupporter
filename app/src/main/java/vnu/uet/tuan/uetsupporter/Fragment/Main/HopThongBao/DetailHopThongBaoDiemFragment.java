package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;
import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;
import vnu.uet.tuan.uetsupporter.Model.Response.DiemResponse;
import vnu.uet.tuan.uetsupporter.Model.Student;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.DetailHopThongBaoDiem.IPresenterDetailHopThongBaoDiemView;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.DetailHopThongBaoDiem.PresenterDetailHopThongBaoDiemLogic;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.DetailHopThongBaoDiem.IViewDetailHopThongBaoDiem;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailHopThongBaoDiemFragment extends Fragment implements OnChartValueSelectedListener, IViewDetailHopThongBaoDiem {

    private final String TAG = this.getClass().getSimpleName();
    private Toolbar toolbar;
    private AnnouncementNotification notification;
    private DiemResponse markResponse;

    private Button btn_xemthem;
    private LinearLayout table;
    private TextView txt_msv, txt_ten, txt_giuaky, txt_cuoiky, txt_tong;
    private PieChart pieChart1;

    protected String[] mParties = new String[]{
            "0 => 4", "4 => 7", "7 => 8", "8 >= 10"
    };

    private IPresenterDetailHopThongBaoDiemView presenter;
    private boolean isShow = false;


    public DetailHopThongBaoDiemFragment() {
        // Required empty public constructor
    }

    private void initUI(View view) {
        markResponse = new DiemResponse();

        btn_xemthem = (Button) view.findViewById(R.id.btn_xemthem);
        table = (LinearLayout) view.findViewById(R.id.table);
        txt_msv = (TextView) view.findViewById(R.id.msv);
        txt_ten = (TextView) view.findViewById(R.id.ten);
        txt_giuaky = (TextView) view.findViewById(R.id.giuaky);
        txt_cuoiky = (TextView) view.findViewById(R.id.cuoiky);
        txt_tong = (TextView) view.findViewById(R.id.tong);
        pieChart1 = (PieChart) view.findViewById(R.id.chart);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> getActivity().finish());
        initPieChart();

        btn_xemthem.setOnClickListener(v -> {
            if (markResponse != null) {

                if (!isShow) {
                    btn_xemthem.setText(getString(R.string.xemthem));
                    for (int pos = 0; pos < markResponse.getLengthData(); pos++) {
                        Log.e(TAG, markResponse.getLengthData() + "");
                        createRow(markResponse.getStudent(pos),markResponse.getMarkMiddle(pos),markResponse.getMarkFinal(pos));
                    }
                } else {
                    btn_xemthem.setText(getString(R.string.thugon));
                }

            }

        });
    }

    /**
     * =======================================================================
     * Khởi tạo pieChart
     */
    private void initPieChart() {
        pieChart1.setUsePercentValues(true);
        pieChart1.getDescription().setEnabled(false);
        pieChart1.setExtraOffsets(5, 10, 5, 5);

        pieChart1.setDragDecelerationFrictionCoef(0.6f);

        pieChart1.setCenterText(generateCenterSpannableText());

        pieChart1.setDrawHoleEnabled(true);
        pieChart1.setHoleColor(Color.WHITE);

        pieChart1.setTransparentCircleColor(Color.WHITE);
        pieChart1.setTransparentCircleAlpha(110);

        pieChart1.setHoleRadius(60f);
        pieChart1.setTransparentCircleRadius(61f); //mờ các màu

        pieChart1.setDrawCenterText(true);

        pieChart1.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart1.setRotationEnabled(true);
        pieChart1.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        pieChart1.setOnChartValueSelectedListener(this);

        pieChart1.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = pieChart1.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(3f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        pieChart1.setEntryLabelColor(Color.WHITE);
        pieChart1.setEntryLabelTextSize(12f);
    }

    private void setData(PieChart mChart, int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        ArrayList<Integer> valueEntry = getValueEntry();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {


            entries.add(new PieEntry(valueEntry.get(i),
                    mParties[i % mParties.length],
                    getResources().getDrawable(R.drawable.ic_action_stat_reply)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Điểm");


        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(8f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
//        //dataSet.setSelectionShift(0f);
//
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private ArrayList<Integer> getValueEntry() {
        int zeroTo4 = 0;
        int fourTo7 = 0;
        int sevenTo8 = 0;
        int eightTo10 = 0;
        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 0; i < markResponse.getLengthData(); i++) {
            double myMiddle = markResponse.getMarkMiddle(i);
            double myFinal = markResponse.getMarkFinal(i);
            float diemTong = Utils.getDiemTong(myMiddle, myFinal);
            if (diemTong < 4) {
                zeroTo4++;
            } else if (diemTong < 7) {
                fourTo7++;
            } else if (diemTong < 8) {
                sevenTo8++;
            } else {
                eightTo10++;
            }
        }
        Log.e(TAG, "Lần lượt là: " + zeroTo4 + fourTo7 + sevenTo8 + eightTo10);
        values.add(zeroTo4);
        values.add(fourTo7);
        values.add(sevenTo8);
        values.add(eightTo10);
        return values;
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Tỉ lệ điểm thi");
        s.setSpan(new StyleSpan(Typeface.ITALIC), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(1.3f), 0, s.length(), 0);
        return s;
    }

    /**
     * UPDATE CHINH USER NAY THOI
     * tim id sinh vien
     */
    private void updateUI() {
        //setDate for pieChart
        setData(pieChart1, 4, 100);

        int postion = 0;
        for (int i = 0; i < markResponse.getLengthData(); i++) {
            if (markResponse.getEmailStudent(i).equals(Utils.getEmailUser(getActivity()))) {
                postion = i;
                break;
            }
        }

        Student my = markResponse.getStudent(postion);
        double myMiddle = markResponse.getMarkMiddle(postion);
        double myFinal = markResponse.getMarkFinal(postion);


        Log.e(TAG,myMiddle + "-----" + myFinal);
        txt_msv.setText(my.getCode());
        txt_ten.setText(my.getName());
        txt_giuaky.setText(String.valueOf(myMiddle));
        txt_cuoiky.setText(String.valueOf(myFinal));
        txt_tong.setText(String.valueOf(Utils.getDiemTong(myMiddle,myFinal)));
        toolbar.setTitle(markResponse.getCourse().getName());
        //==============================

        //delete position
        markResponse.removeDataItem(postion);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_hop_thong_bao_diem, container, false);

        getDataFromIntent();

        initUI(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new PresenterDetailHopThongBaoDiemLogic(getActivity(), this);
        presenter.executeDetailHopThongBaoDiem(notification.getLink());

    }


    @Override
    public void onPreExecute() {

    }

    @Override
    public void onExecuteSuccess(DiemResponse diemResponse) {
        markResponse = diemResponse;
        updateUI();
    }

    @Override
    public void onExecuteFailure(String fail) {
        Toast.makeText(getActivity(), fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelExecuteSuccess() {
    }

    @Override
    public void onCancelExecuteFailure() {

    }

    /** ===================================================================
     * Create UI xem them
 */

    private void getDataFromIntent() {
        notification = (AnnouncementNotification) getArguments().getSerializable(Config.KEY_PUSHNOTIFICATION);
    }

    private void createRow(Student student, double middleMark, double finalMark) {
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
                2, student.getCode()));
        row.addView(createCol(R.drawable.border_linerlayout_left_bottom,
                3.5f, student.getName()));
        row.addView(createCol(R.drawable.border_linerlayout_left_bottom,
                1.5f, String.valueOf(middleMark)));
        row.addView(createCol(R.drawable.border_linerlayout_left_bottom,
                1.5f, String.valueOf(finalMark)));
        row.addView(createCol(R.drawable.border_linerlayout_left_right_bottom,
                1.5f, String.valueOf(Utils.getDiemTong(middleMark,finalMark))));

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

    /**
     * ===================================================================
     * Listener Piechart
     */
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry entry = (PieEntry) e;
        String label = entry.getLabel();
    }

    @Override
    public void onNothingSelected() {

    }
//===================================================================

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cancelExecuteDetailHopThongBaoDiem();
    }
}
