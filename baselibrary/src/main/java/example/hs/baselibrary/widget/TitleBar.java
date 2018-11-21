package example.hs.baselibrary.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import example.hs.baselibrary.R;


public class TitleBar extends RelativeLayout implements OnClickListener {
    private TextView tvBackName;
    private TextView tvTitle;
    private TextView mIcon1;
    private TextView tvRightName;
    private View viewTitle;
    private RelativeLayout mRl;
    private float scale;

    private String backName;
    //左边文字大小
    private int backSize;
    //左边颜色
    private int backColor;
    //左边图标
    private int backIcon;

    //中间标题文字
    private String titleText;
    //中间标题文字
    private int titleColor;
    //中间文字大小
    private int titleSize;


    private int icon1;

    //最右边的图标
    private int rightIcon;
    //最右边文字
    private String rightName;
    //最右边文字大小
    private int rightSize;
    //最右边文字颜色
    private int rightColor;
    //背景色
    private int backgroud;

    // private Paint mPaint;
    private TypedArray mTypedArray;
    private TitleBarOnClickListener mListener;
    private TextView mPaddTop;

    public TitleBar(Context context) {
        this(context, null);

    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 将xml挂载到class上
        View.inflate(context, R.layout.title_bar, this);
        //获取定义的属性文件
        mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        //根据这个值,转换为对应的dp值
        scale = context.getResources().getDisplayMetrics().density;
        // 初始化view
        initView();

        initData();
    }

    /**
     * 功能:初始化数据
     */
    private void initData() {
        // 获得xml里定义的属性,格式为 名称_属性名 后面是默认值back
        backName = mTypedArray.getString(R.styleable.TitleBar_backName);
        backSize = (int) (mTypedArray.getDimensionPixelSize(R.styleable.TitleBar_backSize, getResources().getDimensionPixelOffset(R.dimen.title_bar_back_size)) / scale);
        backColor = mTypedArray.getColor(R.styleable.TitleBar_backColor, Color.BLACK);
        backIcon = mTypedArray.getResourceId(R.styleable.TitleBar_backIcon, 10);

        //中间文字
        titleText = mTypedArray.getString(R.styleable.TitleBar_titleText);
        //titleSize = mTypedArray.getDimensionPixelSize(R.styleable.TitleBar_titleSize, getResources().getDimensionPixelOffset(R.dimen.title_bar_text_size));
        titleSize = (int) (mTypedArray.getDimensionPixelOffset(R.styleable.TitleBar_titleSize, getResources().getDimensionPixelOffset(R.dimen.title_bar_text_size)) / scale);
        titleColor = mTypedArray.getColor(R.styleable.TitleBar_titleColor, getResources().getColor(R.color.title_bar_text_color));

        icon1 = mTypedArray.getResourceId(R.styleable.TitleBar_icon1, 10);

        rightIcon = mTypedArray.getResourceId(R.styleable.TitleBar_rightIcon, 10);
        rightName = mTypedArray.getString(R.styleable.TitleBar_rightName);
        rightSize = (int) (mTypedArray.getDimension(R.styleable.TitleBar_rightSize, 18) / scale);
        rightColor = mTypedArray.getColor(R.styleable.TitleBar_rightColor, Color.BLACK);

        backgroud = mTypedArray.getColor(R.styleable.TitleBar_backgroud, Color.WHITE);
        //int showBackOrLogo = mTypedArray.getInt(R.styleable.TitleBar_showBackOrLogo, SHOWLOGO);


        //设置背景颜色
        mRl.setBackgroundColor(backgroud);
        //mRl.setBackgroundResource(backgroud);
        if (backName == null) {
            //没有设置文字,就显示图片
            if (backIcon == 10) {
                tvBackName.setVisibility(INVISIBLE);
            } else {
                tvBackName.setVisibility(VISIBLE);
                //设置显示图片
                Drawable mLogoIcon = getResources().getDrawable(backIcon);
                mLogoIcon.setBounds(0, 0, mLogoIcon.getMinimumWidth(), mLogoIcon.getMinimumHeight());
                tvBackName.setCompoundDrawables(mLogoIcon, null, null, null);
            }

        } else {
            //设置了文字
            tvBackName.setText(backName);
            tvBackName.setTextSize(backSize);
            tvBackName.setTextColor(backColor);
            if (backIcon == 10) {
                tvBackName.setVisibility(INVISIBLE);
            } else {
                tvBackName.setVisibility(VISIBLE);
                //设置显示图片
                Drawable mLogoIcon = getResources().getDrawable(backIcon);
                mLogoIcon.setBounds(0, 0, mLogoIcon.getMinimumWidth(), mLogoIcon.getMinimumHeight());
                tvBackName.setCompoundDrawables(mLogoIcon, null, null, null);
            }
        }


        //设置显示title
        tvTitle.setText(titleText);
        tvTitle.setTextSize(titleSize);
        tvTitle.setTextColor(titleColor);

        if (icon1 != 10) {
            mIcon1.setVisibility(VISIBLE);
            //设置显示图片
            Drawable drawable = getResources().getDrawable(icon1);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            mIcon1.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        } else {
            mIcon1.setVisibility(INVISIBLE);
        }

        if (rightName == null) {
            //没有设置文字,就显示图片
            if (rightIcon == 10) {
                tvRightName.setVisibility(INVISIBLE);
            } else {

                tvRightName.setVisibility(VISIBLE);
                //设置显示图片
                Drawable drawable = getResources().getDrawable(rightIcon);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvRightName.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            }

        } else {
            //设置了文字
            tvRightName.setText(rightName);
            tvRightName.setTextSize(rightSize);
            tvRightName.setTextColor(rightColor);

            //tvRightName.setVisibility(INVISIBLE);
        }


        // 为了保持以后使用该属性一致性,返回一个绑定资源结束的信号给资源
        mTypedArray.recycle();
    }

