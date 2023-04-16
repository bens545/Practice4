package com.benshaner.practice4;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mSignInButton;
    private Button mSignUpButton;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mEditTextEmail = findViewById(R.id.email);
        mEditTextPassword = findViewById(R.id.password);
        mSignInButton = findViewById(R.id.sign_in);
        mSignUpButton = findViewById(R.id.sign_up);

        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        firebaseAuth = FirebaseAuth.getInstance();

        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            mEditTextEmail.setError(getString(R.string.invalid_username));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mEditTextPassword.setError(getString(R.string.invalid_password));
            return;
        }

        switch (view.getId()) {
            case R.id.sign_in:
                SignIn(email, password);
                break;
            case R.id.sign_up:
                SignUp(email, password);
                break;
        }
    }

    private void SignIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void SignUp(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
