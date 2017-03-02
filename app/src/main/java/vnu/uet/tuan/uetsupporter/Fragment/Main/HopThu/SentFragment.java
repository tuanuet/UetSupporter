package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.Model.Mail.MailUet;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;


/**
 * A simple {@link Fragment} subclass.
 */
public class SentFragment extends Fragment {

    private final String TAG = this.getClass().getName();

    public SentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sent, container, false);
        initUI(view);

        settingEmail();
        return view;
    }

    private void settingEmail() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ExecueEmail().execute();
            }
        });

    }

    private class ExecueEmail extends AsyncTask<Void, Void, ArrayList<Email>> {

        @Override
        protected ArrayList<Email> doInBackground(Void... params) {
            MailUet sent = MailUet.getInstance(
//                Utils.getEmailUser(getActivity()),
//                Utils.getPassword(getActivity())
                    "14020521", "1391996"
            ).readEmails(Config.MailBox.Sent.toString());

            return sent.getMessage();


        }

        @Override
        protected void onPostExecute(ArrayList<Email> emails) {
            super.onPostExecute(emails);

            for (Email mail : emails) {
                Log.e(TAG, mail.toString());
            }
        }
    }

    private void initUI(View root) {

    }

}
