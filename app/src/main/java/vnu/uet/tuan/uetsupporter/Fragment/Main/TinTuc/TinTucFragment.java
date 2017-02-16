package vnu.uet.tuan.uetsupporter.Fragment.Main.TinTuc;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Model.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class TinTucFragment extends Fragment implements View.OnClickListener {



    ArrayList<LoaiTinTuc> loaiTinTucArrayList;
    FloatingActionMenu menu;
    FloatingActionButton btn_all, btn_tuyendung,
            btn_vanhoa_thethao, btn_doanthe,
            btn_nghiencuu, btn_hoptac, btn_hoithao, btn_daotao;


    public TinTucFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tin_tuc, container, false);
        initUI(view);
        showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(-1), "all");
        return view;
    }

    public void showChangeFragment(Fragment fragment, String name) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        Fragment fragmentInStack = getChildFragmentManager().findFragmentByTag(name);
        if (fragmentInStack != null) {
            //Có fragment trong stack
            ft.replace(R.id.tintuc_contentview, fragmentInStack, name);
            ft.addToBackStack(null);
            ft.commit();
            Log.e("MainActi", "Fragment in Stack");
        } else {
            ft.replace(R.id.tintuc_contentview, fragment, name);
            ft.addToBackStack(name);
            ft.commit();
            Log.e("MainActi", "Fragment not in Stack");
        }
    }

    private void initUI(View view) {
        loaiTinTucArrayList = Utils.getAllLoaiTinTuc(getActivity());
        menu = (FloatingActionMenu) view.findViewById(R.id.social_floating_menu);
        btn_all = (FloatingActionButton) view.findViewById(R.id.tinall);
        btn_daotao = (FloatingActionButton) view.findViewById(R.id.tindaotao);
        btn_doanthe = (FloatingActionButton) view.findViewById(R.id.tinhoatdongdoanthe);
        btn_hoithao = (FloatingActionButton) view.findViewById(R.id.tinhoithao);
        btn_hoptac = (FloatingActionButton) view.findViewById(R.id.tinhoptac);
        btn_nghiencuu = (FloatingActionButton) view.findViewById(R.id.tinnghiencuu);
        btn_tuyendung = (FloatingActionButton) view.findViewById(R.id.tintuyendung);
        btn_vanhoa_thethao = (FloatingActionButton) view.findViewById(R.id.tinvanhoathethao);


        menu.setClosedOnTouchOutside(true);

        btn_vanhoa_thethao.setOnClickListener(this);
        btn_all.setOnClickListener(this);
        btn_daotao.setOnClickListener(this);
        btn_doanthe.setOnClickListener(this);
        btn_hoithao.setOnClickListener(this);
        btn_hoptac.setOnClickListener(this);
        btn_nghiencuu.setOnClickListener(this);
        btn_tuyendung.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        menu.close(true);
        switch (v.getId()) {
            case R.id.tinall: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(-1), "all");
                break;
            }
            case R.id.tindaotao: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(0), "tindaotao");
                break;
            }
            case R.id.tinhoithao: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(1), "tinhoithao");
                break;
            }
            case R.id.tinhoptac: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(2), "tinhoptac");
                break;
            }
            case R.id.tinnghiencuu: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(3), "tinnghiencuu");
                break;
            }
            case R.id.tinhoatdongdoanthe: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(4), "tinhoatdongdoanthe");
                break;
            }
            case R.id.tinvanhoathethao: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(5), "tinvanhoathethao");
                break;
            }
            case R.id.tintuyendung: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(6), "tintuyendung");
                break;
            }
        }
    }
}
