package example.hs.baselibrary.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Huang hai-sen on 2016/12/19.
 * 设置viewpager是否可以滑动
 */

public class NoScrollViewPager extends ViewPager {
    private boolean scroll = true;
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return scroll && super.onTouchEvent(ev);//设置viewPager是否可以滑动
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return scroll && super.onInterceptTouchEvent(arg0);
    }

    public void setCanScrollable(boolean scrollable) {
        this.scroll = scrollable;
    }
}
