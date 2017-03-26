package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vnu.uet.tuan.uetsupporter.Activities.Result2Activity;
import vnu.uet.tuan.uetsupporter.Adapter.RecyclerAdapterInboxAndSentMessage;
import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.MainEmail.IPresenterMainEmailView;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.MainEmail.PresenterMainEmailLogic;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.View.Main.HopThu.MainEmail.IViewMainEmail;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class InboxFragment extends Fragment implements RecyclerAdapterInboxAndSentMessage.ClickListener,
        SwipeRefreshLayout.OnRefreshListener, IViewMainEmail {

    private static final int EMAIL_LOADER_ID = 0;
    private final String TAG = this.getClass().getName();
    private RecyclerView recycler;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapterInboxAndSentMessage adapter;
    private ArrayList<Email> emails;
    private SwipeRefreshLayout refreshLayout;
    private IPresenterMainEmailView presenter;
    public InboxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        initUI(view);

        return view;
    }

    private void initUI(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(mLayoutManager);
        emails = new ArrayList<Email>();
        adapter = new RecyclerAdapterInboxAndSentMessage(getActivity(), emails);
        recycler.setAdapter(adapter);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(this);
        adapter.setOnItemClickListener(this);
        presenter = new PresenterMainEmailLogic(getActivity(), this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.excuteLoadEmail(Config.MailBox.Inbox.toString(), 0);
    }

    @Override
    public void onItemClick(int position, View v) {
        Intent intent = new Intent(getActivity(), Result2Activity.class);
        intent.putExtra(Config.POSITION_EMAIL, emails.get(position).getPosition());
        intent.putExtra(Config.FOLDER_EMAIL, Config.MailBox.Inbox.toString());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position, View v) {

    }

    @Override
    public void onRefresh() {
        emails.clear();
        adapter.notifyDataSetChanged();
        presenter.excuteLoadEmail(Config.MailBox.Inbox.toString(), 0);
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onExecuteSuccess(List<Email> emails) {
        this.emails.addAll(emails);
        this.adapter.notifyItemInserted(this.emails.size() - emails.size());
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onExecuteFailure(String fail) {
        Log.e(TAG, "Fail:" + fail);
        Toast.makeText(getActivity(), fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelExecuteSuccess(String success) {
        Toast.makeText(getActivity(), TAG + ": " + success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelExecuteFailure(String fail) {
        Log.e(TAG, "onCancelExecuteFailure: " + fail);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelLoadEmail();
    }

    //    private class ExecueEmail extends AsyncTask<Integer, Void, ArrayList<Email>> {
//
//        @Override
//        protected ArrayList<Email> doInBackground(Integer... params) {
//            int first = params[0] * 10, last = first + 10;
//
//            MailUet sent = MailUet.getInstance(
////                Utils.getEmailUser(getActivity()),
////                Utils.getPassword(getActivity())
//                    "14020521", "1391996"
//            ).readEmails(Config.MailBox.Inbox.toString());
//            try {
//                return sent.getMessage(first, last);
//            } catch (Exception e) {
//
//                e.printStackTrace();
//                return new ArrayList<>();
//            }
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Email> list) {
//            super.onPostExecute(list);
//            //update UI
//            int postion = emails.size();
//            if (list != null && list.size() != 0) {
////                emailSQLHelper.insertBulk(list);
//                emails.addAll(list);
//                adapter.notifyItemInserted(postion);
//
//            } else
//                Toast.makeText(getActivity(), getString(R.string.fail_download), Toast.LENGTH_SHORT).show();
//
//            if (refreshLayout.isRefreshing()) {
//                refreshLayout.setRefreshing(false);
//            }
//        }
//    }

}
