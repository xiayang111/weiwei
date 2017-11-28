package com.dongwukj.weiwei.ui.fragment;

import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.net.utils.NetworkUtil;

/**
 * Created by sunjaly on 15/4/2.
 */
public class FragmentProductDetailImageList extends Fragment {

    private List<String> urls;
    private FinalBitmap finalBitmap;

    private LinearLayout imageListLinearLayout;
	private SharedPreferences sp;
	private LinearLayout ll;
	private Context context;
    public FragmentProductDetailImageList(Context context) {
		this.context=context;
	}

	public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	ll = (LinearLayout) inflater.inflate(R.layout.loadphoto, null);
    	sp = context.getSharedPreferences("config", 0);
        imageListLinearLayout=new LinearLayout(context);
        imageListLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        imageListLinearLayout.setOrientation(LinearLayout.VERTICAL);
        finalBitmap=FinalBitmap.create(getActivity());
        finalBitmap.configLoadfailImage(R.drawable.weiwei_home_1);
        finalBitmap.configLoadingImage(R.drawable.weiwei_home_1);
        updateUI();
        return imageListLinearLayout;
    }

    public void updateUI(){
    	boolean isHint = sp.getBoolean("isHint", false);
    	
   /* 	if (isHint&&!NetworkUtil.isWiFiActive(context)) {
    		 imageListLinearLayout.removeAllViews();
    			TextView bt=(TextView)ll.findViewById(R.id.bt);
    			bt.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						 if(urls!=null && urls.size()>0){
					            imageListLinearLayout.removeAllViews();
					            for(String url:urls){
					                ImageView imageView=new ImageView(getActivity());
					                imageView.setAdjustViewBounds(true);
					                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
					                imageListLinearLayout.addView(imageView);
					                String imgUrl=url;
					                if(!NetworkUtil.checkUrl(imgUrl)){
					                    imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
					                }
					                finalBitmap.display(imageView,imgUrl);
					            }
					        }
					}
				});
    		  imageListLinearLayout.addView(ll);
    		  if(this.urls!=null && this.urls.size()>0){
		           
		            for(String url:this.urls){
		                ImageView imageView=new ImageView(getActivity());
		                imageView.setAdjustViewBounds(true);
		                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
		                imageListLinearLayout.addView(imageView);
		                String imgUrl=url;
		                if(!NetworkUtil.checkUrl(imgUrl)){
		                    imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
		                }
		                finalBitmap.display(imageView,"123"+imgUrl);
		            }
		        }
    		  
		}else {*/
			 if(this.urls!=null && this.urls.size()>0){
		            imageListLinearLayout.removeAllViews();
		            for(String url:this.urls){
		                ImageView imageView=new ImageView(getActivity());
		                imageView.setAdjustViewBounds(true);
		                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
		                imageListLinearLayout.addView(imageView);
		                String imgUrl=url;
		                if(!NetworkUtil.checkUrl(imgUrl)){
		                    imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
		                }
		                finalBitmap.display(imageView,imgUrl);
		            }
		        }
		//}
       
    }
}
