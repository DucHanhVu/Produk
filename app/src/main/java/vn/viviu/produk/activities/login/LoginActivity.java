package vn.viviu.produk.activities.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

import vn.viviu.produk.activities.MainActivity;
import vn.viviu.produk.R;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private LinearLayout llContainer;
    private EditText edtEmail, edtPassword;
    private Button btnSignIn;
    private ProgressBar progressSignIn;

    private FirebaseAuth mAuth;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Mapping
        llContainer = findViewById(R.id.ll_container);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSignIn = findViewById(R.id.login_btn);
        progressSignIn = findViewById(R.id.progress_login);
        //Object
        mAuth = FirebaseAuth.getInstance();
        loginPresenter = new LoginPresenter(this, mAuth);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                //Action check email, password
                loginPresenter.validateCredentials(email, password);
            }
        });
    }

    @Override
    public void showProgress() {
        llContainer.setVisibility(View.GONE);
        progressSignIn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        llContainer.setVisibility(View.VISIBLE);
        progressSignIn.setVisibility(View.GONE);
    }

    @Override
    public void setEmailError() {
        edtEmail.setError("Invalid Email Address.");
        edtEmail.requestFocus();
    }

    @Override
    public void setPasswordError() {
        edtPassword.setError("Password incorrect.");
        edtPassword.requestFocus();
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
