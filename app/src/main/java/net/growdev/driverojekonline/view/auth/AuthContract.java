package net.growdev.driverojekonline.view.auth;


import net.growdev.driverojekonline.base.BasePresenter;
import net.growdev.driverojekonline.base.BaseView;
import net.growdev.driverojekonline.model.modelauth.ResponseAuth;

public interface AuthContract {

    interface View extends BaseView<BasePresenter> {
        void showMessage(String msg);
        void showLoading(String msg);
        void hideLoading();
        void showError(String localizedMessage);
        void pindahActivity();
        void dataUser(ResponseAuth dataUser);
    }

    interface Presenter extends BasePresenter{
        void actionLogin(String uuid, String email, String password);
        void actionRegister(String nama, String email, String password, String phone);
    }
}
