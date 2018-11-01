package example.hs.haihai.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import example.hs.haihai.home_fragment.CFragment;
import example.hs.haihai.home_fragment.BFragment;
import example.hs.haihai.home_fragment.HomeFragment;
import example.hs.haihai.home_fragment.MineFragment;

public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new BFragment());
        fragments.add(new CFragment());
        fragments.add(new MineFragment());
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }
}
