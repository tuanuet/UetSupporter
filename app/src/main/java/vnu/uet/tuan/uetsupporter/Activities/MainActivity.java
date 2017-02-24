package vnu.uet.tuan.uetsupporter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import me.leolin.shortcutbadger.ShortcutBadger;
import vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao.HopThongBaoFragment;
import vnu.uet.tuan.uetsupporter.Fragment.Main.TinTuc.TinTucFragment;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        Fragment tintuc = new TinTucFragment();
        showChangeFragment(tintuc,tintuc.getClass().getName());
    }

    private void init() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        initNavHead();

    }

    private void initShortcutBadger() {
        int badgeCount = Utils.getNumberOnNav(getApplicationContext());
        ShortcutBadger.applyCount(getApplicationContext(), badgeCount); //for 1.1.4+

    }

    private void initNavHead() {

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView txt_Ten = (TextView) header.findViewById(R.id.nav_head_name);
        TextView txt_email = (TextView) header.findViewById(R.id.nav_head_email);
        txt_Ten.setText(Utils.getUsername(getApplicationContext()));
        txt_email.setText(Utils.getEmailUser(getApplicationContext()));

    }

    //load số lượng các notifi chưa được đọc
    private void setupNumberForNav() {
        navigationView.getMenu().getItem(2).setActionView(R.layout.nav_item_number);
        TextView homthu_number = (TextView) navigationView.getMenu().getItem(2)
                .getActionView().findViewById(R.id.hopthu_number);
        homthu_number.setText(String.valueOf(Utils.getNumberOnNav(getApplicationContext())));
    }


    @Override
    protected void onResume() {
        super.onResume();
        setupNumberForNav();
        initShortcutBadger();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount()>0){
                finish();
            }
            else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_thongbao) {

        } else if (id == R.id.nav_tintuc) {

            Fragment tintuc = new TinTucFragment();
            showChangeFragment(tintuc, getString(R.string.nav_tintuc));

        } else if (id == R.id.nav_mynotification) {
            Fragment hopthu = new HopThongBaoFragment();
            showChangeFragment(hopthu, getString(R.string.nav_mynotification));
        } else if (id == R.id.nav_myprofile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            return false;
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showChangeFragment(Fragment fragment,String name) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Fragment fragmentInStack = getSupportFragmentManager().findFragmentByTag(name);
        if(fragmentInStack!=null){
            //Có fragment trong stack
            ft.replace(R.id.content_main,fragmentInStack,name);
            ft.addToBackStack(null);
            ft.commit();
            Log.e("MainActi","Fragment in Stack");
        }else{
            ft.replace(R.id.content_main, fragment,name);
            ft.addToBackStack(name);
            ft.commit();
            Log.e("MainActi","Fragment not in Stack");
        }
        getSupportActionBar().setTitle(name);
    }

}
