package com.dongwukj.weiwei.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

//import com.dongwukj.weiwei.MainActivity;
import com.dongwukj.weiwei.R;

import java.io.*;
import java.util.HashMap;

public class RecommendedJoinFragment extends AbstractHeaderFragment implements OnClickListener,PlatformActionListener,Handler.Callback {

	//private TextView tv_feedback;
	private TextView tv_more_detail;
	private TextView tv_share;
	private Button btn_share;
	private TextView tv_go_more;
	private LinearLayout ll_share;
	private LinearLayout ll_feedback;
	private Button btn_wancheng;
	private EditText et_word;
    private View view;
	private TextView tv_recom;
	private TextView tv_back;

    private Context context;
	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.recommend_join, container, false);
        context=getActivity();
		return view;
	}

	@Override
	protected String setTitle() {
		return getResources().getString(R.string.recommend_join);
	}

	@Override
	protected void findView(View v) {
		//tv_feedback = (TextView) v.findViewById(R.id.tv_feedback);
		//tv_share = (TextView) v.findViewById(R.id.tv_share);
		tv_more_detail = (TextView) v.findViewById(R.id.tv_more_detail);
		btn_share = (Button) v.findViewById(R.id.btn_share);
		tv_go_more = (TextView) v.findViewById(R.id.tv_go_more);
		btn_wancheng = (Button) v.findViewById(R.id.btn_wancheng);
		et_word = (EditText) v.findViewById(R.id.et_word);
		tv_recom = (TextView) v.findViewById(R.id.tv_recom);
		tv_back = (TextView) v.findViewById(R.id.tv_back);
		tv_back.setOnClickListener(this);
		ll_share = (LinearLayout) v.findViewById(R.id.ll_share);
		ll_feedback = (LinearLayout) v.findViewById(R.id.ll_feedback);
		//tv_feedback.setOnClickListener(this);
		//tv_share.setOnClickListener(this);
		tv_more_detail.setOnClickListener(this);
		btn_share.setOnClickListener(this);
		btn_wancheng.setOnClickListener(this);
		
	}
	private void showShare() {
		 ShareSDK.initSDK(activity);
		 OnekeyShare oks = new OnekeyShare();	
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 
		 
		 
		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle(getString(R.string.share));
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		 oks.setTitleUrl("http://sharesdk.cn");
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("我是分享文本");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl("http://sharesdk.cn");
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("我是测试评论文本");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl("http://sharesdk.cn");
		 oks.setSilent(true);
		 oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/05/21/oESpJ78_533x800.jpg");
		// 启动分享GUI
		 oks.show(activity);
		 }
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/*case R.id.tv_share:
			//tv_feedback.setBackgroundColor(android.graphics.Color.parseColor("#FAFFF0"));
			tv_share.setBackgroundColor(android.graphics.Color.parseColor("#FF9912"));
			ll_share.setVisibility(View.VISIBLE);
			ll_feedback.setVisibility(View.GONE);
			break;*/
		/*case R.id.tv_feedback:
			tv_feedback.setBackgroundColor(android.graphics.Color.parseColor("#FF9912"));
			tv_share.setBackgroundColor(android.graphics.Color.parseColor("#FAFFF0"));
			ll_share.setVisibility(View.GONE);
			ll_feedback.setVisibility(View.VISIBLE);
			break;*/
		case R.id.tv_more_detail:
			/*if(tv_go_more.getVisibility()==View.VISIBLE){
				tv_go_more.setVisibility(View.GONE);
			}else{
				tv_go_more.setVisibility(View.VISIBLE);//
			}*/
			tv_more_detail.setVisibility(View.GONE);
			tv_recom.setVisibility(View.GONE);
			tv_go_more.setVisibility(View.VISIBLE);
			tv_back.setVisibility(View.VISIBLE);
			break;
		case R.id.btn_share:
			//showShare();
            // 直接分享
            showShare(false, null, false);
			break;
		case R.id.btn_wancheng:   						   //提交口令
			String word = btn_wancheng.getText().toString().trim();
		
		case R.id.tv_back:
			tv_back.setVisibility(View.GONE);
			tv_more_detail.setVisibility(View.VISIBLE);
			tv_recom.setVisibility(View.VISIBLE);
			tv_go_more.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

    /**
     *  执行拷贝任务
     *  @param asset 需要拷贝的assets文件路径
     *  @return 拷贝成功后的目标文件句柄
     *  @throws IOException
     */
    protected File copy( String asset ) throws IOException {

        InputStream source = context.getAssets().open(new File(asset).getPath());
        File mAppDirectory=context.getExternalFilesDir(null);
        File destinationFile = new File(mAppDirectory, asset);
        destinationFile.getParentFile().mkdirs();
        OutputStream destination = new FileOutputStream(destinationFile);
        byte[] buffer = new byte[1024];
        int nread;

        while ((nread = source.read(buffer)) != -1) {
            if (nread == 0) {
                nread = source.read();
                if (nread < 0)
                    break;
                destination.write(nread);
                continue;
            }
            destination.write(buffer, 0, nread);
        }
        destination.close();

        return destinationFile;
    }

    // 使用快捷分享完成分享（请务必仔细阅读位于SDK解压目录下Docs文件夹中OnekeyShare类的JavaDoc）
    /**ShareSDK集成方法有两种</br>
     * 1、第一种是引用方式，例如引用onekeyshare项目，onekeyshare项目再引用mainlibs库</br>
     * 2、第二种是把onekeyshare和mainlibs集成到项目中，本例子就是用第二种方式</br>
     * 请看“ShareSDK 使用说明文档”，SDK下载目录中 </br>
     * 或者看网络集成文档 http://wiki.mob.com/Android_%E5%BF%AB%E9%80%9F%E9%9B%86%E6%88%90%E6%8C%87%E5%8D%97
     * 3、混淆时，把sample或者本例子的混淆代码copy过去，在proguard-project.txt文件中
     *
     *
     * 平台配置信息有三种方式：
     * 1、在我们后台配置各个微博平台的key
     * 2、在代码中配置各个微博平台的key，http://mob.com/androidDoc/cn/sharesdk/framework/ShareSDK.html
     * 3、在配置文件中配置，本例子里面的assets/ShareSDK.conf,
     */
    private void showShare(boolean silent, String platform, boolean captureView) {
        Context context = activity;
        String url="http://www.vvlife.com/account/recommendation?uid="+baseApplication.getUserResult().getUserAccount();
        
        final OnekeyShare oks = new OnekeyShare();

        //oks.setAddress("12345678901");
        oks.setTitle("推荐新用户");
        oks.setTitleUrl( url);
        String customText = "分享迎好礼";
        if (customText != null) {
            oks.setText(customText+url);
        }
        if (captureView) {	
            oks.setViewToShare(view);
        } else {
            //oks.setImagePath(CustomShareFieldsPage.getString("imagePath", MainActivity.TEST_IMAGE));
            oks.setImageUrl("http://www.vvlife.com/image/shared.jpg");
            /*try{
                File file=copy("shared.jpg");
                //oks.setImagePath("file:///android_asset/shared.jpg");
                oks.setImagePath(file.getAbsolutePath());
            }catch (Exception e){

            }*/

            
//			oks.setImageArray(new String[]{MainActivity.TEST_IMAGE, MainActivity.TEST_IMAGE_URL});
        }
        oks.setUrl(url);
        //oks.setFilePath(CustomShareFieldsPage.getString("filePath", MainActivity.TEST_IMAGE));
        oks.setComment("");
        oks.setSite( context.getString(R.string.app_name));
        oks.setSiteUrl(url);
        //oks.setVenueName(CustomShareFieldsPage.getString("venueName", "ShareSDK"));
        //oks.setVenueDescription(CustomShareFieldsPage.getString("venueDescription", "This is a beautiful place!"));

        oks.setSilent(silent);
        oks.setShareFromQQAuthSupport(false);
        String theme = "classic";
        if(OnekeyShareTheme.SKYBLUE.toString().toLowerCase().equals(theme)){
            oks.setTheme(OnekeyShareTheme.SKYBLUE);
        }else{
            oks.setTheme(OnekeyShareTheme.CLASSIC);
        }

        if (platform != null) {
            oks.setPlatform(platform);
        }


        // 令编辑页面显示为Dialog模式
        oks.setDialogMode();

        // 在自动授权时可以禁用SSO方式
        //if(!CustomShareFieldsPage.getBoolean("enableSSO", true))
        oks.disableSSOWhenAuthorize();

        // 去除注释，则快捷分享的操作结果将通过OneKeyShareCallback回调
        //oks.setCallback(new OneKeyShareCallback());

        // 去自定义不同平台的字段内容
        //oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());

        // 去除注释，演示在九宫格设置自定义的图标
        Bitmap enableLogo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        Bitmap disableLogo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        String label = getResources().getString(R.string.app_name);
        OnClickListener listener = new OnClickListener() {
            public void onClick(View v) {
                String text = "Customer Logo -- ShareSDK " + ShareSDK.getSDKVersionName();
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
            }
        };
        //oks.setCustomerLogo(enableLogo, disableLogo, label, listener);

        // 去除注释，则快捷分享九宫格中将隐藏新浪微博和腾讯微博
//		oks.addHiddenPlatform(SinaWeibo.NAME);
//		oks.addHiddenPlatform(TencentWeibo.NAME);
        // 为EditPage设置一个背景的View
        oks.setEditPageBackground(view);
        oks.show(context);
    }



    public void onComplete(Platform plat, int action,
                           HashMap<String, Object> res) {

        Message msg = new Message();
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    public void onCancel(Platform palt, int action) {
        Message msg = new Message();
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = palt;
        UIHandler.sendMessage(msg, this);
    }

    public void onError(Platform palt, int action, Throwable t) {
        t.printStackTrace();

        Message msg = new Message();
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = palt;
        UIHandler.sendMessage(msg, this);
    }

    public boolean handleMessage(Message msg) {
        Platform plat = (Platform) msg.obj;
        String text = actionToString(msg.arg2);
        switch (msg.arg1) {
            case 1: {
                // 成功
                text = plat.getName() + " completed at " + text;
            }
            break;
            case 2: {
                // 失败
                text = plat.getName() + " caught error at " + text;
            }
            break;
            case 3: {
                // 取消
                text = plat.getName() + " canceled at " + text;
            }
            break;
        }

        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
        return false;
    }


    /** 将action转换为String */
    public static String actionToString(int action) {
        switch (action) {
            case Platform.ACTION_AUTHORIZING: return "ACTION_AUTHORIZING";
            case Platform.ACTION_GETTING_FRIEND_LIST: return "ACTION_GETTING_FRIEND_LIST";
            case Platform.ACTION_FOLLOWING_USER: return "ACTION_FOLLOWING_USER";
            case Platform.ACTION_SENDING_DIRECT_MESSAGE: return "ACTION_SENDING_DIRECT_MESSAGE";
            case Platform.ACTION_TIMELINE: return "ACTION_TIMELINE";
            case Platform.ACTION_USER_INFOR: return "ACTION_USER_INFOR";
            case Platform.ACTION_SHARE: return "ACTION_SHARE";
            default: {
                return "UNKNOWN";
            }
        }
    }

}
