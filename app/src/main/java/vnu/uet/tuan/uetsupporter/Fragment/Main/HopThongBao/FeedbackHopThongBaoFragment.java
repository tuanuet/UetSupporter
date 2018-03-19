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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vnu.uet.tuan.uetsupporter.Adapter.RecyclerAdapterFeedBack;
import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;
import vnu.uet.tuan.uetsupporter.Model.DetailThongBao;
import vnu.uet.tuan.uetsupporter.Model.Response.Feedback;
import vnu.uet.tuan.uetsupporter.Model.Response.Message;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.Feedback.IPresenterFeedbackLogic;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.Feedback.PresenterFeedbackLogic;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.Feedback.IViewFeedBack;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackHopThongBaoFragment extends Fragment implements IViewFeedBack {

    private final String TAG = this.getClass().getSimpleName();

    TextView totalReaction;
    RecyclerView mRecycler;
    EditText textSend;
    Button btnFinish;
    LinearLayoutManager manager;
    CircleImageView sendNow;
    RecyclerAdapterFeedBack adapter;
    private IPresenterFeedbackLogic presenter;
    private List<Feedback> feedbackList;
    private AnnouncementNotification notification;

    public FeedbackHopThongBaoFragment() {
        // Required empty public constructor
    }


    private void getData() {
        this.notification = (AnnouncementNotification) getArguments().getSerializable(Config.KEY_PUSHNOTIFICATION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback_hop_thong_bao, container, false);
        getData();

        initUI(view);

        initListener();

        updateUI();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.execute(notification.get_id());

    }

    private void updateUI() {
        totalReaction.setText(String.format(getString(R.string.total_reaction),Utils.getTotalReaction(notification)+""));
    }

    private void initUI(View view) {
        feedbackList = new ArrayList<>();
        btnFinish = (Button) view.findViewById(R.id.finish);
        totalReaction = (TextView) view.findViewById(R.id.total_reaction);
        mRecycler = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        textSend = (EditText) view.findViewById(R.id.commenttext);
        sendNow = (CircleImageView) view.findViewById(R.id.send);
        manager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(manager);
        presenter= new PresenterFeedbackLogic(getContext(),this);
        adapter = new RecyclerAdapterFeedBack(getActivity(),feedbackList);
        mRecycler.setAdapter(adapter);
    }

    private void initListener(){
        adapter.setOnItemClickListener(new RecyclerAdapterFeedBack.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });

        sendNow.setOnClickListener(v -> {
            String noiDung = textSend.getText().toString();
            if (noiDung.trim().length() != 0) {
                presenter.postFeedback(notification.get_id(),noiDung);
            }
        });

        btnFinish.setOnClickListener(v -> {
            getActivity().finish();
            System.gc();
        });
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onExecuteSuccess(List<Feedback> feedbacks) {
        feedbackList.addAll(feedbacks);
        adapter.notifyDataSetChanged();
        mRecycler.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void onExecuteFailure(String fail) {
        Toast.makeText(getActivity(), fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostSuccess(Feedback feedback) {
        feedbackList.add(feedback);
        adapter.notifyItemInserted(feedbackList.size()-1);
        textSend.setText("");
        mRecycler.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void onPostFailure(String failure) {
        Log.e(TAG,failure);
        Toast.makeText(getActivity(), failure, Toast.LENGTH_SHORT).show();
    }
}
