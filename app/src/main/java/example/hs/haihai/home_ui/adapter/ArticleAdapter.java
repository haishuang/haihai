package example.hs.haihai.home_ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.text.HtmlCompat;
import android.text.Html;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import example.hs.haihai.R;
import example.hs.haihai.bean.Article;

public class ArticleAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {

    public ArticleAdapter(@Nullable List<Article> data) {
        super(R.layout.item_home_artice, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        //helper.setText(R.id.tv_title, item.getTitle());
        //其中的flags表示：
        //FROM_HTML_MODE_COMPACT：html块元素之间使用一个换行符分隔
        //FROM_HTML_MODE_LEGACY：html块元素之间使用两个换行符分隔
        ((TextView)helper.getView(R.id.tv_title)).setText(HtmlCompat.fromHtml(item.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_time, item.getNiceDate());
    }
}
