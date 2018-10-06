package com.wings.wings;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.BarcodeDetector;

public class BarCodeActivity extends AppCompatActivity {
    private static final int QR_RENAME = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == QR_RENAME){
            if(resultCode == Activity.RESULT_OK){
                if(data != null){
                    Toast.makeText(BarCodeActivity.this, data.toString(), Toast.LENGTH_SHORT);
                    //data.toString();

                }
            }
        }
    }
}
