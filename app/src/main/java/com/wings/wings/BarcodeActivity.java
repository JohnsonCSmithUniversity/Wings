package com.wings.wings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.Surface;
import android.widget.Toast;

import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class BarcodeActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG = 8888;
    private static final int RESULT_TAKE_IMG = 9999;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = this.getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

                FirebaseVisionBarcodeDetectorOptions options =
                        new FirebaseVisionBarcodeDetectorOptions.Builder()
                                .setBarcodeFormats(
                                        FirebaseVisionBarcode.FORMAT_QR_CODE,
                                        FirebaseVisionBarcode.FORMAT_AZTEC,
                                        FirebaseVisionBarcode.FORMAT_UPC_A,
                                        FirebaseVisionBarcode.FORMAT_UPC_E)
                                .build();
                FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, RESULT_TAKE_IMG);


    }
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    /**
     * Get the angle by which an image must be rotated given the device's current
     * orientation.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, Context context)
            throws CameraAccessException {
        // Get the device's current rotation relative to its "native" orientation.
        // Then, from the ORIENTATIONS table, look up the angle the image must be
        // rotated to compensate for the device's rotation.
        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        // On most devices, the sensor orientation is 90 degrees, but for some
        // devices it is 270 degrees. For devices with a sensor orientation of
        // 270, rotate the image an additional 180 ((270 + 270) % 360) degrees.
        CameraManager cameraManager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);
        rotationCompensation = (rotationCompensation + sensorOrientation + 270) % 360;

        // Return the corresponding FirebaseVisionImageMetadata rotation value.
        int result;
        switch (rotationCompensation) {
            case 0:
                result = FirebaseVisionImageMetadata.ROTATION_0;
                break;
            case 90:
                result = FirebaseVisionImageMetadata.ROTATION_90;
                break;
            case 180:
                result = FirebaseVisionImageMetadata.ROTATION_180;
                break;
            case 270:
                result = FirebaseVisionImageMetadata.ROTATION_270;
                break;
            default:
                result = FirebaseVisionImageMetadata.ROTATION_0;
               // Log.e(TAG, "Bad rotation value: " + rotationCompensation);
        }
        return result;
    }
}
