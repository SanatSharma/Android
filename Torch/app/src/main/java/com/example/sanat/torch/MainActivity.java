package com.example.sanat.torch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.security.Policy;

public class MainActivity extends AppCompatActivity {

    ToggleButton toggleButton;
    TextView textView;

    private CameraManager c;
    private Camera camera = null;
    private boolean isFlashOpen;
    private boolean flashSupported;
    Camera.Parameters parameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = (ToggleButton) findViewById(R.id.toggle_button);
        textView = (TextView) findViewById(R.id.text);

        flashSupported = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if(!flashSupported){
            //phone doesn't support flash
            showAlertButton();
            return;
        }

        startCamera();

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                if(isFlashOpen) {
                    textView.setText(getResources().getString(R.string.text_off));
                    turnOffFlash();
                }else {
                    textView.setText(getResources().getString(R.string.text_on));
                    turnOnFlash();
                }
            }
        });

    }

    private void turnOnFlash() {
        if (!isFlashOpen) {
            if (camera == null || parameters == null) {
                return;
            }
            parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
            isFlashOpen = true;
        }
    }

    private void turnOffFlash() {
        if (isFlashOpen) {
            if (camera == null || parameters == null) {
                return;
            }
            parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.startPreview();
            isFlashOpen = false;
        }
    }


    private void startCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                parameters = camera.getParameters();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Unable to open camera", Toast.LENGTH_SHORT).show();
                Log.e("Unable to open camera", e.getMessage());
            }
        }
    }

    private void showAlertButton() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        alertDialog.setTitle("Error Message");
        alertDialog.setMessage("Flash capability not supported");
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        turnOffFlash();

        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(flashSupported)
            if(isFlashOpen)
                turnOnFlash();
            else
                turnOffFlash();
    }
}
