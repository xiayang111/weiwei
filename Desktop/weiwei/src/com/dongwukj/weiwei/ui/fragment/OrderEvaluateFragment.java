package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.OrderEvaluateEntity;
import com.dongwukj.weiwei.idea.request.OrderEvaluateRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.CartItemEntity;
import com.dongwukj.weiwei.idea.result.CartProductEntity;
import com.dongwukj.weiwei.idea.result.OrderProductEntity;
import com.dongwukj.weiwei.idea.result.OrderProductInfo;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.dongwukj.weiwei.ui.widget.MyListView;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;

public class OrderEvaluateFragment extends AbstractHeaderFragment {
	List<OrderEvaluateEntity> OrderEvaluates=new ArrayList<OrderEvaluateEntity>();
	Map<Integer, OrderEvaluateEntity> Evaluates_map=new HashMap<Integer, OrderEvaluateEntity>();
	private FinalBitmap fb;
	private OrderProductEntity entityForlist;
	private ArrayList<OrderProductInfo> list=new ArrayList<OrderProductInfo>();
	protected String tag="OrderEvaluateFragment";
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.orderevaluate_fragment, null);
		return view;
	}

	@Override
	protected String setTitle() {
		return activity.getResources().getString(R.string.orderevaluate);
	}

	@Override
	protected void findView(View v) {
		imgurl=activity.getIntent().getStringExtra("imgUrl");
		publish = (Button) v.findViewById(R.id.publish);
		logistics = (RatingBar) v.findViewById(R.id.logistics);
		deliver = (RatingBar) v.findViewById(R.id.deliver);
		serve = (RatingBar) v.findViewById(R.id.serve);
		fb = FinalBitmap.create(activity);
        fb.configLoadingImage(R.drawable.default_small);
        fb.configLoadfailImage(R.drawable.default_small);
		MyListView lv=(MyListView) v.findViewById(R.id.lv);
		//entityForlist = (OrderProductEntity) activity.getIntent().getSerializableExtra("detail_entity");
		//list = entityForlist.getOrderProductList();//1111111111111111111111111111111111111111111111111111111111111111111111111111111111
		 DataBase db=LiteOrm.newCascadeInstance(activity, BaseApplication.DB_NAME);
		 OrderProductEntity entity = db.queryAll(OrderProductEntity.class).get(0);
		 ArrayList<CartItemEntity> cartItemList = entity.getCartItemList();
		 for (CartItemEntity cartItemEntity : cartItemList) {
			if (cartItemEntity.getCartSuit()!=null) {
				ArrayList<CartProductEntity> productList = cartItemEntity.getCartSuit().getCartProductList();
				for (CartProductEntity cartProductEntity : productList) {
					list.add(cartProductEntity.getOrderProductInfo());
				}
			}else if (cartItemEntity.getCartProduct()!=null) {
				list.add(cartItemEntity.getCartProduct().getOrderProductInfo());
			}
		}
		MyBaseAdapter adapter=new MyBaseAdapter();
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(activity, ""+position, Toast.LENGTH_SHORT).show();
			
			}
		});
		publish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for (Integer key : Evaluates_map.keySet()) {
					OrderEvaluates.add(Evaluates_map.get(key));
				}
				evaluates();
			}
		});
	}
	protected void evaluates() {
		BaseRequestClient client=new BaseRequestClient(activity);
		UserResult result = baseApplication.getUserResult();
		OrderEvaluateRequest request=new OrderEvaluateRequest();
		request.setOrderEvaluates(OrderEvaluates);
		request.setLogistics(logistics.getRating());
		request.setServe(serve.getRating());
		request.setDeliver(deliver.getRating());
		client.httpPostByJson("Phonecomment", result, request, BaseResult.class, new BaseRequestClient.RequestClientCallBack<BaseResult>() {

			@Override
			public void callBack(BaseResult result) {
				if (getActivity()==null) {
					return;
				}
				if (result!=null) {
					if (result.getCode()==BaseResult.CodeState.Success.getCode()) {
						activity.setResult(Activity.RESULT_OK);
						activity.finish();
					}else {
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void loading(long count, long current) {
				
			}
			@Override
			public void logOut(boolean isLogOut,BaseResult result) {FinalDb finalDb=FinalDb.create(activity);
			List<UserResult> findAllByWhere = finalDb.findAllByWhere(
					UserResult.class, "isLogin=1");
			for (UserResult userResult : findAllByWhere) {
				userResult.setLogin(false);
				finalDb.update(userResult);
			}
			baseApplication.setUserResult(null);
			Intent intent = new Intent(activity, LoginActivity.class);
			intent.putExtra("isLoginOut", true);
			startActivity(intent);}
		});
	}
	public class ViewHolder{
        public TextView tv_ordername;
        public ImageView iv_ordericon;
        public TextView tv_orderprice;
        public TextView tv_buycount;
        public TextView tv_weight;
        public RatingBar start;
        public EditText et;
		public ViewHolder(View view) {
			iv_ordericon=(ImageView) view.findViewById(R.id.iv_ordericon); 
			tv_ordername=(TextView) view.findViewById(R.id.tv_ordername);
			tv_orderprice=(TextView) view.findViewById(R.id.tv_orderprice);
			//tv_buycount=(TextView) view.findViewById(R.id.tv_buycount);
			tv_weight=(TextView) view.findViewById(R.id.tv_weight);
			start=(RatingBar) view.findViewById(R.id.start);
			et=(EditText) view.findViewById(R.id.et);
		}
        
    }
	private String imgurl;
	private Button publish;
	private RatingBar logistics;
	private RatingBar deliver;
	private RatingBar serve;
	private class MyBaseAdapter extends BaseAdapter{
		View view;
		ViewHolder holder;
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			
			if (convertView==null) {
				view=View.inflate(activity, R.layout.orderevaluate_item, null);
				holder=new ViewHolder(view);
				holder.et.addTextChangedListener(new TextWatcher() {
					
					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						Evaluates_map.get(position).setMessage(s.toString());
					}
				});
				view.setTag(holder);
			}else {
				view=convertView;
				holder=(ViewHolder) view.getTag();
			}
            /*String imgUrl=list.get(position).getShowImg();
            if(!NetworkUtil.checkUrl(imgUrl)){
                imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
                imgurl.replace("{0}",list.get(position).getShowImg());
            }*/
			
			fb.display(holder.iv_ordericon,  imgurl.replace("{0}",list.get(position).getShowImg()));
			holder.tv_ordername.setText(list.get(position).getName());
			//holder.tv_orderprice.setText("￥"+list.get(position).getDiscountPrice());
			holder.tv_orderprice.setText(String.format("￥%.1f", list.get(position).getDiscountPrice()));
			holder.tv_weight.setText(list.get(position).getWeight()+"克");
			if (Evaluates_map.get(position)==null) {
				OrderEvaluateEntity entity=new OrderEvaluateEntity();
				entity.setOrderId(list.get(position).getOid());
				entity.setRecordId(list.get(position).getRecordId());
				entity.setStar(holder.start.getRating());
				entity.setMessage(holder.et.getHint().toString());
				Evaluates_map.put(position, entity);
			}
			holder.start.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				public void onRatingChanged(RatingBar ratingBar, float rating,
						boolean fromUser) {
					Evaluates_map.get(position).setStar(rating);
				}
			});
			
			return view;
		}
	} 
}
