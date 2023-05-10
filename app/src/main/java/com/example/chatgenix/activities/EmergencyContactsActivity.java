package com.example.chatgenix.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatgenix.R;
import com.example.chatgenix.databinding.ActivityEmergencyContactsBinding;
import com.example.chatgenix.utilities.PreferenceManager;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EmergencyContactsActivity extends AppCompatActivity
        implements View.OnClickListener {

    private Context mContext;
    private EditText mEditTextEmergencyContact1, mEditTextEmergencyContact2,
            mEditTextEmergencyContact3, mEditTextEmergencyContact4, mEditTextEmergencyContact5;
    private Button mButtonUpdate;
    private ActivityEmergencyContactsBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmergencyContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        initObjects();
        initCallbacks();
        populateData();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
    public void onClick(View view) {
        if (view == mButtonUpdate) {
            processProfile();
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void populateData() {
        mEditTextEmergencyContact1.setText(preferenceManager.getEmergencyContact1());
        mEditTextEmergencyContact2.setText(preferenceManager.getEmergencyContact2());
        mEditTextEmergencyContact3.setText(preferenceManager.getEmergencyContact3());
        mEditTextEmergencyContact4.setText(preferenceManager.getEmergencyContact4());
        mEditTextEmergencyContact5.setText(preferenceManager.getEmergencyContact5());
    }

    private void processProfile() {
        String emergencyContact1 = mEditTextEmergencyContact1.getText().toString().trim();
        String emergencyContact2 = mEditTextEmergencyContact2.getText().toString().trim();
        String emergencyContact3 = mEditTextEmergencyContact3.getText().toString().trim();
        String emergencyContact4 = mEditTextEmergencyContact4.getText().toString().trim();
        String emergencyContact5 = mEditTextEmergencyContact5.getText().toString().trim();

        if (isValidContactsDetails()) {
            loading(true);
            showToast("Updated");
            preferenceManager.setEmergencyContact1(emergencyContact1);
            preferenceManager.setEmergencyContact2(emergencyContact2);
            preferenceManager.setEmergencyContact3(emergencyContact3);
            preferenceManager.setEmergencyContact4(emergencyContact4);
            preferenceManager.setEmergencyContact5(emergencyContact5);
            finish();
        }
    }

    private void initObjects() {
        mEditTextEmergencyContact1 = (EditText) findViewById(
                R.id.inputPhone1);
        mEditTextEmergencyContact2 = (EditText) findViewById(
                R.id.inputPhone2);
        mEditTextEmergencyContact3 = (EditText) findViewById(
                R.id.inputPhone3);
        mEditTextEmergencyContact4 = (EditText) findViewById(
                R.id.inputPhone4);
        mEditTextEmergencyContact5 = (EditText) findViewById(
                R.id.inputPhone5);
        mButtonUpdate = (Button) findViewById(R.id.buttonUpdate);

        mContext = this;
        preferenceManager = new PreferenceManager(mContext);
    }

    private void initCallbacks() {
        mButtonUpdate.setOnClickListener(this);
    }

    private boolean isValidContactsDetails() {
        if (!Patterns.PHONE.matcher(mEditTextEmergencyContact1.getText().toString()).matches()) {
            showToast("Enter valid phone no.");
            return false;
        } else if (!Patterns.PHONE.matcher(mEditTextEmergencyContact2.getText().toString()).matches()) {
            showToast("Enter valid phone no.");
            return false;
        } else if (!Patterns.PHONE.matcher(mEditTextEmergencyContact3.getText().toString()).matches()) {
            showToast("Enter valid phone no.");
            return false;
        } else if (!Patterns.PHONE.matcher(mEditTextEmergencyContact4.getText().toString()).matches()) {
            showToast("Enter valid phone no.");
            return false;
        } else if (!Patterns.PHONE.matcher(mEditTextEmergencyContact5.getText().toString()).matches()) {
            showToast("Enter valid phone no.");
            return false;
        } else {
            return true;
        }
    }

        private void loading(Boolean isLoading) {
            if(isLoading) {
                binding.buttonUpdate.setVisibility(View.INVISIBLE);
                binding.progressBar.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.buttonUpdate.setVisibility(View.VISIBLE);
            }
        }
}