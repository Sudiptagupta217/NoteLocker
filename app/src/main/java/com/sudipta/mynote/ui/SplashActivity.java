package com.sudipta.mynote.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sudipta.mynote.FingerprintHandler;
import com.sudipta.mynote.R;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class SplashActivity extends AppCompatActivity {
    private Button button;
    private Button button1;
    private EditText password;
    private EditText apassword;
    private EditText EtextView;
    private EditText mPassword;
    private LinearLayout setPasswordLayout;
    private LinearLayout showPasswordLayout;

    private KeyStore keyStore;
    private static final String KEY_NAME = "RIJURJ";
    private Cipher cipher;
    public static TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        EtextView = findViewById(R.id.textView);
        setPasswordLayout = findViewById(R.id.set_password_layout);
        showPasswordLayout = findViewById(R.id.show_password_layout);

        textView=findViewById(R.id.textview);

        setFingerPrint();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singUpPassword();
            }
        });

        //Get the value of shared preference back
        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        String value = getShared.getString("str", null);
        EtextView.setText(value);

        if (!TextUtils.isEmpty(EtextView.getText())) {
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
                EtextView.setText(rpassword);
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
        String textview = EtextView.getText().toString();
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


    //set fingerprint//
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setFingerPrint(){
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)!= PackageManager.PERMISSION_GRANTED){

            return;
        }
        if (!fingerprintManager.isHardwareDetected()) {
            Toast.makeText(this, "Fingerprint authentication permission not enable", Toast.LENGTH_SHORT).show();
        } else {
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(this, "Fingerprint authentication permission not enable", Toast.LENGTH_SHORT).show();
            } else {
                if (!keyguardManager.isKeyguardSecure()) {
                    Toast.makeText(this, "Lock screen security not enabled in settings", Toast.LENGTH_SHORT).show();
                }
                else {
                    getKey();
                }
                if (cipherinit()){
                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler handler = new FingerprintHandler(this);
                    handler.startAuthentication(fingerprintManager,cryptoObject);
                }
            }
        }
    }

    private boolean cipherinit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (CertificateException certificateException) {
            certificateException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
            return false;
        } catch (UnrecoverableKeyException unrecoverableKeyException) {
            unrecoverableKeyException.printStackTrace();
            return false;
        } catch (KeyStoreException keyStoreException) {
            keyStoreException.printStackTrace();
            return false;
        } catch (InvalidKeyException invalidKeyException) {
            invalidKeyException.printStackTrace();
            return false;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getKey() {
        try {
            keyStore= KeyStore.getInstance("AndroidkeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator = null;

        try {
            keyGenerator= KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build()
            );
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e){
            e.printStackTrace();
        }



        keyGenerator.generateKey();
    }
    //end fingerprint//

}