    public void setFull(boolean isFull) {
        if (isFull) {
            mPaddTop.setVisibility(View.VISIBLE);
        } else {
            mPaddTop.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化view
     */
    private void initView() {
        // 获取对应的id
        tvBackName = (TextView) findViewById(R.id.tv_logo);
        mPaddTop = (TextView) findViewById(R.id.tv_paddtop);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRightName = (TextView) findViewById(R.id.tv_icon2);
        mIcon1 = (TextView) findViewById(R.id.tv_icon1);
        mRl = (RelativeLayout) findViewById(R.id.rl);
        viewTitle = findViewById(R.id.view_title);

        mPaddTop.setVisibility(GONE);
        mIcon1.setOnClickListener(this);
        tvRightName.setOnClickListener(this);
        tvBackName.setOnClickListener(this);
    }

    public void setDividerVisibility(boolean visi) {
        if (viewTitle != null)
            viewTitle.setVisibility(visi ? VISIBLE : GONE);
    }

    public void setMoreVisibility(boolean visi) {
        if (mIcon1 != null)
            mIcon1.setVisibility(visi ? VISIBLE : GONE);
    }

    public void setAddVisibility(boolean visi) {
        if (tvRightName != null)
            tvRightName.setVisibility(visi ? VISIBLE : GONE);
    }

    @Override
    public void onClick(View v) {
        if (null != mListener) {
            if (v == mIcon1) {
                mListener.searchClick();
            } else if (v == tvRightName) {
                mListener.addClick();
            } else if (v == tvBackName) {
                mListener.backClick();
            }
        } else if (backListener != null) {
            backListener.backClick();
        }
    }


    public void setOnClickListener(TitleBarOnClickListener listener) {
        this.mListener = listener;
    }

    public interface TitleBarOnClickListener {
        void searchClick();

        void backClick();

        void addClick();
    }

    public void setOnTitleBarBackOnClickListener(TitleBarBackOnClickListener listener) {
        this.backListener = listener;
    }

    public TitleBarBackOnClickListener backListener;

    public interface TitleBarBackOnClickListener {
        void backClick();
    }

    //给logo设置方法
    public void setBackName(String name) {
        tvBackName.setText(name);
    }

    public void setBackColor(int color) {
        tvBackName.setTextColor(color);
    }

    public void setBackSize(int size) {
        tvBackName.setTextSize(size);
    }

    public void setBackVisibility(int visibility) {
        tvBackName.setVisibility(visibility);
    }

    //给title设置方法
    public void setTitleName(String name) {
        tvTitle.setText(name);
    }

    public void setTitleColor(int color) {
        tvTitle.setTextColor(color);
    }

    public void setTitleSize(int size) {
        tvTitle.setTextSize(size);
    }


    //给icon1设置方法
    public void setRightName(String name) {
        tvRightName.setText(name);
    }
    //给icon1设置方法
    public void setRightIcon(int  res) {
        tvRightName.setVisibility(VISIBLE);
        //设置显示图片
        Drawable drawable = getResources().getDrawable(res);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvRightName.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    //给icon1设置方法
    public void setRightNameEnable(boolean enable) {
        tvRightName.setEnabled(enable);
        tvRightName.setTextColor(getResources().getColor(enable ? R.color.colorApp : R.color.colorApp50));
    }

    public void setRightColor(int color) {
        tvRightName.setTextColor(color);
    }

    public void setRightSize(int size) {
        tvRightName.setTextSize(size);
    }

    public void setRightVisibility(int visibility) {
        tvRightName.setVisibility(visibility);
    }

    public View getRightView() {
        return tvRightName;
    }

    public String getRightName() {
        return tvRightName.getText().toString();
    }

}
