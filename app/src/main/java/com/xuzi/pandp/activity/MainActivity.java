package com.xuzi.pandp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuzi.pandp.R;
import com.xuzi.pandp.fragment.HomeFragment;
import com.xuzi.pandp.fragment.MeFragment;
import com.xuzi.pandp.fragment.MoreFragment;
import com.xuzi.pandp.fragment.TouziFragment;
import com.xuzi.pandp.util.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {

    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.iv_touzi)
    ImageView ivTouzi;
    @BindView(R.id.tv_touzi)
    TextView tvTouzi;
    @BindView(R.id.ll_touzi)
    LinearLayout llTouzi;
    @BindView(R.id.iv_me)
    ImageView ivMe;
    @BindView(R.id.tv_me)
    TextView tvMe;
    @BindView(R.id.ll_me)
    LinearLayout llMe;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    private HomeFragment homeFragment;
    private TouziFragment touziFragment;
    private MeFragment meFragment;
    private MoreFragment moreFragment;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        setSelect(0);//默认是首页
    }

    @OnClick({R.id.ll_home, R.id.ll_touzi, R.id.ll_me, R.id.ll_more})
    public void changeTab(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                setSelect(0);
                break;
            case R.id.ll_touzi:
                setSelect(1);
                break;
            case R.id.ll_me:
                setSelect(2);
                break;
            case R.id.ll_more:
                setSelect(3);
                break;
        }
    }

    //选择Fragment
    public void setSelect(int i) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        hideFragment();
        reSetTab();
        switch (i) {
            case 0:
                //首页
                if (homeFragment == null) {
                    //单例模式
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.content, homeFragment);
                }
                ivHome.setImageResource(R.mipmap.bid01);
                tvHome.setTextColor(getResources().getColor(R.color.home_back_selected));
                transaction.show(homeFragment);

                break;
            case 1:
                //投资
                if (touziFragment == null) {
                    touziFragment = new TouziFragment();
                    transaction.add(R.id.content, touziFragment);
                }
                ivTouzi.setImageResource(R.mipmap.bid03);
                tvTouzi.setTextColor(getResources().getColor(R.color.home_back_selected));
                transaction.show(touziFragment);
                break;
            case 2:
                //我的资产
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    transaction.add(R.id.content, meFragment);
                }
                ivMe.setImageResource(R.mipmap.bid05);
                tvMe.setTextColor(UiUtils.getColor(R.color.home_back_selected));
                transaction.show(meFragment);
                break;
            case 3:
                //更多
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    transaction.add(R.id.content, moreFragment);
                }
                ivMore.setImageResource(R.mipmap.bid07);
                tvMore.setTextColor(getResources().getColor(R.color.home_back_selected));
                transaction.show(moreFragment);
                break;
        }
        transaction.commit();
    }

    private void reSetTab() {
        ivHome.setImageResource(R.mipmap.bid02);
        ivTouzi.setImageResource(R.mipmap.bid04);
        ivMe.setImageResource(R.mipmap.bid06);
        ivMore.setImageResource(R.mipmap.bid08);

        tvHome.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tvTouzi.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tvMe.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tvMore.setTextColor(getResources().getColor(R.color.home_back_unselected));

    }

    //隐藏Fragment
    private void hideFragment() {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (touziFragment != null) {
            transaction.hide(touziFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }
    }
}
