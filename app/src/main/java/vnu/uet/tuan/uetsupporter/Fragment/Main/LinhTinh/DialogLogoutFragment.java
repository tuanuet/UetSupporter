package vnu.uet.tuan.uetsupporter.Fragment.Main.LinhTinh;


import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Activities.LoginActivity;
import vnu.uet.tuan.uetsupporter.Model.Response.Message;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new RunLogout().execute();
                    }
                });
                break;
            }
            case R.id.btn_dialog_logout_cancel : {
                isLogout = false;
                this.dismiss();
                break;
            }
        }
    }

    public Call<Message> postDeleteTokenFirebase() {
        Call<Message> call;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.postToDeleteTokenFirebase(Utils.getFirebaseToken(getActivity()), Utils.getUserToken(getActivity()));
        return call;

    }

    //xóa hết Email,passwword,token cua nuoguoif dung
    private void deleteUserFromPrefer() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.edit()
                .remove(Config.EMAIL)
                .remove(Config.PASSWORD)
                .remove(Config.USER_TOKEN)
                .remove(Config.CAN_BE_FIREBASE_TOKEN)
                .apply();
    }

    class RunLogout extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            //chay ve m.h login
            Call<Message> call = postDeleteTokenFirebase();

            try {
                Response<Message> response = call.execute();
                if (response.code() == 200 && response.isSuccessful()) {
                    Message message = response.body();

                    if (message.getSuccess()) {
                        isLogout = true;
                        //delete prefernce
                        deleteUserFromPrefer();


                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        //xóa hết các activity chạy trc đó
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();

                        DialogLogoutFragment.this.dismiss();
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
