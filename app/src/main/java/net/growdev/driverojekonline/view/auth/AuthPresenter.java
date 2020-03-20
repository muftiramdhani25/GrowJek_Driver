package net.growdev.driverojekonline.view.auth;

import net.growdev.driverojekonline.base.BaseView;
import net.growdev.driverojekonline.model.modelauth.ResponseAuth;
import net.growdev.driverojekonline.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthPresenter implements AuthContract.Presenter {

    AuthContract.View authView;
    BaseView baseView;

    public AuthPresenter(AuthContract.View authView) {
        this.authView = authView;
    }

    @Override
    public void actionLogin(String uuid, String email, String password) {
        authView.showLoading("proses login");

        ApiClient.getApiService().login(uuid, email, password).enqueue(new Callback<ResponseAuth>() {
            @Override
            public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                authView.hideLoading();

                if (response.isSuccessful() || response.code() == 200){
                    String msg = response.body().getMsg();
                    boolean result = response.body().isResult();

                    if (result){
                        authView.showMessage(msg);

                        ResponseAuth dataUser = response.body();
                        authView.dataUser(dataUser);

                        authView.pindahActivity();
                    } else {
                        authView.showMessage(msg);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAuth> call, Throwable t) {
                authView.hideLoading();
                authView.showError(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void actionRegister(String nama, String email, String password, String phone) {
        authView.showLoading("proses register");

        ApiClient.getApiService().register(nama, email, password, phone).enqueue(new Callback<ResponseAuth>() {
            @Override
            public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                authView.hideLoading();

                if (response.isSuccessful() || response.code() == 200){
                    boolean result = response.body().isResult();
                    String msg = response.body().getMsg();

                    if (result){
                        authView.showMessage(msg);
                    } else {
                        authView.showMessage(msg);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    authView.hideLoading();
                    authView.showError(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onAttach(BaseView view) {
        baseView = view;
    }

    @Override
    public void onDetach() {
        baseView = null;
    }
}
