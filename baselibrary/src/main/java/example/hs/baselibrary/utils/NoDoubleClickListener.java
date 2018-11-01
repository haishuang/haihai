package example.hs.baselibrary.utils;

import android.view.View;

import java.util.Calendar;

/**
 * Created by Huanghs on 2017/5/16.
 */

public abstract class NoDoubleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 500;
    private long lastClickTime = 0;

    public abstract void onNoDoubleClick(View view);
    @Override
    public void onClick(View v) {

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
            //LogUtils.e("--------------------------------------------->"+System.currentTimeMillis());
        }
    }
}
