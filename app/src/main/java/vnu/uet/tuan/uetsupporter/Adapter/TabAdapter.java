package vnu.uet.tuan.uetsupporter.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 13/01/2017.
 */

public class TabAdapter extends SmartFragmentStatePagerAdapter {

    private ArrayList<Fragment> mArray_Fragments;
    private ArrayList<String> mName_Fragment;
    public TabAdapter(FragmentManager fm) {
        super(fm);
        mArray_Fragments = new ArrayList<Fragment>();
        mName_Fragment = new ArrayList<String>();
    }

    public void addFragment(Fragment fragment,String name){
        mArray_Fragments.add(fragment);
        mName_Fragment.add(name);
    }
    @Override
    public Fragment getItem(int position) {
        return mArray_Fragments.get(position);
    }

    @Override
    public int getCount() {
        return mArray_Fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return new String(mName_Fragment.get(position));
    }


}