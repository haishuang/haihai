package example.hs.baselibrary.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import example.hs.baselibrary.utils.NoDoubleClickListener;


/**
 * Created by Huanghs on 2017/7/4.
 */

public abstract class BaseRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //定义接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }//定义接口
    //声明接口变量
    protected OnItemClickListener listener;


    //暴露给使用者调用
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        onBindView(viewHolder, position);

        if (listener != null) {
            viewHolder.itemView.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View view) {
                    listener.onItemClick(view, position);
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(view, position);
                    //如果是长按事件，那么久不用再触发点击事件了，所以返回true
                    //将事件直接消费掉
                    return true;
                }
            });
        }
    }

    public abstract void onBindView(RecyclerView.ViewHolder holder, int position);
}
