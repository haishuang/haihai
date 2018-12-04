package example.hs.haihai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import example.hs.haihai.R;
import example.hs.haihai.bean.Contacts;

public class ContactsAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Contacts> contacts;

    public ContactsAdapter(Context context, List<Contacts> contacts) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts == null ? 0 : contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_contacts, null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.ivMsg = convertView.findViewById(R.id.iv_msg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String word = contacts.get(position).getLetter();
        holder.tvTitle.setText(word);
        holder.tvName.setText(contacts.get(position).getName());
        //将相同字母开头的合并在一起
        if (position == 0) {
            //第一个是一定显示的
            holder.tvTitle.setVisibility(View.VISIBLE);
        } else {
            //后一个与前一个对比,判断首字母是否相同，相同则隐藏
            String headerWord = contacts.get(position - 1).getLetter();

            holder.tvTitle.setVisibility(word.equals(headerWord) ? View.GONE : View.VISIBLE);

        }

        //控制点击事件
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClick != null)
                    onItemClick.onClick(position, view);
            }
        });
        holder.ivMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClick != null)
                    onItemClick.onClick(position, view);
            }
        });

        return convertView;
    }

    public interface OnItemClick {
        void onClick(int i, View view);
    }

    private OnItemClick onItemClick;

    public void setOnItemClickListener(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    private class ViewHolder {
        private TextView tvTitle;
        private TextView tvName;
        private ImageView ivMsg;
    }
}
