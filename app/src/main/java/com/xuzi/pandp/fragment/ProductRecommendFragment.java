package com.xuzi.pandp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuzi.pandp.R;
import com.xuzi.pandp.ui.randomLayout.StellarMap;
import com.xuzi.pandp.util.UiUtils;

import java.util.Random;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/12/16.
 */
public class ProductRecommendFragment extends Fragment {

    StellarMap stellarMap;

    private String[] datas = new String[]{"超级新手计划", "乐享活系列90天计划", "钱包计划", "30天理财计划(加息2%)", "90天理财计划(加息5%)", "180天理财计划(加息10%)",
            "林业局投资商业经营", "中学老师购买车辆", "屌丝下海经商计划", "新西游影视拍摄投资", "Java培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "阿里巴巴洗钱计划", "铁路局回款计划", "高级白领赢取白富美投资计划"
    };
    private Random random;

    private String[] one = new String[8];

    private String[] two = new String[8];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = UiUtils.inflate(R.layout.fragment_product_recommend);
        stellarMap = (StellarMap) view.findViewById(R.id.stellarMap);
        initData();
        return view;
    }

    private void initData() {
        for (int i = 0; i < 8; i++) {
            one[i] = datas[i];
        }

        for (int j = 0; j < 8; j++) {
            two[j] = datas[j + 8];
        }
        random = new Random();
        int padding = UiUtils.dp2px(5);
        stellarMap.setInnerPadding(padding, padding, padding, padding);
        stellarMap.setAdapter(new MyAdapter());
        //每组出现的数据组的搭配规则
        stellarMap.setRegularity(8, 8);
        //设置从哪一组开始做动画操作
        stellarMap.setGroup(0, true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class MyAdapter implements StellarMap.Adapter {
        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getCount(int group) {
            return 8;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            TextView tv = new TextView(getActivity());
            int r = random.nextInt(210);
            int g = random.nextInt(210);
            int b = random.nextInt(210);
            tv.setTextColor(Color.rgb(r, g, b));
            tv.setTextSize(UiUtils.dp2px(8) + random.nextInt(8));
            if (group == 0) {
                tv.setText(one[position]);
            } else if (group == 1) {
                tv.setText(two[position]);
            }
            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            return 1;
        }
    }
}
