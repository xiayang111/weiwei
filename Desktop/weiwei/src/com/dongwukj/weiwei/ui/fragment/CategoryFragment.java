package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.result.CategoryEntity;
import com.dongwukj.weiwei.ui.widget.MyGridView;

import de.greenrobot.event.EventBus;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryFragment extends BaseFragment {
	private ArrayList<CategoryEntity> list=new ArrayList<CategoryEntity>();
	private MyGridView gd;
	private Itemclick itemclick;
	private FinalBitmap fb;
/*	public CategoryFragment(ArrayList<CategoryEntity> list,Itemclick itemclick) {
		super();
		this.list = list;
		this.itemclick=itemclick;
		fb = FinalBitmap.create(activity);
		fb.configLoadfailImage(R.drawable.default_small);
		fb.configLoadingImage(R.drawable.default_small);
	}
	*/
	

	@Override
	public View setView_parent(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.category,null);
		return view;
	}

	@Override
	public void setListener() {
		
		gd.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				itemclick.itemClick(list.get(position).getCateId());
				Toast.makeText(activity, list.get(position).getName(), 0).show();;
			}
		});
	}
	public void setCategoryFragment(ArrayList<CategoryEntity> list,Itemclick itemclick){
		this.list.clear();
		this.list.addAll(list);
		this.itemclick=itemclick;
		fb = FinalBitmap.create(activity);
		fb.configLoadfailImage(R.drawable.default_small);
		fb.configLoadingImage(R.drawable.default_small);
	} 
	
	@Override
	public void initview() {
		gd = (MyGridView) view_parent.findViewById(R.id.gd);
		Myadapter adapter=new Myadapter();
		gd.setAdapter(adapter);
		//gd = (MyGridView) view_parent.findViewById(R.id.gd);
		//Myadapter adapter=new Myadapter();
		//gd.setAdapter(adapter);
	}
	class Myadapter extends BaseAdapter{
		private Viewholder holder;
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView==null) {
				convertView= View.inflate(activity, R.layout.categoryitem, null);
				holder=new Viewholder();
				holder.img=(ImageView) convertView.findViewById(R.id.img);
				holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
				convertView.setTag(holder);
			}else {
				holder=(Viewholder) convertView.getTag();
			}
			holder.tv_name.setText(list.get(position).getName());
			fb.display(holder.img,list.get(position).getIcon());
			return convertView;
		}
		
	}
	class Viewholder{
		public ImageView img;
		public TextView tv_name;
	}
	public interface Itemclick{
		public void itemClick(int id);
	}
}
