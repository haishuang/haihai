package example.hs.baselibrary.widget;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import example.hs.baselibrary.R;


/**
 * Created by Administrator on 2018/4/10.
 */

public class BToast extends Toast {

    /**
     * Toast单例
     */
    private static BToast toast;
    //图标
    private static ImageView imageToast;
    /**
     * 图标状态 不显示图标
     */
    private static final int TYPE_HIDE = -1;
    /**
     * 图标状态 显示√
     */
    private static final int TYPE_TRUE = 0;
    /**
     * 图标状态 显示×
     */
    private static final int TYPE_ERROR = 1;
    /**
     * 显示哭脸
     */
    private static final int TYPE_FALSE = 2;
    /**
     * 显示叹号
     */
    private static final int TYPE_WARN = 3;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    public BToast(Context context) {
        super(context);
    }

    /**
     * 长时间无图显示
     * @param context
     * @param text
     */
    public static void showLong(Context context, CharSequence text){
        showToast(context,text,Toast.LENGTH_LONG,TYPE_HIDE);
    }
    /**
     * 短时间无图显示
     * @param context
     * @param text
     */
    public static void showShort(Context context, CharSequence text){
        showToast(context,text,Toast.LENGTH_SHORT,TYPE_HIDE);
    }

    public static void showShort(Context context, CharSequence text,int type){
        showToast(context,text,Toast.LENGTH_SHORT,type);
    }

    /**
     * 网络
     * @param context
     * @param text
     */
    public static void showShortNet(Context context, CharSequence text){
        showShort(context,text,TYPE_FALSE);
    }
    /**
     * 警告
     * @param context
     * @param text
     */
    public static void showShortWARN(Context context, CharSequence text){
        showShort(context,text,TYPE_WARN);
    }
    /**
     * 成功
     * @param context
     * @param text
     */
    public static void showShortTrue(Context context, CharSequence text){
        showShort(context,text,TYPE_TRUE);
    }
    /**
     * 成功
     * @param context
     * @param text
     */
    public static void showShortError(Context context, CharSequence text){
        showShort(context,text,TYPE_ERROR);
    }

    private static void showToast(Context context, CharSequence text, int time, int imgType) {
        initToast(context, text);

        // 设置显示时长
        if (time == Toast.LENGTH_LONG) {
            toast.setDuration(Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        if (imgType == TYPE_HIDE) {
            imageToast.setVisibility(View.GONE);
        } else {
            if (imgType == TYPE_TRUE) {
                imageToast.setBackgroundResource(R.drawable.ic_toast_success);
            } else if (imgType == TYPE_ERROR) {
                imageToast.setBackgroundResource(R.drawable.ic_toast_error);
            } else if (imgType == TYPE_WARN) {
                imageToast.setBackgroundResource(R.drawable.ic_toast_warring);
            } else if (imgType == TYPE_FALSE) {
                imageToast.setBackgroundResource(R.drawable.ic_toast_false);
            }
            imageToast.setVisibility(View.VISIBLE);
        }

        // 显示Toast
        toast.show();
    }
    public static void showToast(Context context, CharSequence text, int img) {
        initToast(context, text);

        // 设置显示时长
        toast.setDuration(Toast.LENGTH_SHORT);
        imageToast.setBackgroundResource(img);
        imageToast.setVisibility(View.VISIBLE);

        // 显示Toast
        toast.show();
    }

    /**
     * 初始化Toast
     *
     * @param context 上下文
     * @param text    显示的文本
     */
    private static void initToast(Context context, CharSequence text) {
        try {
            cancelToast();

            toast = new BToast(context);
            // 获取LayoutInflater对象
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 由layout文件创建一个View对象
            View layout = inflater.inflate(R.layout.layout_toast, null);

            // 吐司上的图片
            imageToast = (ImageView) layout.findViewById(R.id.toast_img);

            // 实例化ImageView和TextView对象
            TextView toast_text = (TextView) layout.findViewById(R.id.toast_text);
            toast_text.setText(text);
            toast.setView(layout);
            toast.setGravity(Gravity.CENTER, 0, 70);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏当前Toast
     */
    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    public void cancel() {
        try {
            super.cancel();
        } catch (Exception e) {

        }
    }

    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {

        }
    }
}
