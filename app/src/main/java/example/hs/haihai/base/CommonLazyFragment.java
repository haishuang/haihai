package example.hs.haihai.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.hs.baselibrary.utils.immersionbar.ImmersionBar;

public abstract class CommonLazyFragment extends BaseLazyFragment {
    private ImmersionBar mImmersionBar;//状态栏沉浸

    private Unbinder mButterKnife;// View注解

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //初始化沉浸式状态栏
        if (isVisibleToUser() && isStatusBarEnabled() && isLazyLoad()) {
            statusBarConfig().init();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mButterKnife = ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 是否在Fragment使用沉浸式
     */
    public boolean isStatusBarEnabled() {
        return false;
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    protected ImmersionBar getStatusBarConfig() {
        return mImmersionBar;
    }

    /**
     * 初始化沉浸式
     */
    private ImmersionBar statusBarConfig() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(statusBarDarkFont())    //默认状态栏字体颜色为黑色
                .keyboardEnable(true);  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        return mImmersionBar;
    }

    /**
     * 获取状态栏字体颜色
     */
    protected boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mButterKnife.unbind();
        if (mImmersionBar != null) mImmersionBar.destroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isStatusBarEnabled() && isLazyLoad()) {
            // 重新初始化状态栏
            statusBarConfig().init();
        }
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
