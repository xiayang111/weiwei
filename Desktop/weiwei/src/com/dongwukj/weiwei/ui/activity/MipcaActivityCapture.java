package com.dongwukj.weiwei.ui.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.dongwukj.weiwei.BaseApplication;
import com.dongwukj.weiwei.R;
import com.dongwukj.weiwei.idea.enums.HeaderActivityType;
import com.dongwukj.weiwei.idea.request.PhoneGetProductTraceRequest;
import com.dongwukj.weiwei.idea.request.PhoneGetSupplyIdByDeviceNoRequest;
import com.dongwukj.weiwei.idea.result.BaseResult;
import com.dongwukj.weiwei.idea.result.PhoneGetProductTraceResult;
import com.dongwukj.weiwei.idea.result.PhoneGetSupplyIdByDeviceNoResult;
import com.dongwukj.weiwei.net.BaseRequestClient;
import com.dongwukj.weiwei.zxing.camera.CameraManager;
import com.dongwukj.weiwei.zxing.decoding.CaptureActivityHandler;
import com.dongwukj.weiwei.zxing.decoding.InactivityTimer;
import com.dongwukj.weiwei.zxing.decoding.RGBLuminanceSource;
import com.dongwukj.weiwei.zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Vector;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

/**
 * Initial the camera
 * 
 * @author Ryan.Tang
 */
public class MipcaActivityCapture extends Activity implements Callback {
	Result result;
	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private Intent openAlbumIntent;
	private int OpenPhoto = 100;
	private static String baseUrl="http://58.221.61.105:8090/trade/TradeCdOIdCamera?cameraId=";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		RadioGroup main_radio = (RadioGroup) findViewById(R.id.main_radio);
		((TextView) findViewById(R.id.textview_title)).setText(getIntent().getStringExtra("title"));
		main_radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rd_from_photo:

					openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
					openAlbumIntent.addCategory(Intent.CATEGORY_OPENABLE);
					openAlbumIntent.setType("image/*");
					startActivityForResult(openAlbumIntent, OpenPhoto);
					RadioButton at0 = (RadioButton) group.getChildAt(0);
					at0.setChecked(false);
					break;
				case R.id.rd_scan:
					Toast.makeText(getApplicationContext(), "扫码", 1).show();
					RadioButton at1 = (RadioButton) group.getChildAt(1);
					at1.setChecked(false);
					break;
				case R.id.rd_order:
					Toast.makeText(getApplicationContext(), "订单签收", 1).show();
					RadioButton at2 = (RadioButton) group.getChildAt(2);
					at2.setChecked(false);
					break;
				case R.id.rd_flashlight:
					Toast.makeText(getApplicationContext(), "闪光灯", 1).show();
					RadioButton at3 = (RadioButton) group.getChildAt(3);
					at3.setChecked(false);
					break;

				default:
					break;
				}

			}
		});
		baseApplication = (BaseApplication) getApplication();
		LinearLayout mButtonBack = (LinearLayout) findViewById(R.id.ll_left);
		mButtonBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MipcaActivityCapture.this.finish();

			}
		});
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;
		
		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	/**
	 * ����ɨ����
	 * 
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if (resultString.equals("")) {
			Toast.makeText(MipcaActivityCapture.this, "扫码失败请重试！",
					Toast.LENGTH_SHORT).show();
		} else {
			if (resultString.contains("osn=")) {
				  Intent intent=new Intent(this,HomeHeaderActivity.class);
				  intent.putExtra("type",HeaderActivityType.PayOrder.ordinal());
				  intent.putExtra("OSN",  resultString.split("osn=")[1]);
				  intent.putExtra("IsUnline", true);
				  startActivity(intent);
			}else if(resultString.contains("tracecode=")){
				Intent intent=new Intent(getApplicationContext(), HomeHeaderActivity.class);
				intent.putExtra("type", HeaderActivityType.SourceFragment.ordinal());
				intent.putExtra("result", resultString.split("tracecode=")[1]);
				startActivity(intent);
			}else {
				Toast.makeText(MipcaActivityCapture.this, "请扫描正确的商品码！",
						Toast.LENGTH_SHORT).show();
			}
			
			
		}
		MipcaActivityCapture.this.finish();
	}
	


	
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			// handler = new CaptureActivityHandler(this,
			// decodeFormats,characterSet);
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};
	private BaseApplication baseApplication;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {

			if (data != null) {
				Uri bitmapUri = data.getData();

				if (bitmapUri != null) {
					ContentResolver contentResolver = this.getContentResolver();
					Bitmap bitmap;
					try {
						bitmap = BitmapFactory.decodeStream(contentResolver
								.openInputStream(bitmapUri));
						if (bitmap != null) {

							Result result2 = decodeQRCode(bitmap);
							if (result2!=null) {
								Toast.makeText(getApplicationContext(), result2.getText(), 1).show();
							}else {
								Toast.makeText(getApplicationContext(), "请选择正确的图片", 1).show();
							}
						}
					} catch (FileNotFoundException e) {

						e.printStackTrace();
					}

				}
			}

		}
	};

	public Result decodeQRCode(Bitmap bitmap) {
		Hashtable<DecodeHintType, String> tab = new Hashtable<DecodeHintType, String>();
		tab.put(DecodeHintType.CHARACTER_SET, "utf-8");

		RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
		// 转成二进制图片
		BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
		// 实例化二维码解码对象
		QRCodeReader reader = new QRCodeReader();
		try {
			// 根据解码类型解码，返回解码结果
			result = reader.decode(bitmap1, tab);
			// 显示解码结果
			// qr_result.setText(result.getText());
		} catch (Exception e) {
			result=null;
			e.printStackTrace();
		}
		return result;

	}
}