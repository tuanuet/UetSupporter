package vnu.uet.tuan.uetsupporter.Fragment.Main.TinTuc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class TinTucFragment extends Fragment {

    FabSpeedDial fabSpeedDial;

    public TinTucFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tin_tuc, container, false);

        fabSpeedDial = (FabSpeedDial) view.findViewById(R.id.fab_tintuc);

        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.tintuc_contentview, PatternFactoryFragmentTinTuc.newInstance(Config.daotao))
                .commit();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                Toast.makeText(getActivity(), "select", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
