package net.growdev.driverojekonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import net.growdev.driverojekonline.helper.SessionManager;
import net.growdev.driverojekonline.model.modelhistory.ResponseHistory;
import net.growdev.driverojekonline.network.ApiClient;
import net.growdev.driverojekonline.view.auth.AuthActivity;
import net.growdev.driverojekonline.view.history.HistoryActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SessionManager sessionManager;
    String tokenFcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        if (sessionManager.getGcm().isEmpty()){
            tokenFcm = FirebaseInstanceId.getInstance().getToken();
            Toast.makeText(this, "token: " +tokenFcm, Toast.LENGTH_SHORT).show();
            insertTokenToDb();
        }
    }

    private void insertTokenToDb() {

        String iduser = sessionManager.getIdUser();
        ApiClient.getApiService().registerToken(iduser,tokenFcm).enqueue(new Callback<ResponseHistory>() {
            @Override
            public void onResponse(Call<ResponseHistory> call, Response<ResponseHistory> response) {
                if (response.isSuccessful()){
                    boolean result=  response.body().getResult();
                    String msg = response.body().getMsg();
                    if (result){
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseHistory> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void onOrder(View view) {
        startActivity(new Intent(this, HistoryActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menu_logout){
            new AlertDialog.Builder(this)
                    .setTitle("Konfirmasi")
                    .setMessage("Anda yakin ingin logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            sessionManager.logout();

                            startActivity(new Intent(MainActivity.this, AuthActivity.class));
                        }
                    })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}
