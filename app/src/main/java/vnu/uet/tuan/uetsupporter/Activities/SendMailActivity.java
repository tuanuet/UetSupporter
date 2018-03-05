package vnu.uet.tuan.uetsupporter.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu.SendEmailFragment;
import vnu.uet.tuan.uetsupporter.R;

public class SendMailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new SendEmailFragment())
                .commit();
        initActionbar();

    }

    private void initActionbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.title_write_email);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
