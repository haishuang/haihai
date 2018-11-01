package example.hs.haihai.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import example.hs.baselibrary.base.BaseRecyclerViewAdapter;

public class MineAdapter extends BaseRecyclerViewAdapter<MineAdapter.ViewHolder> {



    @Override
    public void onBindView(RecyclerView.ViewHolder holder, int position) {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
