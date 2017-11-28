package com.dongwukj.weiwei.net.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import com.dongwukj.weiwei.R;

/**
 * Created by pc on 2015/3/16.
 */
public class DeviceUtil {

    /*
   * 电话状态：
   * 1.tm.CALL_STATE_IDLE=0          无活动
   * 2.tm.CALL_STATE_RINGING=1  响铃
   * 3.tm.CALL_STATE_OFFHOOK=2  摘机
   */
    public static int getCallState(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getCallState();
    }
    /*
       * 电话方位：
       *
       */
    public static CellLocation getCellLocation(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getCellLocation();
    }
    /*
       * 唯一的设备ID：
       * GSM手机的 IMEI 和 CDMA手机的 MEID.
       * Return null if device ID is not available.
       */
    public static String getDeviceId(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
    /*
   * 设备的软件版本号：
   * 例如：the IMEI/SV(software version) for GSM phones.
   * Return null if the software version is not available.
   */
    public static String getDeviceSoftwareVersion(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceSoftwareVersion();
    }
    /*
   * 手机号：
   * GSM手机的 MSISDN.
   * Return null if it is unavailable.
   */
    public static String getLine1Number(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }
    /*
   * 当前使用的网络类型：
   * 例如： NETWORK_TYPE_UNKNOWN  网络类型未知  0
     NETWORK_TYPE_GPRS     GPRS网络  1
     NETWORK_TYPE_EDGE     EDGE网络  2
     NETWORK_TYPE_UMTS     UMTS网络  3
     NETWORK_TYPE_HSDPA    HSDPA网络  8
     NETWORK_TYPE_HSUPA    HSUPA网络  9
     NETWORK_TYPE_HSPA     HSPA网络  10
     NETWORK_TYPE_CDMA     CDMA网络,IS95A 或 IS95B.  4
     NETWORK_TYPE_EVDO_0   EVDO网络, revision 0.  5
     NETWORK_TYPE_EVDO_A   EVDO网络, revision A.  6
     NETWORK_TYPE_1xRTT    1xRTT网络  7
   */
    public static int getNetworkType(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getNetworkType();
    }
    /*
   * 手机类型：
   * 例如： PHONE_TYPE_NONE  无信号
     PHONE_TYPE_GSM   GSM信号
     PHONE_TYPE_CDMA  CDMA信号
   */
    public static int getPhoneType(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getPhoneType();
    }
    /*
   * 服务商名称：
   * 例如：中国移动、联通
   * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
   */
    public static String getSimOperatorName(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimOperatorName();
    }
    /*
   * SIM的状态信息：
   *  SIM_STATE_UNKNOWN          未知状态 0
   SIM_STATE_ABSENT           没插卡 1
   SIM_STATE_PIN_REQUIRED     锁定状态，需要用户的PIN码解锁 2
   SIM_STATE_PUK_REQUIRED     锁定状态，需要用户的PUK码解锁 3
   SIM_STATE_NETWORK_LOCKED   锁定状态，需要网络的PIN码解锁 4
   SIM_STATE_READY            就绪状态 5
   */
    public static int getSimState(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimState();
    }
    /*
   * 唯一的用户ID：
   * 例如：IMSI(国际移动用户识别码) for a GSM phone.
   * 需要权限：READ_PHONE_STATE
   */


    public static String getCurrentVersionName(Context context)//获取版本号
    {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "未知";
        }
    }

    public static int getCurrentVersionCode(Context context)//获取版本号(内部识别号)
    {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

}
