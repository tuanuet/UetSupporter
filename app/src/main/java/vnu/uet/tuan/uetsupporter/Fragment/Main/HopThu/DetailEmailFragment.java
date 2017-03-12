package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.Model.Mail.MailUet;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailEmailFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();
    private String folder;
    private TextView title, content, time, mucdo, from;
    private ImageView avatar;
    private WebView webView;
    private LinearLayout layout_html, layout_attach;

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
        hasOptionsMenu();
        setHasOptionsMenu(true);
        title = (TextView) view.findViewById(R.id.title);
        from = (TextView) view.findViewById(R.id.from);
        content = (TextView) view.findViewById(R.id.noidung);
        time = (TextView) view.findViewById(R.id.time);
        mucdo = (TextView) view.findViewById(R.id.mucdo);
        avatar = (ImageView) view.findViewById(R.id.image_avatar);
        webView = (WebView) view.findViewById(R.id.webview);
        layout_html = (LinearLayout) view.findViewById(R.id.layout_html);
        layout_attach = (LinearLayout) view.findViewById(R.id.layout_attach);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_email_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                break;
            case R.id.action_delete:
                break;
            case R.id.action_important:
                break;
        }
        return super.onOptionsItemSelected(item);
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
        title.setText(email.getTitle());
        from.setText(email.getFrom());
        mucdo.setText(email.getImportance());
        Log.e(TAG, email.getReceiveDate());
        time.setText(Utils.getTimeEmail(email.getReceiveDate()));
        content.setText(email.getContent());

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .fontSize(30) /* size in px */
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRoundRect(Utils.getFirstChar(email.getFrom()), Utils.getRandomColor(email.getFrom()), 15);
        avatar.setImageDrawable(drawable);

        if (!email.isHasFile()) {
            layout_attach.setVisibility(View.GONE);
        } else {
            //có file ...
        }

        if (email.getHtml() == "" || email.getHtml() == null) {
            layout_html.setVisibility(View.GONE);
        } else {
            //có html ...
            webView.setInitialScale(1);
            WebSettings settings = webView.getSettings();
            settings.setBuiltInZoomControls(false);
            settings.setUseWideViewPort(true);
            settings.setJavaScriptEnabled(true);
            settings.setSupportMultipleWindows(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setLoadsImagesAutomatically(true);
            settings.setLightTouchEnabled(true);
            settings.setDomStorageEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.loadData(email.getHtml(), "text/html", "UTF-8");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
