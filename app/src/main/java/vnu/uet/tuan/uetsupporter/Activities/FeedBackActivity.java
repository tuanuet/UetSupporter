package vnu.uet.tuan.uetsupporter.Activities;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao.FeedbackHopThongBaoFragment;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

public class FeedBackActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        if (getIntent().hasExtra(Config.KEY_PUSHNOTIFICATION)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Config.KEY_PUSHNOTIFICATION, getIntent().getSerializableExtra(Config.KEY_PUSHNOTIFICATION));
            FeedbackHopThongBaoFragment fragment = new FeedbackHopThongBaoFragment();
            fragment.setArguments(bundle);
            showChangeFragment(fragment, "FeedBack");
        }

    }

    public void showChangeFragment(final Fragment fragment, final String name) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragmentInStack = getSupportFragmentManager().findFragmentByTag(name);
        if (fragmentInStack != null) {
            //Có fragment trong stack
            ft.replace(R.id.content_main, fragmentInStack, name);
            ft.addToBackStack(null);
            ft.commit();
            Log.e(TAG, "Fragment in Stack");
        } else {
            ft.replace(R.id.content_main, fragment, name);
            ft.addToBackStack(name);
            ft.commit();
            Log.e(TAG, "Fragment not in Stack");
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        System.gc();

    }
}
