package vn.viviu.produk.activities.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import vn.viviu.produk.utils.LoginUtil;

/**
 * Created by h on 4/6/2018.
 */

public class LoginPresenter {
    private LoginView loginView;
    private FirebaseAuth mAuth;
    private Activity activity;

    public LoginPresenter(Activity activity, FirebaseAuth auth) {
        this.activity = activity;
        this.loginView = (LoginView) activity;
        mAuth = auth;
    }

    public void validateCredentials(String email, String password) {
        loginView.showProgress();
        boolean error = false;

        if (!LoginUtil.isPassword(password)) {
            onPasswordError();
            error = true;
        }
        if (!LoginUtil.isEmail(email)) {
            onEmailError();
            error = true;
        }
        if (!error)
            onLogin(email, password);
    }

    public void onLogin(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        onSuccess();
                    } else {
                        Toast.makeText(activity, "Sign in failed! Please try again.", Toast.LENGTH_SHORT).show();
                        onPasswordError();
                        onEmailError();
                    }
                });
    }

    public void onEmailError() {
        loginView.hideProgress();
        loginView.setEmailError();
    }

    public void onPasswordError() {
        loginView.hideProgress();
        loginView.setPasswordError();
    }

    public void onSuccess() {
        loginView.hideProgress();
        loginView.navigateToHome();
        Toast.makeText(activity, "Success!!!", Toast.LENGTH_SHORT).show();
    }
}
