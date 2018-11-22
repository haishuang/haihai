package example.hs.haihai.home_fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import example.hs.baselibrary.base.BaseRecyclerViewAdapter;
import example.hs.baselibrary.widget.TitleBar;
import example.hs.haihai.R;
import example.hs.haihai.adapter.MineAdapter;
import example.hs.haihai.base.CommonLazyFragment;
import example.hs.haihai.bean.MenuItem;
import example.hs.haihai.mine.DeviceInfoActivity;
import example.hs.haihai.mine.OtherActivity;

/**
 */
public class MineFragment extends CommonLazyFragment {

    @BindView(R.id.title_mine)
    TitleBar titleMine;
    @BindView(R.id.rcv)
    RecyclerView rcv;


    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }


    @Override
    protected void initView() {
        titleMine.setFull(true);
    }

    @Override
    protected void initData() {
        final List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(1, "关于手机"));
        menuItems.add(new MenuItem(2, "一些控件测试"));

        MineAdapter adapter = new MineAdapter(this, menuItems);
        rcv.setLayoutManager(new LinearLayoutManager(mActivity));
        rcv.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (menuItems.get(position).getId()) {
                    case 1:
                        startActivity(DeviceInfoActivity.class);
                        break;
                    case 2:
                        startActivity(OtherActivity.class);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }


}