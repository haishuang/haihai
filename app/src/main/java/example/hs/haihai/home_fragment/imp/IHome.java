package example.hs.haihai.home_fragment.imp;


import android.content.Context;

import java.util.List;

import example.hs.haihai.base.BaseContract;
import example.hs.haihai.bean.Article;
import example.hs.haihai.bean.Banner;

public interface IHome {
    interface IView extends BaseContract.BaseView {
        void showBanner(List<Banner> banners);
        void showArticles(List<Article> banners, int pageNum);
    }

    interface IPresenter<T> extends BaseContract.BasePresenter<T> {
        void getBanner(Context context);

        void getHomeArtices(Context context, int pageStart);
    }


}
