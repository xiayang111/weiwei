package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.BaseRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.CategoryEntity;
import com.dongwukj.weiwei.idea.result.EventBusEntity;
import com.dongwukj.weiwei.idea.result.PhoneGetcategoryResult;
import com.dongwukj.weiwei.idea.result.UpdateClassifyEntity;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.fragment.CategoryFragment.Itemclick;

import de.greenrobot.event.EventBus;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ClassifyMenu extends BaseFragment {
	private int cateId=0;
	ArrayList<CategoryEntity> categories=new ArrayList<CategoryEntity>();
	private ListView lv_classify;
	private ClassifyAdapter adapter;
	private DrawerLayout dw;
	public ClassifyMenu(DrawerLayout dw) {
		this.dw=dw;
	}

	@Override
	public View setView_parent(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.classifymenuone, null);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub

	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	public void onEventMainThread(EventBusEntity entity){
		  
	}
	public void onEventMainThread(String type) {
		if (type.equals("login")) {
			getCategory();
			
		}else if (type.equals("UpdateClassify")) {
			getCategory();
		}
	}
	@Override
	public void initview() {
		lv_classify = (ListView) view_parent.findViewById(R.id.lv_classify);
		adapter = new ClassifyAdapter();
		lv_classify.setAdapter(adapter);
		getCategory();
		lv_classify.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			for (CategoryEntity categoryEntity : categories) {
				if (categoryEntity==categories.get(arg2)) {
					categoryEntity.setChecked(true);
				}else {
					categoryEntity.setChecked(false);
				}
			}	
			adapter.notifyDataSetChanged();
			dw.closeDrawer(Gravity.LEFT);
			cateId=categories.get(arg2).getCateId();
			EventBus.getDefault().post(new EventBusEntity(cateId, true));
			}
		});
	}
	 private void getCategory() {
			BaseRequestClient client=new BaseRequestClient(activity);
			client.httpPostByJsonNew("PhoneGetcategory",baseApplication.getUserResult(), new BaseRequest(), PhoneGetcategoryResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneGetcategoryResult>() {

				@Override
				public void callBack(PhoneGetcategoryResult result) {
					if (result!=null) {
						if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
							if (result.getCategories().size()>0) {
								categories.clear();
								adapter.notifyDataSetChanged();
								categories.addAll(result.getCategories());
								result.getCategories().get(0).setChecked(true);
								adapter.notifyDataSetChanged();
							}else {
								Toast.makeText( activity, "暂时没有分类列表", Toast.LENGTH_SHORT).show();	
							}
								//cateId=result.getCategories().get(0).getCateId();
								
						}else {
							
							Toast.makeText( activity, result.getMessage(), Toast.LENGTH_SHORT).show();
						}
					}else {
						Toast.makeText( activity,activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
						
					}
					
				}

				@Override
				public void loading(long count, long current) {
					
				}

				@Override
				public void logOut(PhoneGetcategoryResult result) {
					FinalDb finalDb=FinalDb.create(activity);
					List<UserResult> findAllByWhere = finalDb.findAllByWhere(
							UserResult.class, "isLogin=1");
					for (UserResult userResult : findAllByWhere) {
						userResult.setLogin(false);
						finalDb.update(userResult);
					}
					baseApplication.setUserResult(null);
					Intent intent = new Intent(activity, LoginActivity.class);
					intent.putExtra("isLoginOut", true);
					startActivity(intent);
				}
			});
		}
	   public void onEventMainThread(UpdateClassifyEntity entity){
		   if (entity.getName().equals("updateClassify")) {
			   for (CategoryEntity categoryEntity : categories) {
					if (categoryEntity.getCateId()==entity.getCateId()) {
						categoryEntity.setChecked(true);
					}else {
						categoryEntity.setChecked(false);
					}
				}	
			   adapter.notifyDataSetChanged();
		}
	   }
	class ClassifyAdapter extends BaseAdapter{
		private Viewholder holder;
		private FinalBitmap fb;
		
		public ClassifyAdapter() {
			super();
			fb = FinalBitmap.create(activity);
			fb.configLoadfailImage(R.drawable.default_small);
			fb.configLoadingImage(R.drawable.default_small);
		}

		@Override
		public int getCount() {
			return categories.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView==null) {
				convertView=View.inflate(activity, R.layout.classify_item,null);
				holder=new Viewholder();
				holder.classify_img=(ImageView) convertView.findViewById(R.id.classify_img);
				holder.ll_classfy=(RelativeLayout) convertView.findViewById(R.id.ll_classfy);
				holder.select=(ImageView) convertView.findViewById(R.id.select);
				holder.classify_name=(TextView) convertView.findViewById(R.id.classify_name);
				convertView.setTag(holder);
			}else {
				holder=(Viewholder) convertView.getTag();
			}
			CategoryEntity entity = categories.get(position);
			if (entity.isChecked()) {
				holder.select.setVisibility(View.VISIBLE);
				holder.ll_classfy.setBackgroundResource(R.color.white);
				
			}else {
				holder.select.setVisibility(View.GONE);
				holder.ll_classfy.setBackgroundResource(R.drawable.classfy_bg);
			}
			fb.display(holder.classify_img, entity.getIcon());
			holder.classify_name.setText(entity.getName());
			return convertView;
		}
		
	}
	class Viewholder{
		
		public ImageView  classify_img;
		public RelativeLayout  ll_classfy;
		public ImageView  select;
		public TextView  classify_name;
	}
}
