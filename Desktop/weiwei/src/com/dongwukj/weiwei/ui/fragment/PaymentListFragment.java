package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.OrderStatisticRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.PaymentEntity;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.LoginActivity;

/**
 * Created by sunjaly on 15/4/14.
 */
public class PaymentListFragment extends AbstractHeaderFragment {

	private int[] images={R.drawable.weiwei_pay_yue,R.drawable.weiwei_pay_zhifubao,R.drawable.weiwei_pay_weixin,R.drawable.weiwei_pay_weixin};
    private ListView payment_list_view;
    private PaymentListAdapter paymentListAdapter;

    private Map<Integer,Boolean> checkList;
    private ArrayList<PaymentEntity> paymentEntityArrayList;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.payment_list_layout,container,false);

        return view;
    }

    @Override
    protected String setTitle() {
        return "支付方式";
    }

    @Override
    protected void findView(View v) {
        paymentEntityArrayList=new ArrayList<PaymentEntity>();
        /*Object object=activity.getIntent().getSerializableExtra("list");
        if(object!=null){
            paymentEntityArrayList.addAll((ArrayList<PaymentEntity>)object);
        }*/
        
       


        payment_list_view=(ListView)v.findViewById(R.id.payment_list_view);
       


        payment_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(Integer index:checkList.keySet()){
                    checkList.put(index,false);
                }
                checkList.put(position,true);
                paymentListAdapter.notifyDataSetChanged();
                Log.d("item","click");
            }
        });
         
       

        setRightButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnResult();
                activity.finish();
            }
        },"确定");
        getStatistics();
    }

    private void returnResult(){
        for(int i=0;i<paymentEntityArrayList.size();i++){
            if(checkList.get(i)){
                PaymentEntity paymentEntity=paymentEntityArrayList.get(i);
                Intent intent=new Intent();
                intent.putExtra("paymentEntity",paymentEntity);
                activity.setResult(Activity.RESULT_OK,intent);
                break;

            }
        }
    }


    private void getStatistics() {
		BaseRequestClient client = new BaseRequestClient(activity);
		UserResult userResult = baseApplication.getUserResult();
		OrderStatisticRequest Request = new OrderStatisticRequest();
		client.httpPostByJson("Phonewallet", userResult, Request, PaymentEntitysResult.class, new BaseRequestClient.RequestClientCallBack<PaymentEntitysResult>() {

			@Override
			public void callBack(PaymentEntitysResult result) {
				if(result!=null){
					if(result.getCode()==BaseResult.CodeState.Success.getCode()){
						
						paymentEntityArrayList=result.getPayPluginList();
						 checkList=new HashMap<Integer, Boolean>();
					        for(int i=0;i<paymentEntityArrayList.size();i++){
					            checkList.put(i,false);
					        }
					        checkList.put(0,true);
					        paymentListAdapter=new PaymentListAdapter();
					        payment_list_view.setAdapter(paymentListAdapter);
					}else{
						Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(activity, activity.getResources().getString(R.string.data_error), Toast.LENGTH_SHORT).show();
				}
				
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void logOut(boolean isLogOut,PaymentEntitysResult result) {FinalDb finalDb=FinalDb.create(activity);
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
		
		
	};


    private class PaymentListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return paymentEntityArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return paymentEntityArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            if(convertView==null){
                convertView=View.inflate(activity,R.layout.payment_list_item,null);
                viewHolder=new ViewHolder();
                viewHolder.imageView=(ImageView)convertView.findViewById(R.id.payment_list_item_img);
                viewHolder.textView=(TextView)convertView.findViewById(R.id.payment_list_item_text);
                viewHolder.checkBox=(CheckBox)convertView.findViewById(R.id.payment_list_item_check);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder)convertView.getTag();
            }
            viewHolder.textView.setText(paymentEntityArrayList.get(position).getKey());

            viewHolder.checkBox.setChecked(checkList.get(position));
            viewHolder.imageView.setImageResource(images[position]);
            /*viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                       for(Integer index:checkList.keySet()){
                           checkList.put(index,false);
                       }
                    checkList.put(position,isChecked);
                    notifyDataSetChanged();

                }
            });*/

            return convertView;
        }

        private class ViewHolder{
            public ImageView imageView;
            public TextView textView;
            public CheckBox checkBox;
        }
    }


}
