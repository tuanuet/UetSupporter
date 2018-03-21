package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
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
import android.widget.FrameLayout;
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

    private TextView totalReaction,to;
    private RecyclerView mRecycler;
    private EditText textSend;
    private Button btnFinish;
    private LinearLayoutManager manager;
    private CircleImageView sendNow;
    private RecyclerAdapterFeedBack adapter;
    private IPresenterFeedbackLogic presenter;
    private List<Feedback> feedbackList;
    private AnnouncementNotification notification;
    private Feedback toFeedback = null;

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
        to = (TextView) view.findViewById(R.id.feedback_to);
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

            @Override
            public void onReplyClick(Feedback feedback,int position, View v) {
                to.setText(String.format(getString(R.string.feedback_to),feedback.getSender().getName()));
                updateBottombar(true);
                toFeedback = feedback;
            }
        });

        sendNow.setOnClickListener(v -> {
            String noiDung = textSend.getText().toString().trim();
            if (noiDung.length() != 0) {
                if(toFeedback != null){
                    String rootId = toFeedback.getSubFeedback() == null ? toFeedback.get_id() : toFeedback.getSubFeedback();
                    presenter.postFeedback(notification.get_id(),noiDung,rootId);
                } else {
                    presenter.postFeedback(notification.get_id(),noiDung);
                }

            }
        });

        btnFinish.setOnClickListener(v -> {
            getActivity().finish();
            System.gc();
        });

        to.setOnClickListener(v -> {
            updateBottombar(false);
            toFeedback = null;
        });
    }

    private void updateBottombar(boolean isSub) {
        if(isSub){
            to.setVisibility(View.VISIBLE);
            Rect bounds = new Rect();
            CharSequence text = to.getText();
            to.getPaint().getTextBounds(text.toString(), 0, text.length(), bounds);
            int width = bounds.width() + 50;
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) textSend.getLayoutParams();
            lp.setMargins(width,0,lp.rightMargin,0);
            textSend.setLayoutParams(lp);
        } else {
            to.setVisibility(View.GONE);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) textSend.getLayoutParams();
            lp.setMargins(0,0,lp.rightMargin,0);
            textSend.setLayoutParams(lp);
        }

    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onExecuteSuccess(List<Feedback> feedbacks) {
        adapter.addAll(feedbacks);
        mRecycler.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void onExecuteFailure(String fail) {
        Toast.makeText(getActivity(), fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostSuccess(Feedback feedback) {
        adapter.addOne(feedback);
        textSend.setText("");
        if(feedback.getSubFeedback() == null){
            mRecycler.scrollToPosition(adapter.getItemCount() - 1);
        } else {
            mRecycler.scrollToPosition(adapter.getParentPosition(feedback));
        }
        updateBottombar(false);
        this.toFeedback = null;
        textSend.clearFocus();
    }

    @Override
    public void onPostFailure(String failure) {
        Log.e(TAG,failure);
        Toast.makeText(getActivity(), failure, Toast.LENGTH_SHORT).show();
    }
}
