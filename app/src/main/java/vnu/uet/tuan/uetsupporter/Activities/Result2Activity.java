package vnu.uet.tuan.uetsupporter.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao.DetailHopThongBaoFragment;
import vnu.uet.tuan.uetsupporter.Fragment.Profile.DetailLopMonHocFragment;
import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.Model.SinhVien;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

public class Result2Activity extends AppCompatActivity {
    Fragment fragment;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);

        //fragment DetailLopMonHoc
        //lay dc position lop mon hoc va ca sinh vien
        if (getIntent().getParcelableExtra(Config.SINHVIEN) != null) {
            SinhVien mSinhVien = getIntent().getParcelableExtra(Config.SINHVIEN);
            int position = getIntent().getIntExtra(Config.POSITION_LOPMONHOC, 0);
            fragment = new DetailLopMonHocFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Config.SINHVIEN, mSinhVien);
            bundle.putInt(Config.POSITION_LOPMONHOC, position);
            fragment.setArguments(bundle);
            name = "Lớp " + mSinhVien.getIdLopMonHoc().get(position).getTenLopMonHoc();
        }

        //fragment DetailPushNotification
        if (getIntent().getSerializableExtra(Config.KEY_PUSHNOTIFICATION) != null) {
            PushNotification notification = (PushNotification) getIntent().getSerializableExtra(Config.KEY_PUSHNOTIFICATION);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Config.KEY_PUSHNOTIFICATION, notification);
            fragment = new DetailHopThongBaoFragment();
            fragment.setArguments(bundle);
            name = notification.getTieuDe();
        }

        showChangeFragment(fragment, name);
    }

    public void showChangeFragment(Fragment fragment, String name) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Fragment fragmentInStack = getSupportFragmentManager().findFragmentByTag(name);
        if (fragmentInStack != null) {
            //Có fragment trong stack
            ft.replace(R.id.activity_result2, fragmentInStack, name);
            ft.addToBackStack(null);
            ft.commit();
            Log.e("Result2", "Fragment in Stack");
        } else {
            ft.replace(R.id.activity_result2, fragment, name);
            ft.addToBackStack(name);
            ft.commit();
            Log.e("Result2", "Fragment not in Stack");
        }
        getSupportActionBar().setTitle(name);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
