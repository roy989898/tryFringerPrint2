package com.roy.tryfringerprint;

import android.app.Application;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;

/**
 * Created by roy.leung on 3/8/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {

            Configure.ALLOW_FRINGURE_PRINT = false;
        } else {
            Configure.ALLOW_FRINGURE_PRINT = getApplicationContext().getSystemService(FingerprintManager.class).isHardwareDetected();
        }

    }
}
