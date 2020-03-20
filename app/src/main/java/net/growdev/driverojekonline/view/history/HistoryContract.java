package net.growdev.driverojekonline.view.history;

import net.growdev.driverojekonline.base.BasePresenter;
import net.growdev.driverojekonline.base.BaseView;
import net.growdev.driverojekonline.model.modelhistory.DataHistory;

import java.util.List;

public interface HistoryContract {

    interface View extends BaseView<BasePresenter> {
        void showLoading(String pesanloading);
        void hideLoading();
        void showError(String localizedMessage);
        void showMsg(String msg);
        void dataHistory(List<DataHistory> dataHistory);
    }

    interface Presenter extends BasePresenter {
        void  getDataHistory(String iduser, String s, String token, String device);
    }


}
