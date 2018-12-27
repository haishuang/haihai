package example.hs.haihai.webapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.hs.baselibrary.Text;
import example.hs.baselibrary.widget.TitleBar;
import example.hs.haihai.R;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.title_web)
    TitleBar titleWeb;
    @BindView(R.id.web)
    WebView webView;

    @BindView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        titleWeb.setOnTitleBarBackOnClickListener(new TitleBar.TitleBarBackOnClickListener() {
            @Override
            public void backClick() {
                if(webView.canGoBack())
                    webView.goBack();
                else
                finish();
            }
        });

        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        String title = intent.getStringExtra("title");

        titleWeb.setTitleName(TextUtils.isEmpty(title) ? "" : title);
        webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();
        //  loadingView.startLoading();
        settings.setJavaScriptEnabled(true);    //允许加载javascript
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progress.setVisibility(View.VISIBLE);
            }
        });

        webView.loadUrl(link);
        //webView.loadDataWithBaseURL(link, null, "text/html", "utf-8", null);//TextUtils.isEmpty(link) ? getString(R.string.text_no_content) : bean.getContent().replace("<img", "<img style='max-width:100%;height:auto;'"), "text/html", "utf-8", null);

    }
}
