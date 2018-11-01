package example.hs.haihai.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;


/**
 * Created by Huanghs on 2018/1/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //  BaseApplication.getInstance().add(this);
        activity = this;
        super.onCreate(savedInstanceState);
        // 竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }

        init();
    }

    public void init(){
        initView();
        initEvent();
        initData();
    }

    //引入布局
    protected abstract int getLayoutId();

    //初始化控件
    protected abstract void initView();

    //初始化点击事件Event
    protected abstract void initEvent();

    //初始化数据
    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
        BaseApplication.getInstance().remove(this);
    }

    /**
     * 跳转至登录页面
     */
    public void toLogin() {
//        AccountHelper.logout();
//
//
//        Bundle bundle = new Bundle();
//        bundle.putInt("loginType", 0);
//        showActivity(LoginActivity.class, bundle);
//        toastShort("登录过期");

//        MainActivity.instance.finish();
        BaseApplication.getInstance().removeAllActivity();
    }

    /**
     * 简单Activity跳转
     *
     * @param cla
     */
    public void showActivity(Class cla) {
        showActivity(cla, null);
    }

    /**
     * 简单Activity跳转,带参数
     *
     * @param clz
     * @param bundle
     */
    public void showActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 简单Activity跳转
     *
     * @param cla
     */
    public void showActivity(Class cla, int requestCode) {
        showActivity(cla, null, requestCode);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void showActivity(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 长时间Toast
     *
     * @param str
     */
    protected void showToast(String str) {
        ToastUtils.show(str);
    }

    /**
     * 提供给子类的显示和隐藏View，避免空指针
     *
     * @param view
     * @param isShow
     */
    protected void showView(View view, boolean isShow) {
        if (view != null)
            view.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
