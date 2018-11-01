package example.hs.haihai.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Huanghs on 2018/3/9.
 */

public abstract class BasePermissionActivity extends BaseActivity {
    private RequestPermissionCallBack mRequestPermissionCallBack;
    private final int REQUEST_CODE = 100;
    private String[] permissionNames;

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
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        StringBuilder permissionName = new StringBuilder();
                        for (String s : permissionNames) {
                            permissionName = permissionName.append(s + "\r\n");
                        }

                        new AlertDialog.Builder(this)
                                .setMessage(
                                        "获取相关权限失败:\r\n\r\n" +
                                                permissionName +
                                                "\n因您选择了“不再提示”选项或者系统默认不再提示（如MIUI），需要您到设置页面手动授权，否则会导致部分功能无法正常使用。")
                                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
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
                                   RequestPermissionCallBack callback) {
        this.mRequestPermissionCallBack = callback;

        //如果所有权限都已授权，则直接返回授权成功,只要有一项未授权，则发起权限请求
        boolean isAllGranted = true;
        permissionNames=permissionsName;
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
