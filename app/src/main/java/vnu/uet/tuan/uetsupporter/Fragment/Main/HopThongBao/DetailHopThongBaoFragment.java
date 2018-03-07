package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao;


import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
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
    private TextView title, noidung, time, sender, loaithongbao, receiver;
    private ImageView avatar;
    private LinearLayout layout_attachfile;

    private ScrollView layout_scrollview;
    private LinearLayout layout_wait;
    private FloatingActionButton fab;
    private DetailThongBao mThongBao;
    private IPresenterDetailHopThongBaoView presenter;
    private BroadcastReceiver onComplete;

    private DownloadManager downloadManager;
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
        receiver = (TextView) view.findViewById(R.id.receiver);
        avatar = (ImageView) view.findViewById(R.id.image_avatar);
        fab = (FloatingActionButton) view.findViewById(R.id.comment_now);
        layout_attachfile = (LinearLayout) view.findViewById(R.id.layout_attach);
        layout_scrollview = (ScrollView) view.findViewById(R.id.scrollView);

        layout_scrollview.setVisibility(View.INVISIBLE);
        layout_wait = (LinearLayout) view.findViewById(R.id.layout_wait);

        downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

        onComplete = new BroadcastReceiver() {

            public void onReceive(Context ctxt, Intent intent) {

                // get the refid from the download manager
                long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                // show a notification
                Log.e("INSIDE", "" + referenceId);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getActivity())
                                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                                .setContentTitle("Download")
                                .setContentText("All Download completed");

                NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());


            }
        };

        getActivity().registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(onComplete);
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
        String urlAvatar = Config.API_HOSTNAME + "/api/avatar/" + notification.getIdSender();
        Log.e(TAG, urlAvatar);
        Glide.with(getActivity()).load(urlAvatar)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(avatar);

        sender.setText(getString(R.string.email_from,notification.getNameSender()));
        time.setText(getString(R.string.email_date_receive,Utils.getThoiGian(getContext(),notification.getThoiGianNhan())));

    }

    private void updateUI(final DetailThongBao detailThongBao) {

        layout_wait.setVisibility(View.GONE);
        layout_scrollview.setVisibility(View.VISIBLE);

        title.setText(detailThongBao.getTieuDe());
        loaithongbao.setText(getString(R.string.notification_type,detailThongBao.getIdLoaiThongBao().getTenLoaiThongBao()));
        noidung.setText(detailThongBao.getNoiDung());
//        receiver.setText(getString(R.string.email_receiver,notification.get));


        setupAvatarWithAuthor();

//        setupMucDo(detailThongBao);

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
                /**
                 * download
                 */

                Uri uri = Uri.parse(Config.API_HOSTNAME+ file.getLink());

                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setTitle(file.getTenFile());
                request.setDescription(file.getTenFile());
                request.setVisibleInDownloadsUi(true);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, file.getTenFile());
                downloadManager.enqueue(request);
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


