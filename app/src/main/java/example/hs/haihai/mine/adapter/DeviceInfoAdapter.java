package example.hs.haihai.mine.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.hs.baselibrary.base.BaseRecyclerViewAdapter;
import example.hs.haihai.R;
import example.hs.haihai.bean.DeviceInfo;

public class DeviceInfoAdapter extends BaseRecyclerViewAdapter<DeviceInfoAdapter.ViewHolder> {
    private Context context;
    private List<DeviceInfo> deviceInfos;

    public DeviceInfoAdapter(Context context, List<DeviceInfo> deviceInfos) {
        this.context = context;
        this.deviceInfos = deviceInfos;
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder h = (ViewHolder) holder;
            DeviceInfo deviceInfo = deviceInfos.get(position);
            h.tvTitle.setText(TextUtils.isEmpty(deviceInfo.getTitle()) ? "" : deviceInfo.getTitle());
            h.tvInfo.setText(TextUtils.isEmpty(deviceInfo.getInfo()) ? "" : deviceInfo.getInfo());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mine_device_info, null));
    }

    @Override
    public int getItemCount() {
        return deviceInfos == null ? 0 : deviceInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvInfo = itemView.findViewById(R.id.tv_info);
        }
    }
}
