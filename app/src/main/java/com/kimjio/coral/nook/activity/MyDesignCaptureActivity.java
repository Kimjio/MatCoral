package com.kimjio.coral.nook.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;

import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.kimjio.coral.activity.BaseActivity;
import com.kimjio.coral.data.nook.MyDesignQR;
import com.kimjio.coral.databinding.MyDesignCaptureActivityBinding;
import com.kimjio.coral.nook.util.MyDesignParser;

public class MyDesignCaptureActivity extends BaseActivity<MyDesignCaptureActivityBinding> {

    public static final String RESULT_MULTIPLE = "SCAN_RESULT_MULTIPLE";
    public static final String RESULT_COLOR_DATA_BACK = "SCAN_RESULT_COLOR_DATA_BACK";
    public static final String RESULT_COLOR_DATA_LEFT = "SCAN_RESULT_COLOR_DATA_LEFT";
    public static final String RESULT_COLOR_DATA_RIGHT = "SCAN_RESULT_COLOR_DATA_RIGHT";

    private CaptureManager capture;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            MyDesignQR qr = MyDesignParser.analyzeQrBinary(result.getRawBytes());
            if (qr.getDesignType() == MyDesignQR.Type.PRO && qr.getUsage() != 6 && qr.getUsage() != 7) {
                if (qr.getSheetIndex() == 0) {
                    binding.barcodeScanner.setStatusText(Integer.toString(extraProColorDataIndex));
                    binding.barcodeScanner.decodeContinuous(result1 -> {
                        if (extraProColorDataIndex < 3) {
                            binding.barcodeScanner.setStatusText(Integer.toString(extraProColorDataIndex));
                            MyDesignQR qrPro = MyDesignParser.analyzeQrBinary(result1.getRawBytes());
                            if (qrPro.getSheetIndex() == extraProColorDataIndex + 1) {
                                extraProColorData[extraProColorDataIndex++] = qrPro.getColorData();
                            }
                            if (extraProColorDataIndex == 3) {
                                binding.barcodeScanner.pause();
                                binding.barcodeScanner.post(() -> {
                                    Intent intent = CaptureManager.resultIntent(result, null);
                                    intent.putExtra(RESULT_MULTIPLE, true);
                                    intent.putExtra(RESULT_COLOR_DATA_BACK, extraProColorData[0]);
                                    intent.putExtra(RESULT_COLOR_DATA_LEFT, extraProColorData[1]);
                                    intent.putExtra(RESULT_COLOR_DATA_RIGHT, extraProColorData[2]);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                });
                            }
                        }
                    });
                }

            } else if (qr.getDesignType() == MyDesignQR.Type.NORMAL) {
                binding.barcodeScanner.pause();
                binding.barcodeScanner.post(() -> {
                    Intent intent = CaptureManager.resultIntent(result, null);
                    intent.putExtra(RESULT_MULTIPLE, false);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                });
            }
        }
    };

    private int extraProColorDataIndex = 0;
    private byte[][] extraProColorData = new byte[3][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        capture = new CaptureManager(this, binding.barcodeScanner);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        binding.barcodeScanner.decodeContinuous(callback);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return binding.barcodeScanner.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    public static void start(Activity activity) {
        new IntentIntegrator(activity)
                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                .setOrientationLocked(false)
                .setCaptureActivity(MyDesignCaptureActivity.class)
                .initiateScan();
    }
}
