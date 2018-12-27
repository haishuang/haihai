package example.hs.haihai.home_fragment;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import example.hs.haihai.R;
import example.hs.haihai.base.CommonLazyFragment;
import example.hs.haihai.bean.Article;
import example.hs.haihai.bean.Banner;
import example.hs.haihai.home_fragment.adapter.HomeArticeAdapter;
import example.hs.haihai.home_fragment.imp.IHome;
import example.hs.haihai.home_fragment.presenter.HomePresenter;
import example.hs.haihai.home_ui.SearchActivity;
import example.hs.haihai.utils.GlideImageLoader;
import example.hs.haihai.webapp.DetailsActivity;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */

public class HomeFragment extends CommonLazyFragment implements IHome.IView, OnBannerListener, OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.tv_01)
    TextView tv01;

    com.youth.banner.Banner banner;
    @BindView(R.id.rcv)
    RecyclerView rcv;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.srl)
    SmartRefreshLayout smartRefreshLayout;

    private IHome.IPresenter presenter;

    //轮播图对象
    private List<Banner> bannerBanners = new ArrayList<>();
    //首页列表
    private List<Article> homeArticles = new ArrayList<>();

    private HomeArticeAdapter articeAdapter;

    //当前加载的页面,从0开始
    private int pageStart = 0;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    protected void initView() {
        presenter = new HomePresenter();
        presenter.attachView(this);
        presenter.getBanner(getContext());

        presenter.getHomeArtices(getContext(), 0);

        View bannerView = LayoutInflater.from(getContext()).inflate(R.layout.item_home_banner, null);
        banner = bannerView.findViewById(R.id.banner);
        //初始化轮播图
        banner.setImages(new ArrayList<String>())
                .setBannerTitles(new ArrayList<String>())
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)//数字加标题模式
                .isAutoPlay(true)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();

        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(articeAdapter = new HomeArticeAdapter(homeArticles));
        articeAdapter.addHeaderView(bannerView);
        smartRefreshLayout.setEnableLoadMore(true);
        //smartRefreshLayout.setOnLoadMoreListener(this);
        smartRefreshLayout.setOnRefreshLoadMoreListener(this);

        articeAdapter.setOnItemClickListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void showArticles(List<Article> articles, int pageNum) {
        pageStart = pageNum + 1;
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
        if (articles != null) {
            if (pageNum == 0) {
                homeArticles.clear();
            }
            homeArticles.addAll(articles);
            articeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showBanner(List<Banner> banners) {
        if (banners != null && banners.size() > 0) {
            //轮播图图片
            List<String> images = new ArrayList<>();
            //轮播图标题
            List<String> titles = new ArrayList<>();

            //封装成适合的数据
            for (Banner banner : banners) {
                images.add(banner.getImagePath());
                titles.add(banner.getTitle());
            }

            this.bannerBanners.clear();
            this.bannerBanners.addAll(banners);
            banner.update(images, titles);//更新对象
        }
    }

    @Override
    public void OnBannerClick(int position) {
        Banner banner = bannerBanners.get(position);
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("link", banner.getUrl());
        intent.putExtra("title", banner.getTitle());
        startActivity(intent);
    }

    @Override
    public void showError(int code, String info) {

    }

    @Override
    public void complete(int code, String info) {
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        presenter.getHomeArtices(getContext(), pageStart);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageStart = 0;//重置
        presenter.getHomeArtices(getContext(), pageStart);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter = null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Article article = homeArticles.get(position);
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("link", article.getLink());
        intent.putExtra("title", article.getTitle());
        startActivity(intent);
    }
}