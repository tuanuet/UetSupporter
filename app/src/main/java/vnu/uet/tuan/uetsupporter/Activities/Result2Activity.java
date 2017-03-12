package vnu.uet.tuan.uetsupporter.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao.DetailHopThongBaoDiemFragment;
import vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao.DetailHopThongBaoFragment;
import vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu.DetailEmailFragment;
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
        if (getIntent().hasExtra(Config.ID_LOPMONHOC)) {
//            SinhVien mSinhVien = getIntent().getParcelableExtra(Config.SINHVIEN);
//            int position = getIntent().getIntExtra(Config.POSITION_LOPMONHOC, 0);
            String idLopMonHoc = getIntent().getStringExtra(Config.ID_LOPMONHOC);

            fragment = new DetailLopMonHocFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Config.ID_LOPMONHOC, idLopMonHoc);
            fragment.setArguments(bundle);
            name = idLopMonHoc;
        }

        //fragment DetailPushNotification
        if (getIntent().getSerializableExtra(Config.KEY_PUSHNOTIFICATION) != null) {
            PushNotification notification = (PushNotification) getIntent().getSerializableExtra(Config.KEY_PUSHNOTIFICATION);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Config.KEY_PUSHNOTIFICATION, notification);

            switch (notification.getKind()) {
                case 1: {
                    fragment = new DetailHopThongBaoFragment();
                    break;
                }
                case 2: {
                    fragment = new DetailHopThongBaoDiemFragment();
                    break;
                }
                case 3: {
                    break;
                }
            }

            fragment.setArguments(bundle);
            name = "Thông báo";
        }

        if (getIntent().hasExtra(Config.POSITION_EMAIL)) {
            int position = getIntent().getIntExtra(Config.POSITION_EMAIL, 0);
            String folder = getIntent().getStringExtra(Config.FOLDER_EMAIL);
            fragment = new DetailEmailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Config.POSITION_EMAIL, position);
            bundle.putString(Config.FOLDER_EMAIL, folder);
            fragment.setArguments(bundle);
            name = "Email";
        }
        showChangeFragment(fragment, name);

    }

    //
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
//            startActivity(intent);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
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
