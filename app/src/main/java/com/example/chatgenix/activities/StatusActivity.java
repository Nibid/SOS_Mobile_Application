package com.example.chatgenix.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.chatgenix.R;
import com.example.chatgenix.utilities.FallDetectionService;
import com.example.chatgenix.utilities.PreferenceManager;
import com.skyfishjy.library.RippleBackground;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StatusActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener {

    private static final int REQUEST_PERMISSIONS = 4;
    private Context mContext;
    private ToggleButton mButtonService;
    private RippleBackground mRippleBackground;
    private TextView mTextViewDetect;
    private PreferenceManager mPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        requestForPermissions();
        initObjects();
        initCallbacks();
        initToggle();
        invalidateService();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext( CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onPause() {
        super.onPause();

    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mPreferenceManager.setService(isChecked);
        invalidateService();
    }

    private void initObjects() {
        mButtonService = (ToggleButton) findViewById(R.id.toggle_service);
        mRippleBackground = (RippleBackground) findViewById(R.id.ripple_bg);
        mTextViewDetect = (TextView) findViewById(R.id.txt_detect);

        mContext = this;
        mPreferenceManager = new PreferenceManager(mContext);
    }

    private void initCallbacks() {
        mButtonService.setOnCheckedChangeListener(this);
    }

    private void initToggle() {
        mButtonService.setChecked(mPreferenceManager.getService());
    }

    private void invalidateService() {
        if (mPreferenceManager.getService()) {
            startService(new Intent(mContext, FallDetectionService.class));
            mTextViewDetect.setTextColor( ContextCompat.getColor(mContext, R.color.accent));
            mTextViewDetect.setText(getString(R.string.txt_detecting));
            mRippleBackground.startRippleAnimation();
        } else {
            stopService(new Intent(mContext, FallDetectionService.class));
            mTextViewDetect.setTextColor(ContextCompat.getColor(mContext, android.R.color.white));
            mTextViewDetect.setText(getString(R.string.txt_enable_detection));
            mRippleBackground.stopRippleAnimation();
        }
    }

    private void requestForPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE},
                REQUEST_PERMISSIONS);
    }
}