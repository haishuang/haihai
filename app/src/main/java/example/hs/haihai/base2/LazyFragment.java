package example.hs.haihai.base2;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.system.ErrnoException;
import android.view.View;

import example.hs.haihai.base.BaseApplication;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class LazyFragment extends BaseFragment {

    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible;

    /**
     * onCreateView()里返回的view，修饰为protected,所以子类继承该类时，在onCreateView里必须对该变量进行初始化
     */
    protected View rootView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }

    /**************************************************************
     *  自定义的回调方法，子类可根据需求重写
     *************************************************************/

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     */
    /*
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected abstract void onFragmentVisibleChange(boolean isVisible);


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
        intent.setClass(getContext(), clz);
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
        intent.setClass(getContext(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    private BasePermissionActivity.RequestPermissionCallBack mRequestPermissionCallBack;
    private final int REQUEST_CODE = 100;

    /**
     * 权限请求结果回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;

        if (requestCode == REQUEST_CODE) {
            for (int i = 0; i < grantResults.length; ++i) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    hasAllGranted = false;
                    //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                    // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[i])) {
                        StringBuilder permissionName = new StringBuilder();
                        for (String s : permissions) {
                            permissionName = permissionName.append(s + "\r\n");
                        }

                        new AlertDialog.Builder(getContext())
                                .setMessage("【用户选择了不在提示按钮，或者系统默认不在提示（如MIUI）。" +
                                        "引导用户到应用设置页去手动授权,注意提示用户具体需要哪些权限】\r\n" +
                                        "获取相关权限失败:\r\n" +
                                        permissionName +
                                        "将导致部分功能无法正常使用，需要到设置页面手动授权")
                                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", BaseApplication.getInstance().getContext().getPackageName(), null);
                                        intent.setData(uri);
                                        try {
                                            startActivity(intent);
                                        } catch (Exception e) {

                                        }

                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mRequestPermissionCallBack.denied();
                                    }
                                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                mRequestPermissionCallBack.denied();
                            }
                        }).show();
                    } else {
                        //用户拒绝权限请求，但未选中“不再提示”选项
                        mRequestPermissionCallBack.denied();
                    }
                }
            }
        }
        if (hasAllGranted) {
            mRequestPermissionCallBack.granted();
        }
    }


    /**
     * 发起权限请求
     *
     * @param context
     * @param permissions
     * @param permissionsName
     * @param callback
     */
    public void requestPermissions(final Context context, final String[] permissions, final String[] permissionsName,
                                   BasePermissionActivity.RequestPermissionCallBack callback) {
        this.mRequestPermissionCallBack = callback;

        //如果所有权限都已授权，则直接返回授权成功,只要有一项未授权，则发起权限请求
        boolean isAllGranted = true;
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                isAllGranted = false;

                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                    StringBuilder permissionNames = new StringBuilder();
                    for (String s : permissionsName) {
                        permissionNames = permissionNames.append(s + "\r\n");
                    }
                    //【用户曾经拒绝过你的请求，所以这次发起请求时解释一下】
                    new AlertDialog.Builder(context)
                            .setMessage("您曾经拒绝了权限申请\r\n" +
                                    "请赋予以下权限：\r\n" +
                                    permissionNames +
                                    " 请允许，否则将影响部分功能的正常使用。")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(((Activity) context), permissions, REQUEST_CODE);
                                }
                            }).show();
                } else {
                    ActivityCompat.requestPermissions(((Activity) context), permissions, REQUEST_CODE);
                }
                break;
            }
        }
        if (isAllGranted) {
            mRequestPermissionCallBack.granted();
            return;
        }
    }

    /**
     * 权限请求结果回调接口
     */
    public interface RequestPermissionCallBack {
        /**
         * 同意授权
         */
        void granted();

        /**
         * 取消授权
         */
        void denied();
    }
}
