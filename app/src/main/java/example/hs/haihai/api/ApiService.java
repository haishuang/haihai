package example.hs.haihai.api;

import android.os.Environment;
import android.util.Log;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import example.hs.haihai.base.BaseApplication;
import example.hs.haihai.base.ConfigApp;
import example.hs.haihai.utils.NetUtils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hhs on 2018/12/12.
 */

public class ApiService {
    private static Api api;
    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
    public static Api getApi() {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(genericClient())
                    .baseUrl(ConfigApp.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(Api.class);
        }
        return api;
    }

    public static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//TimeUnit.MILLISECONDS
        httpClient = new OkHttpClient.Builder()
                .readTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                            for (Cookie cookie : cookies) {
                                Log.e("cookie", "----------->" + cookie.toString() + "---" + cookie.name());
                            }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                //.addInterceptor(new MoreBaseUrlInterceptor())
                .addInterceptor(logging)

                //缓存添加下列三项
                //.addInterceptor(new CacheInterceptor())
                //.addNetworkInterceptor(new CacheNetworkInterceptor())
                //必须取得SD卡读写权限，否则无法进行缓存操作
                //.cache(new Cache(new File(Environment.getExternalStoragePublicDirectory(""), "ok-cache"), 1024 * 1024 * 30L))


                .build();
        return httpClient;
    }


    static class CacheNetworkInterceptor implements Interceptor {
        public Response intercept(Chain chain) throws IOException {
            //无缓存,进行缓存
            return chain.proceed(chain.request()).newBuilder()
                    .removeHeader("Pragma")
                    //对请求进行最大60秒的缓存
                    .addHeader("Cache-Control", "max-age=60")
                    .build();
        }
    }


    static class CacheInterceptor implements Interceptor {
        public Response intercept(Chain chain) throws IOException {
            Response resp;
            Request req;
            if (NetUtils.isConnected(BaseApplication.getInstance().getContext())) {
                Log.e("tga", "-----------------------------------------------------------------------有网络");
                //有网络,检查10秒内的缓存
                req = chain.request()
                        .newBuilder()
                        .cacheControl(new CacheControl
                                .Builder()
                                .maxAge(10, TimeUnit.SECONDS)
                                .build())
                        .build();
            } else {
                Log.e("tga", "-----------------------------------------------------------------------无网络");
                //无网络,检查30天内的缓存,即使是过期的缓存
                req = chain.request().newBuilder()
                        .cacheControl(new CacheControl.Builder()
                                .onlyIfCached()
                                .maxStale(30, TimeUnit.SECONDS)
                                .build())
                        .build();
            }
            resp = chain.proceed(req);
            return resp.newBuilder().build();
        }
    }

}
