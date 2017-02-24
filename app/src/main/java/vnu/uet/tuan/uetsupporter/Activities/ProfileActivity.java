package vnu.uet.tuan.uetsupporter.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import vnu.uet.tuan.uetsupporter.Fragment.LinhTinh.WaitingFragment;
import vnu.uet.tuan.uetsupporter.Fragment.Profile.ProfileFragment;
import vnu.uet.tuan.uetsupporter.R;

public class ProfileActivity extends AppCompatActivity implements ProfileFragment.OnDataRecived {
    ProfileFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initUI();

        fragment = new ProfileFragment();
        fragment.setOnDataRecived(this);
        showChangeFragment(new WaitingFragment(), "Waiting");
    }

    private void initUI() {
        //init actionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showChangeFragment(Fragment fragment, String name) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Fragment fragmentInStack = getSupportFragmentManager().findFragmentByTag(name);
        if (fragmentInStack != null) {
            //Có fragment trong stack
            ft.replace(R.id.activity_profile, fragmentInStack, name);
            ft.addToBackStack(null);
            ft.commit();
            Log.e("profile", "Fragment in Stack");
        } else {
            ft.replace(R.id.activity_profile, fragment, name);
            ft.addToBackStack(name);
            ft.commit();
            Log.e("profile", "Fragment not in Stack");
        }
    }

    @Override
    public void onRecived(Boolean isRecived) {
        Log.e("onRecived", isRecived + "");
        if (isRecived) {
            showChangeFragment(fragment, "Thông tin cá nhân");
        } else {
            Toast.makeText(getApplicationContext(), "Đường truyền có lỗi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
