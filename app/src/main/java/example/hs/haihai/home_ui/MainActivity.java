package example.hs.haihai.home_ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.hs.baselibrary.utils.immersionbar.ImmersionBar;
import example.hs.haihai.R;
import example.hs.haihai.adapter.HomeViewPagerAdapter;
import example.hs.baselibrary.widget.NoScrollViewPager;

/**
 * 首页，采用BottomNavigationView方式
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.vp)
    NoScrollViewPager vp;
    @BindView(R.id.btn_nav_view)
    BottomNavigationView btnNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            // 竖屏锁定
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 限制页面数量
        vp.setOffscreenPageLimit(4);
        vp.addOnPageChangeListener(this);

        vp.setCanScrollable(false);

        // 不使用图标默认变色
        btnNavView.setItemIconTintList(null);
        btnNavView.setOnNavigationItemSelectedListener(this);

        initData();
    }

    private void initData() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)    //默认状态栏字体颜色为黑色
                .keyboardEnable(true);
        vp.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
    }

    //#1addOnPageChangeListener
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    //#2addOnPageChangeListener
    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0:
                btnNavView.setSelectedItemId(R.id.menu_home);
                break;
            case 2:
                btnNavView.setSelectedItemId(R.id.home_c);
                break;
            case 1:
                btnNavView.setSelectedItemId(R.id.home_b);
                break;
            case 3:
                btnNavView.setSelectedItemId(R.id.home_mine);
                break;
            default:
                break;
        }
    }

    //#3addOnPageChangeListener
    @Override
    public void onPageScrollStateChanged(int i) {

    }

    //#setOnNavigationItemSelectedListener
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_home:
                vp.setCurrentItem(0);
                return true;
            case R.id.home_b:
                vp.setCurrentItem(1);
                return true;
            case R.id.home_c:
                vp.setCurrentItem(2);
                return true;
            case R.id.home_mine:
                vp.setCurrentItem(3);
                return true;
        }
        return false;
    }
}
