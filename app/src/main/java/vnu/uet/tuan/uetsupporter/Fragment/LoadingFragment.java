package vnu.uet.tuan.uetsupporter.Fragment;


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

import vnu.uet.tuan.uetsupporter.Model.LoaiTinTuc;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Utils.Utils;


import static vnu.uet.tuan.uetsupporter.config.Config.GET_LOAITINTUC;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingFragment extends Fragment {

    public static final String LOAI_TIN_TUC = "loaitintuc";
    public static final String _ID = "_id";
    public static final String LINKPAGE = "linkPage";
    public static final String KIND = "kind";
    private static final String SUCCESS = "success";

    public LoadingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loading, container, false);

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
            LoaiTinTuc[] arrLoaiTinTuc = parseJsonResponse(s);
            if (arrLoaiTinTuc!=null){

            }
            else {
                Toast.makeText(getActivity(),"đường truyền lỗi kiểm tra lại",Toast.LENGTH_LONG).show();
            }
        }
    }
    private LoaiTinTuc[] parseJsonResponse(String s){
        try {
            JSONObject object = new JSONObject(s);
            LoaiTinTuc[] arrLoaiTinTuc = new LoaiTinTuc[10];

            if(object.getBoolean(SUCCESS)){
                JSONArray jsonArray = object.getJSONArray(LOAI_TIN_TUC);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    int id = json.getInt(_ID);
                    String kind = json.getString(KIND);
                    String linkPage = json.getString(LINKPAGE);
                    LoaiTinTuc loaitintuc = new LoaiTinTuc(id,kind,linkPage);
                    Log.e("Loading",loaitintuc.getKind());
                    arrLoaiTinTuc[i] = loaitintuc;
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
