package com.roy.tryfringerprint;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String DIALOG_FRAGMENT_TAG = "myFragment";


    private Button btFingerPrint;
    private FingerPrintAuthHelper mFingerPrintAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//                mFingerPrintAuthHelper.startAuth();

                FingerprintAuthenticationDialogFragment fragment
                        = new FingerprintAuthenticationDialogFragment();

                fragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
                break;
        }

    }

    @Override
    protected void onPause() {
        if (mFingerPrintAuthHelper != null)


            super.onPause();
    }


}
