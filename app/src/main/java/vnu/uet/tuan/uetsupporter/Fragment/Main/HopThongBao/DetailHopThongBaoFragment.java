package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailHopThongBaoFragment extends Fragment {

    PushNotification notification;
    TextView txt_test;

    public DetailHopThongBaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_hop_thong_bao, container, false);

        getData();

        initUI(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt_test.setText(notification.getTieuDe() + "\n" + notification.getLink());
    }

    private void getData() {
        notification = (PushNotification) getArguments().getSerializable(Config.KEY_PUSHNOTIFICATION);
    }

    private void initUI(View view) {
        txt_test = (TextView) view.findViewById(R.id.test);
    }

}
