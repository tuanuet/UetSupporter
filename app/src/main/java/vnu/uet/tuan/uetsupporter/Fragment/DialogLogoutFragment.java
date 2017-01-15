package vnu.uet.tuan.uetsupporter.Fragment;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import vnu.uet.tuan.uetsupporter.R;

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
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_dialog_logout_submit : {
                isLogout = true;
                this.dismiss();
                Toast.makeText(getActivity().getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.btn_dialog_logout_cancel : {
                isLogout = false;
                this.dismiss();
                break;
            }
        }
    }
}
