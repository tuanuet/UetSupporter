package vnu.uet.tuan.uetsupporter.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Adapter.TabAdapter;
import vnu.uet.tuan.uetsupporter.Model.Notification;
import vnu.uet.tuan.uetsupporter.Model.TinTuc;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class TinTucFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    TabLayout tabLayout;
    ViewPager pager;

    public TinTucFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tin_tuc, container, false);
        // khoi tao Tab, ViewPager
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout_tintuc);
        pager = (ViewPager) view.findViewById(R.id.viewPager_tintuc);
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

        Fragment fragment_a = PatternFactoryFragmentTinTuc.newInstance(Config.daotao);
        Fragment fragment_b = PatternFactoryFragmentTinTuc.newInstance(Config.tuyendung);


        Log.e("TAG","setupViewPager");
        adapter.addFragment(fragment_a, getResources().getString(R.string.daotao));
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
