package vnu.uet.tuan.uetsupporter.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailTinTucFragment extends Fragment {

    WebView webView;

    public DetailTinTucFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_tin_tuc, container, false);

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


//        webView.setWebViewClient(new WebViewClient() {
//            public void onPageFinished(WebView view, String url) {
//
//            }
//            /*
//            Không có mạng sẽ hiển thi thông báo lỗi
//            */
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                webView.loadUrl("file:///android_asset/loi.html");
//            }
//
//        });
        return view;
    }

    public String getURL(String hostname, String query) {
        return hostname + "/tintuc/detail?url=" + query;
    }
}
