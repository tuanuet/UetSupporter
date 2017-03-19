package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendEmailFragment extends Fragment {

    private EditText to, from, cc, bcc, title, content;

    public SendEmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_email, container, false);
        setHasOptionsMenu(true);
        initUI(view);
        return view;
    }


    private void initUI(View view) {
        to = (EditText) view.findViewById(R.id.email_action_to);
        from = (EditText) view.findViewById(R.id.email_action_from);
        from.setText(getFullEmail(Utils.getEmailUser(getActivity())));
        cc = (EditText) view.findViewById(R.id.email_action_cc);
        bcc = (EditText) view.findViewById(R.id.email_action_bcc);
        title = (EditText) view.findViewById(R.id.email_action_title);
        content = (EditText) view.findViewById(R.id.email_action_content);
    }

    private String getFullEmail(String emailUser) {
        Resources res = getResources();
        return res.getString(
                R.string.fullmail_from_ma_sinh_vien, emailUser);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_send_email, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_attach_file:
                break;
            case R.id.action_huy:
                break;
            case R.id.action_send_now:
                break;
            case R.id.action_settings:
                break;
            case R.id.action_help_feedback:
                break;
        }
        return true;
    }
}
