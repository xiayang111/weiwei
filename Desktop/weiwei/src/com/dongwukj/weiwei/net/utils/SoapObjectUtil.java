package com.dongwukj.weiwei.net.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

/** 
 * soapObject->javaBeanת������ 
 * @author zjf 
 * 
 */  
public class SoapObjectUtil {  
	/**
     * soapת��Ϊjavabean
     * javaBean��ֻ֧��int,String,Long,Double����
     * @param <T>
     * @param clazz
     * @param soapObject
     * @return
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     */
    public static <T> T soapToPojo(Class<T> clazz, SoapObject soapObject) throws IllegalArgumentException,
           SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
           InstantiationException {
 
       Field[] fields = clazz.getDeclaredFields();
       Object obj = clazz.newInstance();
       for (Field f : fields) {
           String method = "set" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
           if (hasMethod(method, clazz.getMethods())) {
              if (f.getType().equals(Long.class)) {
                  clazz.getMethod(method, new Class[] { f.getType() }).invoke(obj,
                         Long.parseLong(soapObject.getProperty(f.getName()).toString()));
              } else if (f.getType().equals(Integer.class)) {
                  clazz.getMethod(method, new Class[] { f.getType() }).invoke(obj,
                         Integer.parseInt(soapObject.getProperty(f.getName()).toString()));
              } else if (f.getType().equals(Double.class)) {
                  clazz.getMethod(method, new Class[] { f.getType() }).invoke(obj,
                         Double.parseDouble(soapObject.getProperty(f.getName()).toString()));
              }else {
                  clazz.getMethod(method, new Class[] { f.getType() }).invoke(obj,
                         soapObject.getProperty(f.getName()).toString());
              }
           }
       }
       return (T) obj;
    }
 
    private static boolean hasMethod(String methodName, Method[] method) {
       for (Method m : method) {
           if (methodName.equals(m.getName())) {
              return true;
           }
       }
       return false;
    }
    /**
     *
     * ��SoapObjectת����List
     *
     * @param detail
     * @param clazz
     * @return List<T>
     * @throws Exception
     */
    public static <T> List<T> soapToList(SoapObject detail, Class<T> clazz) throws Exception {
       ArrayList result = new ArrayList();
       for (int i = 0; i < detail.getPropertyCount(); i++) {
           result.add(soapToPojo(clazz, (SoapObject) detail.getProperty(i)));
       }
       return (List<T>) result;
    }

}  