package com.lesports.bike.settings.ui;

import android.app.admin.DevicePolicyManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.internal.widget.LockPatternUtils;
import com.lesports.bike.settings.R;
import com.lesports.bike.settings.widget.PasswordInput;

/**
 * Created by gaowei3 on 2016/5/25.
 */
public class PasswordFragment extends BaseFragment implements View.OnClickListener {

    public static final String PASSWORD_TODO = "todo";
    public static final int PASSWORD_ON_LOCK = 1;
    public static final int PASSWORD_OFF_LOCK = 2;
    public static final int PASSWORD_MODIFY = 3;

    private LockPatternUtils mLockPatternUtils;
    PasswordInput mPasswordInput;
    String[] mInput = new String[]{"", "", "", ""};
    private TextView mPasswordNotMatchTip;
    private TextView mPasswordCheckNewTip;
    private int mTodo = 0;
    private String mLastInput;
    private Handler mHandle;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_password, container, false);
    }

    @Override
    protected String getTitleName() {
        return getResources().getString(R.string.password_manager);
    }

    @Override
    protected void initViewAndData() {
        getActivity().findViewById(R.id.password_button_0).setOnClickListener(this);
        getActivity().findViewById(R.id.password_button_1).setOnClickListener(this);
        getActivity().findViewById(R.id.password_button_2).setOnClickListener(this);
        getActivity().findViewById(R.id.password_button_3).setOnClickListener(this);
        getActivity().findViewById(R.id.password_button_4).setOnClickListener(this);
        getActivity().findViewById(R.id.password_button_5).setOnClickListener(this);
        getActivity().findViewById(R.id.password_button_6).setOnClickListener(this);
        getActivity().findViewById(R.id.password_button_7).setOnClickListener(this);
        getActivity().findViewById(R.id.password_button_8).setOnClickListener(this);
        getActivity().findViewById(R.id.password_button_9).setOnClickListener(this);
        getActivity().findViewById(R.id.password_button_cancel).setOnClickListener(this);
        getActivity().findViewById(R.id.password_button_delete).setOnClickListener(this);
        mPasswordInput = (PasswordInput)getActivity().findViewById(R.id.password_input);
        mPasswordNotMatchTip = (TextView)getActivity().findViewById(R.id.password_not_match);
        mPasswordCheckNewTip = (TextView)getActivity().findViewById(R.id.password_check_new);

        mLockPatternUtils = new LockPatternUtils(getActivity());
        mTodo = getActivity().getIntent().getIntExtra(PasswordFragment.PASSWORD_TODO, 0);
        mHandle = new Handler();
    }

    private void verifyPassword() {
        if ("".equals(mInput[3])) {
            return;
        }

        String password = "";
        for (int i = 0; i < 4; i++) {
            password += mInput[i];
        }

        if (mTodo == PASSWORD_ON_LOCK) {
            if (mLastInput == null) {
                mLastInput = password;
                clearPassword();
                mPasswordCheckNewTip.setText(R.string.password_input_check_new);
                return;
            }
            if (!password.equals(mLastInput)) {
                mPasswordNotMatchTip.setVisibility(View.VISIBLE);
                mLastInput = null;
                clearPassword();
                mPasswordCheckNewTip.setText(R.string.password_input);
                return;
            }
            if (mLockPatternUtils.checkPassword(password)) {
                mLockPatternUtils.saveLockPassword(password, DevicePolicyManager.PASSWORD_QUALITY_NUMERIC, false);
                Toast.makeText(getActivity(), getResources().getText(R.string.password_set_success), Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
        if (mTodo == PASSWORD_OFF_LOCK) {
            if (mLockPatternUtils.checkPassword(password)) {
                mLockPatternUtils.clearLock(true);
                getActivity().finish();
            } else {
                clearPassword();
                Toast.makeText(getActivity(), "密码错误", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void clearPassword() {
        for (int i = 0; i < 4; i++) {
            mInput[i] = "";
            mPasswordInput.setPasswordDot(-1);
        }
    }

    @Override
    public void onClick(View v) {
        String input = null;
        switch (v.getId()) {
            case R.id.password_button_0:
                input = "0";
                break;
            case R.id.password_button_1:
                input = "1";
                break;
            case R.id.password_button_2:
                input = "2";
                break;
            case R.id.password_button_3:
                input = "3";
                break;
            case R.id.password_button_4:
                input = "4";
                break;
            case R.id.password_button_5:
                input = "5";
                break;
            case R.id.password_button_6:
                input = "6";
                break;
            case R.id.password_button_7:
                input = "7";
                break;
            case R.id.password_button_8:
                input = "8";
                break;
            case R.id.password_button_9:
                input = "9";
                break;
            case R.id.password_button_cancel:
                getActivity().finish();
                return;
            case R.id.password_button_delete:
                for (int i = 3; i >= 0; i--) {
                    if (!"".equals(mInput[i])) {
                        mInput[i] = "";
                        mPasswordInput.setPasswordDot(i-1);
                        break;
                    }
                }
                return;
        }
        for (int i = 0; i < 4; i++) {
            if ("".equals(mInput[i])) {
                mInput[i] = input;
                mPasswordInput.setPasswordDot(i);
                break;
            }
        }
        mPasswordNotMatchTip.setVisibility(View.INVISIBLE);
        mHandle.postDelayed(new Runnable() {
            @Override
            public void run() {
                verifyPassword();
            }
        }, 50);
    }
}