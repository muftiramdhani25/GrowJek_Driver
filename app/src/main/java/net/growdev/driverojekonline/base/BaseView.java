package net.growdev.driverojekonline.base;

public interface BaseView<T extends BasePresenter> {

    void onAttachView();
    void onDetachView();
}
