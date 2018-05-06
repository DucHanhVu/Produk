package vn.viviu.produk.activities.login;

/**
 * Created by h on 4/6/2018.
 */

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setEmailError();

    void setPasswordError();

    void navigateToHome();
}
