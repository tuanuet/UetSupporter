package vnu.uet.tuan.uetsupporter.Fragment.Profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Activities.SettingsActivity;
import vnu.uet.tuan.uetsupporter.Model.Student;
import vnu.uet.tuan.uetsupporter.Presenter.Profile.MainProfile.IPresenterMainProfileView;
import vnu.uet.tuan.uetsupporter.Presenter.Profile.MainProfile.PresenterMainProfileLogic;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.View.Profile.MainProfile.IViewMainProfile;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener, IViewMainProfile {


    private TextView txt_tenSinhVien;
    private TextView txt_lopChinh;
    private TextView txt_khoa;
    private RelativeLayout profile_action_lopmonhoc;
    private Student mSinhVien;
    private DialogLopMonHocFragment dialog;
    private IPresenterMainProfileView presenter;

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initUI(view);

        listenerUI();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new PresenterMainProfileLogic(getActivity(), this);
        presenter.executeLoadStudent(Utils.getUserToken(getActivity()));

    }

    private void initUI(View view) {
        txt_tenSinhVien = (TextView) view.findViewById(R.id.txt_profile_hoten);
        txt_khoa = (TextView) view.findViewById(R.id.txt_profile_Khoa);
        txt_lopChinh = (TextView) view.findViewById(R.id.txt_profile_tenlopchinh);
        profile_action_lopmonhoc = (RelativeLayout) view.findViewById(R.id.profile_action_lopmonhoc);

        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialog = new DialogLopMonHocFragment();
    }

    private void listenerUI() {
        profile_action_lopmonhoc.setOnClickListener(this);
    }

    private void updateUI(Student sinhVien) {

        txt_tenSinhVien.setText(sinhVien.getName());
        txt_lopChinh.setText(sinhVien.getMyClass().getName());
        // txt_khoa.setText(sinhVien.get().getIdKhoa().getTenKhoa());

    }


    @Override
    public void onPreExecute() {

    }

    @Override
    public void onExecuteSuccess(Student sinhVien) {
        mSinhVien = sinhVien;
        updateUI(sinhVien);
    }

    @Override
    public void onExecuteFailure(String fail) {
        Toast.makeText(getActivity(), fail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelExecuteSuccess(String success) {
        Toast.makeText(getActivity(), success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelExecuteFailure(String fail) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity().getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        //show dialog and passData
        if (mSinhVien == null) {

        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Config.SINHVIEN, mSinhVien);
            dialog.setArguments(bundle);
            android.app.FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
            dialog.show(transaction, "ahihi");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cancelLoadStudent();
    }
}
