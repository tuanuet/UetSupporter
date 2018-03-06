package vnu.uet.tuan.uetsupporter.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu.DetailEmailFragment;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

public class EmailDetailActivity extends AppCompatActivity {
    private Fragment fragment;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_detail);

        if (getIntent().hasExtra(Config.POSITION_EMAIL)) {
            int position = getIntent().getIntExtra(Config.POSITION_EMAIL, 0);
            String folder = getIntent().getStringExtra(Config.FOLDER_EMAIL);
            fragment = new DetailEmailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Config.POSITION_EMAIL, position);
            bundle.putString(Config.FOLDER_EMAIL, folder);
            fragment.setArguments(bundle);
        }

        name = fragment.getClass().getSimpleName();
        showChangeFragment(fragment, name);
    }

    public void showChangeFragment(Fragment fragment, String name) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Fragment fragmentInStack = getSupportFragmentManager().findFragmentByTag(name);
        if (fragmentInStack != null) {
            //Có fragment trong stack
            ft.replace(R.id.fragment_replace, fragmentInStack, name);
            ft.addToBackStack(null);
            ft.commit();
        } else {
            ft.replace(R.id.fragment_replace, fragment, name);
            ft.addToBackStack(name);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.gc();
    }
}
