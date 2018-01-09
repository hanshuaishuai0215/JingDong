package com.jingdong.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jingdong.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class WelcomeFistActivity extends AppCompatActivity implements View.OnClickListener{
    /**
     * 倒计时:5秒
     */
    private TextView mTvTime;
    /**
     * 跳过
     */
    private TextView mTvSkip;
    private boolean isFirstIn;
    private CompositeDisposable mDisposables = new CompositeDisposable();
    private Subscription subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_fist);
        initView();
        SharedPreferences preferences = getSharedPreferences("first_open",MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        if (isFirstIn) {
            //第一次进入时先把first_open置为false以便后来进入时进行判断，除此之外，还可以写入第一次进入时苏要执行的动作
            preferences = getSharedPreferences("first_open", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstIn", false);
            editor.commit();
            countDown();
        } else {
            //不是第一次进入时所要做的动作
            Intent intent = new Intent(WelcomeFistActivity.this, WelcomeGuideActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 使用RxJava实现倒计时
     */
    private void countDown() {
        final long count = 5;
        //计时次数
        subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .take(6)//计时次数
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long integer) {
                        return count - integer;
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onCompleted() {
                        mTvTime.setText("倒计时:0秒");
                        Intent intent = new Intent(WelcomeFistActivity.this, WelcomeGuideActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        mTvTime.setText("倒计时:" + aLong + "秒");
                    }
                });
    }

    private void initView() {
        mTvTime = (TextView) findViewById(R.id.tvTime);
        mTvSkip = (TextView) findViewById(R.id.tvSkip);
        mTvSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSkip:
                Intent intent = new Intent(WelcomeFistActivity.this, WelcomeGuideActivity.class);
                startActivity(intent);
                if (subscribe != null){
                    subscribe.unsubscribe();
                }
                break;
        }
    }
}
