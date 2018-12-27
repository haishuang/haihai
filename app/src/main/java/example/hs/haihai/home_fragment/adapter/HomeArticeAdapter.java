package example.hs.haihai.home_fragment.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import example.hs.haihai.R;
import example.hs.haihai.bean.Article;

public class HomeArticeAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {

    public HomeArticeAdapter( @Nullable List<Article> data) {
        super(R.layout.item_home_artice, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Article item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_time, item.getNiceDate());
    }
}
