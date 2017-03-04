package vnu.uet.tuan.uetsupporter.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import vnu.uet.tuan.uetsupporter.Async.EmailSyncAdapter;
import vnu.uet.tuan.uetsupporter.Fragment.Main.HopThongBao.HopThongBaoFragment;
import vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu.HopThuFragment;
import vnu.uet.tuan.uetsupporter.Fragment.Main.TinTuc.TinTucFragment;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    int postionNav = 0;
    private static final String POSITIONNAV = "postionNav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        //chay luon fragment tintuc
        Fragment tintuc = new TinTucFragment();
        showChangeFragment(tintuc, getString(R.string.nav_tintuc));
        postionNav = R.id.nav_tintuc;
        //
    }

    private void init() {
        //setINTERVAL
        EmailSyncAdapter.initializeSyncAdapter(this);


        //================
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        }, 0);
        return setFragment(id);
    }

    /**
     * thay doi fragment
     * dua fragment vao backstack
     * cap nhap lai name cho toolbar
     * cap nhat lai position nav
     *
     * @param fragment
     * @param name
     */
    public void showChangeFragment(final Fragment fragment, final String name) {
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

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            postionNav = savedInstanceState.getInt(POSITIONNAV);
            setFragment(postionNav);

        } else {
            setFragment(R.id.nav_tintuc);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITIONNAV, postionNav);
    }

    private boolean setFragment(int id) {
        if (id == R.id.nav_thongbao) {

        } else if (id == R.id.nav_tintuc) {

            Fragment tintuc = new TinTucFragment();
            showChangeFragment(tintuc, getString(R.string.nav_tintuc));
            postionNav = R.id.nav_tintuc;

        } else if (id == R.id.nav_hopthongbao) {
            Fragment hopthu = new HopThongBaoFragment();
            showChangeFragment(hopthu, getString(R.string.nav_hopthongbao));
            postionNav = R.id.nav_hopthongbao;
        } else if (id == R.id.nav_myprofile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            return false;
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_hopthu) {
            Fragment hopthu = new HopThuFragment();
            showChangeFragment(hopthu, getString(R.string.nav_hopthu));
            postionNav = R.id.nav_hopthu;
        }

        return true;
    }
}
