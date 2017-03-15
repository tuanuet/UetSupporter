package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import de.hdodenhof.circleimageview.CircleImageView;
import vnu.uet.tuan.uetsupporter.Adapter.RecyclerAdapterFeedBack;
import vnu.uet.tuan.uetsupporter.Model.DetailThongBao;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackHopThongBaoFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    TextView title, time, sender, loaithongbao, mucdo;
    ImageView avatar;
    RecyclerView mRecycler;
    EditText textSend;
    CircleImageView sendNow;
    RecyclerAdapterFeedBack adapter;

    public FeedbackHopThongBaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback_hop_thong_bao, container, false);
        Log.e(TAG, "onCreateView");
        initUI(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI((DetailThongBao) getArguments().getSerializable(Config.DETAILTHONGBAO));
    }

    private void updateUI(DetailThongBao thongBao) {
        Toast.makeText(getActivity(), thongBao.getFeedback().size() + "", Toast.LENGTH_SHORT).show();
        title.setText(thongBao.getTieuDe());
        loaithongbao.setText(thongBao.getIdLoaiThongBao().getTenLoaiThongBao());
        mucdo.setText(thongBao.getIdMucDoThongBao().getTenMucDoThongBao());
        sender.setText(thongBao.getIdSender());
        time.setText(Utils.getThoiGian(thongBao.getTime()));
        adapter = new RecyclerAdapterFeedBack(getActivity(), thongBao.getFeedback());
        mRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerAdapterFeedBack.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });

        sendNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noiDung = textSend.getText().toString();
                if (noiDung.trim().length() != 0) {

                }
            }
        });
    }

    private void initUI(View view) {
        title = (TextView) view.findViewById(R.id.title);
        time = (TextView) view.findViewById(R.id.time);
        loaithongbao = (TextView) view.findViewById(R.id.loaithongbao);
        sender = (TextView) view.findViewById(R.id.sender);
        mucdo = (TextView) view.findViewById(R.id.mucdo);
        avatar = (ImageView) view.findViewById(R.id.image_avatar);
        mRecycler = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        textSend = (EditText) view.findViewById(R.id.commenttext);
        sendNow = (CircleImageView) view.findViewById(R.id.send);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(manager);
    }

}
