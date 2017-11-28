package com.dongwukj.weiwei.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.tsz.afinal.FinalDb;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.data.PushMessageData;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.MessageIsChecked;
import com.dongwukj.weiwei.idea.request.NewHomeEntity;
import com.dongwukj.weiwei.idea.request.PhoneJpushRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.JpushMessageEntity;
import com.dongwukj.weiwei.idea.result.PhoneJpushResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;

/**
 * Created by sunjaly on 15/4/20.
 */
public class MessageContentFragment extends Fragment {

	protected static final int PULL_DOWN_MODE = 100;
	protected static final int PULL_UP_MODE = 200;


	private PullToRefreshListView message_list;


    private int size;

    private int businessType;

   // private List<PushMessageData> list;
	private ArrayList<JpushMessageEntity> list=new ArrayList<JpushMessageEntity>();
    //private DataBase dataBase;
    FinalDb finalDb;

    private MessageListAdapter messageListAdapter;


	private FragmentActivity activity;

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public void setSize(int size) {
        this.size = size;
    }


    @Override
    public void onResume() {
        super.onResume();
        /* if(list!=null){
            list.clear();
        }
        if(businessType==0){
//            for(PushMessage pushMessage:srcList){
//               list=finalDb.findAll(PushMessageData.class);
//            }
            list=finalDb.findAllByWhere(PushMessageData.class, "", "createTime desc");

        }else if(businessType==1){
//            for(PushMessage pushMessage:srcList){
//                if(pushMessage.getBusinessType()==1) list.add(pushMessage);
//            }
            list=finalDb.findAllByWhere(PushMessageData.class,"businessType="+1,"createTime desc");
        }else if(businessType==2){
            list=finalDb.findAllByWhere(PushMessageData.class,"businessType="+2,"createTime desc");
        }else if(businessType==3){
            list=finalDb.findAllByWhere(PushMessageData.class,"businessType="+3,"createTime desc");
        }else if(businessType==4){
            list=finalDb.findAllByWhere(PushMessageData.class,"businessType="+4,"createTime desc");
        }

        if(list==null){
            list=new ArrayList<PushMessageData>();
        }
        if(messageListAdapter!=null){
            messageListAdapter.notifyDataSetChanged();
        }
    */}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.message_content_list,container,false);
        activity = getActivity();
        db = LiteOrm.newCascadeInstance(activity, baseApplication.DB_NAME);
        baseApplication = (BaseApplication) activity.getApplication();
        getUserinfo();
        init(view);
        return view;
    }



    private void init(View view) {
    	 message_list=(PullToRefreshListView)view.findViewById(R.id.message_list);
         message_list.setOnRefreshListener(new OnRefreshListener2<ListView>() {

 			@Override
 			public void onPullDownToRefresh(
 					PullToRefreshBase<ListView> refreshView) {
 				loadDataHandler.sendEmptyMessage(PULL_DOWN_MODE);
 			}

 			@Override
 			public void onPullUpToRefresh(
 					PullToRefreshBase<ListView> refreshView) {
 				loadDataHandler.sendEmptyMessage(PULL_UP_MODE);
 				
 			}
 		});
        // list=new ArrayList<PushMessageData>();
         finalDb=FinalDb.create(activity);
         messageListAdapter=new MessageListAdapter();
         message_list.setAdapter(messageListAdapter);
         message_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	
            	 
            	 QueryBuilder qb = new QueryBuilder(MessageIsChecked.class);
         		qb.where("userAccount=? and jpushid=?",new Integer[] {Integer.parseInt(result.getUserAccount()),list.get(Integer.parseInt(id+"")).getJpushid() });
         		ArrayList<MessageIsChecked> query = db.query(qb);
         		if (query.size()>0) {
         			MessageIsChecked checked = query.get(0);
					checked.setIsChecked(1);
					db.update(checked);
				}
         		messageListAdapter.notifyDataSetChanged();
            	 /*
                 PushMessageData pushMessageData=list.get(position);
                 pushMessageData.setReaded(true);
                 finalDb.update(pushMessageData);
                 messageListAdapter.notifyDataSetChanged();

                 switch (pushMessageData.getMsgType()){
                     case 1:
                         Intent intent=new Intent(activity, HomeHeaderActivity.class);
                         intent.putExtra("productId",pushMessageData.getBusinessId());
                         intent.putExtra("type", HeaderActivityType.ProductDetail.ordinal());
                         intent.putExtra("hasHeader",false);
                         startActivity(intent);
                         break;
                     case 2:
                         Intent intent1=new Intent(activity, HomeHeaderActivity.class);
                         intent1.putExtra("url",pushMessageData.getBusinessUrl());
                         intent1.putExtra("type",HeaderActivityType.BannerWebViewFragment.ordinal());
                         intent1.putExtra("hasHeader",false);
                         startActivity(intent1);
                         break;
                     case  3:

                         break;
                     case 4:

                         break;
                 }


             */}
         });
         loadDataHandler.sendEmptyMessageDelayed(300, 500);
	}

	private Handler loadDataHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case PULL_DOWN_MODE:

				fetchMessageData(true);
				break;
			case PULL_UP_MODE:
				fetchMessageData(false);
				break;
			case 300:
				message_list.setMode(Mode.PULL_FROM_START);
				message_list.setRefreshing();
				break;

			}
		}

		
	};
	 private void getUserinfo() {
			FinalDb db=FinalDb.create(activity);
			List<UserResult> list = db.findAllByWhere(UserResult.class, "isLogin='1'");
			if (list.size()==1) {
				result = list.get(0);
				//baseApplication.setUserResult(result);
			}
		}
	private int pageIndex=1;
	private BaseApplication baseApplication;
	private DataBase db;
	private UserResult result;
	private void fetchMessageData(final boolean isFirst) {
		PhoneJpushRequest request=new PhoneJpushRequest();
		if (isFirst) {
			request.setPageNumber(1);
		} else {
			request.setPageNumber(++pageIndex);
		}
		request.setBusinesstype(businessType);
		BaseRequestClient client=new BaseRequestClient(activity);
		
		client.httpPostByJsonNew("PhoneJpush", result, request, PhoneJpushResult.class, new BaseRequestClient.RequestClientNewCallBack<PhoneJpushResult>() {

			@Override
			public void callBack(PhoneJpushResult result) {

				if (result != null) {
					if (result.getCode() == BaseResult.CodeState.Success
							.getCode()) {
						
						if (result.getJpushinfolist().size() > 0) {
							if (isFirst) {
								list.clear();
								messageListAdapter.notifyDataSetChanged();
								list.addAll(result.getJpushinfolist());
								messageListAdapter.notifyDataSetChanged();
								pageIndex = 1;
							} else {

								list.addAll(result.getJpushinfolist());
								messageListAdapter.notifyDataSetChanged();
							}
							message_list.onRefreshComplete();
							if (list.size() >= result.getListNumber()) {
								message_list.setMode(Mode.PULL_FROM_START);
							} else {
								message_list.setMode(PullToRefreshBase.Mode.BOTH);
							}
						}else {
							Toast.makeText(activity, "暂时没有消息",Toast.LENGTH_SHORT).show();
						}
						
						
					} else {
						Toast.makeText(activity, result.getMessage(),Toast.LENGTH_SHORT).show();
					}

				} else {

					Toast.makeText(activity,activity.getResources().getString(R.string.data_error),Toast.LENGTH_SHORT).show();
				}
				message_list.onRefreshComplete();

			
			}

			@Override
			public void loading(long count, long current) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void logOut(PhoneJpushResult result) {

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
   /* public static String ToDBC(String input) { 
        char c[] = input.toCharArray(); 
        for (int i = 0; i < c.length; i++) { 
            if (c[i] == ' ') { 
                c[i] = '\u3000'; 
            } else if (c[i] < '\177') { 
                c[i] = (char) (c[i] + 65248); 
            } 
        } 
        return new String(c); 
    } */
	public static String ToDBC(String input) {          
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {              
        if (c[i] == 12288) {                 
        c[i] = (char) 32;                  
        continue;
         }
         if (c[i] > 65280 && c[i] < 65375)
            c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }  
	public static String stringFilter(String str) {         
        str = str.replaceAll("【", "[").replaceAll("】", "]")
               .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符         
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
   }  
	private class MessageListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            if(convertView==null){
                convertView=View.inflate(activity,R.layout.message_content_list_item,null);
                viewHolder=new ViewHolder(convertView);

                viewHolder.messageStateText=(TextView)convertView.findViewById(R.id.messageStateText);
                viewHolder.messageIcon=(ImageView)convertView.findViewById(R.id.messageIcon);
                viewHolder.messageTitle=(TextView)convertView.findViewById(R.id.messageTitle);
                viewHolder. messageContent=(TextView)convertView.findViewById(R.id.messageContent);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder)convertView.getTag();
            }
            switch (list.get(position).getBusinesstype()) {
			case 1:
				 viewHolder.messageIcon.setImageResource(R.drawable.jiaoyi);
				break;
			case 2:
				 viewHolder.messageIcon.setImageResource(R.drawable.zixun);
				break;
			case 3:
				 viewHolder.messageIcon.setImageResource(R.drawable.wuliu);
				break;
			case 4:
				 viewHolder.messageIcon.setImageResource(R.drawable.tixing);
				break;
				
			default:
				break;
				
			}

			 QueryBuilder qb = new QueryBuilder(MessageIsChecked.class);
        		qb.where("userAccount=? and jpushid=? ",new Integer[] {Integer.parseInt(result.getUserAccount()),list.get(position).getJpushid()});
        		ArrayList<MessageIsChecked> query = db.query(qb);
        		if (query.size()>0) {
        			if (query.get(0).getIsChecked()==1) {
        				 viewHolder.messageStateText.setVisibility(View.INVISIBLE);
					}else {
						 viewHolder.messageStateText.setVisibility(View.VISIBLE);
					}
        			
				}else {
					MessageIsChecked checked=new MessageIsChecked();
					checked.setIsChecked(0);
					checked.setJpushid(list.get(position).getJpushid());
					checked.setUserAccount(result.getUserAccount());
					db.save(checked);
					
				}
              // messageTitle.setText(pushMessageData.getTitle());
        		// viewHolder.messageContent.setText("【"+list.get(position).getTitle()+"】"+ToSBC(list.get(position).getContent()));
        		// viewHolder.messageContent.setText(Html.fromHtml("<font color='red'>"+"【"+list.get(position).getTitle()+"】</font>")+ToSBC(list.get(position).getContent()));
        		// viewHolder.messageContent.setText(Html.fromHtml("<font color='red'>"+"【"+list.get(position).getTitle()+"】"+ToSBC(list.get(position).getContent())));
        		// viewHolder.messageContent.setText(Html.fromHtml("<font color='#8fc320'>"+"【"+list.get(position).getTitle()+"】"+"</font><font color='#7c7c7c'>"+ToSBC(list.get(position).getContent()+"</font>")));
        		 viewHolder.messageContent.setText(Html.fromHtml("<font color='#8fc320'>"+"【"+list.get(position).getTitle()+"】"+"</font><font color='#7c7c7c'>"+ToDBC(list.get(position).getContent())+"</font>"));
					
        		 return convertView;
        }
        

        private class ViewHolder{
            public TextView messageStateText;
            public ImageView messageIcon;
            public TextView messageTitle;
            public TextView messageContent;

            public ViewHolder(View view){

            }

            public void setUpView(JpushMessageEntity pushMessageData){
                if(pushMessageData==null) return;
                /*if(pushMessageData.isReaded()){
                    messageStateText.setVisibility(View.INVISIBLE);
                }else{
                    messageStateText.setVisibility(View.VISIBLE);
                }*/
                switch (pushMessageData.getBusinesstype()) {
				case 1:
					messageIcon.setImageResource(R.drawable.jiaoyi);
					break;
				case 2:
					messageIcon.setImageResource(R.drawable.zixun);
					break;
				case 3:
					messageIcon.setImageResource(R.drawable.wuliu);
					break;
				case 4:
					messageIcon.setImageResource(R.drawable.tixing);
					break;

				default:
					break;
				}
                QueryBuilder qb = new QueryBuilder(MessageIsChecked.class);
         		qb.where("userAccount=? and jpushid=?",new Integer[] {Integer.parseInt(result.getUserAccount()),pushMessageData.getJpushid() });
         		ArrayList<MessageIsChecked> query = db.query(qb);
         		if (query.size()>0) {
         			messageStateText.setVisibility(View.GONE);
				}else {
					messageStateText.setVisibility(View.VISIBLE);
				}
               // messageTitle.setText(pushMessageData.getTitle());
               messageContent.setText("【"+pushMessageData.getTitle()+"】"+stringFilter(ToDBC(pushMessageData.getContent())));
               
              // messageContent.setText("您的订单:00001300000600000049于2015/8/25 17:07:22支付成功！我们将尽快为您发货。");
                
            }


        }
    }

}
