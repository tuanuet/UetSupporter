package vnu.uet.tuan.uetsupporter.Presenter.Main.HopThongBao.MainHopThongBao;

import android.content.Context;

import java.util.List;

import vnu.uet.tuan.uetsupporter.Model.AnnouncementNotification;
import vnu.uet.tuan.uetsupporter.View.Main.HopThongBao.MainHopThongBao.IViewHopThongBao;

/**
 * Created by vmtuan on 3/24/2017.
 */

public class PresenterHopThongBaoLogic implements IPresenterHopThongBaoView,
        IPresenterHopThongBaoModel.OnGetPushNotificationFinish,
        IPresenterHopThongBaoModel.OnReactFinish {
    private IViewHopThongBao iViewHopThongBao;
    private PresenterHopThongBaoModel presenterHopThongBaoModel;

    public PresenterHopThongBaoLogic(Context context, IViewHopThongBao iViewHopThongBao) {
        this.iViewHopThongBao = iViewHopThongBao;
        this.presenterHopThongBaoModel = new PresenterHopThongBaoModel(context);
    }

    @Override
    public void executeRetrigerPushNotification() {
        iViewHopThongBao.OnPreExcute();
        presenterHopThongBaoModel.getPushNotification(this);
    }

    @Override
    public void react(String id, int code) {
        presenterHopThongBaoModel.pushReact(id,code,this);
    }

    @Override
    public void OnSuccess(List<AnnouncementNotification> notifications) {
        iViewHopThongBao.OnGetHopThongBaoSuccess(notifications);
    }

    @Override
    public void OnFailure(String fail) {
        iViewHopThongBao.OnGetHopThongBaoFailure(fail);
    }

    @Override
    public void OnReactSuccess(int code) {
        iViewHopThongBao.OnReactionSuccess(code);
    }

    @Override
    public void OnReactFailure(String fail) {
        iViewHopThongBao.OnReactionFailure(fail);
    }
}
