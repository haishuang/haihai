package example.hs.haihai.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import example.hs.baselibrary.utils.ScreenUtils;


/**
 * Created by Huanghs on 2018/1/17.
 * 基础的BaseApplication
 */

public class BaseApplication extends Application {
    //public class BaseApplication extends MultiDexApplication {
    private static BaseApplication mInstance = null;
    //一个用来记录开启过的Activity
    private List<Activity> activities = new ArrayList<>();
    //是否使用调试：true-开启调试，false关闭调试
    public static boolean isDebug = true;
    private Context mContext;

    //屏幕宽
    private int screenWidth = 720;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        screenWidth = ScreenUtils.getScreenWidth(this);
    }



    public static BaseApplication getInstance() {
        return mInstance;
    }


    public Context getContext() {
        if (mContext == null) {
            mContext = this.getBaseContext();
        }
        return mContext;
    }


    /**
     * 添加一个activity到集合中
     *
     * @param activity
     */
    public void add(Activity activity) {
        activities.add(activity);
    }

    /**
     * 从集合中移除一个activity
     *
     * @param activity
     */
    public void remove(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 从集合中移除所有activity
     */
    public void removeAllActivity() {
        for (Activity atv : activities) {
            if (atv != null) {
                atv.finish();
            }
        }
        activities.clear();
    }

    /**
     * 清空关闭所有activity，并重启程序
     */
    public void ExitApp() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        activities.clear();
        // 退出程序
        System.exit(0);
    }
    //---------------------------Activty 栈管理结束-----------------------------------------------


    public int getScreenWidth() {
        return screenWidth;
    }
}