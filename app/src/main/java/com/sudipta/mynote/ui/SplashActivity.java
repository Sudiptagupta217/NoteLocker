package com.sudipta.mynote.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sudipta.mynote.R;

public class SplashActivity extends AppCompatActivity {
    private Button button;
    private Button button1;
    private EditText password;
    private EditText apassword;
    private EditText textView;
    private EditText mPassword;
    private LinearLayout setPasswordLayout;
    private LinearLayout showPasswordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);  //set full screen;

        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);
        password = findViewById(R.id.password);
        apassword = findViewById(R.id.apassword);
        mPassword = findViewById(R.id.mpassword);
        textView = findViewById(R.id.textView);
        setPasswordLayout = findViewById(R.id.set_password_layout);
        showPasswordLayout = findViewById(R.id.show_password_layout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singUpPassword();
            }
        });

        //Get the value of shared preference back
        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        String value = getShared.getString("str", null);
        textView.setText(value);

        if (!TextUtils.isEmpty(textView.getText())) {
            showPasswordLayout.setVisibility(View.VISIBLE);
            setPasswordLayout.setVisibility(View.INVISIBLE);
        } else {
            setPasswordLayout.setVisibility(View.VISIBLE);
            showPasswordLayout.setVisibility(View.INVISIBLE);
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singInPassword();
            }
        });
    }

    private void singUpPassword() {
        String rpassword = apassword.getText().toString();
        String spassword = password.getText().toString();
        if (!TextUtils.isEmpty(password.getText())) {
            if (spassword.equals(rpassword)) {
                setPasswordLayout.setVisibility(View.GONE);
                showPasswordLayout.setVisibility(View.VISIBLE);
                SharedPreferences sharedPreferences = getSharedPreferences("demo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("str", rpassword);
                editor.apply();
                textView.setText(rpassword);
            } else {
                apassword.setError("Password Dosen't matched!");
                apassword.requestFocus();
            }
        } else {
            password.setError("Enter the Password");
            password.requestFocus();
        }
    }

    private void singInPassword() {
        String textview = textView.getText().toString();
        String mpassword = mPassword.getText().toString();
        if (textview.equals(mpassword)) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            mPassword.setError("Incorrect Password!");
            mPassword.requestFocus();
        }
    }

}