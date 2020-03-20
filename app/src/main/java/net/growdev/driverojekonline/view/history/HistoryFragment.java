package net.growdev.driverojekonline.view.history;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.growdev.driverojekonline.R;
import net.growdev.driverojekonline.helper.HeroHelper;
import net.growdev.driverojekonline.helper.SessionManager;
import net.growdev.driverojekonline.model.modelhistory.DataHistory;
import net.growdev.driverojekonline.view.history.adapter.CustomHistoryAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements HistoryContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    HistoryPresenter presenter;
    private SessionManager session;
    int status;
    ProgressDialog loading;

    public HistoryFragment(){

    }

    public HistoryFragment(int i) {
        // Required empty public constructor
        status = i;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new HistoryPresenter(this);
        session = new SessionManager(getActivity());
        loading = new ProgressDialog(getActivity());
        String iduser = session.getIdUser();

        String token = session.getToken();
        String device = HeroHelper.getDeviceUUID(getContext());

        presenter.getDataHistory(iduser, String.valueOf(status), token, device);
    }

    @Override
    public void showLoading(String pesanloading) {
        loading.setTitle("Proses" + pesanloading);
        loading.setMessage("loading . .. . ");
        loading.show();

    }

    @Override
    public void hideLoading() {
        loading.dismiss();
    }

    @Override
    public void showError(String localizedMessage) {
        Toast.makeText(getActivity(), localizedMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void dataHistory(List<DataHistory> dataHistory) {
        CustomHistoryAdapter adapter = new CustomHistoryAdapter
                (getActivity(),dataHistory,status);

        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onAttachView() {
        presenter.onAttach(this);

    }

    @Override
    public void onDetachView() {
        presenter.onDetach();

    }

    @Override
    public void onStart() {
        super.onStart();
        onAttachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onDetachView();
    }
}


