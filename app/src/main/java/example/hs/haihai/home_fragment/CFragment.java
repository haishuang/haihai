package example.hs.haihai.home_fragment;


import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import example.hs.baselibrary.widget.TitleBar;
import example.hs.haihai.R;
import example.hs.haihai.adapter.ContactsAdapter;
import example.hs.haihai.base.LazyFragment;
import example.hs.haihai.bean.Contacts;
import example.hs.haihai.utils.ContactUtils;
import example.hs.haihai.widget.WordsNavigation;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public class CFragment extends LazyFragment {

    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.words)
    WordsNavigation words;
    @BindView(R.id.tv)
    TextView tv;
    private Unbinder unbinder;
    @BindView(R.id.ti_contacts)
    TitleBar tiContacts;

    private ContactsAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 11:
                    isSuccess=true;
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    //获取数据成功
    private boolean isSuccess;

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible && !isSuccess)//获取数据成功后再次切换不再去获取数据
            checkPermiss();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_c, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        init();
        initEvent();
        return rootView;
    }

    private void checkPermiss() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

//        }
//         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(getContext(), new String[]{Manifest.permission.READ_PHONE_STATE}, new String[]{"读取手机通讯录"}, new BasePermissionActivity.RequestPermissionCallBack() {
//                @Override
//                public void granted() {
//                    getContacts();
//                }
//
//                @Override
//                public void denied() {
//
//                }
//            });
            if (PermissionsUtil.hasPermission(getContext(), Manifest.permission.READ_CONTACTS)) {
                getContacts();
            } else {
                PermissionsUtil.TipInfo tip = new PermissionsUtil.TipInfo("注意:", "我就是想看下你的通讯录", "不让看", "打开权限");
                PermissionsUtil.requestPermission(getContext(), new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permission) {
                        getContacts();
                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permission) {

                    }
                }, new String[]{Manifest.permission.READ_CONTACTS}, true, tip);
            }
        } else {
            getContacts();
        }
    }

    private List<Contacts> contacts = new ArrayList<>();

    private void getContacts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                contacts.clear();
                contacts.addAll(ContactUtils.getContactsBySort(getContext()));
                handler.sendEmptyMessage(11);
            }
        }).start();

    }

    private void initEvent() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                //当滑动列表的时候，更新右侧字母列表的选中状态
                if (contacts.size() > 0)
                    words.setTouchIndex(contacts.get(i).getLetter());
            }
        });

        words.setOnWordsChangeListener(new WordsNavigation.onWordsChangeListener() {
            @Override
            public void wordsChange(String words) {
                updateWord(words);
                updateListView(words);
            }
        });
    }


    /**
     * @param words 首字母
     */
    private void updateListView(String words) {
        for (int i = 0; i < contacts.size(); i++) {
            String headerWord = contacts.get(i).getLetter();
            //将手指按下的字母与列表中相同字母开头的项找出来
            if (words.equals(headerWord)) {
                //将列表选中哪一个
                list.setSelection(i);
                //找到开头的一个即可
                return;
            }
        }
    }

    /**
     * 更新中央的字母提示
     *
     * @param words 首字母
     */
    private void updateWord(String words) {
        tv.setText(words);
        tv.setVisibility(View.VISIBLE);
        //清空之前的所有消息
        handler.removeCallbacksAndMessages(null);
        //1s后让tv隐藏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setVisibility(View.GONE);
            }
        }, 500);
    }

    private void init() {
        adapter = new ContactsAdapter(getContext(), contacts);
        list.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}