package vnu.uet.tuan.uetsupporter.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import vnu.uet.tuan.uetsupporter.Fragment.Profile.ProfileFragment;
import vnu.uet.tuan.uetsupporter.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_profile, new ProfileFragment())
                .commit();
    }

}
