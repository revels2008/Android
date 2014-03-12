/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jwetherell;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.zxing.Result;
import com.jwetherell.data.Preferences;
import com.powerlong.electric.app.R;

/**
 * Example Capture Activity.
 * 
 * @author Justin Wetherell (phishman3579@gmail.com)
 */
public class CaptureActivity extends DecoderActivity {
    private final String QRCODE = "qrcode";

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private TextView statusView = null;

    private View resultView = null;

    private boolean inScanMode = false;

    private BeepManager beepManager;

    private ImageButton mImageButton;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.capture);
        Log.v(TAG, "onCreate()");

        resultView = findViewById(R.id.result_view);
        statusView = (TextView)findViewById(R.id.status_view);
        addListenerOnButton();

        beepManager = new BeepManager(this);

        inScanMode = false;
    }

    public void addListenerOnButton() {

        mImageButton = (ImageButton)findViewById(R.id.torchOnOff);
        mImageButton.getBackground().setAlpha(0);

        mImageButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (!inScanMode)
                    return;
                Preferences.KEY_FRONT_LIGHT = Preferences.KEY_FRONT_LIGHT ? false : true;
                if (Preferences.KEY_FRONT_LIGHT) {
                    cameraManager.turnOnTorch();
                    mImageButton.setImageResource(R.drawable.bulb_on);
                    mImageButton.getBackground().setAlpha(0);
                } else {
                    cameraManager.turnOffTorch();
                    mImageButton.setImageResource(R.drawable.bulb_off);
                    mImageButton.getBackground().setAlpha(0);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.v(TAG, "onDestroy()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        beepManager.updatePrefs();
        mImageButton.setImageResource(R.drawable.bulb_off);
        mImageButton.getBackground().setAlpha(0);

        Log.v(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause()");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inScanMode) {
                // if (cameraManager != null) {
                // setResult(RESULT_CANCELED);
                // cameraManager.turnOffTorch();
                // finish();
                // } else {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        setResult(RESULT_CANCELED);
                        cameraManager.turnOffTorch();
                        finish();
                    }
                }, 1000);
                // }
            } else
                onResume();
            return true;
        }
        return true;
    }

    Timer timer = new Timer();

    @Override
    public void handleDecode(Result rawResult, Bitmap barcode) {
        drawResultPoints(barcode, rawResult);

        beepManager.playBeepSoundAndVibrate();
        handleDecodeInternally(rawResult, barcode);
    }

    protected void showScanner() {
        inScanMode = true;
        resultView.setVisibility(View.GONE);
        statusView.setText(R.string.msg_default_status);
        statusView.setVisibility(View.VISIBLE);
        viewfinderView.setVisibility(View.VISIBLE);
        mImageButton.setVisibility(View.VISIBLE);
    }

    protected void showResults() {
        inScanMode = false;
        statusView.setVisibility(View.GONE);
        viewfinderView.setVisibility(View.GONE);
        resultView.setVisibility(View.VISIBLE);
        mImageButton.setVisibility(View.GONE);
    }

    // Put up our own UI for how to handle the decodBarcodeFormated contents.
    private void handleDecodeInternally(Result rawResult, Bitmap barcode) {
        onPause();
        Intent data = this.getIntent();
        data.putExtra(QRCODE, rawResult.getText());
        Log.i("result", rawResult.getText());
        setResult(RESULT_OK, data);
        finish();
        // showResults();

        // TextView formatTextView =
        // (TextView)findViewById(R.id.format_text_view);
        // formatTextView.setText(rawResult.getBarcodeFormat().toString());
        //
        // TextView typeTextView = (TextView)findViewById(R.id.type_text_view);
        // typeTextView.setVisibility(View.GONE);
        //
        // TextView metaTextView = (TextView)findViewById(R.id.meta_text_view);
        // View metaTextViewLabel = findViewById(R.id.meta_text_view_label);
        // metaTextView.setVisibility(View.GONE);
        // metaTextViewLabel.setVisibility(View.GONE);
        //
        // TextView contentsTextView =
        // (TextView)findViewById(R.id.contents_text_view);
        // contentsTextView.setText(rawResult.getText());
        // int scaledSize = 32;
        // contentsTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, scaledSize);
    }
}
