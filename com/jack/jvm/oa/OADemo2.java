package com.jack.jvm.oa;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * URL类加载器加载外部的jar,没有生效，还是使用out下的class文件中的类
 */
public class OADemo2 {
    public static void main(String[] args) throws Exception {
        Double salary = 15000.00;
        Double money = 0.00;

        // 加载URL中Jar
        URL jarPath = new URL("file:E:/05_github/01_JVM/out/artifacts/01_JVM_jar/01_JVM.jar");
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {jarPath});
        int i = 0;
        //模拟不停机状态
        while (true) {
            try {
                money = calSalary(salary,urlClassLoader);
                System.out.println(i+"实际到手Money:" + money);
            }catch(Exception e) {
                e.printStackTrace();
                System.out.println("加载出现异常 ："+e.getMessage());
            }
            Thread.sleep(5000);
            i++;
        }
    }

    private static Double calSalary(Double salary,ClassLoader classloader) throws Exception {
        Class<?> clazz = classloader.loadClass("com.jack.jvm.oa.SalaryCaler");
        if(null != clazz) {
            Object object = clazz.newInstance();
            return (Double)clazz.getMethod("cal", Double.class).invoke(object, salary);
        }
        return -1.00;
    }
}
