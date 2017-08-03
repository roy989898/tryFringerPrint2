package com.roy.tryfringerprint;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FingerPrintAuthCallback {


    private Button btFingerPrint;
    private FingerPrintAuthHelper mFingerPrintAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);
        initView();

        if (!Configure.ALLOW_FRINGURE_PRINT)
            btFingerPrint.setVisibility(View.GONE);
    }

    private void initView() {
        btFingerPrint = (Button) findViewById(R.id.bt_finger_print);
        btFingerPrint.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finger_print:
                mFingerPrintAuthHelper.startAuth();
                break;
        }

    }

    @Override
    protected void onPause() {
        if (mFingerPrintAuthHelper != null)
            stopTheAuth(mFingerPrintAuthHelper);

        super.onPause();
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        Toast.makeText(this, "No Hardware", Toast.LENGTH_LONG).show();
        stopTheAuth(mFingerPrintAuthHelper);

    }

    @Override
    public void onNoFingerPrintRegistered() {
        Toast.makeText(this, "onNoFingerPrintRegistered", Toast.LENGTH_LONG).show();
        stopTheAuth(mFingerPrintAuthHelper);

    }

    @Override
    public void onBelowMarshmallow() {
        Toast.makeText(this, "onBelowMarshmallow", Toast.LENGTH_LONG).show();
        stopTheAuth(mFingerPrintAuthHelper);
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        Toast.makeText(this, "onAuthSuccess", Toast.LENGTH_LONG).show();
        stopTheAuth(mFingerPrintAuthHelper);
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        Toast.makeText(this, "onAuthFailed" + " " + errorCode + " " + errorMessage, Toast.LENGTH_LONG).show();
        stopTheAuth(mFingerPrintAuthHelper);
    }

    private void stopTheAuth(FingerPrintAuthHelper fingerPrintAuthHelper) {
        fingerPrintAuthHelper.stopAuth();

    }
}
