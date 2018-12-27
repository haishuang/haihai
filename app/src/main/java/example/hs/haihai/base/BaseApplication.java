package example.hs.haihai.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

import example.hs.baselibrary.utils.ScreenUtils;
import example.hs.haihai.R;


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

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

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
