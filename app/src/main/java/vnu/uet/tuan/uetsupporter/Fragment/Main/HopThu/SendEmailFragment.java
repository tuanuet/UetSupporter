package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu;


import android.content.Intent;
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
import android.widget.Toast;

import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.SendEmail.IPresenterSendEmailModel;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.SendEmail.IPresenterSendEmailView;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.SendEmail.PresenterSendEmailLogic;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.View.Main.HopThu.SendEmail.IViewSendEmail;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendEmailFragment extends Fragment implements IViewSendEmail{

    private EditText to, from, cc, bcc, title, content;
    private IPresenterSendEmailView presenter;
    public SendEmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_email, container, false);
        setHasOptionsMenu(true);

        Intent intent = getActivity().getIntent();
        initUI(view);

        if(intent.hasExtra(Config.REPLY_EMAIL_TITLE)){
            String _title = intent.getStringExtra(Config.REPLY_EMAIL_TITLE);
            title.setText(Utils.getReplyTitle(_title));
        }
        if(intent.hasExtra(Config.REPLY_EMAIL_CONTENT)) {
            String _content = intent.getStringExtra(Config.REPLY_EMAIL_CONTENT);
            content.setText(_content.replaceAll("\n","\n>"));

        }
        if(intent.hasExtra(Config.REPLY_EMAIL_FROM)) {
            String _from = intent.getStringExtra(Config.REPLY_EMAIL_FROM);
            to.setText(_from);
        }

        return view;
    }


    private void initUI(View view) {
        to = (EditText) view.findViewById(R.id.email_action_to);
        from = (EditText) view.findViewById(R.id.email_action_from);
        from.setText(Utils.getEmailUser(getActivity()));
        cc = (EditText) view.findViewById(R.id.email_action_cc);
        bcc = (EditText) view.findViewById(R.id.email_action_bcc);
        title = (EditText) view.findViewById(R.id.email_action_title);
        content = (EditText) view.findViewById(R.id.email_action_content);
        presenter = new PresenterSendEmailLogic(getActivity(),this);
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

                String to = this.to.getText().toString();
                String from = this.from.getText().toString();
                String cc = this.cc.getText().toString();
                String bcc = this.bcc.getText().toString();
                String sublect = this.title.getText().toString();
                String content = this.content.getText().toString();
                presenter.excuteSendEmail(to,from,cc,bcc,sublect,content);

                break;
            case R.id.action_settings:
                break;
            case R.id.action_help_feedback:
                break;
        }
        return true;
    }

    @Override
    public void onPreExecute() {
    }

    @Override
    public void onExecuteSuccess() {
        Toast.makeText(getActivity(), "Đã gửi thư thành công!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onExecuteFailure(String fail) {
        Toast.makeText(getActivity(), "Đã gửi thư thất bại vui lòng thử lại!", Toast.LENGTH_SHORT).show();
    }
}
