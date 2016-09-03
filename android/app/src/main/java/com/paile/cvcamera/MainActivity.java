package com.paile.cvcamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final String TAG = "GQ";
    static final int REQUEST_CAPTURE = 1;
    String curr_image_path;

    private static File getTmpImagePath() throws IOException {
        File saveFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String time_stamp = new SimpleDateFormat("yyyymmdd_").format(new Date());
        File file = File.createTempFile(time_stamp, ".jpg", saveFolder);

        return file;
    }

    private void scanMedia(String image_path) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(image_path);
        intent.setData(Uri.fromFile(f));
        this.sendBroadcast(intent);
        Log.d(TAG, "scan image: " + image_path);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set capture function
        findViewById(R.id.capture).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "click");

        if (v.getId() != R.id.capture) {
            Log.d(TAG, "view.id != capture");
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) == null) {
            Log.w(TAG, "no available application");
            return;
        }

        File tmp_image_file = null;
        try {
            tmp_image_file = getTmpImagePath();
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        if (tmp_image_file != null) {
            Log.d(TAG, tmp_image_file.getAbsolutePath());
            curr_image_path = tmp_image_file.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tmp_image_file));
            startActivityForResult(intent, REQUEST_CAPTURE);
            Log.d(TAG, "send intent: " + curr_image_path);
        } else {
            Log.d(TAG, "tmp_image_file null");
        }
    }

    protected void onActivityResult(int req_code, int res_code, Intent data) {
        Log.d(TAG, String.format("res_code: %d", res_code));
        if (req_code == REQUEST_CAPTURE) {
            if (res_code == RESULT_OK) {
                Log.d(TAG, "captured");
                scanMedia(curr_image_path);

                File image = new File(curr_image_path);
                if (image.exists()) {
                    final ImageView iv = (ImageView) findViewById(R.id.imageview);
                    Bitmap bitmap = null;
                    bitmap = BitmapFactory.decodeFile(curr_image_path);
                    iv.setImageBitmap(bitmap);
                    Toast.makeText(MainActivity.this, "captured", Toast.LENGTH_SHORT).show();

                    String out_image_path = CvProcess.processImage(curr_image_path);
                    Log.d(TAG, "jni result: " + out_image_path);
                    scanMedia(out_image_path);

                    bitmap = BitmapFactory.decodeFile(out_image_path);
                    iv.setImageBitmap(bitmap);
                }
            } else {
                Toast.makeText(MainActivity.this, "not captured", Toast.LENGTH_SHORT).show();
                curr_image_path = null;
            }
        }
    }
}
