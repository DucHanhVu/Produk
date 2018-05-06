package vn.viviu.produk.activities.splash;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import vn.viviu.produk.activities.MainActivity;
import vn.viviu.produk.R;
import vn.viviu.produk.managers.PrefManager;

public class SplashActivity extends AppCompatActivity implements OnFinishedWelcomeListener{
    private FragmentManager fm;
    private FragmentTransaction transaction;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_splash);
        if (!PrefManager.getInstance(this).isFirstTimeLaunch()) {
            fm = getSupportFragmentManager();
            transaction = fm.beginTransaction();
            transaction.add(R.id.container_splash, new WelcomeFrag());
            transaction.commit();
        } else {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(2000);
                        launchMainScreen();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
    }

    private void launchMainScreen() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFinished() {
        launchMainScreen();
    }
}
