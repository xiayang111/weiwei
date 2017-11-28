package com.dongwukj.weiwei.ui.activity;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.request.PayPasswordRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.UserResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.net.MyWeiWeiRequestClient;
import com.dongwukj.weiwei.net.utils.DESUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.tsz.afinal.FinalDb;

public class PayPasswordActivity extends BaseActivity {

	private BaseApplication baseApplication;
	private List<String> list= new ArrayList<String>();
	private Spinner spinner;
	private EditText editText;
	private EditText editText4;
	private EditText editText5;
	private ArrayAdapter<String> adapter;
	private Button bt_next; 
	private String question;
	private PayPasswordRequest request;
	//private String trim;
	private LinearLayout ll_left;
	private EditText et_person_num;

	@Override
	protected void findViewById() {
		LinearLayout left=(LinearLayout) findViewById(R.id.left);
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		spinner = (Spinner) findViewById(R.id.spinner);   			//选折问题
		editText = (EditText) findViewById(R.id.editText);			//问题答案
		et_person_num = (EditText) findViewById(R.id.et_person_num);//身份证号码
		editText4 = (EditText) findViewById(R.id.editText4);   		//支付密码
		editText5 = (EditText) findViewById(R.id.editText5);   		//确认支付密码
		ll_left = (LinearLayout) findViewById(R.id.ll_left);
		bt_next = (Button) findViewById(R.id.bt_next);
		
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);//为下拉列表定义一个适配器，这里就用到里前面定义的list。
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//为适配器设置下拉列表下拉时的菜单样式。  
		
		spinner.setAdapter(adapter);
		bt_next.setOnClickListener(this);
		ll_left.setOnClickListener(this);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				question = adapter.getItem(position);
				parent.setVisibility(View.VISIBLE);
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				parent.setVisibility(View.VISIBLE);
			}
		});

		
	}
	

	@Override
	protected void initView() {
		setContentView(R.layout.setting_pay_password_layout);
		baseApplication = (BaseApplication) getApplication();
		//添加密码验证问题到集合
		list.add("您最喜欢的颜色是什么？");
		list.add("我是谁？");
		list.add("值得您回忆的一个物品是什么？");
		list.add("您最爱吃的水果是什么?");
		list.add("您喜欢吃的蔬菜是什么？");
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_next:
			String pwd = editText4.getText().toString().trim();
			String pwd2 = editText5.getText().toString().trim();
			String answer = editText.getText().toString().trim();
			String person_num = et_person_num.getText().toString().trim();
		
			if(TextUtils.isEmpty(answer)){
				Toast.makeText(getApplicationContext(), "密保问题不能为空!", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if(TextUtils.isEmpty(person_num)){
				Toast.makeText(getApplicationContext(), "身份证号码不能为空!", Toast.LENGTH_SHORT).show();
				return;
			}else {
				Pattern p = Pattern.compile("^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X)?$");
                Matcher matcher = p.matcher(person_num);
                boolean matches = matcher.matches();
                if (!matches) {
                	 Toast.makeText(getApplicationContext(), "请输入正确的身份证号码!", Toast.LENGTH_SHORT).show();
                	 return;
				}
			}
			Pattern p = Pattern.compile("^[0-9]*[1-9][0-9]*$");
            Matcher matcher = p.matcher(pwd);
            boolean matches = matcher.matches();
			if (matches&&pwd.length()==6) {
				if(pwd.equals(pwd2)){
					try {
						String pwd_DES = DESUtils.encrypt(pwd2, DESUtils.DES_KEY_STRING);
						request = new PayPasswordRequest();
						request.setQuestionOne(question);
						request.setAnswerOne(answer);
						
						request.setQuestionTwo("您的身份证号?");
						request.setAnswerTwo(person_num);
						request.setPayPassword(pwd_DES);
					} catch (Exception e) {
						Log.e("error", "error", e);
					}
					
					commitQuestion();
				}else{
					Toast.makeText(getApplicationContext(), "两次输入的密码不一致!", Toast.LENGTH_SHORT).show();
				}

			}else {
				Toast.makeText(getApplicationContext(), "密码必须为6位数纯数字!", Toast.LENGTH_SHORT).show();
			}
			
			
			break;

		case R.id.ll_left:
			finish();
			break;
		default:
			break;
		}
	}

	private void commitQuestion() {

		MyWeiWeiRequestClient myWeiWeiRequestClient=new MyWeiWeiRequestClient(getApplicationContext(),baseApplication);
		
		myWeiWeiRequestClient.payPassword(request, new MyWeiWeiRequestClient.PhonePayPasswordRequestCallback() {
            protected void setPaypassword(BaseResult result) {
                Toast.makeText(getApplicationContext(), "支付密码设置成功!请牢记安全问题", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                baseApplication.getUserResult().setIsByMoney(1);
                saveResultToDB(baseApplication.getUserResult());
                finish();
            }
		});
	}
	/*
	 * 跟新数据库，将当前用户的是否设置支付密码更改
	 */
	private void saveResultToDB(UserResult result) {
		FinalDb db=FinalDb.create(getApplicationContext());
        List<UserResult> list = db.findAllByWhere(UserResult.class, "userAccount='" + result.getUserAccount() + "'");
        if (list.size() == 0) {
            db.save(result);
        } else {
            db.update(result, "userAccount='" + result.getUserAccount() + "'");
        }
	}
}
