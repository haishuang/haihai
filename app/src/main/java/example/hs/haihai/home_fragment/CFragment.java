package example.hs.haihai.home_fragment;


import example.hs.haihai.R;
import example.hs.haihai.base.CommonLazyFragment;

/**
 *    author : HJQ
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
 */
public class CFragment extends CommonLazyFragment {

    public static CFragment newInstance() {
        return new CFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_c;
    }



    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}