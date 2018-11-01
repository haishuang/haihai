package example.hs.haihai.home_ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.hs.baselibrary.utils.immersionbar.ImmersionBar;
import example.hs.haihai.R;
import example.hs.haihai.adapter.HomeViewPagerAdapter;
import example.hs.baselibrary.widget.NoScrollViewPager;

public class Main2Activity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.vp)
    NoScrollViewPager vp;
    @BindView(R.id.rl_home)
    RelativeLayout rlHome;
    @BindView(R.id.rl_b)
    RelativeLayout rlB;
    @BindView(R.id.rl_c)
    RelativeLayout rlC;
    @BindView(R.id.rl_mine)
    RelativeLayout rlMine;

    @BindView(R.id.iv_tab_home)
    ImageView ivTabHome;
    @BindView(R.id.tv_tab_home)
    TextView tvTabHome;
    @BindView(R.id.iv_tab_b)
    ImageView ivTabB;

    @BindView(R.id.iv_tab_msg)
    ImageView ivTabMsg;
    @BindView(R.id.tv_tab_msg)
    TextView tvTabMsg;
    @BindView(R.id.tv_tab_msg_count)
    TextView tvTabMsgCount;
    @BindView(R.id.iv_main_mine)
    ImageView ivMainMine;
    @BindView(R.id.tv_tab_mine)
    TextView tvTabMine;
    @BindView(R.id.tv_tab_b_count)
    TextView tvTabBCount;
    @BindView(R.id.tv_tab_b)
    TextView tvTabB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            // 竖屏锁定
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        // 限制页面数量
        vp.setOffscreenPageLimit(4);
        vp.addOnPageChangeListener(this);

        vp.setCanScrollable(false);

        vp.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));

        //默认第一个页面
        vp.setCurrentItem(0);
        selectHome();

        ImmersionBar.with(this)
                .statusBarDarkFont(true)    //默认状态栏字体颜色为黑色
                .keyboardEnable(true);
    }

    @OnClick({R.id.rl_home, R.id.rl_b,R.id.rl_mine, R.id.rl_c})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_home:
                selectHome();
                break;
            case R.id.rl_b:
                selectB();
                break;
            case R.id.rl_c:
                selectC();
                break;
            case R.id.rl_mine:
                selectMine();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0:
                vp.setCurrentItem(0);
                break;
            case 1:
                vp.setCurrentItem(1);
                break;
            case 2:
                vp.setCurrentItem(2);
                break;
            case 3:
                vp.setCurrentItem(3);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void selectHome() {
        vp.setCurrentItem(0);
        ivTabHome.setEnabled(false);
        tvTabHome.setEnabled(false);
        ivTabB.setEnabled(true);
        tvTabB.setEnabled(true);
        ivTabMsg.setEnabled(true);
        tvTabMsg.setEnabled(true);
        ivMainMine.setEnabled(true);
        tvTabMine.setEnabled(true);
    }

    private void selectB() {
        vp.setCurrentItem(1);
        ivTabHome.setEnabled(true);
        tvTabHome.setEnabled(true);
        ivTabB.setEnabled(false);
        tvTabB.setEnabled(false);
        ivTabMsg.setEnabled(true);
        tvTabMsg.setEnabled(true);
        ivMainMine.setEnabled(true);
        tvTabMine.setEnabled(true);
    }

    private void selectC() {
        vp.setCurrentItem(2);
        ivTabHome.setEnabled(true);
        tvTabHome.setEnabled(true);
        ivTabB.setEnabled(true);
        tvTabB.setEnabled(true);
        ivTabMsg.setEnabled(false);
        tvTabMsg.setEnabled(false);
        ivMainMine.setEnabled(true);
        tvTabMine.setEnabled(true);
    }

    private void selectMine() {
        vp.setCurrentItem(3);
        ivTabHome.setEnabled(true);
        tvTabHome.setEnabled(true);
        ivTabB.setEnabled(true);
        tvTabB.setEnabled(true);
        ivTabMsg.setEnabled(true);
        tvTabMsg.setEnabled(true);
        ivMainMine.setEnabled(false);
        tvTabMine.setEnabled(false);
    }
}
