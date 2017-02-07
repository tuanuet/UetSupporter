package vnu.uet.tuan.uetsupporter.Fragment;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vnu.uet.tuan.uetsupporter.Model.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.Contract;
import vnu.uet.tuan.uetsupporter.SQLiteHelper.LoaiTinTucSQLHelper;
import vnu.uet.tuan.uetsupporter.Utils.Utils;



import static vnu.uet.tuan.uetsupporter.config.Config.GET_LOAITINTUC;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingFragment extends Fragment {

    public static final String LOAI_TIN_TUC = "loaitintuc";
    private static final String SUCCESS = "success";
    private LoaiTinTucSQLHelper db;
    public LoadingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loading, container, false);

        db = new LoaiTinTucSQLHelper(getActivity());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Setup().execute();
            }
        });
        return view;
    }

    class Setup extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            return Utils.getJSONFromSever(GET_LOAITINTUC);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s!=null){
                ArrayList<LoaiTinTuc> arrLoaiTinTuc = parseJsonResponse(s);
                if (arrLoaiTinTuc!=null){
                    int count = db.insertBulkLoaiTinTuc(arrLoaiTinTuc);
                    Log.e("Loading",count+"");
                }
                else {
                    Toast.makeText(getActivity(),"Đường truyền lỗi kiểm tra lại",Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(getActivity(),"Đường truyền lỗi kiểm tra lại",Toast.LENGTH_LONG).show();
            }
        }
    }
    private ArrayList<LoaiTinTuc> parseJsonResponse(String s){
        try {
            JSONObject object = new JSONObject(s);
            ArrayList<LoaiTinTuc> arrLoaiTinTuc = new ArrayList<>();

            if(object.getBoolean(SUCCESS)){
                JSONArray jsonArray = object.getJSONArray(LOAI_TIN_TUC);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    int id = json.getInt(Contract.LoaiTinTuc._ID);
                    String kind = json.getString(Contract.LoaiTinTuc.KIND);
                    String linkPage = json.getString(Contract.LoaiTinTuc.LINKPAGE);
                    LoaiTinTuc loaitintuc = new LoaiTinTuc(id,kind,linkPage);
                    arrLoaiTinTuc.add(loaitintuc);
                }
                return arrLoaiTinTuc;

            }else {
                return null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
