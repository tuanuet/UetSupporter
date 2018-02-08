package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.clans.fab.FloatingActionButton;

import vnu.uet.tuan.uetsupporter.Activities.FeedBackActivity;
import vnu.uet.tuan.uetsupporter.Model.DetailThongBao;
import vnu.uet.tuan.uetsupporter.Model.File;
import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.DetailHopThongBao.IPresenterDetailHopThongBaoView;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.DetailHopThongBao.PresenterDetailHopThongBaoLogic;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.DetailHopThongBao.IViewDetailHopThongBao;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailHopThongBaoFragment extends Fragment implements IViewDetailHopThongBao {

    private AnnouncementNotification notification;
    private TextView title, noidung, time, sender, loaithongbao;
    private ImageView avatar;
    private LinearLayout layout_attachfile;

    private ScrollView layout_scrollview;
    private LinearLayout layout_wait;
    private FloatingActionButton fab;
    private DetailThongBao mThongBao;
    private IPresenterDetailHopThongBaoView presenter;

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

    private void interactPresent() {
        presenter = new PresenterDetailHopThongBaoLogic(getActivity(), this);
        Log.e(TAG,notification.getLink());
        presenter.executeDetailHopThongBao(notification.getLink());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        interactPresent();
    }

    private void getData() {
        notification = (AnnouncementNotification) getArguments().getSerializable(Config.KEY_PUSHNOTIFICATION);
    }

    private void initUI(View view) {
        title = (TextView) view.findViewById(R.id.title);
        noidung = (TextView) view.findViewById(R.id.noidung);
        time = (TextView) view.findViewById(R.id.time);
        loaithongbao = (TextView) view.findViewById(R.id.loaithongbao);
        sender = (TextView) view.findViewById(R.id.sender);
        avatar = (ImageView) view.findViewById(R.id.image_avatar);
        fab = (FloatingActionButton) view.findViewById(R.id.comment_now);
        layout_attachfile = (LinearLayout) view.findViewById(R.id.layout_attach);
        layout_scrollview = (ScrollView) view.findViewById(R.id.scrollView);

        layout_scrollview.setVisibility(View.INVISIBLE);
        layout_wait = (LinearLayout) view.findViewById(R.id.layout_wait);

    }

    /**
     * thêm tên laoij thông báo
     * * lấy tên thông báo trong csdl
     *
     * @param detailThongBao
     */
    private void setupLoaiThongBao(DetailThongBao detailThongBao) {
        loaithongbao.setText(detailThongBao.getIdLoaiThongBao().getTenLoaiThongBao());
    }

//    private void setupMucDo(DetailThongBao detailThongBao) {
//        switch (detailThongBao.getIdMucDoThongBao().get_id()) {
//            case 1:
//                title.setTextColor(getActivity().getResources().getColor(R.color.dark_red));
//                break;
//            case 2:
//                title.setTextColor(getActivity().getResources().getColor(R.color.dark_yellow));
//                break;
//            case 3:
//                title.setTextColor(getActivity().getResources().getColor(R.color.black));
//                break;
//            default:
//
//                break;
//        }
//    }

    private void setupAvatarWithAuthor() {
        String urlAvatar = Config.API_HOSTNAME + "/avatar/" + notification.getIdSender();
        Log.e(TAG, urlAvatar);
        Glide.with(getActivity()).load(urlAvatar)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(avatar);

        sender.setText(notification.getNameSender());
    }

//    protected class AsynDetailThongBao extends AsyncTask<Void, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            call = getDiem();
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//
//            try {
//                Response<ResponseBody> responseBody = call.execute();
//                return responseBody.body().string();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String json) {
//            super.onPostExecute(json);
//            if (json != null) {
//                DetailThongBao detailThongBao = null;
//                try {
//                    detailThongBao = new DetailThongBao(json);
//                    updateUI(detailThongBao);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.e(TAG, e.getMessage());
//                    Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
//                }
//
//            } else {
//                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }

    private void updateUI(final DetailThongBao detailThongBao) {

        layout_wait.setVisibility(View.GONE);
        layout_scrollview.setVisibility(View.VISIBLE);

        title.setText(detailThongBao.getTieuDe());
        loaithongbao.setText(detailThongBao.getIdLoaiThongBao().getTenLoaiThongBao());
        sender.setText(detailThongBao.getIdSender());
        time.setText(Utils.getThoiGian(detailThongBao.getTime()));
        noidung.setText(detailThongBao.getNoiDung());

        setupAvatarWithAuthor();

//        setupMucDo(detailThongBao);

        setupLoaiThongBao(detailThongBao);

        if (detailThongBao.getIdFile().size() == 0) {
            layout_attachfile.setVisibility(View.GONE);
        } else {
            for (int i = 0; i < detailThongBao.getIdFile().size(); i++) {
                createUIAttachfile(detailThongBao.getIdFile().get(i));
            }

        }

        //ONCLICK fab => activity feedback
        mThongBao = detailThongBao;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedBackActivity.class);
                intent.putExtra(Config.DETAILTHONGBAO, detailThongBao);
                startActivity(intent);
            }
        });
    }

    private void createUIAttachfile(final File file) {
        LinearLayout row = new LinearLayout(getActivity());
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        row.setOrientation(LinearLayout.HORIZONTAL);
        ImageView imageView = new ImageView(getActivity());
        imageView.setPadding(5, 3, 0, 0);
        imageView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        Utils.getPixelFromDP(getActivity(), 36),
                        Utils.getPixelFromDP(getActivity(), 36)));
        imageView.setImageResource(R.drawable.icon_word);

        row.addView(imageView);

        TextView txt = new TextView(getActivity());
        txt.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER_HORIZONTAL));
        txt.setText(file.getTenFile());
        txt.setPadding(5, 3, 0, 0);

        row.addView(txt);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), file.getLink(), Toast.LENGTH_SHORT).show();
            }
        });
        layout_attachfile.addView(row);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cancelExecuteDetailHopThongBao();
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onExecuteSuccess(DetailThongBao detailThongBao) {
        updateUI(detailThongBao);
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
}
