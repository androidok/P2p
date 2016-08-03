package com.xuzi.pandp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuzi.pandp.R;
import com.xuzi.pandp.util.UiUtils;

public class TouziFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return UiUtils.inflate(R.layout.fragment_touzi);
    }
}
