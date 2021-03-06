package com.lesports.bike.settings.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.internal.widget.LockPatternUtils;
import com.lesports.bike.settings.R;
import com.lesports.bike.settings.application.SettingApplication;
import com.lesports.bike.settings.control.FingerprintControl;
import com.lesports.bike.settings.utils.ActivityUtils;
import com.lesports.bike.settings.widget.SwitchButton;

/**
 * Created by gaowei3 on 2016/5/16.
 */
public class SecurityFragment extends BaseFragment implements View.OnClickListener {
    private SwitchButton mSwitchButton;
    private TextView mPasswordManager;
    private TextView mFingerprintManager;
    private LockPatternUtils mLockPatternUtils;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_security, container, false);
    }

    @Override
    protected String getTitleName() {
        return getResources().getString(R.string.security_and_password);
    }

    @Override
    protected void initViewAndData() {
        mSwitchButton = (SwitchButton)getActivity().findViewById(R.id.security_switch);
        mPasswordManager = (TextView)getActivity().findViewById(R.id.password_manager);
        mFingerprintManager = (TextView)getActivity().findViewById(R.id.fingerprint_manager);
        mSwitchButton.setOnClickListener(this);
        mPasswordManager.setOnClickListener(this);
        mFingerprintManager.setOnClickListener(this);
        if (!FingerprintControl.getInstance(getActivity()).hasFingerPrint()) {
            getActivity().findViewById(R.id.fingerprint_manager_container).setVisibility(View.GONE);
            ((TextView)getActivity().findViewById(R.id.lockscreen_password_tip)).setText(getString(R.string.lockscreen_password_tip_no_fingerprint));
        }
        mLockPatternUtils = new LockPatternUtils(getActivity());
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.security_switch:
                int todo = mSwitchButton.isSwitchOn() ? PasswordFragment.PASSWORD_OFF_LOCK : PasswordFragment.PASSWORD_ON_LOCK;
                bundle.putInt(PasswordFragment.PASSWORD_TODO, todo);
                ActivityUtils.startFragmentActivity(getActivity(), PasswordFragment.class, bundle);
                break;
            case R.id.password_manager:
                if (mSwitchButton.isSwitchOn()) {
                    bundle.putInt(PasswordFragment.PASSWORD_TODO, PasswordFragment.PASSWORD_MODIFY);
                    ActivityUtils.startFragmentActivity(getActivity(), PasswordFragment.class, bundle);
                }
                break;
            case R.id.fingerprint_manager:
                if (mSwitchButton.isSwitchOn()) {
                    bundle.putInt(PasswordFragment.PASSWORD_TODO, PasswordFragment.PASSWORD_VERIFY);
                    ActivityUtils.startFragmentActivityForResult(this, PasswordFragment.class, PasswordFragment.PASSWORD_VERIFY, bundle);
                }
                break;
        }
    }

    private void onLockView() {
        mSwitchButton.setSwitchStatus(true);
        mPasswordManager.setTextColor(getResources().getColor(R.color.colorWhite));
        mFingerprintManager.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    private void offLockView() {
        mSwitchButton.setSwitchStatus(false);
        mPasswordManager.setTextColor(getResources().getColor(R.color.colorGray));
        mFingerprintManager.setTextColor(getResources().getColor(R.color.colorGray));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PasswordFragment.PASSWORD_VERIFY) {
            if (resultCode == Activity.RESULT_OK) {
                ActivityUtils.startFragmentActivity(getActivity(), FingerprintFragment.class);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshState();
    }

    private void refreshState() {
        if (mLockPatternUtils.isLockPasswordEnabled()) {
            Settings.Global.putInt(SettingApplication.getContext().getContentResolver(), Settings.Global.FINGERPRINT_UNLOCK, 1);
            onLockView();
        } else {
            Settings.Global.putInt(SettingApplication.getContext().getContentResolver(), Settings.Global.FINGERPRINT_UNLOCK, 0);
            offLockView();
        }
    }
}
