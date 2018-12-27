package example.hs.haihai.api;

import java.util.List;

import example.hs.haihai.bean.Article;
import example.hs.haihai.bean.ArticleResult;
import example.hs.haihai.bean.Banner;
import example.hs.haihai.bean.Result;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    //获取banner
    @GET("banner/json")
    Observable<Result<List<Banner>>> getBanner();

    //获取首页文章，页码从0开始
    @GET("article/list/{pageStart}/json")
    Observable<Result<ArticleResult>> getArticles(@Path("pageStart") int pageStart);

    //搜索文章
    @POST("article/query/{pageStart}/json")
    Observable<Result<ArticleResult>> searchArticles(@Path("pageStart") int pageStart, @Body RequestBody body,@Query("k") String key);
}
