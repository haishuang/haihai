package example.hs.haihai.home_ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.hs.baselibrary.widget.CountDownProgressView;
import example.hs.haihai.R;
import example.hs.haihai.base.CommonActivity;

public class SplashActivity extends CommonActivity {

    @BindView(R.id.progressView)
    CountDownProgressView progressView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        //防止重复启动
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        }

        //倒计时开始
        progressView.start();
    }

    @Override
    protected void initEvent() {

        progressView.setProgressListener(new CountDownProgressView.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                if (progress == 0) {
                    showActivity(MainActivity.class);//去主页
                    finish();
                }
            }
        });
    }

    @Override
    protected void initData() {
        //showActivity(MainActivity.class);//去主页
        //showActivity(Main2Activity.class);//去主页
    }


    @OnClick({R.id.progressView, R.id.tv_01, R.id.tv_02})
    public void onViewClicked(View view) {
        //跳转前先停止倒计时
        progressView.stop();
        switch (view.getId()) {
            case R.id.progressView:
                showActivity(MainActivity.class);//去主页
                break;
            case R.id.tv_01:
                showActivity(MainActivity.class);//去主页
                break;
            case R.id.tv_02:
                showActivity(Main2Activity.class);//去主页
                break;
        }
        finish();
    }
}
