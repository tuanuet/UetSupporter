package vnu.uet.tuan.uetsupporter.Fragment.Main.TinTuc;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.gordonwong.materialsheetfab.MaterialSheetFabEventListener;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Animation.Fab;
import vnu.uet.tuan.uetsupporter.Model.Download.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class TinTucFragment extends Fragment implements View.OnClickListener {

    ArrayList<LoaiTinTuc> loaiTinTucArrayList;
    private MaterialSheetFab materialSheetFab;
    private int statusBarColor;

    public TinTucFragment() {
        // Required empty public constructor
    }

    public interface OnBackpressToFinish {
        public void finish(boolean isFinish);
    }

    private OnBackpressToFinish onFinish;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onFinish = (OnBackpressToFinish) activity;
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

        setupFab(view);



        loaiTinTucArrayList = Utils.getAllLoaiTinTuc(getActivity());
//        menu = (FloatingActionMenu) view.findViewById(R.id.social_floating_menu);
//        btn_all = (FloatingActionButton) view.findViewById(R.id.tinall);
//        btn_daotao = (FloatingActionButton) view.findViewById(R.id.tindaotao);
//        btn_doanthe = (FloatingActionButton) view.findViewById(R.id.tinhoatdongdoanthe);
//        btn_hoithao = (FloatingActionButton) view.findViewById(R.id.tinhoithao);
//        btn_hoptac = (FloatingActionButton) view.findViewById(R.id.tinhoptac);
//        btn_nghiencuu = (FloatingActionButton) view.findViewById(R.id.tinnghiencuu);
//        btn_tuyendung = (FloatingActionButton) view.findViewById(R.id.tintuyendung);
//        btn_vanhoa_thethao = (FloatingActionButton) view.findViewById(R.id.tinvanhoathethao);


//        menu.setClosedOnTouchOutside(true);
//
//        btn_vanhoa_thethao.setOnClickListener(this);
//        btn_all.setOnClickListener(this);
//        btn_daotao.setOnClickListener(this);
//        btn_doanthe.setOnClickListener(this);
//        btn_hoithao.setOnClickListener(this);
//        btn_hoptac.setOnClickListener(this);
//        btn_nghiencuu.setOnClickListener(this);
//        btn_tuyendung.setOnClickListener(this);
    }

    private void setupFab(View view) {

        Fab fab = (Fab) view.findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_icon_filter);
        fab.setRippleColor(getResources().getColor(R.color.theme_primary_dark));
        fab.setBackgroundColor(getResources().getColor(R.color.theme_primary_dark));

        View sheetView = view.findViewById(R.id.fab_sheet);
        View overlay = view.findViewById(R.id.overlay);
        int sheetColor = getResources().getColor(R.color.background_card);
        int fabColor = getResources().getColor(R.color.theme_primary_dark);

        // Create material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

        // Set material sheet event listener
        materialSheetFab.setEventListener(new MaterialSheetFabEventListener() {
            @Override
            public void onShowSheet() {
                // Save current status bar color
                statusBarColor = getStatusBarColor();
                // Set darker status bar color to match the dim overlay
//                setStatusBarColor(getResources().getColor(R.color.theme_primary_dark2));
            }

            @Override
            public void onHideSheet() {
                // Restore status bar color
                setStatusBarColor(statusBarColor);
            }
        });

        // Set material sheet item click listeners
        view.findViewById(R.id.tinall).setOnClickListener(this);
        view.findViewById(R.id.tindaotao).setOnClickListener(this);
        view.findViewById(R.id.tinhoatdongdoanthe).setOnClickListener(this);
        view.findViewById(R.id.tinhoithao).setOnClickListener(this);
        view.findViewById(R.id.tinhoptac).setOnClickListener(this);
        view.findViewById(R.id.tinnghiencuu).setOnClickListener(this);
        view.findViewById(R.id.tintuyendung).setOnClickListener(this);
        view.findViewById(R.id.tinvanhoathethao).setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (materialSheetFab.isSheetVisible()) {
                        materialSheetFab.hideSheet();
                        return true;
                    }

                } else {
                    Toast.makeText(getActivity(), "Nhấn 1 lần nữa để thoát ", Toast.LENGTH_SHORT).show();
                    onFinish.finish(true);
                }

                return false;
            }
        });
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onClick(View v) {
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
        materialSheetFab.hideSheet();
    }

    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getActivity().getWindow().getStatusBarColor();
        }
        return 0;
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(color);
        }
    }
}
