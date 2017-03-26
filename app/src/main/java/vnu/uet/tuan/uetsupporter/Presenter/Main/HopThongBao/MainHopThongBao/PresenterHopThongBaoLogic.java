package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.MainHopThongBao;

import android.content.Context;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.PushNotification;
import vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.MainHopThongBao.IViewHopThongBao;

/**
 * Created by vmtuan on 3/24/2017.
 */

public class PresenterHopThongBaoLogic implements IPresenterHopThongBaoView, IPresenterHopThongBaoModel.OnGetPushNotificationFinish {
    private IViewHopThongBao iViewHopThongBao;
    private PresenterHopThongBaoModel presenterHopThongBaoModel;
    private Context context;

    public PresenterHopThongBaoLogic(Context context, IViewHopThongBao iViewHopThongBao) {
        this.iViewHopThongBao = iViewHopThongBao;
        this.context = context;
        this.presenterHopThongBaoModel = new PresenterHopThongBaoModel(context);
    }

    @Override
    public void executeRetrigerPushNotification() {
        iViewHopThongBao.OnPreExcute();
        presenterHopThongBaoModel.getPushNotification(this);
    }

    @Override
    public void OnSuccess(List<PushNotification> notifications) {
        iViewHopThongBao.OnGetHopThongBaoSuccess(notifications);
    }

    @Override
    public void OnFailure(String fail) {
        iViewHopThongBao.OnGetHopThongBaoFailure(fail);
    }
}
