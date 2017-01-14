package vnu.uet.tuan.uetsupporter.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Adapter.TabAdapter;
import vnu.uet.tuan.uetsupporter.Model.Notification;
import vnu.uet.tuan.uetsupporter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaoTaoFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    TabLayout tabLayout;
    ViewPager pager;

    public DaoTaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dao_tao, container, false);
        // khoi tao Tab, ViewPager
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout_daotao);
        pager = (ViewPager) view.findViewById(R.id.viewPager_daotao);
        setupViewPager(pager);
        return view;
    }

    /**
     * cai dat ViewPager
     * Tao class TabAdapter de dua vao ViewPager
     * @param pager
     */
    private void setupViewPager(ViewPager pager) {
        TabAdapter adapter = new TabAdapter(getChildFragmentManager());

        Fragment fragment_a = PatternFactoryFragment.newInstance(getArr());
        Fragment fragment_b = PatternFactoryFragment.newInstance(getArr());

        Log.e("TAG","setupViewPager");
        adapter.addFragment(fragment_a, getResources().getString(R.string.tkb));
        adapter.addFragment(fragment_b, getResources().getString(R.string.tuyendung));


        tabLayout.setupWithViewPager(pager);
        tabLayout.addOnTabSelectedListener(this);
        pager.setAdapter(adapter);

        //setcustomview cho tap
    }


    //fake notification
    public Notification[] getArr(){
        Log.e("TAG","getArr");
        Notification[] arrayList = new Notification[10];
        for (int i = 0; i < 10; i++) {
            arrayList[i] = new Notification("abc","abc","abc","abc",i,i+5);
        }
        return arrayList;
    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
