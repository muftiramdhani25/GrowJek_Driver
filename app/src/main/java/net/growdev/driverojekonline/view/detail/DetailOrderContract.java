package net.growdev.driverojekonline.view.detail;

import net.growdev.driverojekonline.base.BasePresenter;
import net.growdev.driverojekonline.base.BaseView;

public interface DetailOrderContract {


    interface View extends BaseView<BasePresenter> {
        void showLoading(String pesanloading);
        void hideLoading();
        void showError(String localizedMessage);
        void showMsg(String msg);
        void getDataMap(String dataGaris);
        void pindahHalaman();
    }

    interface Presenter extends BasePresenter {
        void detailRute(String origin, String desti, String key);
        void completeBooking(String iduser, String idbooking, String token, String device);
        void takeBooking(String idbooking, String iddriver, String device, String token);
    }


}
