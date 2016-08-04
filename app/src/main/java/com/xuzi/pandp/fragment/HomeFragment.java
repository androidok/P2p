package com.xuzi.pandp.fragment;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;
import com.xuzi.pandp.R;
import com.xuzi.pandp.common.AppNetConfig;
import com.xuzi.pandp.common.BaseFragmrnt;
import com.xuzi.pandp.entity.Image;
import com.xuzi.pandp.entity.Index;
import com.xuzi.pandp.entity.Product;
import com.xuzi.pandp.ui.LoadingPage;
import com.xuzi.pandp.ui.MyScrollView;
import com.xuzi.pandp.ui.RoundProgress;

import java.util.List;

import butterknife.Bind;

public class HomeFragment extends BaseFragmrnt {
    @Bind(R.id.title_letf)
    ImageView titleLetf;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.title_right)
    ImageView titleRight;
    @Bind(R.id.vp_barner)
    ViewPager vpBarner;
    @Bind(R.id.circle_barner)
    CirclePageIndicator circleBarner;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.p_progresss)
    RoundProgress pProgresss;
    @Bind(R.id.p_yearlv)
    TextView pYearlv;
    @Bind(R.id.button1)
    Button button1;
    @Bind(R.id.myScrollView)
    MyScrollView myScrollView;


    private Index index;
    private int totalProgress;

    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.INDEX;
    }

    @Override
    protected void initData(LoadingPage.ResultState resultState) {
        if (resultState.getContent() != null) {
            //网络请求结构不为空时才会进行解析
            index = new Index();
            JSONObject jsonObject = JSON.parseObject(resultState.getContent());
            String proInfo = jsonObject.getString("proInfo");
            Product product = JSON.parseObject(proInfo, Product.class);
            String imageArr = jsonObject.getString("imageArr");
            List<Image> imageList = JSON.parseArray(imageArr, Image.class);
            index.product = product;
            index.imageList = imageList;

            vpBarner.setAdapter(new MyAdapter());
            //将viewpager交给指示器
            circleBarner.setViewPager(vpBarner);
            totalProgress = Integer.parseInt(index.product.progress);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int tempProhress = 0;
                    while (tempProhress <= totalProgress) {
                        int rs = tempProhress * 2;
                        pProgresss.setProgress(tempProhress);
                        pProgresss.setRoundProgressColor(Color.argb(55 + rs, 55 + rs, 200 - rs, 200 - rs));
                        tempProhress++;
                        SystemClock.sleep(50);
                    }
                }
            }).start();
        }
    }


    @Override
    protected void initTitle() {
        titleLetf.setVisibility(View.INVISIBLE);
        titleRight.setVisibility(View.INVISIBLE);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return index.imageList == null ? 0 : index.imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String url = AppNetConfig.BASEURL + index.imageList.get(position).IMAURL;
            Log.e("xuzi", url);
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //从网络上加载图片，本身会进行缓存
            Picasso.with(getActivity()).load(url).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
