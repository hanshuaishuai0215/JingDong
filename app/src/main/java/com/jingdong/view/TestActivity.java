package com.jingdong.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jingdong.R;
import com.jingdong.utils.LoggingInterceptor;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * 时间:2017/12/3 21:19
 * 作者:韩帅帅
 * 详情:
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 请求
     */
    private Button mBt;
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        mBt = (Button) findViewById(R.id.bt);
        mBt.setOnClickListener(this);
        mIv = (ImageView) findViewById(R.id.iv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt:
                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(new LoggingInterceptor())
//                        .addNetworkInterceptor(new LoggingInterceptor())
                        .build();

                Request request = new Request.Builder()
                        .url("http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        InputStream inputStream = response.body().byteStream();

                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mIv.setImageBitmap(bitmap);
                            }
                        });

                    }
                });


                break;
        }
    }
}
