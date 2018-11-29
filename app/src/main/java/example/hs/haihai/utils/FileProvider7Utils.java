package example.hs.haihai.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

import example.hs.haihai.BuildConfig;

public class FileProvider7Utils {
    //拍照调用
    // Uri fileUri = FileProvider7.getUriForFile(this, file);
    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = getUriForFile24(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    public static Uri getUriForFile24(Context context, File file) {
        Uri fileUri = android.support.v4.content.FileProvider.getUriForFile(context,
                //context.getPackageName() + ".android7.fileProvider",
                // 这里认为用BuildConfig.APPLICATION_ID比.getPackageName()更好，因为与AndroidManifest.xml中的authorities对应
                BuildConfig.APPLICATION_ID + ".android7.fileProvider"
                , file);
        return fileUri;
    }


    /**
     * 安装apk调等用
     * FileProvider7.setIntentDataAndType(this,intent, "application/vnd.android.package-archive", file);
     *
     * @param context
     * @param intent
     * @param type    "application/vnd.android.package-archive" 这个是调用安装包，此处是这样，如果不是安装apk的，只需修改对应的type
     * @param file    安装包名称
     * @param writeAble 是否需要写权限
     */
    public static void setIntentDataAndType(Context context,
                                            Intent intent,
                                            String type,
                                            File file,boolean writeAble) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setDataAndType(getUriForFile(context, file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
    }

}
