package vnu.uet.tuan.uetsupporter.Fragment.Main.HopThu;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vnu.uet.tuan.uetsupporter.Adapter.TabAdapter;
import vnu.uet.tuan.uetsupporter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HopThuFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    TabLayout tabLayout;
    ViewPager pager;

    public HopThuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hop_thu, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        pager = (ViewPager) view.findViewById(R.id.viewPager);
        setupViewPager(pager);

    }

    private void setupViewPager(ViewPager pager) {
        TabAdapter adapter = new TabAdapter(getChildFragmentManager());

        adapter.addFragment(new InboxFragment(), getResources().getString(R.string.title_inbox));
        adapter.addFragment(new DraftsFragment(), getResources().getString(R.string.title_drafts));
        adapter.addFragment(new SentFragment(), getResources().getString(R.string.title_sent));
        adapter.addFragment(new TrashFragment(), getResources().getString(R.string.title_trash));

        tabLayout.setupWithViewPager(pager);
        tabLayout.addOnTabSelectedListener(this);
        pager.setAdapter(adapter);
        //setcustomview cho tap

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
