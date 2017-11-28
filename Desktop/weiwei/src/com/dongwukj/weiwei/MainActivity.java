//package com.dongwukj.weiwei;
//
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import com.dongwukj.weiwei.idea.request.LoginEntity;
//import com.dongwukj.weiwei.idea.request.User;
//import com.dongwukj.weiwei.idea.result.HelloWorldResponse;
//import com.dongwukj.weiwei.idea.result.LoginResult;
//import com.dongwukj.weiwei.idea.result.UserResult;
//import com.dongwukj.weiwei.net.BaseRequestClient;
//import com.dongwukj.weiwei.net.BaseRequestClient.RequestClientCallBack;
//
//
//public class MainActivity extends ActionBarActivity {
//
//	public static final String WEB_SERVER_URL = "http://192.168.0.224:82/MtsWebService.asmx"; 
//	
//	private Button button;
//	private ProgressDialog progressDialog;
//	
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        
//        button=(Button)findViewById(R.id.button);
//        button.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				fetchData2();
//			}
//		});
//    }
//    private void fetchData2(){
//        progressDialog=ProgressDialog.show(this, "loading", "loading....");
//        User user=new User("123","1233");
//        LoginEntity requestEntity=new LoginEntity();
//        BaseRequestClient client=new BaseRequestClient(getApplicationContext());
//        UserResult userResult=new UserResult();
////        client.httpPostByJson("http://192.168.0.124/wwapi/Login/",userResult, requestEntity, LoginResult.class, new RequestClientCallBack<LoginResult>() {
////
////            public void callBack(LoginResult result) {
////                System.out.println(result);
////                progressDialog.dismiss();
////            }
////
////            @Override
////            public void loading(long count, long current) {
////                // TODO Auto-generated method stub
////
////            }
////
////            ;
////
////
////        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
////        if (id == R.id.action_settings) {
////            return true;
////        }
//        return super.onOptionsItemSelected(item);
//    }
//    
//    
//    
//    private class TestThread extends Thread{
//    	
//    	
//    	@Override
//    	public void run() {
//    		
//    	}
//    }
//}
