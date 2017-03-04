package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.Model.Mail.MailUet;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailEmailFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();
    private String folder;

    public DetailEmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_email, container, false);

        initUI(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(Config.POSITION_EMAIL)) {
            final int position = intent.getIntExtra(Config.POSITION_EMAIL, 0);
            folder = intent.getStringExtra(Config.FOLDER_EMAIL);
            Log.e(TAG, "Email posstion: " + position);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new RetrigerEmail().execute(position);
                }
            });
        }
    }

    private void initUI(View view) {
    }

    private class RetrigerEmail extends AsyncTask<Integer, Void, Email> {

        @Override
        protected Email doInBackground(Integer... params) {
            return MailUet.getInstance(
//                    Utils.getEmailUser(getActivity()),
//                    Utils.getPassword(getActivity())
                    "14020521", "1391996")
                    .readEmails(folder)
                    .getMessage(params[0]);
        }

        @Override
        protected void onPostExecute(Email email) {
            super.onPostExecute(email);
            updateUI(email);
        }
    }

    private void updateUI(Email email) {
        Log.e(TAG, email.getFile().size() != 0 ? email.getFile().get(0) : "Không có file");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
