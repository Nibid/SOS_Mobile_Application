package com.example.chatgenix.activities;

import static com.example.chatgenix.utilities.Api.KEY_COLLECTION_EMERGENCY_CONTACTS;
import static com.example.chatgenix.utilities.Api.KEY_DIRECTION;
import static com.example.chatgenix.utilities.Api.KEY_LATITUDE;
import static com.example.chatgenix.utilities.Api.KEY_LONGITUDE;
import static com.example.chatgenix.utilities.Api.KEY_POS_X;
import static com.example.chatgenix.utilities.Api.KEY_POS_Y;
import static com.example.chatgenix.utilities.Api.KEY_POS_Z;
import static com.example.chatgenix.utilities.Constants.EXTRA_DIRECTION;
import static com.example.chatgenix.utilities.Constants.EXTRA_POS_X;
import static com.example.chatgenix.utilities.Constants.EXTRA_POS_Y;
import static com.example.chatgenix.utilities.Constants.EXTRA_POS_Z;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.chatgenix.R;
import com.example.chatgenix.utilities.Constants;
import com.example.chatgenix.utilities.PreferenceManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FallDetectedActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, TextToSpeech.OnInitListener {

    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 200;
    private static final int REQUEST_CALL_PHONE = 4;
    private static final int REQUEST_SEND_SMS = 4;

    private Context mContext;
    private TextView mTextViewAlert;
    private Button mButtonNo, mButtonYes;
    private AnimationDrawable mAnimationDrawable;
    private CountDownTimer mCountDownTimer;
    private Location mLocation;
    private TextToSpeech mTextToSpeech;
    private PreferenceManager preferenceManager;
    private int mTimeLeft;
    private float mPosX, mPosY, mPosZ;
    private String mDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wakeupScreen();
        setContentView(R.layout.activity_fall_detected);
        initObjects();
        initCallbacks();
        processIntent();
        initCountDownTimer();
        startCountDown();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        startLocationUpdates();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions,
                                           @androidx.annotation.NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PHONE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAnimationDrawable != null && !mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        if (mCountDownTimer != null) {
            resetCountDown();
            startCountDown();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCountDownTimer != null) resetCountDown();
    }

    // Trigger new location updates at interval
    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        Task task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(this,new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null)
                    mLocation = location;
                Toast.makeText(getApplicationContext(), mLocation.getLatitude() + "," +
                        mLocation.getLongitude(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        String msg = "Updated Location: " +
                Double.toString(mLocation.getLatitude()) + "," +
                Double.toString(mLocation.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        LatLng latLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mTextToSpeech.setLanguage( Locale.getDefault());
            mTextToSpeech.setPitch(0.8f);
            mTextToSpeech.setSpeechRate(0.8f);
            //noinspection deprecation
            mTextToSpeech.speak("Fall Detected. Did you meet with an accident?", TextToSpeech.QUEUE_FLUSH,
                    null);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mButtonNo) {
            Intent intent = new Intent(FallDetectedActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
        } else if (view == mButtonYes) {
            sendAlert();
            sendSms();

        }
    }


    private void wakeupScreen() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                + WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                + WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }

    private void initObjects() {
        mTextViewAlert = (TextView) findViewById(R.id.txt_alert);
        mButtonNo = (Button) findViewById(R.id.btn_no);
        mButtonYes = (Button) findViewById(R.id.btn_yes);

        mContext = this;
        mAnimationDrawable = (AnimationDrawable) findViewById(
                R.id.activity_fall_detected).getBackground();
        mTextToSpeech = new TextToSpeech(mContext, (TextToSpeech.OnInitListener) this);
        preferenceManager = new PreferenceManager(mContext);
    }

    private void initCallbacks() {
        mButtonNo.setOnClickListener((View.OnClickListener) this);
        mButtonYes.setOnClickListener((View.OnClickListener) this);
    }

    private void processIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPosX = bundle.getFloat(EXTRA_POS_X);
            mPosY = bundle.getFloat(EXTRA_POS_Y);
            mPosZ = bundle.getFloat(EXTRA_POS_Z);
            mDirection = bundle.getString(EXTRA_DIRECTION);
        }
    }

    private void initCountDownTimer() {
        mCountDownTimer = new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long l) {
                mTimeLeft--;
                setTimeLeft();
            }

            @Override
            public void onFinish() {
                mTextViewAlert.setVisibility(View.GONE);
                sendAlert();
                sendSms();

            }
        };
    }

    private void startCountDown() {
        mTimeLeft = 30;
        if (mCountDownTimer != null) mCountDownTimer.start();
    }

    private void resetCountDown() {
        if (mCountDownTimer != null) mCountDownTimer.cancel();
    }

    private void setTimeLeft() {
        mTextViewAlert.setText(
                String.format(Locale.getDefault(), getString(R.string.format_alert), mTimeLeft));
    }

    private void sendAlert() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_COLLECTION_EMERGENCY_CONTACTS, preferenceManager.getId());
            jsonObject.put(KEY_POS_X, mPosX);
            jsonObject.put(KEY_POS_Y, mPosY);
            jsonObject.put(KEY_POS_Z, mPosZ);
            jsonObject.put(KEY_DIRECTION, mDirection);

            if (mLocation != null) {
                jsonObject.put(KEY_LATITUDE, mLocation.getLatitude());
                jsonObject.put(KEY_LONGITUDE, mLocation.getLongitude());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void sendSms() {
        SmsManager smsManager = SmsManager.getDefault();
        String message = preferenceManager.getString(Constants.KEY_NAME) + " may have met with an accident. Try to contact " + preferenceManager.getString(Constants.KEY_NAME) + " soon and help.";
        if (mLocation != null) {
            message += " Location: https://www.google.com/maps/search/?api=1&query="
                    + mLocation.getLatitude() + "," + mLocation.getLongitude();
        }
        smsManager.sendTextMessage(preferenceManager.getEmergencyContact1(), null, message, null,
                null);
        smsManager.sendTextMessage(preferenceManager.getEmergencyContact2(), null, message, null,
                null);
        smsManager.sendTextMessage(preferenceManager.getEmergencyContact3(), null, message, null,
                null);
        smsManager.sendTextMessage(preferenceManager.getEmergencyContact4(), null, message, null,
                null);
        smsManager.sendTextMessage(preferenceManager.getEmergencyContact5(), null, message, null,
                null);
        Intent intent = new Intent(FallDetectedActivity.this, DoneActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}