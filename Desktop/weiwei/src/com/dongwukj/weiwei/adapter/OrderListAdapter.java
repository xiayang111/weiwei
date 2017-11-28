package com.dongwukj.weiwei.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.NewHomeEntity;
import com.dongwukj.weiwei.idea.result.CartItemEntity;
import com.dongwukj.weiwei.idea.result.CartSuitEntity;
import com.dongwukj.weiwei.idea.result.NewOrderEntity;
import com.dongwukj.weiwei.idea.result.NewOrderProductEntity;
import com.dongwukj.weiwei.idea.result.OrderEntity;
import com.dongwukj.weiwei.idea.result.OrderProductEntity;
import com.dongwukj.weiwei.idea.result.OrderProductInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.widget.MyGridView;
import com.dongwukj.weiwei.ui.widget.MyGridViewNoClick;

import net.tsz.afinal.FinalBitmap;

/**
 * Created by pc on 2015/3/17.
 */
public class OrderListAdapter extends BaseAdapter {
	private buttonClick click;
    private Context context;
    private boolean isUndone;
    private List<NewOrderEntity> orderList;

	
  /*  public void setOrderList(List<NewOrderEntity> orderList){
    	this.orderList = orderList;
    	notifyDataSetChanged();
    }
    public void setOrderList(List<NewOrderEntity> orderList,String imgurl,String taocanImgurl){
    	this.orderList = orderList;
    	
    	notifyDataSetChanged();
    	
    }*/
    
