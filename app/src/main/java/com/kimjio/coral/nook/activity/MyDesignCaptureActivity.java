package com.kimjio.coral.nook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.kimjio.coral.R;
import com.kimjio.coral.data.nook.MyDesignQR;
import com.kimjio.coral.nook.util.MyDesignParser;

import org.jetbrains.annotations.NotNull;

public class MyDesignCaptureActivity extends AppCompatActivity {

    public static final String RESULT_MULTIPLE = "SCAN_RESULT_MULTIPLE";
    public static final String RESULT_BYTES_BACK = "SCAN_RESULT_BYTES_BACK";
    public static final String RESULT_BYTES_LEFT = "SCAN_RESULT_BYTES_LEFT";
    public static final String RESULT_BYTES_RIGHT = "SCAN_RESULT_BYTES_RIGHT";

    public static final String RESULT_USAGE_2 = "SCAN_RESULT_USAGE_2";
    public static final String RESULT_USAGE_3 = "SCAN_RESULT_USAGE_3";
    public static final String RESULT_USAGE_4 = "SCAN_RESULT_USAGE_4";

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            MyDesignQR qr = MyDesignParser.analyzeQrBinary(result.getRawBytes());
            if (qr.getDesignType() == MyDesignQR.Type.PRO && qr.getUsage() != 6 && qr.getUsage() != 7) {
                if (qr.getSheetIndex() == 0) {
                    proQRUsage[proQRIndex] = qr.getUsage();
                    proQRBytes[proQRIndex++] = result.getRawBytes();
                    barcodeScannerView.setStatusText(Integer.toString(proQRIndex));
                    barcodeScannerView.decodeContinuous(result1 -> {
                        if (proQRIndex < 4) {
                            barcodeScannerView.setStatusText(Integer.toString(proQRIndex));
                            MyDesignQR qr1 = MyDesignParser.analyzeQrBinary(result1.getRawBytes());
                            if (qr1.getSheetIndex() == proQRIndex) {
                                proQRUsage[proQRIndex] = qr1.getUsage();
                                proQRBytes[proQRIndex++] = result1.getRawBytes();
                            }
                            if (proQRIndex == 4) {
                                barcodeScannerView.pause();
                                barcodeScannerView.post(() -> {
                                    Intent intent = CaptureManager.resultIntent(result, null);
                                    intent.putExtra(RESULT_MULTIPLE, true);
                                    intent.putExtra(RESULT_BYTES_BACK, proQRBytes[1]);
                                    intent.putExtra(RESULT_BYTES_LEFT, proQRBytes[2]);
                                    intent.putExtra(RESULT_BYTES_RIGHT, proQRBytes[3]);
                                    /*intent.putExtra(RESULT_USAGE_2, proQRUsage[1]);
                                    intent.putExtra(RESULT_USAGE_3, proQRUsage[2]);
                                    intent.putExtra(RESULT_USAGE_4, proQRUsage[3]);*/
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                });
                            }
                        }
                    });
                }

            } else if (qr.getDesignType() == MyDesignQR.Type.NORMAL) {
                barcodeScannerView.pause();
                barcodeScannerView.post(() -> {
                    Intent intent = CaptureManager.resultIntent(result, null);
                    intent.putExtra(RESULT_MULTIPLE, false);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                });
            }
        }
    };

    private int proQRIndex = 0;
    private byte[][] proQRBytes = new byte[4][];
    private int[] proQRUsage = new int[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barcodeScannerView = initializeContent();

        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        barcodeScannerView.decodeContinuous(callback);
    }

    /**
     * Override to use a different layout.
     *
     * @return the DecoratedBarcodeView
     */
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.zxing_capture);
        return (DecoratedBarcodeView) findViewById(R.id.zxing_barcode_scanner);
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
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
