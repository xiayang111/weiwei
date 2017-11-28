package com.dongwukj.weiwei.ui.activity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;



import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.LoginEntity;
import com.dongwukj.weiwei.idea.result.LoginUserResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.net.utils.DESUtils;
import com.dongwukj.weiwei.ui.AppManager;
import com.litesuits.android.log.Log;
import com.umeng.analytics.MobclickAgent;

import de.greenrobot.event.EventBus;
import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends BaseActivity {
	private String phone_num;
	private String pwd_num;
	private FinalDb db;
	private LinearLayout ll_left;
	private TextView find_password;
	private ImageButton select;
	private TextView view;
	private MyBaseAdapter adapter;
	private PopupWindow pw;
	private TextView tv_title;
    private EditText et_phone;
    private EditText et_pwd;
    private Button bt_login;
    private Button bt_register;
    private final int REQUEST_CODE = 100;
    private List<UserResult> lists=new ArrayList<UserResult>();

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        switch (arg0) {
            case REQUEST_CODE:
                if (arg1 == 200) {
                  finish();
                }
                break;

            default:
                super.onActivityResult(arg0, arg1, arg2);
                break;
        }

    }

    @Override
    protected void findViewById() {
    	ll_left = (LinearLayout) findViewById(R.id.ll_left);
    	ll_left.setVisibility(View.GONE);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_register = (Button) findViewById(R.id.bt_register);
        find_password = (TextView) findViewById(R.id.find_password);
        select = (ImageButton) findViewById(R.id.select);
        view = (TextView) findViewById(R.id.view);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("登录");
        select.setOnClickListener(this);
        ll_left.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        find_password.setOnClickListener(this);
       
    }
  
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                phone_num = et_phone.getText().toString().trim();
                pwd_num = et_pwd.getText().toString().trim();
            	if (TextUtils.isEmpty(phone_num) || TextUtils.isEmpty(pwd_num)) {
                    Toast.makeText(getApplicationContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (pwd_num.length()<6||pwd_num.length()>16) {
                	 Toast.makeText(getApplicationContext(), "密码为6-16位", Toast.LENGTH_SHORT).show();
                	 return;
				}else {
                    Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[678])|(14[57])|(18[0-9]))\\d{8}$");
                    Matcher matcher = p.matcher(phone_num);
                    boolean matches = matcher.matches();
                    if (!matches) {
                        Toast.makeText(getApplicationContext(), "请输入合法的手机号", Toast.LENGTH_SHORT).show();
                    } else {
                        loginHandler.sendEmptyMessage(100);
                    }
                }
                break;
            case R.id.bt_register:
                Intent intent = new Intent(getApplicationContext(), RegisterQuick.class);
                startActivityForResult(intent, REQUEST_CODE);

                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.find_password:
                Intent intent1 = new Intent(getApplicationContext(), FindPassword.class);
                startActivity(intent1);
                break;
            case R.id.select:
            	FinalDb db=FinalDb.create(getApplicationContext());
            	lists = db.findAll(UserResult.class);
            	if (lists==null||lists.size()==0) {
					return;
				}
                selectphonenumber();
                break;
            default:
                break;
        }
    }
    /**
     * 设置下拉框用户信息
     */
    private void selectphonenumber() {

		ListView contentView =(ListView)View.inflate(getApplicationContext(), R.layout.select_phone, null);
        contentView.setSelector(R.color.weiwei_content_item_divider_color);
		adapter = new MyBaseAdapter();
		contentView.setAdapter(adapter);
		pw = new PopupWindow(contentView, view.getWidth(),lists.size()<=4?lists.size()*(int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics())+0.5):4*(int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics())+0.5));
		pw.setBackgroundDrawable(getResources().getDrawable(R.drawable.weiwei_pop_bg));
		pw.setFocusable(true);
		pw.showAsDropDown(view);
		contentView.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				et_phone.setText(lists.get(position).getTel());
				et_phone.setSelection(lists.get(position).getTel().length());
				pw.dismiss();
			}
		});
		
	}
    private class MyBaseAdapter extends BaseAdapter{

    	private TextView tv;

    	@Override
    	public int getCount() {
    		return lists.size();
    	}

    	@Override
    	public Object getItem(int position) {
    		return lists.get(position);
    	}

    	@Override
    	public long getItemId(int position) {
    		return position;
    	}

    	@SuppressLint("ViewHolder")
		@Override
    	public View getView(final int position, View convertView, ViewGroup parent) {
    		LinearLayout view = (LinearLayout)View.inflate(getApplicationContext(), R.layout.pop_phone_item, null);
    		ImageView img=(ImageView) view.findViewById(R.id.img);
    		img.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					deleteuser(lists.get(position).getTel());
				}
    		});
    		tv = (TextView) view.findViewById(R.id.tv_phone);
    		tv.setText(lists.get(position).getTel());
    		return view;
    	}
    	
    }
    private void deleteuser(String tel) {
		db.deleteByWhere(UserResult.class, "tel='" + tel+ "'");
		lists=db.findAll(UserResult.class);
		if (lists.size()==0) {
			pw.dismiss();
			return;
		}
		pw.update(view.getWidth(),lists.size()*lists.size()<=4?lists.size()*(int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics())+0.5):4*(int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics())+0.5));
		adapter.notifyDataSetChanged();
	}
    
    
	@Override
    protected void initView() {
		boolean isLoginOut = getIntent().getBooleanExtra("isLoginOut", false);
	        if (isLoginOut) {
				Toast.makeText(getApplicationContext(), "您的账号已经在别处登录。。。", 	Toast.LENGTH_LONG).show();
			}
		setContentView(R.layout.activity_login);
        db = FinalDb.create(this);
    }
	/**
	 * 登录hander
	 */
    private Handler loginHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            bt_login.setText(getResources().getString(R.string.login_ing));
            String md5Pwd;
            try {
                md5Pwd = DESUtils.encrypt(pwd_num, DESUtils.DES_KEY_STRING);
            } catch (Exception e) {
                md5Pwd = null;
                android.util.Log.e("error", "error", e);
            }
            LoginEntity entity = new LoginEntity();
            entity.setUserPassword(md5Pwd);
            entity.setUserAccount(phone_num);
            
            
            MyWeiWeiRequestClient myWeiWeiRequestClient = new MyWeiWeiRequestClient(LoginActivity.this, baseApplication);
            
            myWeiWeiRequestClient.login(entity, new MyWeiWeiRequestClient.LoginRequestClientCallback() {
                @Override
                protected void login(LoginUserResult result) {
                	UserResult result2=new UserResult();
                	result2.setUserAccount(result.getUserAccount());
                	result2.setLogin(true);
                	//result2.setLevel(result.getLevel());
                	result2.setTel(phone_num);
                	result2.setAvatar(result.getAvatar());
                	result2.setIsByMoney(result.getIsByMoney());
                	result2.setNickName(result.getNickName());
                	result2.setTokenId(result.getTokenId());
                	result2.setMarketId(result.getMarket().getId());
                    baseApplication.setUserResult(result2);
                   if (result.getDefaultSaId()!=-1) {
						result2.setPlotid(result.getDefaultSaId());
					}else {
						result2.setPlotid(result.getPlotid());
					}
                    //保存用户信息到数据库
                    saveResultToDB(result2);

                    //设置用户标签
                    setPushTagAndAlias(result2);
                   Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                   intent.putExtra("homeTab",0);
                   startActivity(intent);
                   EventBus.getDefault().post("login");
                   isexit=true;
                   finish();
				 }

                @Override
                protected void loginComplete(LoginUserResult result) {
                    bt_login.setText(getResources().getString(R.string.login_normal));
                }
                protected void logOut(com.dongwukj.weiwei.idea.result.BaseResult result) {
                	Toast.makeText(baseApplication, "请检查手机当前时间", 0).show();
                	 bt_login.setText(getResources().getString(R.string.login_normal));
                	 
                };
            });

        }

        ;
    };
    public void onEventMainThread(String type){}
 
  
   @Override
   public void onCreate(Bundle savedInstanceState) {
	EventBus.getDefault().register(this);
	super.onCreate(savedInstanceState);
	
   }
   @Override
   protected void onDestroy() {
	  EventBus.getDefault().unregister(this);
	super.onDestroy();
   }
   @Override
   protected void onResume() {
   	super.onResume();
   	 MobclickAgent.onPageStart("Login");
   	 MobclickAgent.onResume(this);  
   }
   @Override
   protected void onPause() {
   	super.onPause();
   	 MobclickAgent.onPageEnd("Login");
   	 MobclickAgent.onPause(this);
   }
   @Override
   public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (keyCode==KeyEvent.KEYCODE_BACK) {
           showExitSystem();
       }
       return true;
   }
   public boolean isexit;
   /**
    * 弹出程序退出框
    */
   public void showExitSystem() {
       AlertDialog.Builder builder=new AlertDialog.Builder(this);
       BitmapDrawable drawable=(BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher_small);
       builder.setIcon(drawable)
               .setTitle("惟惟")
               .setMessage("是否退出应用")
               .setNegativeButton("确定", new DialogInterface.OnClickListener() {

                  
					@Override
                   public void onClick(DialogInterface dialog, int which) {
                   	isexit = true;
                   	AppManager.getInstance().AppExit(getApplicationContext());
                   }
               })
               .setPositiveButton("取消",null )
               .show();
   }
   
   public void finish() {
		 if (isexit) {
			 super.finish();
		}else {
			EventBus.getDefault().post("login");
			Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
			intent.putExtra("homeTab",0);
			startActivity(intent);
			super.finish();
		}
	};
    /**
     * 设置别名和标签
     * @param result
     */
    private void setPushTagAndAlias(UserResult result){
        if(result==null) return;
        // 根据用户等级标签
        String tag=result.getLevel()==null?"0":result.getLevel();
        HashSet<String> tags=new HashSet<String>();
        tags.add(tag);
        String alias=result.getTokenId();
        JPushInterface.setAliasAndTags(this, alias, tags, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if(i==0){
                    //成功
                    Log.d("jpush","success");
                }else{
                    //失败
                    Log.d("jpush","fail");
                }
            }
        });

    }
    
    /**
     * 保存用户信息到本地数据库
     * @param result
     */
    private void saveResultToDB(UserResult result) {
        List<UserResult> list = db.findAllByWhere(UserResult.class, "userAccount='" + result.getUserAccount() + "'");
        if (list.size() == 0) {
            db.save(result);
        } else {
            db.update(result, "userAccount='" + result.getUserAccount() + "'");
        }
    }

   
}
