package vnu.uet.tuan.uetsupporter.Fragment.Main.TinTuc;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
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
public class TinTucFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private ArrayList<LoaiTinTuc> loaiTinTucArrayList;
    private MaterialSheetFab materialSheetFab;
    private int statusBarColor;

    public TinTucFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.tatca: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tatca)), "tatca");
                break;
            }
            case R.id.tintuc: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tintuc)), "tintuc");
                break;
            }
            case R.id.tindaotao: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tindaotao)), "tindaotao");
                break;
            }
            case R.id.tinsinhvien: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tinsinhvien)), "tinsinhvien");
                break;
            }
            case R.id.tincohoivieclam: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tincohoivieclam)), "tincohoivieclam");
                break;
            }
            case R.id.tinhoptac: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tinhoptac)), "tinhoptac");
                break;
            }
            case R.id.tinnghiencuu: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tinnghiencuu)), "tinnghiencuu");
                break;
            }
            case R.id.tinhocphihocbong: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tinhocphihocbong)), "tinhocphihocbong");
                break;
            }
            case R.id.tincuusinhvien: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tincuusinhvien)), "tincuusinhvien");
                break;
            }
            case R.id.tinquyche: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tinquyche)), "tinquyche");
                break;
            }
            case R.id.tinsanpham: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tinsanpham)), "tinsanpham");
                break;
            }
            case R.id.tinsaudaihoc: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tinsaudaihoc)), "tinsaudaihoc");
                break;
            }
            case R.id.tinthacsy: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tinthacsy)), "tinthacsy");
                break;
            }
            case R.id.tintiensy: {
                showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tintiensy)), "tintiensy");
                break;
            }
        }
        materialSheetFab.hideSheet();
    }

    public interface OnBackpressToFinish {
        void finish(boolean isFinish);
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
        showChangeFragment(PatternFactoryFragmentTinTuc.newInstance(getContext().getString(R.string.tatca)), "tatca");
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

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.groupbutton);
        radioGroup.setOnCheckedChangeListener(this);
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
