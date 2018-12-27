package example.hs.haihai.home_ui.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonObject;

import example.hs.baselibrary.Text;
import example.hs.haihai.R;
import example.hs.haihai.api.ApiService;
import example.hs.haihai.api.subscriber.SubscriberObserver;
import example.hs.haihai.bean.ArticleResult;
import example.hs.haihai.bean.Result;
import example.hs.haihai.home_ui.imp.ISearch;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SearchPresenter implements ISearch.IPresenter<ISearch.IView> {
    private ISearch.IView view;

    @Override
    public void searchArticles(final Context context, String key, final int pageStart) {
        JsonObject loginInfo = new JsonObject();
        loginInfo.addProperty("k", key);

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), loginInfo.toString());

        ApiService.getApi().searchArticles(pageStart,body,key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SubscriberObserver<Result<ArticleResult>>(context) {
                    @Override
                    public void onSuccess(Result<ArticleResult> articleResult) {
                        if(articleResult.getErrorCode()==0){
                            if(view!=null)
                                view.showArticles(articleResult.getData().getDatas(),pageStart);
                        }else {
                            if(view!=null)
                                view.complete(0, TextUtils.isEmpty(articleResult.getErrorMsg())?context.getString(R.string.text_net_error):articleResult.getErrorMsg());
                        }
                    }

                    @Override
                    public void onFailure(int code, String info) {
                        if(view!=null)
                            view.complete(code,info);
                    }
                });
    }

    @Override
    public void attachView(ISearch.IView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
