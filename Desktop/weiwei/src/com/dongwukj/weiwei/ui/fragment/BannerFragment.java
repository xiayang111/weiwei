package com.dongwukj.weiwei.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.AdEntity;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2015/3/10.
 * 用于显示
 */
public class BannerFragment extends Fragment {

    private LayoutInflater layoutInflater;
    private ViewPager vp_show;
    private TextView tv_description;
    private LinearLayout ll_viewgroup;
    private List<ImageView> imageViewList = new ArrayList<ImageView>();
    private int lastposition = 0;// 记录上一个页面的位置
    private boolean isRunning = false;// 记录ViewPager正在播放

    private View parentView;
    private Context context;
    private FinalBitmap fb;

    private List<AdEntity> bannerEntityList=new ArrayList<AdEntity>();
    private BannerCliclkListner bListner;
    private MyAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView=inflater.inflate(R.layout.banner_view,container,false);
        context=getActivity().getApplicationContext();
        initView(parentView);
        return parentView;
    }

    //设置广告，刷新信息
    public void setBannerEntityList(List<AdEntity> bannerEntityList) {
        this.bannerEntityList = bannerEntityList;
        reload();
    }

    private void reload(){
        if(this.bannerEntityList!=null){
            imageViewList.clear();
            ll_viewgroup.removeAllViews();
            for(AdEntity entity:bannerEntityList){
                // 初始化图片资源
                ImageView iv = new ImageView(context);
                iv.setScaleType(ScaleType.FIT_XY);
                String imgUrl=entity.getBody();
                if(!NetworkUtil.checkUrl(imgUrl)){
                    imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
                }
                fb.display(iv, imgUrl);
                imageViewList.add(iv);
                // 初始化指示点
                ImageView iv_point = new ImageView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.rightMargin = px2dip(context, 10);
                params.topMargin = px2dip(context, 5);
                params.bottomMargin=px2dip(context,45);
                iv_point.setLayoutParams(params);
                iv_point.setBackgroundResource(R.drawable.imageview_selector);
                iv_point.setEnabled(false);
                ll_viewgroup.addView(iv_point);
            }
            if(this.bannerEntityList.size()>0){
                //tv_description.setText(this.bannerEntityList.get(0).getDescription());
                ll_viewgroup.getChildAt(0).setEnabled(true);
            }
            if (this.bannerEntityList.size()==0) {
            	vp_show.setBackgroundResource(R.drawable.default_big);
			}
            if(myAdapter!=null) myAdapter.notifyDataSetChanged();

        }
    }

    private void initView(View view){
        vp_show = (ViewPager) view.findViewById(R.id.vp_show);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        ll_viewgroup = (LinearLayout) view.findViewById(R.id.ll_viewgroup);
        fb = FinalBitmap.create(getActivity());//初始化FinalBitmap模块
        fb.configLoadingImage(R.drawable.default_big);
        fb.configLoadfailImage(R.drawable.default_big);
        //ll_viewgroup.getChildAt(0).setEnabled(true);

        // 设置ViewPager的Adapter
        myAdapter=new MyAdapter();
        vp_show.setAdapter(myAdapter);

        // 设置ViewPager的OnPageChangeListener事件
        vp_show.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * 显示一个新的页面时执行
             */
        	
            @Override
            public void onPageSelected(int position) {
            	tv_description.setText(bannerEntityList.get(position).getTitle());
                ll_viewgroup.getChildAt(position).setEnabled(true);
                ll_viewgroup.getChildAt(lastposition).setEnabled(false);
                lastposition = position;
            }

            /**
             * 页面正在滑动时调用
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            /**
             * 页面状态发生变化时调用
             */
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        isRunning = true;
        myHandler.sendEmptyMessageDelayed(0, 2000);
    }
    private Handler myHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if(imageViewList.size()>0){
                vp_show.setCurrentItem((vp_show.getCurrentItem() + 1)%imageViewList.size());
                if (isRunning) {
                    myHandler.sendEmptyMessageDelayed(0, 2000);
                }
            }

        };
    };

    private class MyAdapter extends PagerAdapter {
    	/**
         * 获得页面的总数
         */
        @Override
        public int getCount() {
            return imageViewList.size();
        }

        /**
         * 判断View和Object的对应关系
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 获得相应位置上的View
         *
         * @param container
         *            View的容器，其实就是ViewPager自身
         * @param position
         *            相应的位置
         */
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(imageViewList.get(position));
            ImageView view = imageViewList.get(position);
            view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
                    if(bListner!=null){
                        bListner.click(bannerEntityList.get(position));
                    }

					
				}
			});
            return imageViewList.get(position);
        }

        /**
         * 销毁对应位置上的Object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static class BannerEntity{
        private Integer id;
        private String  url;
        private String description;

        public void setDescription(String description) {
            this.description = description;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }
    }
    /**
     * 设置图片点击事件
     */
    public void setBannerCliclkListner(BannerCliclkListner bListner){
    	this.bListner=bListner;
    }
    
    public interface BannerCliclkListner{
    	void click(AdEntity entity);
    }


}
