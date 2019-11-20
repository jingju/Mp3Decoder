package com.jingju.ffmpegdecoder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jingju.ffmpegdecoder.utils.Constant;
import com.jingju.ffmpegdecoder.utils.permission.ActivityPermissionDispatcher;
import com.jingju.ffmpegdecoder.utils.permission.PermissionCallback;
public class MainActivity extends AppCompatActivity implements PermissionCallback {


    private static final String TAG="Mp3Decoder";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    /** 原始的文件路径 **/
    private static String mp3FilePath = "/storage/emulated/0/jingju/mp3decoder/131.mp3";
    /** 解码后的PCM文件路径 **/
    private static String pcmFilePath = "/storage/emulated/0/jingju/mp3decoder/131.pcm";
    private ActivityPermissionDispatcher dispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: 2019-11-18 请求权限
        dispatcher = ActivityPermissionDispatcher.getInstance();
        dispatcher.setPermissionCallback(MainActivity.this,MainActivity.this);
        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019-11-20 解码
                dispatcher.checkedWithStorage(MainActivity.this);
            }
        });

    }

    private void decodeMp3() {
        long startTimeMills = System.currentTimeMillis();
        Mp3Decoder decoder = new Mp3Decoder();
        int ret = decoder.init(mp3FilePath, pcmFilePath);
        if(ret >= 0) {
            decoder.decode();
            decoder.destroy();
        } else {
            Log.i(TAG, "Decoder Initialized Failed...");
        }
        int wasteTimeMills = (int)(System.currentTimeMillis() - startTimeMills);
        Log.i(TAG, "Decode Mp3 Waste TimeMills : " + wasteTimeMills + "ms");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void onPermission(int request, int state) {
        if(request==Constant.PERMISSION_STORAGE_REQUEST_CODE){
            switch (state){
                case PermissionCallback.SUCCESS:
                    decodeMp3();
                    break;

            }
        }
    }
}