    public OrderListAdapter(Context context, List<NewOrderEntity> orderList,boolean isUndone,buttonClick click) {
        this.context = context;
        this.orderList = orderList;
        this.isUndone=isUndone;
        this.click=click;

    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position-1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        FinalBitmap bp = FinalBitmap.create(context);
        if(convertView==null){
            convertView=View.inflate(context, R.layout.order_list_item,null);
            viewHolder=new ViewHolder();
            viewHolder.orderNumTextView=(TextView)convertView.findViewById(R.id.tv_ordernum); 			//订单号
            viewHolder.orderStateTextView=(TextView)convertView.findViewById(R.id.tv_orderstate);		//订单状态
            viewHolder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);           //小计:实付.
            viewHolder.product_name1=(TextView)convertView.findViewById(R.id.product_name1);           
            viewHolder.product_name2=(TextView)convertView.findViewById(R.id.product_name2);      
            viewHolder.product_weight1=(TextView)convertView.findViewById(R.id.product_weight1);          
            viewHolder.product_weight2=(TextView)convertView.findViewById(R.id.product_weight2);         
            viewHolder.ll_product2=(LinearLayout)convertView.findViewById(R.id.ll_product2);         
            
            viewHolder.bt_accept=(Button) convertView.findViewById(R.id.bt_accept);

           viewHolder.orderPayTextView=(TextView)convertView.findViewById(R.id.tv_orderpay);;

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        final NewOrderEntity orderEntity = orderList.get(position);
        viewHolder.tv_time.setText(formatTime(orderEntity.getAddtime()));
        viewHolder.orderNumTextView.setText(orderEntity.getOsn());
        if (orderEntity.getProducts().size()>1) {
        	  viewHolder.ll_product2.setVisibility(View.VISIBLE);
        	  viewHolder.product_name1.setText(orderEntity.getProducts().get(0).getName());
        	  viewHolder.product_weight1.setText((int)orderEntity.getProducts().get(0).getWeight()+orderEntity.getProducts().get(0).getUomname());
        	  viewHolder.product_name2.setText(orderEntity.getProducts().get(1).getName());
        	  if (orderEntity.getProducts().size()>2) {
        		  viewHolder.product_weight2.setText((int)orderEntity.getProducts().get(1).getWeight()+orderEntity.getProducts().get(0).getUomname()+" ...");
			}else {
				viewHolder.product_weight2.setText((int)orderEntity.getProducts().get(1).getWeight()+orderEntity.getProducts().get(0).getUomname());
			}
        	  
		}else {
			viewHolder.ll_product2.setVisibility(View.GONE);
			 viewHolder.product_name1.setText(orderEntity.getProducts().get(0).getName());
       	  viewHolder.product_weight1.setText((int)orderEntity.getProducts().get(0).getWeight()+orderEntity.getProducts().get(0).getUomname());
		}
        switch (orderEntity.getOrderstate()) {
		case 70:
			 viewHolder.orderStateTextView.setText("已付款");
			break;
		case 75:
			 viewHolder.orderStateTextView.setText("申请平台服务");
			break;
		case 80:
			 viewHolder.orderStateTextView.setText("订单已确认");
			break;
		case 85:
			 viewHolder.orderStateTextView.setText("完成分拣");
			break;
		case 90:
			 viewHolder.orderStateTextView.setText("配送中");
			break;
		case 120:
			viewHolder.orderStateTextView.setText("已经到达自提点");
			break;
		case 110:
			 viewHolder.orderStateTextView.setText("配送中");
			break;
		case 145:
			 viewHolder.orderStateTextView.setText("订单完成");
			break;
		case 150:
			 viewHolder.orderStateTextView.setText("换货");
			break;
		case 160:
			 viewHolder.orderStateTextView.setText("退货");
			break;
		case 200:
			 viewHolder.orderStateTextView.setText("订单已经取消（未退款）");
			break;
		case 210:
			viewHolder.orderStateTextView.setText("订单已经取消（已退款）");
			break;

		default:
			break;
		}
       viewHolder.orderPayTextView.setText(String.format("￥%.2f", orderEntity.getSurplusmoney()));
     //  viewHolder.orderStateTextView.setTextColor(context.getResources().getColor(R.color.red));
        if (!isUndone) {
        	
        	if (orderEntity.getOrderstate()==145) {
        		if (orderEntity.getIsreview()==1) {
            		viewHolder.bt_accept.setText("已评价");
            	}else {
    				viewHolder.bt_accept.setText("订单评价");
    			}
            	
            	viewHolder.bt_accept.setOnClickListener(new OnClickListener() {
            		public void onClick(View v) {
            			if (orderEntity.getIsreview()==1) {
    					}else {
    						click.evaluateOrder(orderEntity);
    					}
            		}
            	});
			
        		viewHolder.bt_accept.setVisibility(View.GONE);
        		}else {
				viewHolder.bt_accept.setVisibility(View.GONE);
			}
        
			
         }else {
        	/*switch (orderEntity.getOrderstate()) {
			case 70:
				viewHolder.orderStateTextView.setTextColor(context.getResources().getColor(R.color.left_category_select_text_color));
				break;
			case 75:
				viewHolder.orderStateTextView.setTextColor(context.getResources().getColor(R.color.left_category_select_text_color));
				break;
			case 80:
				viewHolder.orderStateTextView.setTextColor(context.getResources().getColor(R.color.red));
				break;
			case 85:
				viewHolder.orderStateTextView.setTextColor(context.getResources().getColor(R.color.left_category_select_text_color));
				break;
			case 90:
				viewHolder.orderStateTextView.setTextColor(context.getResources().getColor(R.color.left_category_select_text_color));
				break;
			case 110:
				viewHolder.orderStateTextView.setTextColor(context.getResources().getColor(R.color.red));
				break;
			case 145:
				viewHolder.orderStateTextView.setTextColor(context.getResources().getColor(R.color.red));
			case 150:
				viewHolder.orderStateTextView.setTextColor(context.getResources().getColor(R.color.red));
			case 200:
				viewHolder.orderStateTextView.setTextColor(context.getResources().getColor(R.color.red));
				break;
			default:
				break;
			}*/
			 if (orderEntity.getOrderstate()>=70&&orderEntity.getOrderstate()<90) {
				viewHolder.bt_accept.setVisibility(View.GONE);
			}else {
				viewHolder.bt_accept.setVisibility(View.VISIBLE);
				viewHolder.bt_accept.setText("收货码:"+orderEntity.getRecode());
			}
		}
        return convertView;
      }

    public class ViewHolder{
        public TextView orderNumTextView;
        public TextView orderArriveTextView;
        public TextView orderPayTextView;
        public TextView orderStateTextView;
        public TextView product_weight2;
        public TextView product_name2;
        public TextView product_name1;
        public TextView product_weight1;
        public LinearLayout ll_product2;
        public TextView tv_time;
        public RelativeLayout rl_undone;
        public RelativeLayout rl_complete;
        public Button bt_evaluate;
        public TextView tv_orderpay_complete;
        public Button bt_accept;
      
    }
    private String formatTime(String time){
    	Date date=new Date(Long.parseLong(time));
           SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String data=simpleDateFormat.format(date);
		return data;
	}
    public interface buttonClick{
    	public void deleteOrder(int orderId);
    	public void evaluateOrder(NewOrderEntity entity);
    	public void service();
    	public void shouhuo(int orderId);
    	public void pay(int orderId);
    }
    
}
