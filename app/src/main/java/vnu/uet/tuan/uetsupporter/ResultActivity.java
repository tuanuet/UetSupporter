package vnu.uet.tuan.uetsupporter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import vnu.uet.tuan.uetsupporter.Fragment.DetailTinTucFragment;
import vnu.uet.tuan.uetsupporter.config.Config;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Fragment DetailTinTuc
        if (getIntent() != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            } else {
                getActionBar().hide();
            }

            setContentView(R.layout.activity_result);
            String url = getIntent().getStringExtra(Config.KEY_URL);

            DetailTinTucFragment fragment = new DetailTinTucFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Config.KEY_URL, url);
            fragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_result, fragment)
                    .commit();

        }


    }
}
