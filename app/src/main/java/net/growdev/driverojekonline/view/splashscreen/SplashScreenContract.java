package net.growdev.driverojekonline.view.splashscreen;

import com.airbnb.lottie.LottieAnimationView;

import net.growdev.driverojekonline.base.BasePresenter;
import net.growdev.driverojekonline.base.BaseView;


public interface SplashScreenContract {

    // bekerja di presenter, dopanggil di activity
    interface Presenter extends BasePresenter {
        void delaySplash(long i, LottieAnimationView splashLottie);
    }

    // ditugaskan di presenter, dijalankan di activity
    interface View extends BaseView<BasePresenter> {
        void pindahActivity();
    }
}
