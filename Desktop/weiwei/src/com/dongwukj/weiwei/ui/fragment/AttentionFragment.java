package com.dongwukj.weiwei.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.AttentionDeleteRequest;
import com.dongwukj.weiwei.idea.request.AttentionRequest;
import com.dongwukj.weiwei.idea.result.AttentionEntity;
import com.dongwukj.weiwei.idea.result.AttentionResult;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.net.utils.NetworkUtil;
import com.dongwukj.weiwei.ui.activity.HomeHeaderActivity;
import com.dongwukj.weiwei.ui.activity.LoginActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.List;

public class AttentionFragment extends AbstractHeaderFragment implements View.OnClickListener{
	private boolean isBoth=false;//是否可以上下刷新
	protected static final int PULL_auto =102;
	 private final int PULL_DOWN=100;
	    private final int PULL_UP=101;
	private PullToRefreshListView lv;
	private boolean isEdit=false;
	private MyBaseAdapter adapter;
	private Button bt_delete;
	private List<AttentionEntity> list;
	private List<AttentionEntity> list_delete=new ArrayList<AttentionEntity>();
    private TextView rightButton;
    private int pageIndex=1;
    private final int pageSize=10;
    private int totalCount=0;
    private FinalBitmap finalBitmap;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_attention, container,false);
    	finalBitmap=FinalBitmap.create(activity);
        finalBitmap.configLoadfailImage(R.drawable.default_small);
        finalBitmap.configLoadingImage(R.drawable.default_small);
        return view;
    }

    @Override
    protected String setTitle() {
        return getResources().getString(R.string.my_attention_text);
    }

    @Override
    protected void findView(View v) {
    	rightButton=(TextView)activity.findViewById(R.id.list_header_rightbutton);
        rightButton.setText("编辑");
        rightButton.setOnClickListener(this);
        lv = (PullToRefreshListView) v.findViewById(R.id.lv);
        ll_wrong = (LinearLayout) v.findViewById(R.id.ll_wrong);
        bt_delete = (Button) v.findViewById(R.id.bt_delete);
        bt_delete.setOnClickListener(this);
        list = new ArrayList<AttentionEntity>();
        adapter=new MyBaseAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {
        	@Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            	if (isEdit) {

                    CheckBox cb=(CheckBox) view.findViewById(R.id.cb);
                    boolean checked = cb.isChecked();
                    AttentionEntity entity1=(AttentionEntity) list.get((int)id);
                    entity1.setChecked(!entity1.getChecked());
				if (checked) {
                        cb.setChecked(false);
                        AttentionEntity entity=(AttentionEntity) list.get((int) id);
                        list_delete.remove(entity);

                    }else {
                        cb.setChecked(true);
                        AttentionEntity entity=(AttentionEntity) list.get((int) id);
                        list_delete.add(entity);

                    }
                }else {
                    AttentionEntity attentionEntity=list.get((int)id);
                    Intent intent=new Intent(activity, HomeHeaderActivity.class);
                    intent.putExtra("productId",attentionEntity.getpId());
                    intent.putExtra("type", HeaderActivityType.ProductDetail.ordinal());
                    intent.putExtra("hasHeader",false);
                    startActivity(intent);
                }

            }
        });
        PullToRefreshTest  test=new PullToRefreshTest(PULL_DOWN,PULL_UP,PULL_auto, updateHandler);
        test.initListView(lv);
    }

   
    private Handler updateHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case PULL_DOWN:
                    updateData(true);
                    break;
                case PULL_UP:
                    updateData(false);
                    break;
                case PULL_auto:
                	lv.setRefreshing();
                	break;
            }
        }
    };
	private LinearLayout ll_wrong;


    private void updateData(final boolean isFirst){

        AttentionRequest attentionRequest=new AttentionRequest();
        if(isFirst){
            attentionRequest.setPageIndex(1);
        }else{
            attentionRequest.setPageIndex(++pageIndex);
        }
        attentionRequest.setAddNum(pageSize);

        final MyWeiWeiRequestClient attentionRequestClient=new MyWeiWeiRequestClient(activity,baseApplication);
        attentionRequestClient.attentionList(attentionRequest, new MyWeiWeiRequestClient.AttentionRequestCallback() {
            @Override
            protected void list(AttentionResult result) {
            	if (getActivity()==null) {
					return;
				}
            	if (result.getAttentions().size()==0) {
            		rightButton.setText("编辑");
                	bt_delete.setVisibility(View.GONE);
                	rightButton.setVisibility(View.INVISIBLE);
					ll_wrong.setVisibility(View.VISIBLE);
				}else {
					if (isFirst) {
	                    list.clear();
	                    adapter.notifyDataSetChanged();
	                    list.addAll(result.getAttentions());
	                    adapter.notifyDataSetChanged();
	                    pageIndex = 1;
	                    if (list.size()>0) {
							rightButton.setVisibility(View.VISIBLE);
						}else {
							rightButton.setVisibility(View.INVISIBLE);
						}
	                } else {
	                    list.addAll(result.getAttentions());
	                    adapter.notifyDataSetChanged();
	                }
				}
                showProgress(false);
            }
            @Override
            protected void logOut(BaseResult result) {

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
            @Override
            protected void listComplete(AttentionResult result) {
            	if (getActivity()==null) {
					return;
				}
                lv.onRefreshComplete();
                if (rightButton.getText().toString().trim().equals("取消")) {
                	lv.setMode(PullToRefreshBase.Mode.DISABLED);
					return;
				}
                if(result!=null && result.getListNumber()!=0){
                    totalCount = result.getListNumber();
                    if (result.getListNumber() <= pageSize * pageIndex) {
                        lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        isBoth=false;
                    }else {
                    	lv.setMode(PullToRefreshBase.Mode.BOTH);
                    	 isBoth=true;
                    }
                }

            }
        });

    }

    private void deleteAttention(){

        AttentionDeleteRequest attentionDeleteRequest=new AttentionDeleteRequest();
        List<Integer> deleteIdList=new ArrayList<Integer>();

        for (AttentionEntity attentionEntity : list_delete) {
        	
            deleteIdList.add(attentionEntity.getpId());
        }
        attentionDeleteRequest.setProductIdList(deleteIdList);
        
        MyWeiWeiRequestClient attentionRequestClient=new MyWeiWeiRequestClient(activity,baseApplication);
        progressDialog.setMessage("正在删除...");
        showProgress(true);
        attentionRequestClient.attentionDelete(attentionDeleteRequest, new MyWeiWeiRequestClient.AttentionRequestCallback() {
            @Override
            protected void delete(BaseResult result) {
            	if (getActivity()==null) {
					return;
				}
               /* for (AttentionEntity attentionEntity : list_delete) {
                    list.remove(attentionEntity);
                }*/
                list_delete.clear();
                /*
                 * BUG #196 
                 */
              //  if (list.size()==0) {
                	/*isEdit=false;
                	rightButton.setText("编辑");
                	bt_delete.setVisibility(View.GONE);
                	rightButton.setVisibility(View.INVISIBLE);
                	ll_wrong.setVisibility(View.VISIBLE);*/
                	updateData(true);
				//}
               /* adapter.notifyDataSetChanged();
                if(totalCount-list_delete.size()<=pageSize*pageIndex){
                    pageIndex--;
                }*/
            }
            @Override
            protected void logOut(BaseResult result) {

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.list_header_rightbutton:
			if (list.size()==0) {
				return;
			}
			if (isEdit) {
				isEdit=false;
                rightButton.setText("编辑");
				bt_delete.setVisibility(View.GONE);
				for (AttentionEntity attentionEntity : list) {
                    attentionEntity.setChecked(false);
				}
				if (isBoth) {
					lv.setMode(PullToRefreshBase.Mode.BOTH);
				}else {
					lv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
				}
				list_delete.clear();
			}else {
				isEdit=true;
                rightButton.setText("取消");
				bt_delete.setVisibility(View.VISIBLE);
                lv.setMode(PullToRefreshBase.Mode.DISABLED);
			}
			adapter.notifyDataSetChanged();
			break;
		case R.id.bt_delete:
			if (list_delete.size()==0) {
				Toast.makeText(activity, "请选择您要删除的关注", Toast.LENGTH_SHORT).show();
			}else {
                deleteAttention();
			}
			break;

		default:
			break;
		}
	}
    private class MyBaseAdapter extends BaseAdapter{

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Viewhodler hodler=null;
                if (convertView!=null) {
                    hodler=(Viewhodler) convertView.getTag();
                }else {
                    convertView=View.inflate(activity, R.layout.fragment_attention_item, null);
                    hodler=new Viewhodler();
                    hodler.img=(ImageView) convertView.findViewById(R.id.img);
                    hodler.cb=(CheckBox) convertView.findViewById(R.id.cb);
                    hodler.tv_title=(TextView) convertView.findViewById(R.id.tv_title);
                    hodler.tv_price=(TextView) convertView.findViewById(R.id.tv_price);
                    hodler.tv_sales=(TextView) convertView.findViewById(R.id.tv_sales);
                    convertView.setTag(hodler);
                }
                AttentionEntity entity = list.get(position);
                //通过url从网络获取图片
                String imgUrl=entity.getShowImg();
                //String imgUrl = entity.get
                if(!NetworkUtil.checkUrl(imgUrl)){
                    imgUrl= BaseApplication.BASE_IMAGE_HOST+imgUrl;
                }
                finalBitmap.display(hodler.img,imgUrl);
                hodler.tv_title.setText(entity.getName());
                //hodler.tv_price.setText("￥"+entity.getShopPrice()+"0");
                hodler.tv_price.setText(String.format("￥%.2f",entity.getShopPrice()));
                
                hodler.tv_sales.setText(entity.getProductMonth()+"克");

                if (isEdit) {
                    hodler.cb.setVisibility(View.VISIBLE);

                }else {
                    hodler.cb.setVisibility(View.INVISIBLE);
                }
                hodler.cb.setChecked(entity.getChecked());
                return convertView;
            }

            @Override
            public long getItemId(int position) {
                // TODO Auto-generated method stub
                return position;
            }

            @Override
            public Object getItem(int position) {
                // TODO Auto-generated method stub
                return list.get(position);
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return list.size();
            }
        }
        class Viewhodler{
            CheckBox cb;
            ImageView img;
            TextView tv_title;
            TextView tv_price;
            TextView tv_sales;
        }
}
