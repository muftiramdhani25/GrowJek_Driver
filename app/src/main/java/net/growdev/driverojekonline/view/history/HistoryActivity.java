package net.growdev.driverojekonline.view.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.tabs.TabLayout;

import net.growdev.driverojekonline.R;
import net.growdev.driverojekonline.helper.HeroHelper;
import net.growdev.driverojekonline.helper.LocationMonitoringService;
import net.growdev.driverojekonline.helper.SessionManager;
import net.growdev.driverojekonline.model.modelhistory.ResponseHistory;
import net.growdev.driverojekonline.network.ApiClient;
import net.growdev.driverojekonline.view.history.adapter.CustomPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 2;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.pager)
    ViewPager pager;
    private SessionManager manager;
    private GoogleApiClient googleApiClient;
    private HeroHelper heroHelper;
    private boolean mAlreadyStartedService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        //cekstatusgps();
        //permissionGPS();
        setLocationDriver();
        aktifkanservice();

        heroHelper = new HeroHelper();
        heroHelper.checkGpsDevice(this, googleApiClient);

        manager = new SessionManager(this);
        tablayout.addTab(tablayout.newTab().setText("Request"));
        tablayout.addTab(tablayout.newTab().setText("Proses"));
        tablayout.addTab(tablayout.newTab().setText("Selesai"));

        PagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setLocationDriver() {

        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String latitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LATITUDE);
                String longitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LONGITUDE);
                sendLocation(latitude, longitude);
            }
        }, new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST));
    }

    private void sendLocation(String latitude, String longitude) {

        String iddriver = manager.getIdUser();
        String token = manager.getToken();
        String device = HeroHelper.getDeviceUUID(this);


        ApiClient.getApiService().insertPosisiDriver(iddriver, latitude, longitude, device, token).enqueue(new Callback<ResponseHistory>() {
            @Override
            public void onResponse(Call<ResponseHistory> call, Response<ResponseHistory> response) {
                boolean result = response.body().getResult();
                String msg = response.body().getMsg();
                if (result) {
                    //Toast.makeText(HistoryActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(HistoryActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseHistory> call, Throwable t) {

            }
        });
    }

    private void aktifkanservice() {
        if (!mAlreadyStartedService) {

            Log.d(TAG, "service start");
            //Start location sharing service to app server.........
            Intent intent = new Intent(this, LocationMonitoringService.class);
            startService(intent);

            mAlreadyStartedService = true;
            //Ends................................................
        }
    }


}


