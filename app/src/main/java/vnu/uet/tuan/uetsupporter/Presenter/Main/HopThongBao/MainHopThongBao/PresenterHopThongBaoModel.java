package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.MainHopThongBao;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;
import vnu.uet.tuan.uetsupporter.Model.Response.Message;
import vnu.uet.tuan.uetsupporter.Model.Response.ReactionResponse;
import vnu.uet.tuan.uetsupporter.R;
import vnu.uet.tuan.uetsupporter.Retrofit.ApiTinTuc;
import vnu.uet.tuan.uetsupporter.Utils.Utils;
import vnu.uet.tuan.uetsupporter.config.Config;

/**
 * Created by vmtuan on 3/24/2017.
 */

public class PresenterHopThongBaoModel implements IPresenterHopThongBaoModel {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private OnReactFinish listenerPush;


    public PresenterHopThongBaoModel(Context context) {
        this.context = context;

    }

    @Override
    public void getPushNotification(OnGetPushNotificationFinish listener) {
        ArrayList<AnnouncementNotification> list = new ArrayList<>();
        try {
            list = Utils.getPushNotification(context);
        } catch (Exception e) {
            listener.OnFailure(e.getMessage());
        }
        String[] ids = Utils.getIdAnnouncements(list);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        RequestBody body = getBodyFetchReact(ids);
        Call<ArrayList<ReactionResponse>> call = apiTinTuc.fetchReaction(body, Utils.getUserToken(context));
        ArrayList<AnnouncementNotification> finalList = list;
        call.enqueue(new Callback<ArrayList<ReactionResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ReactionResponse>> call, Response<ArrayList<ReactionResponse>> response) {

                if (response.isSuccessful() && response.code() == 401) {
                    listener.OnFailure(context.getResources().getString(R.string.fail_authentication));

                } else if (response.isSuccessful() && response.code() < 400) {
                    ArrayList<ReactionResponse> reactions = response.body();
                    for (int i = 0; i < reactions.size(); i++) {
                        finalList.get(i).setTotalFeedback(reactions.get(i).getFeedbackCount());
                        if (reactions.get(i) != null) {
                            finalList.get(i).setAngry(reactions.get(i).getAngry());
                            finalList.get(i).setCry(reactions.get(i).getCry());
                            finalList.get(i).setLove(reactions.get(i).getLove());
                            finalList.get(i).setWow(reactions.get(i).getWow());
                            finalList.get(i).setSurprise(reactions.get(i).getSurprise());
                        } else {
                            finalList.get(i).setAngry(null);
                            finalList.get(i).setCry(null);
                            finalList.get(i).setLove(null);
                            finalList.get(i).setWow(null);
                            finalList.get(i).setSurprise(null);
                        }
                    }
                }

                listener.OnSuccess(finalList);
            }

            @Override
            public void onFailure(Call<ArrayList<ReactionResponse>> call, Throwable t) {
                if (t != null) {
                    listener.OnFailure(t.getMessage());
                } else {
                    listener.OnFailure(context.getResources().getString(R.string.fail_download));
                }
            }
        });

    }

    @Override
    public void pushReact(String _id, int code, OnReactFinish listener) {
        this.listenerPush = listener;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiTinTuc apiTinTuc = retrofit.create(ApiTinTuc.class);
        RequestBody body = getBodyPushReact(_id, code);
        Call<Message> call = apiTinTuc.postReaction(body, Utils.getUserToken(context));
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.code() < 400 && response.isSuccessful()) {
                    Message message = response.body();
                    if (message.getSuccess()) {
                        listenerPush.OnReactSuccess(code);
                    } else {
                        listenerPush.OnReactFailure(message.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                if (t.getMessage() != null) {
                    listenerPush.OnReactFailure(t.getMessage());
                } else {
                    listenerPush.OnReactFailure(context.getResources().getString(R.string.fail_download));
                }
            }
        });
    }

    private RequestBody getBodyPushReact(String _id, int code) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("_id", _id);
        map.put("code", code);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(map)).toString());
    }

    private RequestBody getBodyFetchReact(String[] ids) {
        Map<String, Object> map = new ArrayMap<>();
        map.put("ids", ids);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(map)).toString());
    }
}
