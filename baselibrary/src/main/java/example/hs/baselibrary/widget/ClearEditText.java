package example.hs.baselibrary.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import example.hs.baselibrary.R;


/**
 * Created by Huang hai-sen on 2015/10/10.
 */
public class ClearEditText extends EditText implements OnFocusChangeListener, TextWatcher {

    public interface OnFoucs {
        void onEtFocusChange(boolean foucs, View view);
    }

    public OnFoucs onFoucsListener;

    public void setEtOnFoucsListener(OnFoucs onFoucsListener) {
        this.onFoucsListener = onFoucsListener;
    }

    public OnClearListener listener;

    public interface OnClearListener {
        void onClick();
    }

    public void setOnClearListener(OnClearListener listener) {
        this.listener = listener;
    }

    public OnNoDataListener onNoDataListener;

    public interface OnNoDataListener{
        void onClick();
    }

    public void setOnNoDataListener(OnNoDataListener onNoDataListener) {
        this.onNoDataListener = onNoDataListener;
    }

    //删除按钮的引用
    private Drawable mClearDrawable;

    //控件是否有焦点
    private boolean hasFoucs;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.ic_clear);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    //是否允许输入空格
    private boolean isCanInputEmpty = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable && isEnabled()) {
                    this.setText("");
                    if (listener != null) {
                        listener.onClick();
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        if (onFoucsListener != null)
            onFoucsListener.onEtFocusChange(hasFocus, v);
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);

            if (s.length()==0&&onNoDataListener != null) {
                onNoDataListener.onClick();
            }
        }
    }

    public boolean isCanInputEmpty() {
        return isCanInputEmpty;
    }

    public void setCanInputEmpty(boolean canInputEmpty) {
        isCanInputEmpty = canInputEmpty;
    }
}

