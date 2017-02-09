package vnu.uet.tuan.uetsupporter.Fragment.Main.TinTuc;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaBrowserCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailTinTucFragment extends Fragment {

    WebView webView;
    ProgressDialog dialog;
    public DetailTinTucFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_tin_tuc, container, false);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.please_wait));
        dialog.show();


        String urlQuery = getArguments().getString(Config.KEY_URL, "");

        webView = (WebView) view.findViewById(R.id.frag_webview);
        WebSettings webSettings = webView.getSettings();

        /*
         setJavaScriptEnabled hỗ trợ xem code javascript trong trang web
         */
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl(getURL(Config.API_HOSTNAME, urlQuery));
         /*
         Tải dữ liệu trang thay vì trắng màn hình
         */
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%

                // Here you can check if the progress is = 40% or not
                if (progress == 40) {
                    // hide the progress bar
                    // show the webview
                    dialog.dismiss();
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getActivity(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public String getURL(String hostname, String query) {
        return hostname + "/tintuc/detail?url=" + query;
    }
}
