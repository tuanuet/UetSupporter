package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Model.DetailThongBao;
import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailHopThongBaoFragment extends Fragment {

    PushNotification notification;
    TextView title, noidung, time, sender, loaithongbao, mucdo;
    ImageView avatar;
    LinearLayout layout_attachfile;
    Call<ResponseBody> call;
    ScrollView layout_scrollview;
    LinearLayout layout_wait;

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
        title = (TextView) view.findViewById(R.id.title);
        noidung = (TextView) view.findViewById(R.id.noidung);
        time = (TextView) view.findViewById(R.id.time);
        loaithongbao = (TextView) view.findViewById(R.id.loaithongbao);
        sender = (TextView) view.findViewById(R.id.sender);
        mucdo = (TextView) view.findViewById(R.id.mucdo);
        avatar = (ImageView) view.findViewById(R.id.image_avatar);
        layout_attachfile = (LinearLayout) view.findViewById(R.id.layout_attach);
        layout_scrollview = (ScrollView) view.findViewById(R.id.scrollView);

        layout_scrollview.setVisibility(View.INVISIBLE);
        layout_wait = (LinearLayout) view.findViewById(R.id.layout_wait);

    }

    private Call<ResponseBody> getDiem() {
        Call<ResponseBody> call;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
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

    protected class AsynDetailThongBao extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            call = getDiem();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Response<ResponseBody> responseBody = call.execute();
                return responseBody.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            if (json != null) {
                DetailThongBao detailThongBao = null;
                try {
                    detailThongBao = new DetailThongBao(json);
                    updateUI(detailThongBao);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void updateUI(DetailThongBao detailThongBao) {

        layout_wait.setVisibility(View.GONE);
        layout_scrollview.setVisibility(View.VISIBLE);

        title.setText(detailThongBao.getTieuDe());
        loaithongbao.setText(detailThongBao.getIdLoaiThongBao().getTenLoaiThongBao());
        mucdo.setText(detailThongBao.getIdMucDoThongBao().getTenMucDoThongBao());
        sender.setText(detailThongBao.getIdSender());
        time.setText(Utils.getThoiGian(detailThongBao.getTime()));
        noidung.setText(detailThongBao.getNoiDung());

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .fontSize(30) /* size in px */
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRoundRect(Utils.getFirstChar(detailThongBao.getIdSender()), Utils.getRandomColor(detailThongBao.getIdSender()), 15);
        avatar.setImageDrawable(drawable);

        if (detailThongBao.getIdFile() == null) {
            layout_attachfile.setVisibility(View.GONE);
        }
//        Toast.makeText(getActivity(),
//                detailThongBao.getFeedback().size()!=0 ? detailThongBao.getFeedback().get(0).getComment().getName(): "Khong co comment",
//                Toast.LENGTH_SHORT).show();
    }
}
