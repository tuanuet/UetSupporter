package vnu.uet.tuan.uetsupporter.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        getSupportActionBar().setTitle(R.string.title_write_email);
    }
}
