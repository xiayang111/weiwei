package com.dongwukj.weiwei.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;



public class ICEMessagebox {
	/**
	 * 消息提示框
	 * @param hContext 上下文指针
	 * @param tTitle 提示框标题
	 * @param tMessage 提示框内容
	 * @param tOK  提示框确认按钮文字
	 * @return 返回对话框对象
	 */
	public static Dialog showMessagebox(Context hContext,String tTitle,String tMessage,String tOK) {
		Dialog dgAlert = new AlertDialog.Builder(hContext). 
                setTitle(tTitle). 
                setCancelable(false).
                setMessage(tMessage).
                setPositiveButton(tOK, new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {						
					}
				}).create(); 
		dgAlert.show();
		return dgAlert;
	}
	
	/**
	 * 消息提示框
	 * @param hContext 上下文指针
	 * @param tMessage 提示框内容
	 * @param tOK  提示框确认按钮文字
	 * @return 返回对话框对象
	 */
	public static Dialog showMessagebox(Context hContext,String tMessage,String tOK) {
		Dialog dgAlert = new AlertDialog.Builder(hContext). 
                setMessage(tMessage).
                setCancelable(false).
                setPositiveButton(tOK, new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {						
					}
				}).create(); 
		dgAlert.show();
		return dgAlert;
	}
	
	/**
	 * 消息提示框
	 * @param hContext 上下文指针
	 * @param tTitle 提示框标题
	 * @param tMessage 提示框内容
	 * @param tOK  提示框确认按钮文字
	 * @param l  确认后执行的命令
	 * @return 返回对话框对象
	 */
	public static Dialog showMessagebox(Context hContext,String tTitle,String tMessage,String tOK,DialogInterface.OnClickListener l){
		Dialog dgAlert = new AlertDialog.Builder(hContext). 
                setTitle(tTitle). 
                setCancelable(false).
                setMessage(tMessage).
                setPositiveButton(tOK, l).create(); 
		dgAlert.show();
		return dgAlert;
	}
	
	/**
	 * 消息提示框
	 * @param hContext 上下文指针
	 * @param tTitle 消息提示框标题
	 * @param tMessage 提示框内容
	 * @param tOK  提示框确认按钮文字
	 * @param l  确认后执行的命令
	 * @param tCancel 提示框取消的文字
	 * @return 返回对话框对象
	 */
	public static Dialog showMessagebox(Context hContext,String tTitle,String tMessage,String tOK,DialogInterface.OnClickListener l,String tCancel) {
		Dialog dgAlert = new AlertDialog.Builder(hContext). 
				setTitle(tTitle). 
                setMessage(tMessage).
                setCancelable(false).
                setPositiveButton(tOK, l).
                setNegativeButton(tCancel, new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).create(); 		
		dgAlert.show();
		return dgAlert;
	}
	
	/**
	 * 消息提示框
	 * @param hContext 上下文指针
	 * @param tMessage 提示框内容
	 * @param tOK  提示框确认按钮文字
	 * @param l  确认后执行的命令
	 * @param tCancel 提示框取消的文字
	 * @return 返回对话框对象
	 */
	public static Dialog showMessagebox(Context hContext,String tMessage,String tOK,DialogInterface.OnClickListener l,String tCancel) {
		Dialog dgAlert = new AlertDialog.Builder(hContext). 
                setMessage(tMessage).
                setCancelable(false).
                setPositiveButton(tOK, l).
                setNegativeButton(tCancel, new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).create(); 		
		dgAlert.show();
		return dgAlert;
	}
	
	/**
	 * 消息提示框
	 * @param hContext 上下文指针
	 * @param tMessage 提示框内容
	 * @param tOK  提示框确认按钮文字
	 * @param l  确认后执行的命令
	 * @return  返回对话框对象
	 */
	public static Dialog showMessagebox(Context hContext,String tMessage,String tOK,DialogInterface.OnClickListener l) {
		Dialog dgAlert = new AlertDialog.Builder(hContext). 
                setMessage(tMessage).
                setCancelable(false).                
                setPositiveButton(tOK, l).create(); 
		dgAlert.show();			
		return dgAlert;
	}
	
	/**
	 * 消息提示框
	 * @param hContext 上下文指针
	 * @param tTitle 提示框内容
	 * @param etText 自定义输入内容
	 * @param tOK  提示框确认按钮文字
	 * @param l  确认后执行的命令
	 * @return 返回对话框对象
	 */
	public static Dialog showInputBox(Context hContext,String tTitle,EditText etText,String tOK,DialogInterface.OnClickListener l) {
		Dialog dgAlert = new AlertDialog.Builder(hContext).                          
                setMessage(tTitle).
                setView(etText).
                setPositiveButton(tOK, l).create();		
		dgAlert.show();
		return dgAlert;
	}
	
	/**
	 * 消息提示框
	 * @param hContext 上下文指针
	 * @param tTitle 提示框内容
	 * @param vView 对话框界面对象
	 * @return 返回对话框对象
	 */
	public static Dialog showViewBox(Context hContext,String tTitle,View vView) {
		Dialog dgAlert = new AlertDialog.Builder(hContext). 
				setMessage(tTitle).   
                setCancelable(false).
                setView(vView).create(); 
		try {
			dgAlert.show();	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return dgAlert;
	}
	
	/**
	 * 消息提示框
	 * @param hContext 上下文指针
	 * @param tTitle 提示框内容
	 * @param vView 对话框界面对象
	 * @param lp 对话框的布局设置
	 * @return 返回对话框对象
	 */
	public static Dialog showViewBox(Context hContext,String tTitle,View vView,int height) {
		Dialog dgAlert = new AlertDialog.Builder(hContext). 
				setMessage(tTitle).   
                setCancelable(false).
                setView(vView).create(); 
		Window dialogWindow = dgAlert.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		height= 200;
		lp.height = height;
		dgAlert.getWindow().setAttributes(lp);
		dgAlert.show();	
		return dgAlert;
	}
}