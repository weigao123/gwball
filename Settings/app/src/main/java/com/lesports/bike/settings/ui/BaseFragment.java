package com.lesports.bike.settings.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lesports.bike.settings.R;
import com.lesports.bike.settings.utils.ActivityUtils;
import com.lesports.bike.settings.utils.DensityUtils;
import com.lesports.bike.settings.widget.TitleBar;

/**
 * Created by gaowei3 on 2016/5/16.
 */
public abstract class BaseFragment extends Fragment {
    public static final String FRAGMENT_CLASS = "fragment_class";

    protected abstract void initViewAndData();
    protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    protected abstract String getTitleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TitleBar titleBar = (TitleBar) getActivity().findViewById(R.id.title_bar);
        if (titleBar != null) {
            ImageView leftImageView = (ImageView) titleBar.findViewById(R.id.left_image);
            TextView titleView = (TextView) titleBar.findViewById(R.id.title);
            leftImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ActivityUtils.isSingleActivity(getActivity())) {
                        getActivity().finish();
                    }
                }
            });
            if (getResources().getString(R.string.setting).equals(getTitleName())) {
                leftImageView.setImageResource(R.drawable.menu);
                titleBar.setPadding(DensityUtils.dp2px(getActivity(), 12), 0,
                                                   DensityUtils.dp2px(getActivity(), 12), 0);
            }
            titleView.setText(getTitleName());
        }

        initViewAndData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
