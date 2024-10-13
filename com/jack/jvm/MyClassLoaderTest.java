package com.jack.jvm;

import java.lang.reflect.Method;

/**
 * 自定义类加载器
 */
public class MyClassLoaderTest{

    public static void main(String[] args) throws Exception {
        // 默認加載 D:/test/com/jack/jvm/User1
        MyClassLoader classLoader = new MyClassLoader("D:/test");
        Class<?> aClass = classLoader.loadClass("com.jack.jvm.User1");
        Object obj = aClass.newInstance();
        Method sout = aClass.getDeclaredMethod("sout", null);
        Object invoke = sout.invoke(obj, null);
        System.out.println(aClass.getClassLoader().getClass().getName());
        System.out.println(aClass.getClassLoader().getParent().getClass().getName());
    }
}
