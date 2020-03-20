package net.growdev.driverojekonline.base;

public interface BasePresenter<T extends BaseView> {

    void onAttach(T view);
    void onDetach();
}
