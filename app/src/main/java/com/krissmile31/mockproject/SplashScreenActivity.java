package com.krissmile31.mockproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class SplashScreenActivity extends AppCompatActivity {
    private final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
    }

    private void checkPermission() {
        if (checkVersion()) {
            if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            } else {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            }
        }
    }

    private boolean checkVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE
        }, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.e(TAG, "onRequestPermissionsResult: ");

        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(SplashScreenActivity.this,
                            MainActivity.class));
                    finish();
                } else {
                    if (DontAskAgain()) {
                        ShowNotAskDialog();
                    } else {
                        showAskDialog();
                    }
                }
                return;
        }
    }

    private boolean DontAskAgain() {
        if (checkVersion()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, WRITE_EXTERNAL_STORAGE)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void ShowNotAskDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("The app does not have critical permissions needed to run. " +
                        "Please check your permission settings.")
                .setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivity(intent);
                    }
                })
                .show();
    }

    private void showAskDialog() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_baseline_folder_open_24)
                .setTitle("Storage")
                .setMessage("The app does not have critical permissions needed to run. " +
                        "To work properly, please grant permissions when asked.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermission();
                    }
                }).show();
    }
}