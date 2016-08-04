package com.xuzi.pandp.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.viewpagerindicator.TabPageIndicator;
import com.xuzi.pandp.R;
import com.xuzi.pandp.common.BaseFragmrnt;
import com.xuzi.pandp.ui.LoadingPage;
import com.xuzi.pandp.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class TouziFragment extends BaseFragmrnt {

    @Bind(R.id.title_letf)
    ImageView titleLetf;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.title_right)
    ImageView titleRight;
    @Bind(R.id.tab_indicator)
    TabPageIndicator tabIndicator;
    @Bind(R.id.pager)
    ViewPager pager;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void initTitle() {
        titleLetf.setVisibility(View.INVISIBLE);
        titleRight.setVisibility(View.INVISIBLE);
        titleTv.setText("我要投资");
    }

    @Override
    protected void initData(LoadingPage.ResultState resultState) {
        initFragment();
        pager.setAdapter(new MyAdapter(getFragmentManager()));
        tabIndicator.setViewPager(pager);
    }

    private void initFragment() {
        ProductListFragment productListFragment = new ProductListFragment();
        ProductRecommendFragment productRecommendFragment = new ProductRecommendFragment();
        ProductHostFragment productHostFragment = new ProductHostFragment();
        fragmentList.add(productListFragment);
        fragmentList.add(productRecommendFragment);
        fragmentList.add(productHostFragment);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_touzi;
    }

    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    protected String getUrl() {
        return "";
    }

    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return UiUtils.getStringArray(R.array.touzi_tab)[position];
        }
    }
}
