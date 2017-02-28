package vnu.uet.tuan.uetsupporter.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao.DetailHopThongBaoDiemFragment;
import vnu.uet.tuan.uetsupporter.R;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_test, new DetailHopThongBaoDiemFragment()).commit();
    }
}
