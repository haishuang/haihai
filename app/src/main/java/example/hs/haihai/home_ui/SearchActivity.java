package example.hs.haihai.home_ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.hs.baselibrary.utils.HtmlUtils;
import example.hs.baselibrary.widget.ClearEditText;
import example.hs.haihai.R;
import example.hs.haihai.base2.BaseActivity;
import example.hs.haihai.bean.Article;
import example.hs.haihai.home_ui.adapter.ArticleAdapter;
import example.hs.haihai.home_ui.imp.ISearch;
import example.hs.haihai.home_ui.presenter.SearchPresenter;
import example.hs.haihai.webapp.DetailsActivity;

public class SearchActivity extends BaseActivity implements OnRefreshLoadMoreListener, ISearch.IView, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.et_search)
    ClearEditText etSearch;
    @BindView(R.id.rcv_search)
    RecyclerView rcvSearch;
    @BindView(R.id.srl_search)
    SmartRefreshLayout srlSearch;
    @BindView(R.id.progress)
    ProgressBar progress;

    private SearchPresenter presenter;

    private ArticleAdapter articleAdapter;
    //列表数据
    private List<Article> articles = new ArrayList<>();

    //当前页码
    private int pageStart = 0;
    //当前关键字，每次输入后替换
    private String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        articleAdapter = new ArticleAdapter(articles);
        articleAdapter.setOnItemClickListener(this);
        rcvSearch.setAdapter(articleAdapter);
        rcvSearch.setLayoutManager(new LinearLayoutManager(this));

        srlSearch.setOnRefreshLoadMoreListener(this);

        presenter = new SearchPresenter();
        presenter.attachView(this);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    key = etSearch.getText().toString().trim();
                    if (!TextUtils.isEmpty(key)) {
                        pageStart = 0;
                        presenter.searchArticles(activity, key, pageStart);
                        progress.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean isRegisterEB() {
        return false;
    }

    @Override
    public void showArticles(List<Article> articles, int pageNum) {
        pageStart = pageNum + 1;
        progress.setVisibility(View.GONE);
        srlSearch.finishLoadMore();
        srlSearch.finishRefresh();
        if (articles != null) {
            if (pageNum == 0) {
                this.articles.clear();
            }
            this.articles.addAll(articles);
            articleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showError(int code, String info) {

    }

    @Override
    public void complete(int code, String info) {
        srlSearch.finishRefresh();
        srlSearch.finishLoadMore();
        progress.setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_back, R.id.et_search, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.et_search:
                break;
            case R.id.tv_search:
                key = etSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(key)) {
                    pageStart = 0;
                    presenter.searchArticles(activity, key, pageStart);
                    progress.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

        presenter.searchArticles(activity, key, pageStart);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageStart = 0;
        presenter.searchArticles(activity, key, pageStart);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Article article = articles.get(position);
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra("link", article.getLink());
        intent.putExtra("title", HtmlUtils.delHTMLTag(article.getTitle()));
        startActivity(intent);
    }
}
