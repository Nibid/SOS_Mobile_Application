package com.example.chatgenix.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatgenix.databinding.ActivityProfileBinding;
import com.example.chatgenix.utilities.Constants;
import com.example.chatgenix.utilities.PreferenceManager;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());
        setListeners();
    }

        private void setListeners() {
            binding.textName.setText(preferenceManager.getString(Constants.KEY_NAME));
            binding.textEmail.setText(preferenceManager.getString(Constants.KEY_EMAIL));
            binding.textPhone.setText(preferenceManager.getString(Constants.KEY_PHONE));
            byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            binding.imageProfile.setImageBitmap(bitmap);
        }}