package vnu.uet.tuan.uetsupporter.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vnu.uet.tuan.uetsupporter.Fragment.LoadingFragment;
import vnu.uet.tuan.uetsupporter.R;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_loading,new LoadingFragment())
                .commit();
    }
}
