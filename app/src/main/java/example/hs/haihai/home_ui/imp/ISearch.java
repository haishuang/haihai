package example.hs.haihai.home_ui.imp;

import android.content.Context;

import java.util.List;

import example.hs.haihai.base.BaseContract;
import example.hs.haihai.bean.Article;

public interface ISearch {
    interface IView extends BaseContract.BaseView {

        void showArticles(List<Article> banners, int pageNum);
    }

    interface IPresenter<T> extends BaseContract.BasePresenter<T> {

        void searchArticles(Context context,String key, int pageStart);
    }
}
