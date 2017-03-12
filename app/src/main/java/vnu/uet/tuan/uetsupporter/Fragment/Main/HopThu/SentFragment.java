package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import vnu.uet.tuan.uetsupporter.Activities.Result2Activity;
import vnu.uet.tuan.uetsupporter.Adapter.RecyclerAdapterInboxAndSentMessage;
import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.Model.Mail.MailUet;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.EmailSQLHelper;
import vnu.uet.tuan.uetsupporter.config.Config;


/**
 * A simple {@link Fragment} subclass.
 */
public class SentFragment extends Fragment implements RecyclerAdapterInboxAndSentMessage.ClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final int EMAIL_LOADER_ID = 0;
    private final String TAG = this.getClass().getName();
    private RecyclerView recycler;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapterInboxAndSentMessage adapter;
    private ArrayList<Email> emails;
    private SwipeRefreshLayout refreshLayout;
    public SentFragment() {
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
        View view = inflater.inflate(R.layout.fragment_sent, container, false);
        initUI(view);


        //get postion 0 -> get 10 mail dau tien
        settingEmail(0);

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
    }


    private void settingEmail(final int postion) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ExecueEmail().execute(postion);
            }
        });
    }

    @Override
    public void onItemClick(int position, View v) {
        Log.e(TAG, emails.get(position).getPosition() + "");
        Intent intent = new Intent(getActivity(), Result2Activity.class);
        intent.putExtra(Config.POSITION_EMAIL, emails.get(position).getPosition());
        intent.putExtra(Config.FOLDER_EMAIL, Config.MailBox.Sent.toString());
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position, View v) {

    }

    @Override
    public void onRefresh() {
        emails.clear();
        adapter.notifyDataSetChanged();
        settingEmail(0);
    }


    private class ExecueEmail extends AsyncTask<Integer, Void, ArrayList<Email>> {

        @Override
        protected ArrayList<Email> doInBackground(Integer... params) {

            MailUet sent = MailUet.getInstance(
//                Utils.getEmailUser(getActivity()),
//                Utils.getPassword(getActivity())
                    "14020521", "1391996"
            ).readEmails(Config.MailBox.Sent.toString());
            return sent.getMessage();
        }

        @Override
        protected void onPostExecute(ArrayList<Email> list) {
            super.onPostExecute(list);
            //update UI
            int postion = emails.size();
            if (list != null && list.size() != 0) {
                emails.addAll(list);
                adapter.notifyItemInserted(postion);

            } else
                Toast.makeText(getActivity(), getString(R.string.fail_download), Toast.LENGTH_SHORT).show();

            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
            }
        }
    }

}