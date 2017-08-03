package com.roy.tryfringerprint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.ajalt.reprint.core.AuthenticationFailureReason;
import com.github.ajalt.reprint.core.AuthenticationListener;
import com.github.ajalt.reprint.core.Reprint;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btFingerPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btFingerPrint = (Button) findViewById(R.id.bt_finger_print);

        btFingerPrint.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finger_print:

                Reprint.authenticate(new AuthenticationListener() {
                    @Override
                    public void onSuccess(int moduleTag) {
                        Toast.makeText(MainActivity.this, "success: " + moduleTag, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(AuthenticationFailureReason failureReason, boolean fatal, CharSequence errorMessage, int moduleTag, int errorCode) {
                        Toast.makeText(MainActivity.this, "Fail: " + failureReason + " " + moduleTag + " " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });


                break;
        }

    }
}
