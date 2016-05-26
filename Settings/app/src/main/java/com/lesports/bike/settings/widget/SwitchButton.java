package com.lesports.bike.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lesports.bike.settings.R;

/**
 * Created by gaowei3 on 2016/5/25.
 */
public class SwitchButton extends FrameLayout {
    private ImageView mOnImage;
    private ImageView mOffImage;

    public SwitchButton(Context context) {
        super(context);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseStyle(context, attrs);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseStyle(context, attrs);
    }

    private void parseStyle(Context context, AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);
        Drawable onDrawable = ta.getDrawable(R.styleable.SwitchButton_switchOnImage);
        Drawable offDrawable = ta.getDrawable(R.styleable.SwitchButton_switchOffImage);
        int switchStatus = ta.getInt(R.styleable.SwitchButton_switchStatus, 0);
        ta.recycle();

        LayoutInflater.from(context).inflate(R.layout.widget_switch_button, this);
        mOnImage = (ImageView) findViewById(R.id.switch_on);
        mOffImage = (ImageView) findViewById(R.id.switch_off);
        if (onDrawable != null) {
            mOnImage.setImageDrawable(onDrawable);
        }
        if (offDrawable != null) {
            mOffImage.setImageDrawable(offDrawable);
        }
        if (switchStatus == 1) {
            setSwitchOff();
        }
    }

    /**
     * 开关是否为打开状态
     */
    public boolean isSwitchOn(){
        return mOnImage.getVisibility() == View.VISIBLE;
    }

    /**
     * 打开开关
     */
    public void setSwitchOn(){
        mOnImage.setVisibility(View.VISIBLE);
        mOffImage.setVisibility(View.INVISIBLE);
    }

    /**
     * 关闭开关
     */
    public void setSwitchOff(){
        mOnImage.setVisibility(View.INVISIBLE);
        mOffImage.setVisibility(View.VISIBLE);
    }
}
