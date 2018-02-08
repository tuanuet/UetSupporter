package vnu.uet.tuan.uetsupporter.Activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vnu.uet.tuan.uetsupporter.Fragment.Main.TinTuc.DetailTinTucFragment;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment fragment = null;

        //Fragment DetailTinTuc
        if (getIntent().getStringExtra(Config.KEY_URL) != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            } else {
                getActionBar().hide();
            }
            String url = getIntent().getStringExtra(Config.KEY_URL);
            fragment = new DetailTinTucFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Config.KEY_URL, url);
            fragment.setArguments(bundle);

        }


        setContentView(R.layout.activity_result);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_result, fragment)
                .commit();
    }
}
