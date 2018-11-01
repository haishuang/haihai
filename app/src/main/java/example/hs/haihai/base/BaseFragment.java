package example.hs.haihai.base;

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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;



/**
 * Created by Administrator on 2018/3/29.
 */

public class BaseFragment extends Fragment {
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

                        new AlertDialog.Builder(getActivity())
                                .setMessage("【用户选择了不在提示按钮，或者系统默认不在提示（如MIUI）。" +
                                        "引导用户到应用设置页去手动授权,注意提示用户具体需要哪些权限】\r\n" +
                                        "获取相关权限失败:\r\n" +
                                        permissionName +
                                        "将导致部分功能无法正常使用，需要到设置页面手动授权")
                                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
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
//
//        MainActivity.instance.finish();
//        BaseApplication.getInstance().removeAllActivity();
    }
    /**
     * 简单Activity跳转
     *
     * @param cla
     */
    public void showActivity(Class cla) {
        showActivity(cla,null);
    }

    /**
     * 简单Activity跳转,带参数
     * @param clz
     * @param bundle
     */
    public void showActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
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
        showActivity(cla,null,requestCode);
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
        intent.setClass(getActivity(), cls);
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
//        ToastUtils.showLong(getContext().getApplicationContext(), str);
    }

    /**
     * 短时间Toast
     *
     * @param str
     */
    protected void toastShort(String str) {
//        ToastUtils.showShort(getContext().getApplicationContext(), str);
    }

    protected void showShortNet(String str){
//        BToast.showShortNet(getContext().getApplicationContext(), TextUtils.isEmpty(str)?"网络不给力":str);
    }

    protected void showShortTrue(String str){
//        BToast.showShortTrue(getContext().getApplicationContext(),str);
    }

    protected void showShortError(String str){
//        BToast.showShortError(getContext().getApplicationContext(),TextUtils.isEmpty(str)?"系统繁忙":str);
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
