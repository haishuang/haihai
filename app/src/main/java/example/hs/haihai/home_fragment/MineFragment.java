package example.hs.haihai.home_fragment;


import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import example.hs.baselibrary.widget.TitleBar;
import example.hs.haihai.R;
import example.hs.haihai.base.CommonLazyFragment;

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

    }


































}