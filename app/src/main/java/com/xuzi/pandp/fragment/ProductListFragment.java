package com.xuzi.pandp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.xuzi.pandp.R;
import com.xuzi.pandp.common.AppNetConfig;
import com.xuzi.pandp.entity.Product;
import com.xuzi.pandp.ui.RoundProgress;
import com.xuzi.pandp.util.UiUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xuzi on 8/3/2016.
 */
public class ProductListFragment extends Fragment {
    @Bind(R.id.lv)
    ListView lv;
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = UiUtils.inflate(R.layout.fragment_product_list);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        asyncHttpClient.get(AppNetConfig.PRODUCT, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {
                JSONObject jsonObject = JSON.parseObject(content);
                if (jsonObject.getBoolean("success")) {
                    String data = jsonObject.getString("data");
                    List<Product> products = JSON.parseArray(data, Product.class);
                    lv.setAdapter(new MyAdapter(products));
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

/*    private class MyAdapter1 extends MyBaseAdapter1<Product> {

        public MyAdapter1(List<Product> list) {
            super(list);
        }

        @Override
        protected BaseHolder getHolder() {
            return new MyHolder();
        }
    }

    private class MyHolder extends BaseHolder<Product> {


        @Bind(R.id.p_name)
        TextView pName;
        @Bind(R.id.p_money)
        TextView pMoney;
        @Bind(R.id.p_yearlv)
        TextView pYearlv;
        @Bind(R.id.p_suodingdays)
        TextView pSuodingdays;
        @Bind(R.id.p_minzouzi)
        TextView pMinzouzi;
        @Bind(R.id.p_progresss)
        RoundProgress pProgresss;

        @Override
        protected View initView() {
            View view = UiUtils.inflate(R.layout.item_product_list);
            ButterKnife.bind(MyHolder.this, view);
            return view;
        }

        @Override
        protected void refreshView() {
            Product product = this.getData();
            //设置数据
            pMinzouzi.setText(product.minTouMoney);
            pMoney.setText(product.money);
            pName.setText(product.name);
            pSuodingdays.setText(product.suodingDays);
            pYearlv.setText(product.yearLv);
            pProgresss.setProgress(Integer.parseInt(product.progress));

        }
    }*/


    private class MyAdapter extends BaseAdapter {
        private List<Product> products;

        public MyAdapter(List<Product> products) {
            this.products = products;
        }

        @Override
        public int getCount() {
            return products == null ? 0 : products.size();
        }

        @Override
        public Object getItem(int position) {
            return products.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Product product = products.get(position);
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //设置数据
            viewHolder.pMinzouzi.setText(product.minTouMoney);
            viewHolder.pMoney.setText(product.money);
            viewHolder.pName.setText(product.name);
            viewHolder.pSuodingdays.setText(product.suodingDays);
            viewHolder.pYearlv.setText(product.yearLv);
            viewHolder.pProgresss.setProgress(Integer.parseInt(product.progress));
            return convertView;
        }
    }

    static class ViewHolder {
        @Bind(R.id.p_name)
        TextView pName;
        @Bind(R.id.p_money)
        TextView pMoney;
        @Bind(R.id.p_yearlv)
        TextView pYearlv;
        @Bind(R.id.p_suodingdays)
        TextView pSuodingdays;
        @Bind(R.id.p_minzouzi)
        TextView pMinzouzi;
        @Bind(R.id.p_progresss)
        RoundProgress pProgresss;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
