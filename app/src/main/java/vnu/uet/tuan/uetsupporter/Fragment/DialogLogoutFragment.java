package vnu.uet.tuan.uetsupporter.Fragment;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import vnu.uet.tuan.uetsupporter.LoginActivity;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogLogoutFragment extends DialogFragment implements View.OnClickListener {

    private Boolean isLogout = false;

    public DialogLogoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog_logout, container, false);
        Button btn_ok = (Button) view.findViewById(R.id.btn_dialog_logout_submit);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_dialog_logout_cancel);

        btn_cancel.setOnClickListener(this);
        btn_ok.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getDialog().setTitle(getString(R.string.logout));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_dialog_logout_submit : {
                isLogout = true;
                //chay ve m.h login

                deleteUserFromPrefer();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                //xóa hết các activity chạy trc đó
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.dismiss();
                break;
            }
            case R.id.btn_dialog_logout_cancel : {
                isLogout = false;
                this.dismiss();
                break;
            }
        }
    }

    //xóa hết Email,passwword,token cua nuoguoif dung
    private void deleteUserFromPrefer() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.edit()
                .remove(Config.EMAIL)
                .remove(Config.PASSWORD)
                .remove(Config.USER_TOKEN)
                .apply();
    }
}
