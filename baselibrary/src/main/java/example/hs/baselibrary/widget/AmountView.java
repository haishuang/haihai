package example.hs.baselibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import example.hs.baselibrary.R;

public class AmountView extends LinearLayout implements TextWatcher, View.OnClickListener {

    //默认最大值
    //private final int MAX_DEFAULT_NUMBER = Integer.MAX_VALUE;
    //private final int MAX_DEFAULT_NUMBER = 2147483647;
    private final int MAX_DEFAULT_NUMBER = 999999999;

    //默认/初始化值
    private final int DEFAULT_NUMBER = 1;

    //控件View
    private View amountView;
    //文本框
    private EditText etAmount;
    //减按钮
    private ImageView ivReduce;
    //加按钮
    private ImageView ivAdd;

    //加号可用状态图标
    private int addEnableImage;
    //加号不可用状态图标
    private int addDisEnableImage;
    //减号可用状态图标
    private int reduceEnableImage;
    //减号不可用状态图标
    private int reduceDisEnableImage;

    //当前值
    private int number = 0;
    //最大值
    private int maxLimitNumber;
    //最小值
    private int minLimitNumber = 0;

    //辅助标记，true表示是通过点击按钮变化，要拦截掉TextWatcher
    private boolean isButtonChange = false;


    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.amountView);

        addEnableImage = ta.getResourceId(R.styleable.amountView_addEnableImage, R.drawable.ic_amount_add_enable);
        addDisEnableImage = ta.getResourceId(R.styleable.amountView_addDisEnableImage, R.drawable.ic_amount_add_disenable);
        reduceEnableImage = ta.getResourceId(R.styleable.amountView_reduceEnableImage, R.drawable.ic_amount_reduce_enable);
        reduceDisEnableImage = ta.getResourceId(R.styleable.amountView_reduceDisEnableImage, R.drawable.ic_amount_reduce_disenable);

        number = ta.getInteger(R.styleable.amountView_defaultNumber, DEFAULT_NUMBER);
        maxLimitNumber = ta.getInteger(R.styleable.amountView_MaxNumber, MAX_DEFAULT_NUMBER);

        int textSize = ta.getDimensionPixelSize(R.styleable.amountView_amountTextSize, 14);
        int textColor = ta.getColor(R.styleable.amountView_amountTextColor, Color.parseColor("#333333"));

        Drawable textBackground = ta.getDrawable(R.styleable.amountView_amountBackground);

        etAmount.setTextColor(textColor);
        etAmount.setTextSize(textSize);
        if (textBackground != null)
            etAmount.setBackground(textBackground);

        if (number < minLimitNumber) {
            number = minLimitNumber;
        } else if (number > maxLimitNumber) {
            number = maxLimitNumber;
        }
        setNumberView();

        ta.recycle();
    }

    public AmountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        amountView = LayoutInflater.from(context).inflate(R.layout.amount_layout, this);

        etAmount = amountView.findViewById(R.id.et_amount);
        ivReduce = amountView.findViewById(R.id.iv_amount_reduce);
        ivAdd = amountView.findViewById(R.id.iv_amount_add);

        etAmount.addTextChangedListener(this);
        ivAdd.setOnClickListener(this);
        ivReduce.setOnClickListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isButtonChange) {
            isButtonChange = false;
            return;
        }

        if (s.toString().isEmpty())
            return;
        long amount = Long.parseLong(s.toString());
        if (amount > maxLimitNumber) {
            number = maxLimitNumber;
            setNumberView();
        } else if (amount < minLimitNumber) {
            number = minLimitNumber;
            setNumberView();
        }
    }


    @Override
    public void onClick(View view) {
        if (view == ivAdd) {
            if (number < maxLimitNumber) {
                number++;
            }
        } else if (view == ivReduce) {
            if (number > minLimitNumber) {
                number--;
            }
        }
        setNumberView();
    }


    private void setNumberView() {
        isButtonChange = true;
        String numberStr = String.valueOf(number);
        etAmount.setText(numberStr);
        etAmount.setSelection(numberStr.length());
        ivAdd.setEnabled(number < maxLimitNumber);
        ivAdd.setImageResource(number < maxLimitNumber ? addEnableImage : addDisEnableImage);
        ivReduce.setEnabled(number > minLimitNumber);
        ivReduce.setImageResource(number > minLimitNumber ? reduceEnableImage : reduceDisEnableImage);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setMaxLimitNumber(int maxLimitNumber) {
        this.maxLimitNumber = maxLimitNumber;
    }

    public void setMinLimitNumber(int minLimitNumber) {
        this.minLimitNumber = minLimitNumber;
    }
}
