package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.BodyPart;
import javax.mail.MessagingException;

import vnu.uet.tuan.uetsupporter.Activities.SettingsActivity;
import vnu.uet.tuan.uetsupporter.Animation.Fab;
import vnu.uet.tuan.uetsupporter.Model.Mail.Email;
import vnu.uet.tuan.uetsupporter.Model.Mail.MailUet;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.DetailEmail.IPresenterDetailEmailView;
import vnu.uet.tuan.uetsupporter.Presenter.Main.HopThu.DetailEmail.PresenterDetailEmailLogic;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.View.Main.HopThu.DetailEmail.IViewDetailEmail;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailEmailFragment extends Fragment implements IViewDetailEmail {

    private final String TAG = this.getClass().getSimpleName();

    private TextView title, content, time, from, to;
    private ImageView avatar;
    private WebView webView;
    private LinearLayout layout_html, layout_attach;
    private IPresenterDetailEmailView presenter;

    public DetailEmailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        Log.d(TAG, "onViewCreated");
        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(Config.POSITION_EMAIL) && intent.hasExtra(Config.FOLDER_EMAIL)) {

            final int position = intent.getIntExtra(Config.POSITION_EMAIL, 0);
            String folder = intent.getStringExtra(Config.FOLDER_EMAIL);
            presenter.excuteLoadEmail(folder, position);
        }
    }

    private void initUI(View view) {
        Fab fab = (Fab) view.findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_action_write_email);
        fab.setRippleColor(getResources().getColor(R.color.theme_primary_dark));
        fab.setBackgroundColor(getResources().getColor(R.color.theme_primary_dark));

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        title = (TextView) view.findViewById(R.id.title);
        from = (TextView) view.findViewById(R.id.from);
        content = (TextView) view.findViewById(R.id.noidung);
        time = (TextView) view.findViewById(R.id.time);
        to = (TextView) view.findViewById(R.id.receiver);
        avatar = (ImageView) view.findViewById(R.id.image_avatar);
        webView = (WebView) view.findViewById(R.id.webview);
        layout_html = (LinearLayout) view.findViewById(R.id.layout_html);
        layout_attach = (LinearLayout) view.findViewById(R.id.layout_attach);
        presenter = new PresenterDetailEmailLogic(getActivity(), this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_email_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
            case android.R.id.home:
                getActivity().finish();
                System.gc();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUI(Email email) {
        title.setText(email.getTitle());
        from.setText(getString(R.string.email_from,email.getFrom()));
        time.setText(getString(R.string.email_date_receive,Utils.getTimeEmail(getActivity(),email.getReceiveDate())));
        to.setText(getString(R.string.email_receiver,email.getRecipient()));

        content.setText(email.getContent());

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .fontSize(48) /* size in px */
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRoundRect(Utils.getFirstChar(email.getFrom()), Utils.getRandomColor(email.getFrom()), 15);
        avatar.setImageDrawable(drawable);

        if (!email.isHasFile()) {
            layout_attach.setVisibility(View.GONE);
        } else {
            for (BodyPart bp : email.getFile()){
                createFile(bp);
            }
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
            settings.setLoadsImagesAutomatically(true);
            settings.setLightTouchEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setAllowFileAccess(true);
            settings.setAllowContentAccess(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setAppCacheEnabled(true);

            webView.loadData(email.getHtml(), "text/html", "UTF-8");
        }
    }
    public void createFile(final BodyPart bp){
        try {
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            10)
            );
            linearLayout.setPadding(10,0,0,0);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            ImageView file = new ImageView(getActivity());
            file.setImageResource(R.drawable.icon_word);
            file.setLayoutParams(new LinearLayout.LayoutParams(
                    80,
                    80
            ));
            linearLayout.addView(file);

            final TextView nameFile = new TextView(getActivity());
            final String name = bp.getFileName();
            nameFile.setText(name);
            nameFile.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            nameFile.setGravity(Gravity.CENTER);
            nameFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputStream is = null;
                    try {
                        is = bp.getInputStream();
                        File f = new File(Environment.DIRECTORY_DOWNLOADS + name);
                        FileOutputStream fos = new FileOutputStream(f);
                        byte[] buf = new byte[10000];
                        int bytesRead;
                        while ((bytesRead = is.read(buf)) != -1) {
                            fos.write(buf, 0, bytesRead);
                            Log.e(TAG,bytesRead+"");
                        }
                        fos.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });

            linearLayout.addView(nameFile);

            layout_attach.addView(linearLayout);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onExecuteSuccess(Email email) {
        updateUI(email);
    }

    @Override
    public void onExecuteFailure(String fail) {
        Toast.makeText(getActivity(), fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelExecuteSuccess(String success) {
    }

    @Override
    public void onCancelExecuteFailure(String fail) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cancelLoadEmail();
    }
}
