package com.dongwukj.weiwei.net.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import org.apache.http.conn.util.InetAddressUtils;

/**
 * 网络信息
 * 
 * @author litingchang
 * 
 */
public class NetworkUtil {
        
        private static final String TAG = "NetworkUtil";

        /**
         * 判断网络是否可用 <br>
         * code from: http://www.androidsnippets.com/have-internet
         * 
         * @param context
         * @return
         */
        public static boolean haveInternet(Context context) {
                NetworkInfo info = (NetworkInfo) ((ConnectivityManager) context
                                .getSystemService(Context.CONNECTIVITY_SERVICE))
                                .getActiveNetworkInfo();

                if (info == null || !info.isConnected()) {
                        return false;
                }
                if (info.isRoaming()) {
                        // here is the roaming option you can change it if you want to
                        // disable internet while roaming, just return false
                        // 是否在漫游，可根据程序需求更改返回值
                        return false;
                }
                return true;
        }
        
        /**
         * 判断网络是否可用
         * @param context
         * @return
         */
        public static boolean isnetWorkAvilable(Context context) {
                ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager == null) {
                        Log.e(TAG, "couldn't get connectivity manager");
                } else {
                        NetworkInfo [] networkInfos = connectivityManager.getAllNetworkInfo();
                        if(networkInfos != null){
                                for (int i = 0, count = networkInfos.length; i < count; i++) {
                                        if(networkInfos[i].getState() == NetworkInfo.State.CONNECTED){
                                                return true;
                                        }
                                }
                        }
                }
                return false;
        }

        /**
         * IP地址<br>
         * code from:
         * http://www.droidnova.com/get-the-ip-address-of-your-device,304.html <br>
         * 
         * @return 如果返回null，证明没有网络链接。 如果返回String，就是设备当前使用的IP地址，不管是WiFi还是3G
         */
        public static String getLocalIpAddress() {
                try {
                        for (Enumeration<NetworkInterface> en = NetworkInterface
                                        .getNetworkInterfaces(); en.hasMoreElements();) {
                                NetworkInterface intf = en.nextElement();
                                for (Enumeration<InetAddress> enumIpAddr = intf
                                                .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                                        InetAddress inetAddress = enumIpAddr.nextElement();

                                        if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {
                                                return inetAddress.getHostAddress().toString();
                                        }
                                }
                        }
                } catch (SocketException ex) {
                        Log.e("getLocalIpAddress", ex.toString());
                }
                return null;
        }

        /**
         * 获取MAC地址 <br>
         * code from: http://orgcent.com/android-wifi-mac-ip-address/
         * 
         * @param context
         * @return
         */
        public static String getLocalMacAddress(Context context) {
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifi.getConnectionInfo();
                return info.getMacAddress();
        }
        
        /**
         * WIFI 是否可用
         * @param context
         * @return
         */
        public static boolean isWiFiActive(Context context) {
                ConnectivityManager connectivity = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        if (connectivity != null) {  
            NetworkInfo[] info = connectivity.getAllNetworkInfo();  
            if (info != null) {  
                for (int i = 0; i < info.length; i++) {  
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {  
                        return true;  
                    }  
                }  
            }  
        }  
        return false;
        }
        
        /**
         * 存在多个连接点
         * @param context
         * @return
         */
        public static boolean hasMoreThanOneConnection(Context context){
                ConnectivityManager manager = (ConnectivityManager)context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                if(manager==null){
                        return false;
                }else{
                        NetworkInfo [] info = manager.getAllNetworkInfo();
                        int counter = 0;
                        for(int i = 0 ;i<info.length;i++){
                                if(info[i].isConnected()){
                                        counter++;
                                }
                        }
                        if(counter>1){
                                return true;
                        }
                }
                
                return false;
        }
        
        /*
     * HACKISH: These constants aren't yet available in my API level (7), but I need to handle these cases if they come up, on newer versions
     */
    public static final int NETWORK_TYPE_EHRPD=14; // Level 11
    public static final int NETWORK_TYPE_EVDO_B=12; // Level 9
    public static final int NETWORK_TYPE_HSPAP=15; // Level 13
    public static final int NETWORK_TYPE_IDEN=11; // Level 8
    public static final int NETWORK_TYPE_LTE=13; // Level 11


    public static double[] getGpsInfo(Context context){
        double [] locations=new double [2];
        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null){
                locations[0] = location.getLatitude();
                locations[1] = location.getLongitude();
            }
        }else{
            LocationListener locationListener = new LocationListener() {

                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {

                }

                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {

                }

                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        Log.e("Map", "Location changed : Lat: "
                                + location.getLatitude() + " Lng: "
                                + location.getLongitude());
                    }
                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000, 0,locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location != null){
                locations[0] = location.getLatitude(); //经度
                locations[1] = location.getLongitude(); //纬度
            }
        }
        return locations;
    }

    /**
     * 判断gps是否开启
     * @param context
     * @return
     */
    public static boolean isGpsOpened(Context context){
        LocationManager lm=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Check if there is any connectivity
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
    /**
     * Check if there is fast connectivity
     * @param context
     * @return
     */
    public static boolean isConnectedFast(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && isConnectionFast(info.getType(),info.getSubtype()));
    }

    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    /**
     * Check if the connection is fast
     * @param type
     * @param subType
     * @return
     */
    public static boolean isConnectionFast(int type, int subType){
        if(type==ConnectivityManager.TYPE_WIFI){
            System.out.println("CONNECTED VIA WIFI");
            return true;
        }else if(type==ConnectivityManager.TYPE_MOBILE){
            switch(subType){
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            // NOT AVAILABLE YET IN API LEVEL 7
            case NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case NETWORK_TYPE_IDEN:
                return false; // ~25 kbps 
            case NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            // Unknown
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false; 
            default:
                return false;
            }
        }else{
            return false;
        }
    }
        
        /**
         * IP转整型
         * @param ip
         * @return
         */
        public static long ip2int(String ip) {
            String[] items = ip.split("\\.");
            return Long.valueOf(items[0]) << 24
                    | Long.valueOf(items[1]) << 16
                    | Long.valueOf(items[2]) << 8 | Long.valueOf(items[3]);
        }
        
        /**
         * 整型转IP
         * @param ipInt
         * @return
         */
        public static String int2ip(long ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
        }



    public static boolean checkUrl(String url){
        if(TextUtils.isEmpty(url)){
            return true;
        }
        String http="http";
        return url.toLowerCase().startsWith(http);
    }
}
