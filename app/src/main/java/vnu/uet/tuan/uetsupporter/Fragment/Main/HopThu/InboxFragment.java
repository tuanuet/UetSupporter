package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import vnu.uet.tuan.uetsupporter.Activities.Result2Activity;
import vnu.uet.tuan.uetsupporter.Activities.ResultActivity;
import vnu.uet.tuan.uetsupporter.Adapter.RecyclerAdapterInboxMessage;
import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.Model.Mail.MailUet;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.EmailSQLHelper;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class InboxFragment extends Fragment implements RecyclerAdapterInboxMessage.ClickListener {

    private static final int EMAIL_LOADER_ID = 0;
    private final String TAG = this.getClass().getName();
    private RecyclerView recycler;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapterInboxMessage adapter;
    private ArrayList<Email> emails;
    private EmailSQLHelper emailSQLHelper;

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


        final Cursor cursor = emailSQLHelper.getAll();
        if (cursor == null || cursor.getCount() == 0) {
            settingEmail();
            Log.e(TAG, "get data first time");
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "reload data");
                    int postion = emails.size();
                    ArrayList<Email> list = Utils.getAllEmail(cursor);
                    emails.addAll(list);
                    adapter.notifyItemInserted(postion);
                }
            });
        }
        return view;
    }

    private void initUI(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(mLayoutManager);
        emails = new ArrayList<Email>();
        adapter = new RecyclerAdapterInboxMessage(getActivity(), emails);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        emailSQLHelper = new EmailSQLHelper(getActivity());
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
        Intent intent = new Intent(getActivity(), Result2Activity.class);
        intent.putExtra(Config.POSITION_EMAIL, emails.get(position).getPosition());
        intent.putExtra(Config.FOLDER_EMAIL, Config.MailBox.Inbox.toString());
        startActivity(intent);
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
            return sent.getMessage(0, 30);
        }

        @Override
        protected void onPostExecute(ArrayList<Email> list) {
            super.onPostExecute(list);
            //update UI
            int postion = emails.size();
            if (list != null && list.size() != 0) {
                Log.e(TAG, list.get(0).getFolder());
                emailSQLHelper.insertBulk(list);
                emails.addAll(list);
                adapter.notifyItemInserted(postion);
            } else
                Toast.makeText(getActivity(), getString(R.string.fail_download), Toast.LENGTH_SHORT).show();
        }
    }

}
