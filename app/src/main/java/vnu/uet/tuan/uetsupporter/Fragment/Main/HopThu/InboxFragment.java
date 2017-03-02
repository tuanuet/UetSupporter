package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Adapter.RecyclerAdapterInboxMessage;
import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.Model.Mail.MailUet;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class InboxFragment extends Fragment implements RecyclerAdapterInboxMessage.ClickListener {

    private final String TAG = this.getClass().getName();
    private RecyclerView recycler;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapterInboxMessage adapter;
    private ArrayList<Email> emails;
    public InboxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(mLayoutManager);
        emails = new ArrayList<Email>();
        adapter = new RecyclerAdapterInboxMessage(getActivity(), emails);
        recycler.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingEmail();
    }

    private void settingEmail() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ExecueEmail().execute();
            }
        });

    }

    @Override
    public void onItemClick(int position, View v) {

    }

    @Override
    public void onItemLongClick(int position, View v) {

    }

    private class ExecueEmail extends AsyncTask<Void, Void, ArrayList<Email>> {

        @Override
        protected ArrayList<Email> doInBackground(Void... params) {
            MailUet sent = MailUet.getInstance(
//                Utils.getEmailUser(getActivity()),
//                Utils.getPassword(getActivity())
                    "14020521", "1391996"
            ).readEmails(Config.MailBox.Inbox.toString());
            return sent.getMessage(0, 15);
        }

        @Override
        protected void onPostExecute(ArrayList<Email> list) {
            super.onPostExecute(list);
            int postion = emails.size();
            boolean isAdd = emails.addAll(list);

            Log.e(TAG, isAdd + " ---- " + emails.size() + "");
            adapter.notifyItemInserted(postion);
        }
    }
}
