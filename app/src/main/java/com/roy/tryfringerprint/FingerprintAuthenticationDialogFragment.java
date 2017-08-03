/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.roy.tryfringerprint;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

/**
 * A dialog which uses fingerprint APIs to authenticate the user, and falls back to password
 * authentication if fingerprint is not available.
 */
public class FingerprintAuthenticationDialogFragment extends DialogFragment implements FingerPrintAuthCallback {

    private Button mCancelButton;
    private MainActivity mActivity;
    private FingerPrintAuthHelper mFingerPrintAuthHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Do not create a new Fragment when the Activity is re-created such as orientation changes.
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(getString(R.string.sign_in));
        View v = inflater.inflate(R.layout.fingerprint_dialog_container, container, false);

        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(mActivity, this);

        mFingerPrintAuthHelper.startAuth();
        mCancelButton = (Button) v.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTheAuth(mFingerPrintAuthHelper);
                dismiss();
            }
        });


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onPause() {
        stopTheAuth(mFingerPrintAuthHelper);
        super.onPause();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) getActivity();

    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        Toast.makeText(mActivity, "No Hardware", Toast.LENGTH_LONG).show();
        stopTheAuth(mFingerPrintAuthHelper);

    }

    @Override
    public void onNoFingerPrintRegistered() {
        Toast.makeText(mActivity, "onNoFingerPrintRegistered", Toast.LENGTH_LONG).show();
        stopTheAuth(mFingerPrintAuthHelper);

    }

    @Override
    public void onBelowMarshmallow() {
        Toast.makeText(mActivity, "onBelowMarshmallow", Toast.LENGTH_LONG).show();
        stopTheAuth(mFingerPrintAuthHelper);
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        Toast.makeText(mActivity, "onAuthSuccess", Toast.LENGTH_LONG).show();
        stopTheAuth(mFingerPrintAuthHelper);
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        Toast.makeText(mActivity, "onAuthFailed" + " " + errorCode + " " + errorMessage, Toast.LENGTH_LONG).show();
        stopTheAuth(mFingerPrintAuthHelper);
    }

    private void stopTheAuth(FingerPrintAuthHelper fingerPrintAuthHelper) {
        fingerPrintAuthHelper.stopAuth();
        dismiss();

    }

    /**
     * Sets the crypto object to be passed in when authenticating with fingerprint.
     */


    /**
     * @return true if {@code password} is correct, false otherwise
     */
    private boolean checkPassword(String password) {
        // Assume the password is always correct.
        // In the real world situation, the password needs to be verified in the server side.
        return password.length() > 0;
    }


    /**
     * Enumeration to indicate which authentication method the user is trying to authenticate with.
     */
    public enum Stage {
        FINGERPRINT,
        NEW_FINGERPRINT_ENROLLED,
        PASSWORD
    }
}
