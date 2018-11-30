package example.hs.haihai.base2;

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


import example.hs.baselibrary.widget.BToast;
import example.hs.haihai.base.BaseApplication;


/**
 * Created by Huanghs on 2018/1/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Activity activity;
    protected boolean isRegisterEB = false;

    public abstract boolean isRegisterEB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BaseApplication.getInstance().add(this);
        activity = this;
        super.onCreate(savedInstanceState);
        // 竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
        BaseApplication.getInstance().remove(this);
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
    protected void toastLong(String str) {
        //ToastUtils.showLong(getApplicationContext(), str);
        BToast.showLong(getApplicationContext(), str);
    }

    /**
     * 短时间Toast
     *
     * @param str
     */
    protected void toastShort(String str) {
        // ToastUtils.showShort(getApplicationContext(), str);
        BToast.showShort(getApplicationContext(), str);
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

    //添加fragment
    protected void addFragment(Fragment fragment, int resId) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(resId, fragment)
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    //移除fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

}
