package vnu.uet.tuan.uetsupporter.Fragment.Main.Profile;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Model.GiangVien;
import vnu.uet.tuan.uetsupporter.Model.Khoa;
import vnu.uet.tuan.uetsupporter.Model.LopChinh;
import vnu.uet.tuan.uetsupporter.Model.LopMonHoc;
import vnu.uet.tuan.uetsupporter.Model.SinhVien;
import vnu.uet.tuan.uetsupporter.Model.Users;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    TextView txt_tenSinhVien;
    TextView txt_email;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_profile, container, false);
        initUI(view);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ExecuInfomationUser().execute();
            }
        });
        return view;
    }

    private void initUI(View view) {
        txt_tenSinhVien = (TextView) view.findViewById(R.id.profile_tensinhvien);
        txt_email = (TextView) view.findViewById(R.id.profile_email);
    }

    class ExecuInfomationUser extends AsyncTask<Void,Integer,SinhVien>{

        @Override
        protected SinhVien doInBackground(Void... params) {
            String json = Utils.getJSONFromSever(Utils.getUrlWithToken(Config.GET_INFORMATION_USER,getActivity()));
            if (json !=null){
                return (SinhVien) getImfomationUsers(json);
            }
            return null;
        }

        @Override
        protected void onPostExecute(SinhVien sinhVien) {
            super.onPostExecute(sinhVien);
            if(sinhVien!=null){
                //update UI
                txt_tenSinhVien.setText(sinhVien.getTenSinhVien());
                txt_email.setText(sinhVien.getUsername());

            }else{
                Toast.makeText(getActivity(),"Đường truyền có lỗi !", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private static final String TENLOPCHINH = "tenLopChinh";
    private static final String KHOA = "idKhoa";
    private static final String TENKHOA = "tenKhoa";
    private static final String ARR_LOPMONHOC = "idLopMonHoc";
    private static final String TENLOPMONHOC = "tenLopMonHoc";
    private static final String THOIGIANBATDAU = "thoiGian";
    private static final String ARR_GIANGVIEN = "idGiangVien";
    private final String USER = "user";
    private final String PROFILE = "profile";
    private final String _ID = "_id";
    private final String LOPCHINH = "lopChinh";
    private final String TENSINHVIEN = "tenSinhVien";
    private static final String TENGIANGVIEN = "tenGiangVien";
    private Users getImfomationUsers(String s){
        try {
            JSONObject root = new JSONObject(s);

            //getUsers
            String email = Utils.getEmailUser(getActivity());
            String pass = new String();

            //LopChinh
            JSONObject lopchinh = root.getJSONObject(LOPCHINH);
            String idLopChinh = lopchinh.getString(_ID);
            String tenLopChinh = lopchinh.getString(TENLOPCHINH);
            //---Khoa

            JSONObject khoa = lopchinh.getJSONObject(KHOA);
            String idKhoa = khoa.getString(_ID);
            String tenKhoa = khoa.getString(TENKHOA);
            Khoa Khoa = new Khoa(idKhoa,tenKhoa);

            LopChinh mainClass = new LopChinh(tenLopChinh,Khoa);

            //getProfile
            JSONObject profile = root.getJSONObject(PROFILE);
            String _id = profile.getString(_ID);
            String tenSinhVien = profile.getString(TENSINHVIEN);

            //getLOPMONHOC
            ArrayList<LopMonHoc> arrLopMonHoc = new ArrayList<>();
            JSONArray arr = profile.getJSONArray(ARR_LOPMONHOC);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String idLopMonHoc = obj.getString(_ID);
                String tenLopMonHoc = obj.getString(TENLOPMONHOC);
                String thoiGian = obj.getString(THOIGIANBATDAU);

                ArrayList<GiangVien> arrGiangVien = new ArrayList<>();
                JSONArray arrJsonGiangVien = obj.getJSONArray(ARR_GIANGVIEN);
                for (int j = 0; j < arrJsonGiangVien.length(); j++) {
                    //ALL GIANGVIEN
                    JSONObject objGV = arrJsonGiangVien.getJSONObject(i);

                    String idGiangVien = objGV.getString(_ID);
                    String tenGiangVien = objGV.getString(TENGIANGVIEN);
                    GiangVien gv = new GiangVien(idGiangVien,tenGiangVien);
                    arrGiangVien.add(gv);
                }

                LopMonHoc LopMonHoc = new LopMonHoc(idLopMonHoc,tenLopMonHoc,arrGiangVien,thoiGian);
                arrLopMonHoc.add(LopMonHoc);
            }

            SinhVien sinhVien = new SinhVien(email,pass,_id,tenSinhVien,mainClass,arrLopMonHoc);

            return sinhVien;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
