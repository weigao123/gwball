package com.lesports.bike.settings.widget;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lesports.bike.settings.R;

/**
 * Created by gaowei3 on 2016/6/1.
 */
public class InputBox extends DialogFragment implements View.OnClickListener {

    private View mRoot;
    private TextView mWifiName;
    private EditText mWifiPassword;
    private ImageView mIsShowPassword;
    private TextView mLeftButton;
    private TextView mRightButton;

    private String mWifiSSID;
    private InputCallback mInputCallback;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.widget_input_box, container, false);
        return mRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().setCanceledOnTouchOutside(true);
        initViewAndData();
    }

    public void initViewAndData() {
        mWifiName = (TextView) mRoot.findViewById(R.id.wifi_ssid);
        mWifiPassword = (EditText) mRoot.findViewById(R.id.wifi_password);
        mIsShowPassword = (ImageView) mRoot.findViewById(R.id.wifi_show_password);
        mLeftButton = (TextView) mRoot.findViewById(R.id.input_box_lbtn);
        mRightButton = (TextView) mRoot.findViewById(R.id.input_box_rbtn);

        mLeftButton.setOnClickListener(this);
        mRightButton.setOnClickListener(this);
        mIsShowPassword.setOnClickListener(this);
        mWifiSSID = getArguments().getString("wifi_name");
        mWifiName.setText(mWifiSSID);
        Shader shader_gradient = new LinearGradient(0, 20, 0, 40, Color.parseColor("#00f79d"),
                Color.parseColor("#0cb8e2"), Shader.TileMode.CLAMP);
        mLeftButton.getPaint().setShader(shader_gradient);
        mRightButton.getPaint().setShader(shader_gradient);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.input_box_lbtn:
                dismiss();
                break;
            case R.id.wifi_show_password:
                if (mWifiPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    mWifiPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mIsShowPassword.setImageResource(R.drawable.input_box_checkbox_off);
                } else {
                    mWifiPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mIsShowPassword.setImageResource(R.drawable.input_box_checkbox_on);
                }
                mWifiPassword.setSelection(mWifiPassword.getText().length());
                break;
            case R.id.input_box_rbtn:
                if (mWifiPassword.getText().length() >= 1) {
                    mInputCallback.confirmCallback(mWifiPassword.getText().toString());
                    dismiss();
                }
                break;
        }
    }

    public void setInputCallback(InputCallback inputCallback) {
        this.mInputCallback = inputCallback;
    }

    public interface InputCallback {
        void confirmCallback(String input);
    }
}
