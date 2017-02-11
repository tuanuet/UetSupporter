package vnu.uet.tuan.uetsupporter.Fragment.Profile;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Activities.SettingsActivity;
import vnu.uet.tuan.uetsupporter.Adapter.ProfileRecyclerLopMonHoc;
import vnu.uet.tuan.uetsupporter.Model.LopMonHoc;
import vnu.uet.tuan.uetsupporter.Model.SinhVien;
import vnu.uet.tuan.uetsupporter.Model.TinTuc;
import vnu.uet.tuan.uetsupporter.Model.Users;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements Callback<SinhVien>, View.OnClickListener {


    TextView txt_tenSinhVien;
    TextView txt_lopChinh;
    TextView txt_khoa;
    RelativeLayout profile_action_lopmonhoc;
    SinhVien mSinhVien;
    DialogLopMonHocFragment dialog;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        setHasOptionsMenu(true);
        initUI(view);

        listenerUI();

        getInformationSinhVien();

        return view;
    }

    private void getInformationSinhVien() {
        Call<SinhVien> call;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                // Sử dụng GSON cho việc parse và maps JSON data tới Object
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        call = apiTinTuc.getInformationSinhVien(Utils.getUserToken(getActivity()));
        call.enqueue(this);
    }

    private void initUI(View view) {
        txt_tenSinhVien = (TextView) view.findViewById(R.id.txt_profile_hoten);
        txt_khoa = (TextView) view.findViewById(R.id.txt_profile_Khoa);
        txt_lopChinh = (TextView) view.findViewById(R.id.txt_profile_tenlopchinh);
        profile_action_lopmonhoc = (RelativeLayout) view.findViewById(R.id.profile_action_lopmonhoc);
        //init actionBar
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new DialogLopMonHocFragment();
    }

    private void listenerUI() {
        profile_action_lopmonhoc.setOnClickListener(this);
    }

    private void updateUI(SinhVien sinhVien) {

        txt_tenSinhVien.setText(sinhVien.getTenSinhVien());
        txt_lopChinh.setText(sinhVien.getIdLopChinh().getTenLopChinh());
        txt_khoa.setText(sinhVien.getIdLopChinh().getIdKhoa().getTenKhoa());

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
    public void onResponse(Call<SinhVien> call, Response<SinhVien> response) {
        mSinhVien = response.body();
        updateUI(mSinhVien);


    }

    @Override
    public void onFailure(Call<SinhVien> call, Throwable t) {
        Toast.makeText(getActivity(), "Đường truyền có lỗi", Toast.LENGTH_SHORT).show();
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
}
