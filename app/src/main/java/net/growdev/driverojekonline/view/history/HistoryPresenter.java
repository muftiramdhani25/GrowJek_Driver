package net.growdev.driverojekonline.view.history;

import net.growdev.driverojekonline.base.BaseView;
import net.growdev.driverojekonline.model.modelhistory.DataHistory;
import net.growdev.driverojekonline.model.modelhistory.ResponseHistory;
import net.growdev.driverojekonline.network.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPresenter implements HistoryContract.Presenter {

    HistoryContract.View historyView;
    BaseView view;
    public static List<DataHistory> dataHistoryRequest;
    public static List<DataHistory> dataHistoryProses;
    public static List<DataHistory> dataHistoryComplete;

    public HistoryPresenter(HistoryContract.View view) {
        historyView = view;
    }

    @Override
    public void getDataHistory(String iduser, String status, String token, String device) {
        historyView.showLoading("proses get data history");

        if (status.equals("1")){

            ApiClient.getApiService().getRequestHistory().enqueue(new Callback<ResponseHistory>() {
                @Override
                public void onResponse(Call<ResponseHistory> call, Response<ResponseHistory> response) {
                    historyView.hideLoading();
                    if (response.isSuccessful()){
                        boolean result = response.body().getResult();
                        String msg = response.body().getMsg();
                        if (result){
                            historyView.showMsg(msg);
                            dataHistoryRequest = response.body().getData();
                            historyView.dataHistory(dataHistoryRequest);

                        }
                        else {
                            historyView.showMsg(msg);
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseHistory> call, Throwable t) {

                }
            });
        }

        else if (status.equals("2")) {
            ApiClient.getApiService().getProsesHistory(iduser,  device,token).enqueue(new Callback<ResponseHistory>() {
                @Override
                public void onResponse(Call<ResponseHistory> call, Response<ResponseHistory> response) {
                    historyView.hideLoading();
                    if (response.isSuccessful()) {
                        boolean result = response.body().getResult();
                        String msg = response.body().getMsg();
                        if (result) {
                            historyView.showMsg(msg);
                            dataHistoryProses = response.body().getData();
                            historyView.dataHistory(dataHistoryProses);
                        } else {
                            historyView.showMsg(msg);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseHistory> call, Throwable t) {
                    historyView.showError(t.getLocalizedMessage());
                    historyView.hideLoading();
                }
            });
        }

        else if (status.equals("4")) {

            ApiClient.getApiService().getCompleteHistory(iduser, device, token).enqueue(new Callback<ResponseHistory>() {
                @Override
                public void onResponse(Call<ResponseHistory> call, Response<ResponseHistory> response) {
                    historyView.hideLoading();
                    if (response.isSuccessful()) {
                        boolean result = response.body().getResult();
                        String msg = response.body().getMsg();
                        if (result) {
                            historyView.showMsg(msg);
                            dataHistoryComplete = response.body().getData();
                            historyView.dataHistory(dataHistoryComplete);
                        }
                        else {
                            historyView.showMsg(msg);

                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseHistory> call, Throwable t) {
                    historyView.showError(t.getLocalizedMessage());
                    historyView.hideLoading();
                }
            });
        }
    }

    @Override
    public void onAttach(BaseView view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }
}


