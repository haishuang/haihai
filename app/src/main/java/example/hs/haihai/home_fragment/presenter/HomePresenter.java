package example.hs.haihai.home_fragment.presenter;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import example.hs.haihai.R;
import example.hs.haihai.api.ApiService;
import example.hs.haihai.api.subscriber.SubscriberObserver;
import example.hs.haihai.bean.Article;
import example.hs.haihai.bean.ArticleResult;
import example.hs.haihai.bean.Banner;
import example.hs.haihai.bean.Result;
import example.hs.haihai.home_fragment.imp.IHome;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements IHome.IPresenter<IHome.IView> {
    private IHome.IView view;

    @Override
    public void attachView(IHome.IView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void getHomeArtices(final Context context, final int pageStart) {
        ApiService.getApi().getArticles(pageStart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SubscriberObserver<Result<ArticleResult>>(context) {
                    @Override
                    public void onSuccess(Result<ArticleResult> listResult) {
                        if (listResult.getErrorCode() == 0) {
                            if (view != null)
                                view.showArticles(listResult.getData().getDatas(), pageStart);
                        } else {
                            if (view != null)
                                view.complete(listResult.getErrorCode(), TextUtils.isEmpty(listResult.getErrorMsg()) ? context.getString(R.string.text_net_error) : listResult.getErrorMsg());
                        }
                    }

                    @Override
                    public void onFailure(int code, String info) {
                        if (view != null)
                            view.complete(code, info);
                    }
                });
    }

    @Override
    public void getBanner(final Context context) {
        ApiService.getApi().getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SubscriberObserver<Result<List<Banner>>>(context) {
                    @Override
                    public void onSuccess(Result<List<Banner>> listResult) {
                        if (listResult.getErrorCode() == 0) {
                            if (view != null)
                                view.showBanner(listResult.getData());
                        } else {
                            showError(listResult.getErrorCode(), TextUtils.isEmpty(listResult.getErrorMsg()) ? context.getString(R.string.text_net_error) : listResult.getErrorMsg());
                        }
                    }

                    @Override
                    public void onFailure(int code, String info) {
                        showError(code, info);
                    }
                });
    }

    private void showError(int code, String info) {
        if (view != null)
            view.showError(code, info);
    }
}
