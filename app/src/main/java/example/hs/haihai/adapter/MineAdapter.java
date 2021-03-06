package example.hs.haihai.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.hs.baselibrary.base.BaseRecyclerViewAdapter;
import example.hs.haihai.R;
import example.hs.haihai.bean.MenuItem;

public class MineAdapter extends BaseRecyclerViewAdapter<MineAdapter.ViewHolder> {
    private Fragment fragment;
    private List<MenuItem> menuItems;

    public MineAdapter(Fragment fragment, List<MenuItem> menuItems) {
        this.fragment = fragment;
        this.menuItems = menuItems;
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder h = (ViewHolder) holder;
            MenuItem menuItem = menuItems.get(position);
            h.tvTitle.setText(TextUtils.isEmpty(menuItem.getTitle()) ? "" : menuItem.getTitle());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(fragment.getContext()).inflate(R.layout.item_mine, null));
    }

    @Override
    public int getItemCount() {
        return menuItems == null ? 0 : menuItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
