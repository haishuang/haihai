package example.hs.haihai.api.subscriber;


import android.content.Context;
import android.net.ParseException;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.hjq.toast.ToastUtils;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.io.NotSerializableException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import example.hs.haihai.api.ApiException;
import example.hs.haihai.utils.NetUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class SubscriberObserver<T> implements Observer<T> {
    private Disposable mDisposable;

    private Context context;

    public SubscriberObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(T value) {
        if (value != null) {
            onSuccess(value);
        } else
            onFailure(-1, "后台返回信息为空");
    }

    @Override
    public void onError(Throwable e) {
        if (!NetUtils.isConnected(context)) {
            //无网络，很多时候只需toast一下就好
            //ToastUtils.show("当前无网络连接，请先设置网络!");
            onFailure(-2, "当前无网络连接，请先设置网络!");
        } else {
            if (e instanceof HttpException) {
                HttpException httpException = (HttpException) e;
                onFailure(httpException.code(), "网络错误,Code:" + httpException.code() + " ,err:" + httpException.getMessage());
//                switch (httpException.code()) {
//                    case UNAUTHORIZED:
//                    case BADREQUEST:
//                    case FORBIDDEN:
//                    case NOT_FOUND:
//                    case REQUEST_TIMEOUT:
//                    case GATEWAY_TIMEOUT:
//                    case INTERNAL_SERVER_ERROR:
//                    case BAD_GATEWAY:
//                    case SERVICE_UNAVAILABLE:
//                    default:
//                        onFailure(httpException.code(), "网络错误,Code:" + httpException.code() + " ,err:" + httpException.getMessage());
//                        break;
//                }

            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof JsonSyntaxException
                    || e instanceof JsonSerializer
                    || e instanceof NotSerializableException
                    || e instanceof ParseException) {
                onFailure(ApiException.ERROR.PARSE_ERROR, "解析错误");
            } else if (e instanceof ClassCastException) {
                onFailure(ApiException.ERROR.CAST_ERROR, "类型转换错误");
            } else if (e instanceof ConnectException) {
                onFailure(ApiException.ERROR.NETWORD_ERROR, "连接失败");
            } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
                onFailure(ApiException.ERROR.SSL_ERROR, "证书验证失败");
            } else if (e instanceof ConnectTimeoutException) {
                onFailure(ApiException.ERROR.TIMEOUT_ERROR, "连接超时");
            } else if (e instanceof java.net.SocketTimeoutException) {
                onFailure(ApiException.ERROR.TIMEOUT_ERROR, "Socket超时");
            } else if (e instanceof UnknownHostException) {
                onFailure(ApiException.ERROR.UNKNOWNHOST_ERROR, "无法解析该域名");
            } else if (e instanceof NullPointerException) {
                onFailure(ApiException.ERROR.NULLPOINTER_EXCEPTION, "NullPointerException");
            } else {
                onFailure(ApiException.ERROR.UNKNOWN, "未知错误");
            }
        }


        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
    }

    @Override
    public void onComplete() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(int code, String info);
}
