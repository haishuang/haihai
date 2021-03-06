package example.hs.haihai.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * 集合了懒加载和baseFragment
 */
public abstract class BaseLazyFragment extends Fragment {
    //是否已经懒加载
    private boolean isLazyLoad = false;
    //根布局
    private View mRootView;
    //Activity对象
    public Activity mActivity;

    /**
     * 获得全局的，防止使用getActivity()为空
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    public Activity getSupportActivity() {
        return mActivity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (mRootView == null && getLayoutId() > 0) {
            mRootView = inflater.inflate(getLayoutId(), null);
        }

        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }

        return mRootView;
    }

    @Override
    public View getView() {
        return mRootView;
    }

    protected boolean isLazyLoad() {
        return isLazyLoad;
    }

    /**
     * 是否在Fragment使用沉浸式
     */
    protected boolean isStatusBarEnabled() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
        mRootView = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isVisibleToUser && !isLazyLoad() && getView() != null) {
            isLazyLoad = true;
            init();
        }
    }

    private boolean isVisibleToUser;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && !isLazyLoad() && getView() != null) {
            isLazyLoad = true;
            init();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public boolean isVisibleToUser() {
        return isVisibleToUser;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //解决java.lang.IllegalStateException: Activity has been destroyed 的错误
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void init() {
        initView();
        initData();
    }

    //引入布局
    protected abstract int getLayoutId();


    //初始化控件
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();



    /**
     * 跳转到其他Activity
     *
     * @param cls          目标Activity的Class
     */
    public void startActivity(Class<?> cls) {
        startActivity(new Intent(getContext(), cls));
    }

    /**
     * Fragment返回键被按下时回调
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //默认不拦截按键事件，传递给Activity
        return false;
    }
}
